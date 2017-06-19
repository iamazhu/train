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

	/*���쳵����Ϣҵ������*/
	private TrainInfoDAO trainInfoDAO = new TrainInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public TrainInfoServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
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

			/*����ҵ���߼���ִ�г�����Ϣ��ѯ*/
			List<TrainInfo> trainInfoList = trainInfoDAO.QueryTrainInfo(trainNumber,startStation,endStation,startDate,seatType);

			/*����ѯ�Ľ����ͨ��xml��ʽ������ͻ���*/
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
			/* ��ӳ�����Ϣ����ȡ������Ϣ�������������浽�½��ĳ�����Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = trainInfoDAO.AddTrainInfo(trainInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = trainInfoDAO.DeleteTrainInfo(seatId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³�����Ϣ֮ǰ�ȸ���seatId��ѯĳ��������Ϣ*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			TrainInfo trainInfo = trainInfoDAO.GetTrainInfo(seatId);

			// �ͻ��˲�ѯ�ĳ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³�����Ϣ����ȡ������Ϣ�������������浽�½��ĳ�����Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = trainInfoDAO.UpdateTrainInfo(trainInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
