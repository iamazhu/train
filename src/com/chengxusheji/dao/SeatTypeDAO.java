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
import com.chengxusheji.domain.SeatType;

@Service @Transactional
public class SeatTypeDAO {

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
    public void AddSeatType(SeatType seatType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seatType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatType> QuerySeatTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatType seatType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List seatTypeList = q.list();
    	return (ArrayList<SeatType>) seatTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatType> QuerySeatTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatType seatType where 1=1";
    	Query q = s.createQuery(hql);
    	List seatTypeList = q.list();
    	return (ArrayList<SeatType>) seatTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatType> QueryAllSeatTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SeatType";
        Query q = s.createQuery(hql);
        List seatTypeList = q.list();
        return (ArrayList<SeatType>) seatTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SeatType seatType where 1=1";
        Query q = s.createQuery(hql);
        List seatTypeList = q.list();
        recordNumber = seatTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SeatType GetSeatTypeBySeatTypeId(int seatTypeId) {
        Session s = factory.getCurrentSession();
        SeatType seatType = (SeatType)s.get(SeatType.class, seatTypeId);
        return seatType;
    }

    /*����SeatType��Ϣ*/
    public void UpdateSeatType(SeatType seatType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seatType);
    }

    /*ɾ��SeatType��Ϣ*/
    public void DeleteSeatType (int seatTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seatType = s.load(SeatType.class, seatTypeId);
        s.delete(seatType);
    }

}
