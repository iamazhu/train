package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderInfoDAO;
import com.mobileserver.domain.OrderInfo;

import org.json.JSONStringer;

public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造订单信息业务层对象*/
	private OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

	/*默认构造函数*/
	public OrderInfoServlet() {
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
			/*获取查询订单信息的参数信息*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String trainNumber = request.getParameter("trainNumber");
			trainNumber = trainNumber == null ? "" : new String(request.getParameter(
					"trainNumber").getBytes("iso-8859-1"), "UTF-8");
			int startStation = 0;
			if (request.getParameter("startStation") != null)
				startStation = Integer.parseInt(request.getParameter("startStation"));
			int endStation = 0;
			if (request.getParameter("endStation") != null)
				endStation = Integer.parseInt(request.getParameter("endStation"));
			Timestamp startDate = null;
			if (request.getParameter("startDate") != null)
				startDate = Timestamp.valueOf(request.getParameter("startDate"));
			int seatType = 0;
			if (request.getParameter("seatType") != null)
				seatType = Integer.parseInt(request.getParameter("seatType"));

			/*调用业务逻辑层执行订单信息查询*/
			List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfo(userObj,trainNumber,startStation,endStation,startDate,seatType);

			/*将查询的结果集通过xml格式传输给客户端*/
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderInfos>").append("\r\n");
			for (int i = 0; i < orderInfoList.size(); i++) {
				sb.append("	<OrderInfo>").append("\r\n")
				.append("		<orderId>")
				.append(orderInfoList.get(i).getOrderId())
				.append("</orderId>").append("\r\n")
				.append("		<userObj>")
				.append(orderInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<trainObj>")
				.append(orderInfoList.get(i).getTrainObj())
				.append("</trainObj>").append("\r\n")
				.append("		<trainNumber>")
				.append(orderInfoList.get(i).getTrainNumber())
				.append("</trainNumber>").append("\r\n")
				.append("		<startStation>")
				.append(orderInfoList.get(i).getStartStation())
				.append("</startStation>").append("\r\n")
				.append("		<endStation>")
				.append(orderInfoList.get(i).getEndStation())
				.append("</endStation>").append("\r\n")
				.append("		<startDate>")
				.append(orderInfoList.get(i).getStartDate())
				.append("</startDate>").append("\r\n")
				.append("		<seatType>")
				.append(orderInfoList.get(i).getSeatType())
				.append("</seatType>").append("\r\n")
				.append("		<seatInfo>")
				.append(orderInfoList.get(i).getSeatInfo())
				.append("</seatInfo>").append("\r\n")
				.append("		<totalPrice>")
				.append(orderInfoList.get(i).getTotalPrice())
				.append("</totalPrice>").append("\r\n")
				.append("		<startTime>")
				.append(orderInfoList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(orderInfoList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("	</OrderInfo>").append("\r\n");
			}
			sb.append("</OrderInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
		} else if (action.equals("add")) {
			/* 添加订单信息：获取订单信息参数，参数保存到新建的订单信息对象 */ 
			OrderInfo orderInfo = new OrderInfo();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			orderInfo.setOrderId(orderId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			int trainObj = Integer.parseInt(request.getParameter("trainObj"));
			orderInfo.setTrainObj(trainObj);
			String trainNumber = new String(request.getParameter("trainNumber").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTrainNumber(trainNumber);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			orderInfo.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			orderInfo.setEndStation(endStation);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			orderInfo.setStartDate(startDate);
			int seatType = Integer.parseInt(request.getParameter("seatType"));
			orderInfo.setSeatType(seatType);
			String seatInfo = new String(request.getParameter("seatInfo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setSeatInfo(seatInfo);
			float totalPrice = Float.parseFloat(request.getParameter("totalPrice"));
			orderInfo.setTotalPrice(totalPrice);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setEndTime(endTime);

			/* 调用业务层执行添加操作 */
			String result = orderInfoDAO.AddOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if(action.equals("userSubmit")) {
			String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "UTF-8");
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			int ticketNum = Integer.parseInt(request.getParameter("ticketNum"));
			
			/* 调用业务层执行订票操作 */
			String result = orderInfoDAO.UserSubmitTicket(username,seatId,ticketNum);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			
		} else if (action.equals("delete")) {
			/*删除订单信息：获取订单信息的记录编号*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*调用业务逻辑层执行删除操作*/
			String result = orderInfoDAO.DeleteOrderInfo(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新订单信息之前先根据orderId查询某个订单信息*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			OrderInfo orderInfo = orderInfoDAO.GetOrderInfo(orderId);

			// 客户端查询的订单信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderId").value(orderInfo.getOrderId());
			  stringer.key("userObj").value(orderInfo.getUserObj());
			  stringer.key("trainObj").value(orderInfo.getTrainObj());
			  stringer.key("trainNumber").value(orderInfo.getTrainNumber());
			  stringer.key("startStation").value(orderInfo.getStartStation());
			  stringer.key("endStation").value(orderInfo.getEndStation());
			  stringer.key("startDate").value(orderInfo.getStartDate());
			  stringer.key("seatType").value(orderInfo.getSeatType());
			  stringer.key("seatInfo").value(orderInfo.getSeatInfo());
			  stringer.key("totalPrice").value(orderInfo.getTotalPrice());
			  stringer.key("startTime").value(orderInfo.getStartTime());
			  stringer.key("endTime").value(orderInfo.getEndTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新订单信息：获取订单信息参数，参数保存到新建的订单信息对象 */ 
			OrderInfo orderInfo = new OrderInfo();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			orderInfo.setOrderId(orderId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			int trainObj = Integer.parseInt(request.getParameter("trainObj"));
			orderInfo.setTrainObj(trainObj);
			String trainNumber = new String(request.getParameter("trainNumber").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTrainNumber(trainNumber);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			orderInfo.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			orderInfo.setEndStation(endStation);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			orderInfo.setStartDate(startDate);
			int seatType = Integer.parseInt(request.getParameter("seatType"));
			orderInfo.setSeatType(seatType);
			String seatInfo = new String(request.getParameter("seatInfo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setSeatInfo(seatInfo);
			float totalPrice = Float.parseFloat(request.getParameter("totalPrice"));
			orderInfo.setTotalPrice(totalPrice);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setEndTime(endTime);

			/* 调用业务层执行更新操作 */
			String result = orderInfoDAO.UpdateOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
