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
	/* 传入充值信息对象，进行充值信息的添加业务 */
	public String AddRecharge(Recharge recharge) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新充值信息 */
			String sqls[] = new String[2];
			sqls[0] = "insert into Recharge(userObj,money,chargeTime) values (";
			sqls[0] += "'" + recharge.getUserObj() + "',";
			sqls[0] += recharge.getMoney() + ",";
			sqls[0] += "'" + recharge.getChargeTime() + "'";
			sqls[0] += ")";
			//然后更新用户余额信息
			sqls[1] = "update userinfo set money=money+" + recharge.getMoney() + " where user_name='" + recharge.getUserObj() + "'";
			 
			db.excuteSqlStrings(sqls);
			result = "用户充值成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户充值失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除充值信息 */
	public String DeleteRecharge(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Recharge where id=" + id;
			db.executeUpdate(sqlString);
			result = "充值信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "充值信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到充值信息 */
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
	/* 更新充值信息 */
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
			result = "充值信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "充值信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
