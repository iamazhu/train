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
import com.chengxusheji.dao.SeatTypeDAO;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SeatTypeAction extends ActionSupport {

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

    private int seatTypeId;
    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }
    public int getSeatTypeId() {
        return seatTypeId;
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
    @Resource SeatTypeDAO seatTypeDAO;

    /*��������SeatType����*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*��ת�����SeatType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SeatType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSeatType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatTypeDAO.AddSeatType(seatType);
            ctx.put("message",  java.net.URLEncoder.encode("SeatType��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSeatType��Ϣ*/
    public String QuerySeatType() {
        if(currentPage == 0) currentPage = 1;
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = seatTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatTypeList",  seatTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySeatTypeOutputToExcel() { 
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SeatType��Ϣ��¼"; 
        String[] headers = { "��¼���","ϯ������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<seatTypeList.size();i++) {
        	SeatType seatType = seatTypeList.get(i); 
        	dataset.add(new String[]{seatType.getSeatTypeId() + "",seatType.getSeatTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SeatType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSeatType��Ϣ*/
    public String FrontQuerySeatType() {
        if(currentPage == 0) currentPage = 1;
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = seatTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatTypeList",  seatTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SeatType��Ϣ*/
    public String ModifySeatTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatTypeId��ȡSeatType����*/
        SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(seatTypeId);

        ctx.put("seatType",  seatType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SeatType��Ϣ*/
    public String FrontShowSeatTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatTypeId��ȡSeatType����*/
        SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(seatTypeId);

        ctx.put("seatType",  seatType);
        return "front_show_view";
    }

    /*�����޸�SeatType��Ϣ*/
    public String ModifySeatType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatTypeDAO.UpdateSeatType(seatType);
            ctx.put("message",  java.net.URLEncoder.encode("SeatType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SeatType��Ϣ*/
    public String DeleteSeatType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatTypeDAO.DeleteSeatType(seatTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("SeatTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
