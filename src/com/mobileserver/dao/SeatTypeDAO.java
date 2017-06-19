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
	/* 传入座位席别对象，进行座位席别的添加业务 */
	public String AddSeatType(SeatType seatType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新座位席别 */
			String sqlString = "insert into SeatType(seatTypeName) values (";
			sqlString += "'" + seatType.getSeatTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "座位席别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位席别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除座位席别 */
	public String DeleteSeatType(int seatTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SeatType where seatTypeId=" + seatTypeId;
			db.executeUpdate(sqlString);
			result = "座位席别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位席别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到座位席别 */
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
	/* 更新座位席别 */
	public String UpdateSeatType(SeatType seatType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SeatType set ";
			sql += "seatTypeName='" + seatType.getSeatTypeName() + "'";
			sql += " where seatTypeId=" + seatType.getSeatTypeId();
			db.executeUpdate(sql);
			result = "座位席别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位席别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
