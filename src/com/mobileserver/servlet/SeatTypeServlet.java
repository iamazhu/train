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

	/*������λϯ��ҵ������*/
	private SeatTypeDAO seatTypeDAO = new SeatTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public SeatTypeServlet() {
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
			/*��ȡ��ѯ��λϯ��Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����λϯ���ѯ*/
			List<SeatType> seatTypeList = seatTypeDAO.QuerySeatType();

			/*����ѯ�Ľ����ͨ��xml��ʽ������ͻ���*/
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
			/* �����λϯ�𣺻�ȡ��λϯ��������������浽�½�����λϯ����� */ 
			SeatType seatType = new SeatType();
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			seatType.setSeatTypeId(seatTypeId);
			String seatTypeName = new String(request.getParameter("seatTypeName").getBytes("iso-8859-1"), "UTF-8");
			seatType.setSeatTypeName(seatTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = seatTypeDAO.AddSeatType(seatType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����λϯ�𣺻�ȡ��λϯ��ļ�¼���*/
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = seatTypeDAO.DeleteSeatType(seatTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������λϯ��֮ǰ�ȸ���seatTypeId��ѯĳ����λϯ��*/
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			SeatType seatType = seatTypeDAO.GetSeatType(seatTypeId);

			// �ͻ��˲�ѯ����λϯ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������λϯ�𣺻�ȡ��λϯ��������������浽�½�����λϯ����� */ 
			SeatType seatType = new SeatType();
			int seatTypeId = Integer.parseInt(request.getParameter("seatTypeId"));
			seatType.setSeatTypeId(seatTypeId);
			String seatTypeName = new String(request.getParameter("seatTypeName").getBytes("iso-8859-1"), "UTF-8");
			seatType.setSeatTypeName(seatTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = seatTypeDAO.UpdateSeatType(seatType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
