package com.chengxusheji.domain;

import java.sql.Timestamp;
public class StationInfo {
    /*��¼���*/
    private int stationId;
    public int getStationId() {
        return stationId;
    }
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    /*վ������*/
    private String stationName;
    public String getStationName() {
        return stationName;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /*��ϵ��*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*�ʱ�*/
    private String postcode;
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /*ͨѶ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}