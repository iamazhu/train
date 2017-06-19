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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StationInfo GetStationInfoByStationId(int stationId) {
        Session s = factory.getCurrentSession();
        StationInfo stationInfo = (StationInfo)s.get(StationInfo.class, stationId);
        return stationInfo;
    }

    /*����StationInfo��Ϣ*/
    public void UpdateStationInfo(StationInfo stationInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(stationInfo);
    }

    /*ɾ��StationInfo��Ϣ*/
    public void DeleteStationInfo (int stationId) throws Exception {
        Session s = factory.getCurrentSession();
        Object stationInfo = s.load(StationInfo.class, stationId);
        s.delete(stationInfo);
    }

}
