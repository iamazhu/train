package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.TrainInfo;
import com.mobileserver.util.DB;

public class TrainInfoDAO {

	public List<TrainInfo> QueryTrainInfo(String trainNumber,int startStation,int endStation,Timestamp startDate,int seatType) {
		List<TrainInfo> trainInfoList = new ArrayList<TrainInfo>();
		DB db = new DB();
		String sql = "select * from TrainInfo where 1=1";
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
				TrainInfo trainInfo = new TrainInfo();
				trainInfo.setSeatId(rs.getInt("seatId"));
				trainInfo.setTrainNumber(rs.getString("trainNumber"));
				trainInfo.setStartStation(rs.getInt("startStation"));
				trainInfo.setEndStation(rs.getInt("endStation"));
				trainInfo.setStartDate(rs.getTimestamp("startDate"));
				trainInfo.setSeatType(rs.getInt("seatType"));
				trainInfo.setPrice(rs.getFloat("price"));
				trainInfo.setSeatNumber(rs.getInt("seatNumber"));
				trainInfo.setLeftSeatNumber(rs.getInt("leftSeatNumber"));
				trainInfo.setStartTime(rs.getString("startTime"));
				trainInfo.setEndTime(rs.getString("endTime"));
				trainInfo.setTotalTime(rs.getString("totalTime"));
				trainInfoList.add(trainInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return trainInfoList;
	}
	/* 传入车次信息对象，进行车次信息的添加业务 */
	public String AddTrainInfo(TrainInfo trainInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新车次信息 */
			String sqlString = "insert into TrainInfo(trainNumber,startStation,endStation,startDate,seatType,price,seatNumber,leftSeatNumber,startTime,endTime,totalTime) values (";
			sqlString += "'" + trainInfo.getTrainNumber() + "',";
			sqlString += trainInfo.getStartStation() + ",";
			sqlString += trainInfo.getEndStation() + ",";
			sqlString += "'" + trainInfo.getStartDate() + "',";
			sqlString += trainInfo.getSeatType() + ",";
			sqlString += trainInfo.getPrice() + ",";
			sqlString += trainInfo.getSeatNumber() + ",";
			sqlString += trainInfo.getLeftSeatNumber() + ",";
			sqlString += "'" + trainInfo.getStartTime() + "',";
			sqlString += "'" + trainInfo.getEndTime() + "',";
			sqlString += "'" + trainInfo.getTotalTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "车次信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "车次信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除车次信息 */
	public String DeleteTrainInfo(int seatId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TrainInfo where seatId=" + seatId;
			db.executeUpdate(sqlString);
			result = "车次信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "车次信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到车次信息 */
	public TrainInfo GetTrainInfo(int seatId) {
		TrainInfo trainInfo = null;
		DB db = new DB();
		String sql = "select * from TrainInfo where seatId=" + seatId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				trainInfo = new TrainInfo();
				trainInfo.setSeatId(rs.getInt("seatId"));
				trainInfo.setTrainNumber(rs.getString("trainNumber"));
				trainInfo.setStartStation(rs.getInt("startStation"));
				trainInfo.setEndStation(rs.getInt("endStation"));
				trainInfo.setStartDate(rs.getTimestamp("startDate"));
				trainInfo.setSeatType(rs.getInt("seatType"));
				trainInfo.setPrice(rs.getFloat("price"));
				trainInfo.setSeatNumber(rs.getInt("seatNumber"));
				trainInfo.setLeftSeatNumber(rs.getInt("leftSeatNumber"));
				trainInfo.setStartTime(rs.getString("startTime"));
				trainInfo.setEndTime(rs.getString("endTime"));
				trainInfo.setTotalTime(rs.getString("totalTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return trainInfo;
	}
	/* 更新车次信息 */
	public String UpdateTrainInfo(TrainInfo trainInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TrainInfo set ";
			sql += "trainNumber='" + trainInfo.getTrainNumber() + "',";
			sql += "startStation=" + trainInfo.getStartStation() + ",";
			sql += "endStation=" + trainInfo.getEndStation() + ",";
			sql += "startDate='" + trainInfo.getStartDate() + "',";
			sql += "seatType=" + trainInfo.getSeatType() + ",";
			sql += "price=" + trainInfo.getPrice() + ",";
			sql += "seatNumber=" + trainInfo.getSeatNumber() + ",";
			sql += "leftSeatNumber=" + trainInfo.getLeftSeatNumber() + ",";
			sql += "startTime='" + trainInfo.getStartTime() + "',";
			sql += "endTime='" + trainInfo.getEndTime() + "',";
			sql += "totalTime='" + trainInfo.getTotalTime() + "'";
			sql += " where seatId=" + trainInfo.getSeatId();
			db.executeUpdate(sql);
			result = "车次信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "车次信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
