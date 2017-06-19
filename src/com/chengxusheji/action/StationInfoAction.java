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

    /*�������Ҫ��ѯ������: վ������*/
    private String stationName;
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getStationName() {
        return this.stationName;
    }

    /*�������Ҫ��ѯ������: ��ϵ��*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: �ʱ�*/
    private String postcode;
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getPostcode() {
        return this.postcode;
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

    private int stationId;
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    public int getStationId() {
        return stationId;
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
    @Resource StationInfoDAO stationInfoDAO;

    /*��������StationInfo����*/
    private StationInfo stationInfo;
    public void setStationInfo(StationInfo stationInfo) {
        this.stationInfo = stationInfo;
    }
    public StationInfo getStationInfo() {
        return this.stationInfo;
    }

    /*��ת�����StationInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���StationInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            stationInfoDAO.AddStationInfo(stationInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯStationInfo��Ϣ*/
    public String QueryStationInfo() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName, connectPerson, telephone, postcode, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        stationInfoDAO.CalculateTotalPageAndRecordNumber(stationName, connectPerson, telephone, postcode);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = stationInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryStationInfoOutputToExcel() { 
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName,connectPerson,telephone,postcode);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StationInfo��Ϣ��¼"; 
        String[] headers = { "վ������","��ϵ��","��ϵ�绰","�ʱ�","ͨѶ��ַ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"StationInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯStationInfo��Ϣ*/
    public String FrontQueryStationInfo() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(postcode == null) postcode = "";
        List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfoInfo(stationName, connectPerson, telephone, postcode, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        stationInfoDAO.CalculateTotalPageAndRecordNumber(stationName, connectPerson, telephone, postcode);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = stationInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�StationInfo��Ϣ*/
    public String ModifyStationInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stationId��ȡStationInfo����*/
        StationInfo stationInfo = stationInfoDAO.GetStationInfoByStationId(stationId);

        ctx.put("stationInfo",  stationInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�StationInfo��Ϣ*/
    public String FrontShowStationInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stationId��ȡStationInfo����*/
        StationInfo stationInfo = stationInfoDAO.GetStationInfoByStationId(stationId);

        ctx.put("stationInfo",  stationInfo);
        return "front_show_view";
    }

    /*�����޸�StationInfo��Ϣ*/
    public String ModifyStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            stationInfoDAO.UpdateStationInfo(stationInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��StationInfo��Ϣ*/
    public String DeleteStationInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            stationInfoDAO.DeleteStationInfo(stationId);
            ctx.put("message",  java.net.URLEncoder.encode("StationInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
