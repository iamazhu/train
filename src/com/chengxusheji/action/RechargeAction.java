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

    /*界面层需要查询的属性: 充值用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 充值时间*/
    private String chargeTime;
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }
    public String getChargeTime() {
        return this.chargeTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource UserInfoDAO userInfoDAO;
    @Resource RechargeDAO rechargeDAO;

    /*待操作的Recharge对象*/
    private Recharge recharge;
    public void setRecharge(Recharge recharge) {
        this.recharge = recharge;
    }
    public Recharge getRecharge() {
        return this.recharge;
    }

    /*跳转到添加Recharge视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Recharge信息*/
    @SuppressWarnings("deprecation")
    public String AddRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try {
            
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(recharge.getUserObj().getUser_name());
            if(userObj==null) {
            	 ctx.put("error",  java.net.URLEncoder.encode("该用户不存在!"));
                 return "error";
            }
            
            recharge.setUserObj(userObj);
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            recharge.setChargeTime(df.format(new Date()));
            
            rechargeDAO.AddRecharge(recharge);
            ctx.put("message",  java.net.URLEncoder.encode("用户充值成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("用户充值失败!"));
            return "error";
        }
    }

    /*查询Recharge信息*/
    public String QueryRecharge() {
        if(currentPage == 0) currentPage = 1;
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj, chargeTime, currentPage);
        /*计算总的页数和总的记录数*/
        rechargeDAO.CalculateTotalPageAndRecordNumber(userObj, chargeTime);
        /*获取到总的页码数目*/
        totalPage = rechargeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryRechargeOutputToExcel() { 
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj,chargeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Recharge信息记录"; 
        String[] headers = { "充值用户","充值金额","充值时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Recharge.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Recharge信息*/
    public String FrontQueryRecharge() {
        if(currentPage == 0) currentPage = 1;
        if(chargeTime == null) chargeTime = "";
        List<Recharge> rechargeList = rechargeDAO.QueryRechargeInfo(userObj, chargeTime, currentPage);
        /*计算总的页数和总的记录数*/
        rechargeDAO.CalculateTotalPageAndRecordNumber(userObj, chargeTime);
        /*获取到总的页码数目*/
        totalPage = rechargeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Recharge信息*/
    public String ModifyRechargeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Recharge对象*/
        Recharge recharge = rechargeDAO.GetRechargeById(id);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("recharge",  recharge);
        return "modify_view";
    }

    /*查询要修改的Recharge信息*/
    public String FrontShowRechargeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Recharge对象*/
        Recharge recharge = rechargeDAO.GetRechargeById(id);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("recharge",  recharge);
        return "front_show_view";
    }

    /*更新修改Recharge信息*/
    public String ModifyRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(recharge.getUserObj().getUser_name());
            recharge.setUserObj(userObj);
            }
            rechargeDAO.UpdateRecharge(recharge);
            ctx.put("message",  java.net.URLEncoder.encode("Recharge信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Recharge信息更新失败!"));
            return "error";
       }
   }

    /*删除Recharge信息*/
    public String DeleteRecharge() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            rechargeDAO.DeleteRecharge(id);
            ctx.put("message",  java.net.URLEncoder.encode("Recharge删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Recharge删除失败!"));
            return "error";
        }
    }

}
