package com.chengxusheji.action;

 
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.AdminDAO;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.Admin;

@Controller @Scope("prototype")
public class LoginAction extends ActionSupport {
 
	@Resource AdminDAO adminDAO;
	
	@Resource UserInfoDAO userInfoDAO;
	
	private Admin admin;
	private String userName;
	private String password;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/*ֱ����ת����½����*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* ��֤�û���¼ */
	public String CheckLogin() { 
		ActionContext ctx = ActionContext.getContext();
		if (!adminDAO.CheckLogin(admin)) {
			ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("username", admin.getUsername());
		return "main_view";

		/*
		 * ActionContext ctx = ActionContext.getContext();
		 * ctx.getApplication().put("app", "Ӧ�÷�Χ");//��ServletContext�����app
		 * ctx.getSession().put("ses", "session��Χ");//��session�����ses ctx.put("req",
		 * "request��Χ");//��request�����req ctx.put("names", Arrays.asList("����", "����",
		 * "�Ϸ�")); HttpServletRequest request = ServletActionContext.getRequest();
		 * ServletContext servletContext = ServletActionContext.getServletContext();
		 * request.setAttribute("req", "����Χ����");
		 * request.getSession().setAttribute("ses", "�Ự��Χ����");
		 * servletContext.setAttribute("app", "Ӧ�÷�Χ����"); // HttpServletResponse
		 * response = ServletActionContext.getResponse();
		 */
	}
	

	/* ��֤ǰ̨��½*/
	public String CheckFrontLogin() {
		ActionContext ctx = ActionContext.getContext();  
		if (!userInfoDAO.CheckLogin(userName,password)) {
			ctx.put("error",  java.net.URLEncoder.encode(userInfoDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("user_name", userName); 
		
		 
		return "front_view";
	}
	
	/*�˳���½*/
	public String LoginOut() {
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().remove("user_name");  
		 
		return "front_view";
	}

	

}
