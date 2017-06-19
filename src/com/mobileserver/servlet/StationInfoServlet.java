package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StationInfoDAO;
import com.mobileserver.domain.StationInfo;

import org.json.JSONStringer;

public class StationInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造站点信息业务层对象*/
	private StationInfoDAO stationInfoDAO = new StationInfoDAO();

	/*默认构造函数*/
	public StationInfoServlet() {
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
			/*获取查询站点信息的参数信息*/
			String stationName = request.getParameter("stationName");
			stationName = stationName == null ? "" : new String(request.getParameter(
					"stationName").getBytes("iso-8859-1"), "UTF-8");
			String connectPerson = request.getParameter("connectPerson");
			connectPerson = connectPerson == null ? "" : new String(request.getParameter(
					"connectPerson").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			String postcode = request.getParameter("postcode");
			postcode = postcode == null ? "" : new String(request.getParameter(
					"postcode").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行站点信息查询*/
			List<StationInfo> stationInfoList = stationInfoDAO.QueryStationInfo(stationName,connectPerson,telephone,postcode);

			/*将查询的结果集通过xml格式传输给客户端*/
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<StationInfos>").append("\r\n");
			for (int i = 0; i < stationInfoList.size(); i++) {
				sb.append("	<StationInfo>").append("\r\n")
				.append("		<stationId>")
				.append(stationInfoList.get(i).getStationId())
				.append("</stationId>").append("\r\n")
				.append("		<stationName>")
				.append(stationInfoList.get(i).getStationName())
				.append("</stationName>").append("\r\n")
				.append("		<connectPerson>")
				.append(stationInfoList.get(i).getConnectPerson())
				.append("</connectPerson>").append("\r\n")
				.append("		<telephone>")
				.append(stationInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<postcode>")
				.append(stationInfoList.get(i).getPostcode())
				.append("</postcode>").append("\r\n")
				.append("		<address>")
				.append(stationInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</StationInfo>").append("\r\n");
			}
			sb.append("</StationInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
		} else if (action.equals("add")) {
			/* 添加站点信息：获取站点信息参数，参数保存到新建的站点信息对象 */ 
			StationInfo stationInfo = new StationInfo();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			stationInfo.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setStationName(stationName);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setTelephone(telephone);
			String postcode = new String(request.getParameter("postcode").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setPostcode(postcode);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setAddress(address);

			/* 调用业务层执行添加操作 */
			String result = stationInfoDAO.AddStationInfo(stationInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除站点信息：获取站点信息的记录编号*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			/*调用业务逻辑层执行删除操作*/
			String result = stationInfoDAO.DeleteStationInfo(stationId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新站点信息之前先根据stationId查询某个站点信息*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			StationInfo stationInfo = stationInfoDAO.GetStationInfo(stationId);

			// 客户端查询的站点信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stationId").value(stationInfo.getStationId());
			  stringer.key("stationName").value(stationInfo.getStationName());
			  stringer.key("connectPerson").value(stationInfo.getConnectPerson());
			  stringer.key("telephone").value(stationInfo.getTelephone());
			  stringer.key("postcode").value(stationInfo.getPostcode());
			  stringer.key("address").value(stationInfo.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新站点信息：获取站点信息参数，参数保存到新建的站点信息对象 */ 
			StationInfo stationInfo = new StationInfo();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			stationInfo.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setStationName(stationName);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setTelephone(telephone);
			String postcode = new String(request.getParameter("postcode").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setPostcode(postcode);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			stationInfo.setAddress(address);

			/* 调用业务层执行更新操作 */
			String result = stationInfoDAO.UpdateStationInfo(stationInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
