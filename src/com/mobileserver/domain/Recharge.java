package com.mobileserver.domain;

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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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