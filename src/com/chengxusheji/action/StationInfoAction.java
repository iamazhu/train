package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class StationInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 站点名称*/
    private String stationName;
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getStationName() {
        return this.stationName;
    }

    /*界面层需要查询的属性: 联系人*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 邮编*/
    private String postcode;
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getPostcode() {
        return this.postcode;
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

    private int stationId;
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    public int getStationId() {
        return stationId;
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
    @Resource StationInfoDAO stationInfoDAO;

    /*待操作的StationInfo对象*/
    private StationInfo stationInfo;
    public void setStationInfo(StationInfo stationInfo) {
        this.stationInfo = stationInfo;
    }
    public StationInfo getStationInfo() {
        return this.stationInfo;
    }

    /*跳转到添加StationInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加StationInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            stationInfoDAO.AddStationInfo(stationInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfo添加失败!"));
            return "error";
        }
    }

    /*查询StationInfo信息*/
    public String QueryStationInfo() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName, connectPerson, telephone, postcode, currentPage);
        /*计算总的页数和总的记录数*/
        stationInfoDAO.CalculateTotalPageAndRecordNumber(stationName, connectPerson, telephone, postcode);
        /*获取到总的页码数目*/
        totalPage = stationInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = stationInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("stationInfoList",  stationInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        ctx.put("connectPerson", connectPerson);
        ctx.put("telephone", telephone);
        ctx.put("postcode", postcode);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryStationInfoOutputToExcel() { 
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName,connectPerson,telephone,postcode);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StationInfo信息记录"; 
        String[] headers = { "站点名称","联系人","联系电话","邮编","通讯地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<stationInfoList.size();i++) {
        	StationInfo stationInfo = stationInfoList.get(i); 
        	dataset.add(new String[]{stationInfo.getStationName(),stationInfo.getConnectPerson(),stationInfo.getTelephone(),stationInfo.getPostcode(),stationInfo.getAddress()});
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
			response.setHeader("Content-disposition","attachment; filename="+"StationInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询StationInfo信息*/
    public String FrontQueryStationInfo() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName, connectPerson, telephone, postcode, currentPage);
        /*计算总的页数和总的记录数*/
        stationInfoDAO.CalculateTotalPageAndRecordNumber(stationName, connectPerson, telephone, postcode);
        /*获取到总的页码数目*/
        totalPage = stationInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = stationInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("stationInfoList",  stationInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        ctx.put("connectPerson", connectPerson);
        ctx.put("telephone", telephone);
        ctx.put("postcode", postcode);
        return "front_query_view";
    }

    /*查询要修改的StationInfo信息*/
    public String ModifyStationInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stationId获取StationInfo对象*/
        StationInfo stationInfo = stationInfoDAO.GetStationInfoByStationId(stationId);

        ctx.put("stationInfo",  stationInfo);
        return "modify_view";
    }

    /*查询要修改的StationInfo信息*/
    public String FrontShowStationInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stationId获取StationInfo对象*/
        StationInfo stationInfo = stationInfoDAO.GetStationInfoByStationId(stationId);

        ctx.put("stationInfo",  stationInfo);
        return "front_show_view";
    }

    /*更新修改StationInfo信息*/
    public String ModifyStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            stationInfoDAO.UpdateStationInfo(stationInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除StationInfo信息*/
    public String DeleteStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            stationInfoDAO.DeleteStationInfo(stationId);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfo删除失败!"));
            return "error";
        }
    }

}
