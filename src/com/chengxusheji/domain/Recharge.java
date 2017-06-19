package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Recharge {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*充值用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*充值金额*/
    private float money;
    public float getMoney() {
        return money;
    }
    public void setMoney(float money) {
        this.money = money;
    }

    /*充值时间*/
    private String chargeTime;
    public String getChargeTime() {
        return chargeTime;
    }
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

}