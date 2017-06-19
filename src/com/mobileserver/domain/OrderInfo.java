package com.mobileserver.domain;

public class OrderInfo {
    /*��¼���*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*�û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*������Ϣ*/
    private int trainObj;
    public int getTrainObj() {
        return trainObj;
    }
    public void setTrainObj(int trainObj) {
        this.trainObj = trainObj;
    }

    /*����*/
    private String trainNumber;
    public String getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    /*ʼ��վ*/
    private int startStation;
    public int getStartStation() {
        return startStation;
    }
    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    /*�յ�վ*/
    private int endStation;
    public int getEndStation() {
        return endStation;
    }
    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    /*��������*/
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*ϯ��*/
    private int seatType;
    public int getSeatType() {
        return seatType;
    }
    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    /*��λ��Ϣ*/
    private String seatInfo;
    public String getSeatInfo() {
        return seatInfo;
    }
    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    /*��Ʊ��*/
    private float totalPrice;
    public float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*����ʱ��*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*�յ�ʱ��*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}