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

import com.mobileserver.util.DB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.dao.TrainInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.domain.TrainInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.SeatTypeDAO;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource TrainInfoDAO trainInfoDAO;
    @Resource StationInfoDAO stationInfoDAO; 
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*待操作的OrderInfo对象*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }
    
    /*用户预订票数*/
    public int ticketNum; 
    public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	
	/*跳转到添加OrderInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的TrainInfo信息*/
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        /*查询所有的StationInfo信息*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
         
        /*查询所有的SeatType信息*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*添加OrderInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            }
            if(true) {
            TrainInfo trainObj = trainInfoDAO.GetTrainInfoBySeatId(orderInfo.getTrainObj().getSeatId());
            orderInfo.setTrainObj(trainObj);
            }
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getStartStation().getStationId());
            orderInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getEndStation().getStationId());
            orderInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(orderInfo.getSeatType().getSeatTypeId());
            orderInfo.setSeatType(seatType);
            }
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo添加失败!"));
            return "error";
        }
    }
    
    
    /*用户预订车票*/
    public String UserSubmitTicket() {
    	ActionContext ctx = ActionContext.getContext(); 
    	String userName = (String)ctx.getSession().get("user_name");
    	if(userName == null) {
    		ctx.put("error",  java.net.URLEncoder.encode("请先登录！"));
            return "error";
    	}
    	synchronized(this){
    		TrainInfo trainInfo = trainInfoDAO.GetTrainInfoBySeatId(orderInfo.getTrainObj().getSeatId());
    		if(ticketNum > trainInfo.getLeftSeatNumber()) {
    			ctx.put("error",  java.net.URLEncoder.encode("余票不足！"));
                return "error";
    		}
    		
    		UserInfo userInfo = userInfoDAO.GetUserInfoByUser_name(userName);
    		float totalPrice = trainInfo.getPrice() * ticketNum;
    		if(userInfo.getMoney() < totalPrice) {
    			ctx.put("error",  java.net.URLEncoder.encode("账户金额不足，请联系管理员充值！"));
                return "error";
    		}
    		
    		orderInfo.setUserObj(userInfo);
    		orderInfo.setTrainObj(trainInfo);
    		orderInfo.setTrainNumber(trainInfo.getTrainNumber());
    		orderInfo.setStartStation(trainInfo.getStartStation());
    		orderInfo.setEndStation(trainInfo.getEndStation());
    		orderInfo.setStartDate(trainInfo.getStartDate());
    		orderInfo.setSeatType(trainInfo.getSeatType());
    		//计算座位编号：
			String seatInfo = seatTypeDAO.GetSeatTypeBySeatTypeId(trainInfo.getSeatType().getSeatTypeId()).getSeatTypeName();
			seatInfo += ticketNum + "张:";
			for(int i=1;i<=ticketNum;i++) {
				seatInfo += (trainInfo.getSeatNumber()-trainInfo.getLeftSeatNumber()+i) + "号  ";
			}
			orderInfo.setSeatInfo(seatInfo);
			orderInfo.setTotalPrice(totalPrice);
			orderInfo.setStartTime(trainInfo.getStartTime());
			orderInfo.setEndTime(trainInfo.getEndTime());
			
			try {
				trainInfo.setLeftSeatNumber(trainInfo.getLeftSeatNumber()-ticketNum);
				trainInfoDAO.UpdateTrainInfo(trainInfo);
				UserInfo userObj = orderInfo.getUserObj();
				userObj.setMoney(userObj.getMoney()-orderInfo.getTotalPrice());
				orderInfoDAO.AddOrderInfo(orderInfo,trainInfo,userObj);  
				
				ctx.put("message",  java.net.URLEncoder.encode("车票预订成功!"));
		        return "add_success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ctx.put("error",  java.net.URLEncoder.encode("车票预订失败！"));
                return "error";
			} 
		}  
    	
    }

    /*查询OrderInfo信息*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj, trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(userObj, trainNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
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
    
    
    /*查询OrderInfo信息*/
    public String QueryMyOrderInfo() {
    	ActionContext ctx = ActionContext.getContext();
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        
        String userName = (String)ctx.getSession().get("user_name");
        UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(userName);
        
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj, trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(userObj, trainNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
       
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("trainNumber", trainNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
       
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "user_query_view";
    }

    /*后台导出到excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj,trainNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo信息记录"; 
        String[] headers = { "用户","车次","始发站","终到站","开车日期","席别","座位信息","总票价","开车时间","终到时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getUserObj().getRealName(),
orderInfo.getTrainNumber(),orderInfo.getStartStation().getStationName(),
orderInfo.getEndStation().getStationName(),
new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getStartDate()),orderInfo.getSeatType().getSeatTypeName(),
orderInfo.getSeatInfo(),orderInfo.getTotalPrice() + "",orderInfo.getStartTime(),orderInfo.getEndTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询OrderInfo信息*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj, trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(userObj, trainNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
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

    /*查询要修改的OrderInfo信息*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
      
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
     
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*更新修改OrderInfo信息*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            }
            if(true) {
            TrainInfo trainObj = trainInfoDAO.GetTrainInfoBySeatId(orderInfo.getTrainObj().getSeatId());
            orderInfo.setTrainObj(trainObj);
            }
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getStartStation().getStationId());
            orderInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getEndStation().getStationId());
            orderInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(orderInfo.getSeatType().getSeatTypeId());
            orderInfo.setSeatType(seatType);
            }
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderInfo信息*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo删除失败!"));
            return "error";
        }
    }

}
