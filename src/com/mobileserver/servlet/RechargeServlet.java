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

	/*构造充值信息业务层对象*/
	private RechargeDAO rechargeDAO = new RechargeDAO();

	/*默认构造函数*/
	public RechargeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询充值信息的参数信息*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String chargeTime = request.getParameter("chargeTime");
			chargeTime = chargeTime == null ? "" : new String(request.getParameter(
					"chargeTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行充值信息查询*/
			List<Recharge> rechargeList = rechargeDAO.QueryRecharge(userObj,chargeTime);

			/*将查询的结果集通过xml格式传输给客户端*/
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
			/* 添加充值信息：获取充值信息参数，参数保存到新建的充值信息对象 */ 
			Recharge recharge = new Recharge();
			int id = Integer.parseInt(request.getParameter("id"));
			recharge.setId(id);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			recharge.setUserObj(userObj);
			float money = Float.parseFloat(request.getParameter("money"));
			recharge.setMoney(money);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式 
			recharge.setChargeTime(df.format(new Date()));

			/* 调用业务层执行添加操作 */
			String result = rechargeDAO.AddRecharge(recharge);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除充值信息：获取充值信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = rechargeDAO.DeleteRecharge(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新充值信息之前先根据id查询某个充值信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Recharge recharge = rechargeDAO.GetRecharge(id);

			// 客户端查询的充值信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新充值信息：获取充值信息参数，参数保存到新建的充值信息对象 */ 
			Recharge recharge = new Recharge();
			int id = Integer.parseInt(request.getParameter("id"));
			recharge.setId(id);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			recharge.setUserObj(userObj);
			float money = Float.parseFloat(request.getParameter("money"));
			recharge.setMoney(money);
			String chargeTime = new String(request.getParameter("chargeTime").getBytes("iso-8859-1"), "UTF-8");
			recharge.setChargeTime(chargeTime);

			/* 调用业务层执行更新操作 */
			String result = rechargeDAO.UpdateRecharge(recharge);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
