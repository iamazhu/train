package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Recharge {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*��ֵ�û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*��ֵ���*/
    private float money;
    public float getMoney() {
        return money;
    }
    public void setMoney(float money) {
        this.money = money;
    }

    /*��ֵʱ��*/
    private String chargeTime;
    public String getChargeTime() {
        return chargeTime;
    }
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

}