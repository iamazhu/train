package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.StationInfo;
import com.mobileserver.util.DB;

public class StationInfoDAO {

	public List<StationInfo> QueryStationInfo(String stationName,String connectPerson,String telephone,String postcode) {
		List<StationInfo> stationInfoList = new ArrayList<StationInfo>();
		DB db = new DB();
		String sql = "select * from StationInfo where 1=1";
		if (!stationName.equals(""))
			sql += " and stationName like '%" + stationName + "%'";
		if (!connectPerson.equals(""))
			sql += " and connectPerson like '%" + connectPerson + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (!postcode.equals(""))
			sql += " and postcode like '%" + postcode + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				StationInfo stationInfo = new StationInfo();
				stationInfo.setStationId(rs.getInt("stationId"));
				stationInfo.setStationName(rs.getString("stationName"));
				stationInfo.setConnectPerson(rs.getString("connectPerson"));
				stationInfo.setTelephone(rs.getString("telephone"));
				stationInfo.setPostcode(rs.getString("postcode"));
				stationInfo.setAddress(rs.getString("address"));
				stationInfoList.add(stationInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return stationInfoList;
	}
	/* 传入站点信息对象，进行站点信息的添加业务 */
	public String AddStationInfo(StationInfo stationInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新站点信息 */
			String sqlString = "insert into StationInfo(stationName,connectPerson,telephone,postcode,address) values (";
			sqlString += "'" + stationInfo.getStationName() + "',";
			sqlString += "'" + stationInfo.getConnectPerson() + "',";
			sqlString += "'" + stationInfo.getTelephone() + "',";
			sqlString += "'" + stationInfo.getPostcode() + "',";
			sqlString += "'" + stationInfo.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "站点信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除站点信息 */
	public String DeleteStationInfo(int stationId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StationInfo where stationId=" + stationId;
			db.executeUpdate(sqlString);
			result = "站点信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到站点信息 */
	public StationInfo GetStationInfo(int stationId) {
		StationInfo stationInfo = null;
		DB db = new DB();
		String sql = "select * from StationInfo where stationId=" + stationId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				stationInfo = new StationInfo();
				stationInfo.setStationId(rs.getInt("stationId"));
				stationInfo.setStationName(rs.getString("stationName"));
				stationInfo.setConnectPerson(rs.getString("connectPerson"));
				stationInfo.setTelephone(rs.getString("telephone"));
				stationInfo.setPostcode(rs.getString("postcode"));
				stationInfo.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return stationInfo;
	}
	/* 更新站点信息 */
	public String UpdateStationInfo(StationInfo stationInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update StationInfo set ";
			sql += "stationName='" + stationInfo.getStationName() + "',";
			sql += "connectPerson='" + stationInfo.getConnectPerson() + "',";
			sql += "telephone='" + stationInfo.getTelephone() + "',";
			sql += "postcode='" + stationInfo.getPostcode() + "',";
			sql += "address='" + stationInfo.getAddress() + "'";
			sql += " where stationId=" + stationInfo.getStationId();
			db.executeUpdate(sql);
			result = "站点信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
