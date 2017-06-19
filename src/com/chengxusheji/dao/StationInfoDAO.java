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
import com.chengxusheji.domain.StationInfo;

@Service @Transactional
public class StationInfoDAO {

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

    /*添加图书信息*/
    public void AddStationInfo(StationInfo stationInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(stationInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationInfo> QueryStationInfoInfo(String stationName,String connectPerson,String telephone,String postcode,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StationInfo stationInfo where 1=1";
    	if(!stationName.equals("")) hql = hql + " and stationInfo.stationName like '%" + stationName + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and stationInfo.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and stationInfo.telephone like '%" + telephone + "%'";
    	if(!postcode.equals("")) hql = hql + " and stationInfo.postcode like '%" + postcode + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List stationInfoList = q.list();
    	return (ArrayList<StationInfo>) stationInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationInfo> QueryStationInfoInfo(String stationName,String connectPerson,String telephone,String postcode) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StationInfo stationInfo where 1=1";
    	if(!stationName.equals("")) hql = hql + " and stationInfo.stationName like '%" + stationName + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and stationInfo.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and stationInfo.telephone like '%" + telephone + "%'";
    	if(!postcode.equals("")) hql = hql + " and stationInfo.postcode like '%" + postcode + "%'";
    	Query q = s.createQuery(hql);
    	List stationInfoList = q.list();
    	return (ArrayList<StationInfo>) stationInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationInfo> QueryAllStationInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From StationInfo";
        Query q = s.createQuery(hql);
        List stationInfoList = q.list();
        return (ArrayList<StationInfo>) stationInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String stationName,String connectPerson,String telephone,String postcode) {
        Session s = factory.getCurrentSession();
        String hql = "From StationInfo stationInfo where 1=1";
        if(!stationName.equals("")) hql = hql + " and stationInfo.stationName like '%" + stationName + "%'";
        if(!connectPerson.equals("")) hql = hql + " and stationInfo.connectPerson like '%" + connectPerson + "%'";
        if(!telephone.equals("")) hql = hql + " and stationInfo.telephone like '%" + telephone + "%'";
        if(!postcode.equals("")) hql = hql + " and stationInfo.postcode like '%" + postcode + "%'";
        Query q = s.createQuery(hql);
        List stationInfoList = q.list();
        recordNumber = stationInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StationInfo GetStationInfoByStationId(int stationId) {
        Session s = factory.getCurrentSession();
        StationInfo stationInfo = (StationInfo)s.get(StationInfo.class, stationId);
        return stationInfo;
    }

    /*更新StationInfo信息*/
    public void UpdateStationInfo(StationInfo stationInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(stationInfo);
    }

    /*删除StationInfo信息*/
    public void DeleteStationInfo (int stationId) throws Exception {
        Session s = factory.getCurrentSession();
        Object stationInfo = s.load(StationInfo.class, stationId);
        s.delete(stationInfo);
    }

}
