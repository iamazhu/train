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

    /*�������Ҫ��ѯ������: ����*/
    private String trainNumber;
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getTrainNumber() {
        return this.trainNumber;
    }

    /*�������Ҫ��ѯ������: ʼ��վ*/
    private StationInfo startStation;
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }
    public StationInfo getStartStation() {
        return this.startStation;
    }

    /*�������Ҫ��ѯ������: �յ�վ*/
    private StationInfo endStation;
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }
    public StationInfo getEndStation() {
        return this.endStation;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*�������Ҫ��ѯ������: ϯ��*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StationInfoDAO stationInfoDAO;
 
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource TrainInfoDAO trainInfoDAO;

    /*��������TrainInfo����*/
    private TrainInfo trainInfo;
    public void setTrainInfo(TrainInfo trainInfo) {
        this.trainInfo = trainInfo;
    }
    public TrainInfo getTrainInfo() {
        return this.trainInfo;
    }

    /*��ת�����TrainInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�StationInfo��Ϣ*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        /*��ѯ���е�SeatType��Ϣ*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*���TrainInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTrainInfo��Ϣ*/
    public String QueryTrainInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        trainInfoDAO.CalculateTotalPageAndRecordNumber(trainNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = trainInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryTrainInfoOutputToExcel() { 
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TrainInfo��Ϣ��¼"; 
        String[] headers = { "����","ʼ��վ","�յ�վ","��������","ϯ��","Ʊ��","����λ��","ʣ����λ��","����ʱ��","�յ�ʱ��","��ʱ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TrainInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯTrainInfo��Ϣ*/
    public String FrontQueryTrainInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfoInfo(trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        trainInfoDAO.CalculateTotalPageAndRecordNumber(trainNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = trainInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�TrainInfo��Ϣ*/
    public String ModifyTrainInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡTrainInfo����*/
        TrainInfo trainInfo = trainInfoDAO.GetTrainInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
 
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("trainInfo",  trainInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�TrainInfo��Ϣ*/
    public String FrontShowTrainInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡTrainInfo����*/
        TrainInfo trainInfo = trainInfoDAO.GetTrainInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("trainInfo",  trainInfo);
        return "front_show_view";
    }

    /*�����޸�TrainInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��TrainInfo��Ϣ*/
    public String DeleteTrainInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            trainInfoDAO.DeleteTrainInfo(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("TrainInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TrainInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
