package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.TrainInfo;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.domain.OrderInfo;

@Service @Transactional
public class OrderInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    
    /*添加订单信息*/
    public void AddOrderInfo(OrderInfo orderInfo) throws Exception {
    	Session s = factory.getCurrentSession();  
        s.merge(orderInfo); 
    }
    
    
    
    /*用户提交订单信息*/
    public void AddOrderInfo(OrderInfo orderInfo,TrainInfo trainInfo,UserInfo userObj) throws Exception {
    	Session s = factory.getCurrentSession();  
        s.merge(orderInfo);
        s.merge(trainInfo);
        s.merge(userObj);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryOrderInfoInfo(UserInfo userObj,String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderInfo orderInfo where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!trainNumber.equals("")) hql = hql + " and orderInfo.trainNumber like '%" + trainNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and orderInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and orderInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and orderInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and orderInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List orderInfoList = q.list();
    	return (ArrayList<OrderInfo>) orderInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryOrderInfoInfo(UserInfo userObj,String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderInfo orderInfo where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!trainNumber.equals("")) hql = hql + " and orderInfo.trainNumber like '%" + trainNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and orderInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and orderInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and orderInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and orderInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	Query q = s.createQuery(hql);
    	List orderInfoList = q.list();
    	return (ArrayList<OrderInfo>) orderInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryAllOrderInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From OrderInfo";
        Query q = s.createQuery(hql);
        List orderInfoList = q.list();
        return (ArrayList<OrderInfo>) orderInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(UserInfo userObj,String trainNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) {
        Session s = factory.getCurrentSession();
        String hql = "From OrderInfo orderInfo where 1=1";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!trainNumber.equals("")) hql = hql + " and orderInfo.trainNumber like '%" + trainNumber + "%'";
        if(null != startStation && startStation.getStationId()!=0) hql += " and orderInfo.startStation.stationId=" + startStation.getStationId();
        if(null != endStation && endStation.getStationId()!=0) hql += " and orderInfo.endStation.stationId=" + endStation.getStationId();
        if(!startDate.equals("")) hql = hql + " and orderInfo.startDate like '%" + startDate + "%'";
        if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and orderInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
        Query q = s.createQuery(hql);
        List orderInfoList = q.list();
        recordNumber = orderInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public OrderInfo GetOrderInfoByOrderId(int orderId) {
        Session s = factory.getCurrentSession();
        OrderInfo orderInfo = (OrderInfo)s.get(OrderInfo.class, orderId);
        return orderInfo;
    }

    /*更新OrderInfo信息*/
    public void UpdateOrderInfo(OrderInfo orderInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(orderInfo);
    }

    /*删除OrderInfo信息*/
    public void DeleteOrderInfo (int orderId) throws Exception {
        Session s = factory.getCurrentSession();
        Object orderInfo = s.load(OrderInfo.class, orderId);
        s.delete(orderInfo);
    }

}
