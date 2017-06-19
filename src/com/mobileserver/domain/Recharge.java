package com.mobileserver.domain;

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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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