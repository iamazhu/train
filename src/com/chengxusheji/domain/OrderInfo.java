package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*������Ϣ*/
    private TrainInfo trainObj;
    public TrainInfo getTrainObj() {
        return trainObj;
    }
    public void setTrainObj(TrainInfo trainObj) {
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
    private StationInfo startStation;
    public StationInfo getStartStation() {
        return startStation;
    }
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }

    /*�յ�վ*/
    private StationInfo endStation;
    public StationInfo getEndStation() {
        return endStation;
    }
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }

    /*��������*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*ϯ��*/
    private SeatType seatType;
    public SeatType getSeatType() {
        return seatType;
    }
    public void setSeatType(SeatType seatType) {
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