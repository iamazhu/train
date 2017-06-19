package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Recharge;
import com.mobileserver.util.DB;

public class RechargeDAO {

	public List<Recharge> QueryRecharge(String userObj,String chargeTime) {
		List<Recharge> rechargeList = new ArrayList<Recharge>();
		DB db = new DB();
		String sql = "select * from Recharge where 1=1";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!chargeTime.equals(""))
			sql += " and chargeTime like '%" + chargeTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Recharge recharge = new Recharge();
				recharge.setId(rs.getInt("id"));
				recharge.setUserObj(rs.getString("userObj"));
				recharge.setMoney(rs.getFloat("money"));
				recharge.setChargeTime(rs.getString("chargeTime"));
				rechargeList.add(recharge);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return rechargeList;
	}
	/* �����ֵ��Ϣ���󣬽��г�ֵ��Ϣ�����ҵ�� */
	public String AddRecharge(Recharge recharge) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³�ֵ��Ϣ */
			String sqls[] = new String[2];
			sqls[0] = "insert into Recharge(userObj,money,chargeTime) values (";
			sqls[0] += "'" + recharge.getUserObj() + "',";
			sqls[0] += recharge.getMoney() + ",";
			sqls[0] += "'" + recharge.getChargeTime() + "'";
			sqls[0] += ")";
			//Ȼ������û������Ϣ
			sqls[1] = "update userinfo set money=money+" + recharge.getMoney() + " where user_name='" + recharge.getUserObj() + "'";
			 
			db.excuteSqlStrings(sqls);
			result = "�û���ֵ�ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û���ֵʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ֵ��Ϣ */
	public String DeleteRecharge(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Recharge where id=" + id;
			db.executeUpdate(sqlString);
			result = "��ֵ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ֵ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ֵ��Ϣ */
	public Recharge GetRecharge(int id) {
		Recharge recharge = null;
		DB db = new DB();
		String sql = "select * from Recharge where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				recharge = new Recharge();
				recharge.setId(rs.getInt("id"));
				recharge.setUserObj(rs.getString("userObj"));
				recharge.setMoney(rs.getFloat("money"));
				recharge.setChargeTime(rs.getString("chargeTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return recharge;
	}
	/* ���³�ֵ��Ϣ */
	public String UpdateRecharge(Recharge recharge) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Recharge set ";
			sql += "userObj='" + recharge.getUserObj() + "',";
			sql += "money=" + recharge.getMoney() + ",";
			sql += "chargeTime='" + recharge.getChargeTime() + "'";
			sql += " where id=" + recharge.getId();
			db.executeUpdate(sql);
			result = "��ֵ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ֵ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
