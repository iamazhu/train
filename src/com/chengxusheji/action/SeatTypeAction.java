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

    private int seatTypeId;
    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }
    public int getSeatTypeId() {
        return seatTypeId;
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
    @Resource SeatTypeDAO seatTypeDAO;

    /*待操作的SeatType对象*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*跳转到添加SeatType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SeatType信息*/
    @SuppressWarnings("deprecation")
    public String AddSeatType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatTypeDAO.AddSeatType(seatType);
            ctx.put("message",  java.net.URLEncoder.encode("SeatType添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatType添加失败!"));
            return "error";
        }
    }

    /*查询SeatType信息*/
    public String QuerySeatType() {
        if(currentPage == 0) currentPage = 1;
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        seatTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = seatTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatTypeList",  seatTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySeatTypeOutputToExcel() { 
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SeatType信息记录"; 
        String[] headers = { "记录编号","席别名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SeatType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SeatType信息*/
    public String FrontQuerySeatType() {
        if(currentPage == 0) currentPage = 1;
        List<SeatType> seatTypeList = seatTypeDAO.QuerySeatTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        seatTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = seatTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatTypeList",  seatTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SeatType信息*/
    public String ModifySeatTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatTypeId获取SeatType对象*/
        SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(seatTypeId);

        ctx.put("seatType",  seatType);
        return "modify_view";
    }

    /*查询要修改的SeatType信息*/
    public String FrontShowSeatTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatTypeId获取SeatType对象*/
        SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(seatTypeId);

        ctx.put("seatType",  seatType);
        return "front_show_view";
    }

    /*更新修改SeatType信息*/
    public String ModifySeatType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatTypeDAO.UpdateSeatType(seatType);
            ctx.put("message",  java.net.URLEncoder.encode("SeatType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatType信息更新失败!"));
            return "error";
       }
   }

    /*删除SeatType信息*/
    public String DeleteSeatType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatTypeDAO.DeleteSeatType(seatTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("SeatType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatType删除失败!"));
            return "error";
        }
    }

}
