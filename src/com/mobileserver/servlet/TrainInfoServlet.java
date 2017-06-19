package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TrainInfoDAO;
import com.mobileserver.domain.TrainInfo;

import org.json.JSONStringer;

public class TrainInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造车次信息业务层对象*/
	private TrainInfoDAO trainInfoDAO = new TrainInfoDAO();

	/*默认构造函数*/
	public TrainInfoServlet() {
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
			/*获取查询车次信息的参数信息*/
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

			/*调用业务逻辑层执行车次信息查询*/
			List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfo(trainNumber,startStation,endStation,startDate,seatType);

			/*将查询的结果集通过xml格式传输给客户端*/
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<TrainInfos>").append("\r\n");
			for (int i = 0; i < trainInfoList.size(); i++) {
				sb.append("	<TrainInfo>").append("\r\n")
				.append("		<seatId>")
				.append(trainInfoList.get(i).getSeatId())
				.append("</seatId>").append("\r\n")
				.append("		<trainNumber>")
				.append(trainInfoList.get(i).getTrainNumber())
				.append("</trainNumber>").append("\r\n")
				.append("		<startStation>")
				.append(trainInfoList.get(i).getStartStation())
				.append("</startStation>").append("\r\n")
				.append("		<endStation>")
				.append(trainInfoList.get(i).getEndStation())
				.append("</endStation>").append("\r\n")
				.append("		<startDate>")
				.append(trainInfoList.get(i).getStartDate())
				.append("</startDate>").append("\r\n")
				.append("		<seatType>")
				.append(trainInfoList.get(i).getSeatType())
				.append("</seatType>").append("\r\n")
				.append("		<price>")
				.append(trainInfoList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<seatNumber>")
				.append(trainInfoList.get(i).getSeatNumber())
				.append("</seatNumber>").append("\r\n")
				.append("		<leftSeatNumber>")
				.append(trainInfoList.get(i).getLeftSeatNumber())
				.append("</leftSeatNumber>").append("\r\n")
				.append("		<startTime>")
				.append(trainInfoList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(trainInfoList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("		<totalTime>")
				.append(trainInfoList.get(i).getTotalTime())
				.append("</totalTime>").append("\r\n")
				.append("	</TrainInfo>").append("\r\n");
			}
			sb.append("</TrainInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
		} else if (action.equals("add")) {
			/* 添加车次信息：获取车次信息参数，参数保存到新建的车次信息对象 */ 
			TrainInfo trainInfo = new TrainInfo();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			trainInfo.setSeatId(seatId);
			String trainNumber = new String(request.getParameter("trainNumber").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setTrainNumber(trainNumber);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			trainInfo.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			trainInfo.setEndStation(endStation);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			trainInfo.setStartDate(startDate);
			int seatType = Integer.parseInt(request.getParameter("seatType"));
			trainInfo.setSeatType(seatType);
			float price = Float.parseFloat(request.getParameter("price"));
			trainInfo.setPrice(price);
			int seatNumber = Integer.parseInt(request.getParameter("seatNumber"));
			trainInfo.setSeatNumber(seatNumber);
			int leftSeatNumber = Integer.parseInt(request.getParameter("leftSeatNumber"));
			trainInfo.setLeftSeatNumber(leftSeatNumber);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setEndTime(endTime);
			String totalTime = new String(request.getParameter("totalTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setTotalTime(totalTime);

			/* 调用业务层执行添加操作 */
			String result = trainInfoDAO.AddTrainInfo(trainInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除车次信息：获取车次信息的记录编号*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			/*调用业务逻辑层执行删除操作*/
			String result = trainInfoDAO.DeleteTrainInfo(seatId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新车次信息之前先根据seatId查询某个车次信息*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			TrainInfo trainInfo = trainInfoDAO.GetTrainInfo(seatId);

			// 客户端查询的车次信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("seatId").value(trainInfo.getSeatId());
			  stringer.key("trainNumber").value(trainInfo.getTrainNumber());
			  stringer.key("startStation").value(trainInfo.getStartStation());
			  stringer.key("endStation").value(trainInfo.getEndStation());
			  stringer.key("startDate").value(trainInfo.getStartDate());
			  stringer.key("seatType").value(trainInfo.getSeatType());
			  stringer.key("price").value(trainInfo.getPrice());
			  stringer.key("seatNumber").value(trainInfo.getSeatNumber());
			  stringer.key("leftSeatNumber").value(trainInfo.getLeftSeatNumber());
			  stringer.key("startTime").value(trainInfo.getStartTime());
			  stringer.key("endTime").value(trainInfo.getEndTime());
			  stringer.key("totalTime").value(trainInfo.getTotalTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新车次信息：获取车次信息参数，参数保存到新建的车次信息对象 */ 
			TrainInfo trainInfo = new TrainInfo();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			trainInfo.setSeatId(seatId);
			String trainNumber = new String(request.getParameter("trainNumber").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setTrainNumber(trainNumber);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			trainInfo.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			trainInfo.setEndStation(endStation);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			trainInfo.setStartDate(startDate);
			int seatType = Integer.parseInt(request.getParameter("seatType"));
			trainInfo.setSeatType(seatType);
			float price = Float.parseFloat(request.getParameter("price"));
			trainInfo.setPrice(price);
			int seatNumber = Integer.parseInt(request.getParameter("seatNumber"));
			trainInfo.setSeatNumber(seatNumber);
			int leftSeatNumber = Integer.parseInt(request.getParameter("leftSeatNumber"));
			trainInfo.setLeftSeatNumber(leftSeatNumber);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setEndTime(endTime);
			String totalTime = new String(request.getParameter("totalTime").getBytes("iso-8859-1"), "UTF-8");
			trainInfo.setTotalTime(totalTime);

			/* 调用业务层执行更新操作 */
			String result = trainInfoDAO.UpdateTrainInfo(trainInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
