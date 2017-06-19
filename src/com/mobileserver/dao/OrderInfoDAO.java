package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.domain.TrainInfo;
import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	private TrainInfoDAO trainInfoDAO = new TrainInfoDAO();
	private UserInfoDAO userInfoDAO = new UserInfoDAO();
	private SeatTypeDAO seatTypeDAO = new SeatTypeDAO();
	
	public List<OrderInfo> QueryOrderInfo(String userObj,String trainNumber,int startStation,int endStation,Timestamp startDate,int seatType) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!trainNumber.equals(""))
			sql += " and trainNumber like '%" + trainNumber + "%'";
		if (startStation != 0)
			sql += " and startStation=" + startStation;
		if (endStation != 0)
			sql += " and endStation=" + endStation;
		if(startDate!=null)
			sql += " and startDate='" + startDate + "'";
		if (seatType != 0)
			sql += " and seatType=" + seatType;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setTrainObj(rs.getInt("trainObj"));
				orderInfo.setTrainNumber(rs.getString("trainNumber"));
				orderInfo.setStartStation(rs.getInt("startStation"));
				orderInfo.setEndStation(rs.getInt("endStation"));
				orderInfo.setStartDate(rs.getTimestamp("startDate"));
				orderInfo.setSeatType(rs.getInt("seatType"));
				orderInfo.setSeatInfo(rs.getString("seatInfo"));
				orderInfo.setTotalPrice(rs.getFloat("totalPrice"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* 传入订单信息对象，进行订单信息的添加业务 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新订单信息 */
			String sqlString = "insert into OrderInfo(userObj,trainObj,trainNumber,startStation,endStation,startDate,seatType,seatInfo,totalPrice,startTime,endTime) values (";
			sqlString += "'" + orderInfo.getUserObj() + "',";
			sqlString += orderInfo.getTrainObj() + ",";
			sqlString += "'" + orderInfo.getTrainNumber() + "',";
			sqlString += orderInfo.getStartStation() + ",";
			sqlString += orderInfo.getEndStation() + ",";
			sqlString += "'" + orderInfo.getStartDate() + "',";
			sqlString += orderInfo.getSeatType() + ",";
			sqlString += "'" + orderInfo.getSeatInfo() + "',";
			sqlString += orderInfo.getTotalPrice() + ",";
			sqlString += "'" + orderInfo.getStartTime() + "',";
			sqlString += "'" + orderInfo.getEndTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "订单信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	
	/*用户提交购票订单*/
	public String UserSubmitTicket(String username, int seatId, int ticketNum) {
		synchronized(this){
			TrainInfo trainInfo = trainInfoDAO.GetTrainInfo(seatId);
			if(ticketNum>trainInfo.getLeftSeatNumber()) {
				return "余票不足!";
			}
			UserInfo userInfo = userInfoDAO.GetUserInfo(username);
			float totalPrice =  trainInfo.getPrice()*ticketNum;
			if(userInfo.getMoney()<totalPrice) {
				return "账户金额不足，请联系管理员充值！";
			}
			
			//通过验证，这里需要存档用户订单信息，更新用户余额，更新车次余票信息，应当在一个事务中执行
			String[] sqls = new String[3];  
			sqls[0] = "insert into OrderInfo(userObj,trainObj,trainNumber,startStation,endStation,startDate,seatType,seatInfo,totalPrice,startTime,endTime) values (";
			sqls[0] += "'" + username + "',";
			sqls[0] += seatId + ",";
			sqls[0] += "'" + trainInfo.getTrainNumber() + "',";
			sqls[0] += trainInfo.getStartStation() + ",";
			sqls[0] += trainInfo.getEndStation() + ",";
			sqls[0] += "'" + trainInfo.getStartDate() + "',";
			sqls[0] += trainInfo.getSeatType() + ","; 
			//计算座位编号：
			String seatInfo = seatTypeDAO.GetSeatType(trainInfo.getSeatType()).getSeatTypeName();
			seatInfo += ticketNum + "张:";
			for(int i=1;i<=ticketNum;i++) {
				seatInfo += (trainInfo.getSeatNumber()-trainInfo.getLeftSeatNumber()+i) + "号  ";
			}
			sqls[0] += "'" + seatInfo + "',";
			sqls[0] += totalPrice + ",";
			sqls[0] += "'" + trainInfo.getStartTime() + "',";
			sqls[0] += "'" + trainInfo.getEndTime() + "'";
			sqls[0] += ")";
			sqls[1] = "update userInfo set money=money-" + totalPrice + " where user_name='" + username + "'";
			sqls[2] = "update trainInfo set leftSeatNumber=leftSeatNumber-" + ticketNum + " where seatId=" + seatId;
			DB db = new DB();
			db.excuteSqlStrings(sqls); 
			return "购买成功！";
		} 
	}
	
	
	
	
	
	/* 删除订单信息 */
	public String DeleteOrderInfo(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "订单信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到订单信息 */
	public OrderInfo GetOrderInfo(int orderId) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setTrainObj(rs.getInt("trainObj"));
				orderInfo.setTrainNumber(rs.getString("trainNumber"));
				orderInfo.setStartStation(rs.getInt("startStation"));
				orderInfo.setEndStation(rs.getInt("endStation"));
				orderInfo.setStartDate(rs.getTimestamp("startDate"));
				orderInfo.setSeatType(rs.getInt("seatType"));
				orderInfo.setSeatInfo(rs.getString("seatInfo"));
				orderInfo.setTotalPrice(rs.getFloat("totalPrice"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* 更新订单信息 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "userObj='" + orderInfo.getUserObj() + "',";
			sql += "trainObj=" + orderInfo.getTrainObj() + ",";
			sql += "trainNumber='" + orderInfo.getTrainNumber() + "',";
			sql += "startStation=" + orderInfo.getStartStation() + ",";
			sql += "endStation=" + orderInfo.getEndStation() + ",";
			sql += "startDate='" + orderInfo.getStartDate() + "',";
			sql += "seatType=" + orderInfo.getSeatType() + ",";
			sql += "seatInfo='" + orderInfo.getSeatInfo() + "',";
			sql += "totalPrice=" + orderInfo.getTotalPrice() + ",";
			sql += "startTime='" + orderInfo.getStartTime() + "',";
			sql += "endTime='" + orderInfo.getEndTime() + "'";
			sql += " where orderId=" + orderInfo.getOrderId();
			db.executeUpdate(sql);
			result = "订单信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	
}
