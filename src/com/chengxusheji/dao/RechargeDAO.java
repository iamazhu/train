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
import com.chengxusheji.domain.Recharge;

@Service @Transactional
public class RechargeDAO {

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
    public void AddRecharge(Recharge recharge) throws Exception {
    	Session s = factory.getCurrentSession();
        s.save(recharge);
        //ͬʱ�����û��˻����
        UserInfo userInfo = (UserInfo)s.get(UserInfo.class, recharge.getUserObj().getUser_name());
        userInfo.setMoney(userInfo.getMoney() + recharge.getMoney());
        s.save(userInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Recharge> QueryRechargeInfo(UserInfo userObj,String chargeTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Recharge recharge where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and recharge.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!chargeTime.equals("")) hql = hql + " and recharge.chargeTime like '%" + chargeTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List rechargeList = q.list();
    	return (ArrayList<Recharge>) rechargeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Recharge> QueryRechargeInfo(UserInfo userObj,String chargeTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Recharge recharge where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and recharge.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!chargeTime.equals("")) hql = hql + " and recharge.chargeTime like '%" + chargeTime + "%'";
    	Query q = s.createQuery(hql);
    	List rechargeList = q.list();
    	return (ArrayList<Recharge>) rechargeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Recharge> QueryAllRechargeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Recharge";
        Query q = s.createQuery(hql);
        List rechargeList = q.list();
        return (ArrayList<Recharge>) rechargeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(UserInfo userObj,String chargeTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Recharge recharge where 1=1";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and recharge.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!chargeTime.equals("")) hql = hql + " and recharge.chargeTime like '%" + chargeTime + "%'";
        Query q = s.createQuery(hql);
        List rechargeList = q.list();
        recordNumber = rechargeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Recharge GetRechargeById(int id) {
        Session s = factory.getCurrentSession();
        Recharge recharge = (Recharge)s.get(Recharge.class, id);
        return recharge;
    }

    /*����Recharge��Ϣ*/
    public void UpdateRecharge(Recharge recharge) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(recharge);
    }

    /*ɾ��Recharge��Ϣ*/
    public void DeleteRecharge (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object recharge = s.load(Recharge.class, id);
        s.delete(recharge);
    }

}
