package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SeatType;
import com.mobileserver.util.DB;

public class SeatTypeDAO {

	public List<SeatType> QuerySeatType() {
		List<SeatType> seatTypeList = new ArrayList<SeatType>();
		DB db = new DB();
		String sql = "select * from SeatType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SeatType seatType = new SeatType();
				seatType.setSeatTypeId(rs.getInt("seatTypeId"));
				seatType.setSeatTypeName(rs.getString("seatTypeName"));
				seatTypeList.add(seatType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatTypeList;
	}
	/* ������λϯ����󣬽�����λϯ������ҵ�� */
	public String AddSeatType(SeatType seatType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������λϯ�� */
			String sqlString = "insert into SeatType(seatTypeName) values (";
			sqlString += "'" + seatType.getSeatTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��λϯ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λϯ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����λϯ�� */
	public String DeleteSeatType(int seatTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SeatType where seatTypeId=" + seatTypeId;
			db.executeUpdate(sqlString);
			result = "��λϯ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λϯ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����λϯ�� */
	public SeatType GetSeatType(int seatTypeId) {
		SeatType seatType = null;
		DB db = new DB();
		String sql = "select * from SeatType where seatTypeId=" + seatTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				seatType = new SeatType();
				seatType.setSeatTypeId(rs.getInt("seatTypeId"));
				seatType.setSeatTypeName(rs.getString("seatTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatType;
	}
	/* ������λϯ�� */
	public String UpdateSeatType(SeatType seatType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SeatType set ";
			sql += "seatTypeName='" + seatType.getSeatTypeName() + "'";
			sql += " where seatTypeId=" + seatType.getSeatTypeId();
			db.executeUpdate(sql);
			result = "��λϯ����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λϯ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
