package com.chengxusheji.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.domain.TrainInfo;

@Service @Transactional
public class TrainInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddTrainInfo(TrainInfo trainInfo) throws Exception {
    	Session s = factory.getCurrentSession();
        s.merge(trainInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TrainInfo> QueryTrainInfoInfo(String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TrainInfo trainInfo where 1=1";
    	if(!trainNumber.equals("")) hql = hql + " and trainInfo.trainNumber like '%" + trainNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and trainInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and trainInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and trainInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and trainInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	//ֻ�ܲ�ѯ��ǰʱ���Ժ�Ļ���Ϣ
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	hql += " and trainInfo.startDate>='" + sdf.format(new java.util.Date()) + "'";
    	
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List trainInfoList = q.list();
    	return (ArrayList<TrainInfo>) trainInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TrainInfo> QueryTrainInfoInfo(String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TrainInfo trainInfo where 1=1";
    	if(!trainNumber.equals("")) hql = hql + " and trainInfo.trainNumber like '%" + trainNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and trainInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and trainInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and trainInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and trainInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	//ֻ�ܲ�ѯ��ǰʱ���Ժ�Ļ���Ϣ
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	hql += " and trainInfo.startDate>='" + sdf.format(new java.util.Date()) + "'";
    	Query q = s.createQuery(hql);
    	List trainInfoList = q.list();
    	return (ArrayList<TrainInfo>) trainInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TrainInfo> QueryAllTrainInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From TrainInfo";
        Query q = s.createQuery(hql);
        List trainInfoList = q.list();
        return (ArrayList<TrainInfo>) trainInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) {
        Session s = factory.getCurrentSession();
        String hql = "From TrainInfo trainInfo where 1=1";
        if(!trainNumber.equals("")) hql = hql + " and trainInfo.trainNumber like '%" + trainNumber + "%'";
        if(null != startStation && startStation.getStationId()!=0) hql += " and trainInfo.startStation.stationId=" + startStation.getStationId();
        if(null != endStation && endStation.getStationId()!=0) hql += " and trainInfo.endStation.stationId=" + endStation.getStationId();
        if(!startDate.equals("")) hql = hql + " and trainInfo.startDate like '%" + startDate + "%'";
        if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and trainInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
        Query q = s.createQuery(hql);
        List trainInfoList = q.list();
        recordNumber = trainInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TrainInfo GetTrainInfoBySeatId(int seatId) {
        Session s = factory.getCurrentSession();
        TrainInfo trainInfo = (TrainInfo)s.get(TrainInfo.class, seatId);
        return trainInfo;
    }

    /*����TrainInfo��Ϣ*/
    public void UpdateTrainInfo(TrainInfo trainInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(trainInfo);
    }

    /*ɾ��TrainInfo��Ϣ*/
    public void DeleteTrainInfo (int seatId) throws Exception {
        Session s = factory.getCurrentSession();
        Object trainInfo = s.load(TrainInfo.class, seatId);
        s.delete(trainInfo);
    }

}
