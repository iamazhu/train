package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.RechargeDAO;
import com.chengxusheji.domain.Recharge;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RechargeAction extends ActionSupport {

    /*�������Ҫ��ѯ������: ��ֵ�û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ��ֵʱ��*/
    private String chargeTime;
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }
    public String getChargeTime() {
        return this.chargeTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource UserInfoDAO userInfoDAO;
    @Resource RechargeDAO rechargeDAO;

    /*��������Recharge����*/
    private Recharge recharge;
    public void setRecharge(Recharge recharge) {
        this.recharge = recharge;
    }
    public Recharge getRecharge() {
        return this.recharge;
    }

    /*��ת�����Recharge��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Recharge��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try {
            
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(recharge.getUserObj().getUser_name());
            if(userObj==null) {
            	 ctx.put("error",  java.net.URLEncoder.encode("���û�������!"));
                 return "error";
            }
            
            recharge.setUserObj(userObj);
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
            recharge.setChargeTime(df.format(new Date()));
            
            rechargeDAO.AddRecharge(recharge);
            ctx.put("message",  java.net.URLEncoder.encode("�û���ֵ�ɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("�û���ֵʧ��!"));
            return "error";
        }
    }

    /*��ѯRecharge��Ϣ*/
    public String QueryRecharge() {
        if(currentPage == 0) currentPage = 1;
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj, chargeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        rechargeDAO.CalculateTotalPageAndRecordNumber(userObj, chargeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = rechargeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = rechargeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("rechargeList",  rechargeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("chargeTime", chargeTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRechargeOutputToExcel() { 
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj,chargeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Recharge��Ϣ��¼"; 
        String[] headers = { "��ֵ�û�","��ֵ���","��ֵʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<rechargeList.size();i++) {
        	Recharge recharge = rechargeList.get(i); 
        	dataset.add(new String[]{recharge.getUserObj().getRealName(),
recharge.getMoney() + "",recharge.getChargeTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Recharge.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯRecharge��Ϣ*/
    public String FrontQueryRecharge() {
        if(currentPage == 0) currentPage = 1;
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj, chargeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        rechargeDAO.CalculateTotalPageAndRecordNumber(userObj, chargeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = rechargeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = rechargeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("rechargeList",  rechargeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("chargeTime", chargeTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Recharge��Ϣ*/
    public String ModifyRechargeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡRecharge����*/
        Recharge recharge = rechargeDAO.GetRechargeById(id);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("recharge",  recharge);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Recharge��Ϣ*/
    public String FrontShowRechargeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡRecharge����*/
        Recharge recharge = rechargeDAO.GetRechargeById(id);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("recharge",  recharge);
        return "front_show_view";
    }

    /*�����޸�Recharge��Ϣ*/
    public String ModifyRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(recharge.getUserObj().getUser_name());
            recharge.setUserObj(userObj);
            }
            rechargeDAO.UpdateRecharge(recharge);
            ctx.put("message",  java.net.URLEncoder.encode("Recharge��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Recharge��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Recharge��Ϣ*/
    public String DeleteRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            rechargeDAO.DeleteRecharge(id);
            ctx.put("message",  java.net.URLEncoder.encode("Rechargeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Rechargeɾ��ʧ��!"));
            return "error";
        }
    }

}
