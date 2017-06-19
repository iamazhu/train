package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mobileserver.dao.RechargeDAO;
import com.mobileserver.domain.Recharge;

import org.json.JSONStringer;

public class RechargeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ֵ��Ϣҵ������*/
	private RechargeDAO rechargeDAO = new RechargeDAO();

	/*Ĭ�Ϲ��캯��*/
	public RechargeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��ֵ��Ϣ�Ĳ�����Ϣ*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String chargeTime = request.getParameter("chargeTime");
			chargeTime = chargeTime == null ? "" : new String(request.getParameter(
					"chargeTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�г�ֵ��Ϣ��ѯ*/
			List<Recharge> rechargeList = rechargeDAO.QueryRecharge(userObj,chargeTime);

			/*����ѯ�Ľ����ͨ��xml��ʽ������ͻ���*/
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Recharges>").append("\r\n");
			for (int i = 0; i < rechargeList.size(); i++) {
				sb.append("	<Recharge>").append("\r\n")
				.append("		<id>")
				.append(rechargeList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<userObj>")
				.append(rechargeList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<money>")
				.append(rechargeList.get(i).getMoney())
				.append("</money>").append("\r\n")
				.append("		<chargeTime>")
				.append(rechargeList.get(i).getChargeTime())
				.append("</chargeTime>").append("\r\n")
				.append("	</Recharge>").append("\r\n");
			}
			sb.append("</Recharges>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
		} else if (action.equals("add")) {
			/* ��ӳ�ֵ��Ϣ����ȡ��ֵ��Ϣ�������������浽�½��ĳ�ֵ��Ϣ���� */ 
			Recharge recharge = new Recharge();
			int id = Integer.parseInt(request.getParameter("id"));
			recharge.setId(id);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			recharge.setUserObj(userObj);
			float money = Float.parseFloat(request.getParameter("money"));
			recharge.setMoney(money);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ 
			recharge.setChargeTime(df.format(new Date()));

			/* ����ҵ���ִ����Ӳ��� */
			String result = rechargeDAO.AddRecharge(recharge);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ֵ��Ϣ����ȡ��ֵ��Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = rechargeDAO.DeleteRecharge(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³�ֵ��Ϣ֮ǰ�ȸ���id��ѯĳ����ֵ��Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Recharge recharge = rechargeDAO.GetRecharge(id);

			// �ͻ��˲�ѯ�ĳ�ֵ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(recharge.getId());
			  stringer.key("userObj").value(recharge.getUserObj());
			  stringer.key("money").value(recharge.getMoney());
			  stringer.key("chargeTime").value(recharge.getChargeTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³�ֵ��Ϣ����ȡ��ֵ��Ϣ�������������浽�½��ĳ�ֵ��Ϣ���� */ 
			Recharge recharge = new Recharge();
			int id = Integer.parseInt(request.getParameter("id"));
			recharge.setId(id);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			recharge.setUserObj(userObj);
			float money = Float.parseFloat(request.getParameter("money"));
			recharge.setMoney(money);
			String chargeTime = new String(request.getParameter("chargeTime").getBytes("iso-8859-1"), "UTF-8");
			recharge.setChargeTime(chargeTime);

			/* ����ҵ���ִ�и��²��� */
			String result = rechargeDAO.UpdateRecharge(recharge);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
