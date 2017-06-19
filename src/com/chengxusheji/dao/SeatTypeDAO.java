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
    public void AddSeatType(SeatType seatType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seatType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatType> QuerySeatTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatType seatType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SeatType GetSeatTypeBySeatTypeId(int seatTypeId) {
        Session s = factory.getCurrentSession();
        SeatType seatType = (SeatType)s.get(SeatType.class, seatTypeId);
        return seatType;
    }

    /*更新SeatType信息*/
    public void UpdateSeatType(SeatType seatType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seatType);
    }

    /*删除SeatType信息*/
    public void DeleteSeatType (int seatTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seatType = s.load(SeatType.class, seatTypeId);
        s.delete(seatType);
    }

}
