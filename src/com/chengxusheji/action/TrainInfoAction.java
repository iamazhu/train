package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.TrainInfoDAO;
import com.chengxusheji.domain.TrainInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.SeatTypeDAO;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TrainInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 车次*/
    private String trainNumber;
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getTrainNumber() {
        return this.trainNumber;
    }

    /*界面层需要查询的属性: 始发站*/
    private StationInfo startStation;
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }
    public StationInfo getStartStation() {
        return this.startStation;
    }

    /*界面层需要查询的属性: 终到站*/
    private StationInfo endStation;
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }
    public StationInfo getEndStation() {
        return this.endStation;
    }

    /*界面层需要查询的属性: 开车日期*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*界面层需要查询的属性: 席别*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int seatId;
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return seatId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource StationInfoDAO stationInfoDAO;
 
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource TrainInfoDAO trainInfoDAO;

    /*待操作的TrainInfo对象*/
    private TrainInfo trainInfo;
    public void setTrainInfo(TrainInfo trainInfo) {
        this.trainInfo = trainInfo;
    }
    public TrainInfo getTrainInfo() {
        return this.trainInfo;
    }

    /*跳转到添加TrainInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的StationInfo信息*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        /*查询所有的SeatType信息*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*添加TrainInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddTrainInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(trainInfo.getStartStation().getStationId());
            trainInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(trainInfo.getEndStation().getStationId());
            trainInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(trainInfo.getSeatType().getSeatTypeId());
            trainInfo.setSeatType(seatType);
            }
            trainInfoDAO.AddTrainInfo(trainInfo);
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfo添加失败!"));
            return "error";
        }
    }

    /*查询TrainInfo信息*/
    public String QueryTrainInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        trainInfoDAO.CalculateTotalPageAndRecordNumber(trainNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = trainInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = trainInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("trainInfoList",  trainInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("trainNumber", trainNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryTrainInfoOutputToExcel() { 
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TrainInfo信息记录"; 
        String[] headers = { "车次","始发站","终到站","开车日期","席别","票价","总座位数","剩余座位数","开车时间","终到时间","历时"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<trainInfoList.size();i++) {
        	TrainInfo trainInfo = trainInfoList.get(i); 
        	dataset.add(new String[]{trainInfo.getTrainNumber(),trainInfo.getStartStation().getStationName(),
			trainInfo.getEndStation().getStationName(),
			new SimpleDateFormat("yyyy-MM-dd").format(trainInfo.getStartDate()),trainInfo.getSeatType().getSeatTypeName(),
			trainInfo.getPrice() + "",trainInfo.getSeatNumber() + "",trainInfo.getLeftSeatNumber() + "",trainInfo.getStartTime(),trainInfo.getEndTime(),trainInfo.getTotalTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TrainInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询TrainInfo信息*/
    public String FrontQueryTrainInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        trainInfoDAO.CalculateTotalPageAndRecordNumber(trainNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = trainInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = trainInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("trainInfoList",  trainInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("trainNumber", trainNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "front_query_view";
    }

    /*查询要修改的TrainInfo信息*/
    public String ModifyTrainInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取TrainInfo对象*/
        TrainInfo trainInfo = trainInfoDAO.GetTrainInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
 
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("trainInfo",  trainInfo);
        return "modify_view";
    }

    /*查询要修改的TrainInfo信息*/
    public String FrontShowTrainInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取TrainInfo对象*/
        TrainInfo trainInfo = trainInfoDAO.GetTrainInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("trainInfo",  trainInfo);
        return "front_show_view";
    }

    /*更新修改TrainInfo信息*/
    public String ModifyTrainInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(trainInfo.getStartStation().getStationId());
            trainInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(trainInfo.getEndStation().getStationId());
            trainInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(trainInfo.getSeatType().getSeatTypeId());
            trainInfo.setSeatType(seatType);
            }
            trainInfoDAO.UpdateTrainInfo(trainInfo);
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除TrainInfo信息*/
    public String DeleteTrainInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            trainInfoDAO.DeleteTrainInfo(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfo删除失败!"));
            return "error";
        }
    }

}
