package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SeatTypeDAO;
import com.mobileserver.domain.SeatType;

import org.json.JSONStringer;

public class SeatTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造座位席别业务层对象*/
	private SeatTypeDAO seatTypeDAO = new SeatTypeDAO();

	/*默认构造函数*/
	public SeatTypeServlet() {
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
			/*获取查询座位席别的参数信息*/

			/*调用业务逻辑层执行座位席别查询*/
			List<SeatType> seatTypeList = seatTypeDAO.QuerySeatType();

			/*将查询的结果集通过xml格式传输给客户端*/
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SeatTypes>").append("\r\n");
			for (int i = 0; i < seatTypeList.size(); i++) {
				sb.append("	<SeatType>").append("\r\n")
				.append("		<seatTypeId>")
				.append(seatTypeList.get(i).getSeatTypeId())
				.append("</seatTypeId>").append("\r\n")
				.append("		<seatTypeName>")
				.append(seatTypeList.get(i).getSeatTypeName())
				.append("</seatTypeName>").append("\r\n")
				.append("	</SeatType>").append("\r\n");
			}
			sb.append("</SeatTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
		} else if (action.equals("add")) {
			/* 添加座位席别：获取座位席别参数，参数保存到新建的座位席别对象 */ 
			SeatType seatType = new SeatType();
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			seatType.setSeatTypeId(seatTypeId);
			String seatTypeName = new String(request.getParameter("seatTypeName").getBytes("iso-8859-1"), "UTF-8");
			seatType.setSeatTypeName(seatTypeName);

			/* 调用业务层执行添加操作 */
			String result = seatTypeDAO.AddSeatType(seatType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除座位席别：获取座位席别的记录编号*/
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = seatTypeDAO.DeleteSeatType(seatTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新座位席别之前先根据seatTypeId查询某个座位席别*/
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			SeatType seatType = seatTypeDAO.GetSeatType(seatTypeId);

			// 客户端查询的座位席别对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("seatTypeId").value(seatType.getSeatTypeId());
			  stringer.key("seatTypeName").value(seatType.getSeatTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新座位席别：获取座位席别参数，参数保存到新建的座位席别对象 */ 
			SeatType seatType = new SeatType();
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			seatType.setSeatTypeId(seatTypeId);
			String seatTypeName = new String(request.getParameter("seatTypeName").getBytes("iso-8859-1"), "UTF-8");
			seatType.setSeatTypeName(seatTypeName);

			/* 调用业务层执行更新操作 */
			String result = seatTypeDAO.UpdateSeatType(seatType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
