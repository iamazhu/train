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
	/* ����վ����Ϣ���󣬽���վ����Ϣ�����ҵ�� */
	public String AddStationInfo(StationInfo stationInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����վ����Ϣ */
			String sqlString = "insert into StationInfo(stationName,connectPerson,telephone,postcode,address) values (";
			sqlString += "'" + stationInfo.getStationName() + "',";
			sqlString += "'" + stationInfo.getConnectPerson() + "',";
			sqlString += "'" + stationInfo.getTelephone() + "',";
			sqlString += "'" + stationInfo.getPostcode() + "',";
			sqlString += "'" + stationInfo.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "վ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��վ����Ϣ */
	public String DeleteStationInfo(int stationId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StationInfo where stationId=" + stationId;
			db.executeUpdate(sqlString);
			result = "վ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��վ����Ϣ */
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
	/* ����վ����Ϣ */
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
			result = "վ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
