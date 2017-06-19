package com.mobileserver.domain;

public class TrainInfo {
    /*��¼���*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
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

    /*Ʊ��*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*����λ��*/
    private int seatNumber;
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /*ʣ����λ��*/
    private int leftSeatNumber;
    public int getLeftSeatNumber() {
        return leftSeatNumber;
    }
    public void setLeftSeatNumber(int leftSeatNumber) {
        this.leftSeatNumber = leftSeatNumber;
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

    /*��ʱ*/
    private String totalTime;
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

}