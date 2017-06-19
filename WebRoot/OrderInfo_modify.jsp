<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.TrainInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的trainObj信息
    List<TrainInfo> trainInfoList = (List<TrainInfo>)request.getAttribute("trainInfoList");
    //获取所有的startStation信息
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
    
    //获取所有的seatType信息
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改订单信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var trainNumber = document.getElementById("orderInfo.trainNumber").value;
    if(trainNumber=="") {
        alert('请输入车次!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="OrderInfo/OrderInfo_ModifyOrderInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="orderInfo.orderId" name="orderInfo.orderId" type="text" value="<%=orderInfo.getOrderId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>用户:</td>
    <td width=70%>
      <select name="orderInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(orderInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getRealName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>车次信息:</td>
    <td width=70%>
      <select name="orderInfo.trainObj.seatId">
      <%
        for(TrainInfo trainInfo:trainInfoList) {
          String selected = "";
          if(trainInfo.getSeatId() == orderInfo.getTrainObj().getSeatId())
            selected = "selected";
      %>
          <option value='<%=trainInfo.getSeatId() %>' <%=selected %>><%=trainInfo.getSeatId() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>车次:</td>
    <td width=70%><input id="orderInfo.trainNumber" name="orderInfo.trainNumber" type="text" size="20" value='<%=orderInfo.getTrainNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>始发站:</td>
    <td width=70%>
      <select name="orderInfo.startStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == orderInfo.getStartStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到站:</td>
    <td width=70%>
      <select name="orderInfo.endStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == orderInfo.getEndStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>开车日期:</td>
    <% DateFormat startDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="orderInfo.startDate"  name="orderInfo.startDate" onclick="setDay(this);" value='<%=startDateSDF.format(orderInfo.getStartDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>席别:</td>
    <td width=70%>
      <select name="orderInfo.seatType.seatTypeId">
      <%
        for(SeatType seatType:seatTypeList) {
          String selected = "";
          if(seatType.getSeatTypeId() == orderInfo.getSeatType().getSeatTypeId())
            selected = "selected";
      %>
          <option value='<%=seatType.getSeatTypeId() %>' <%=selected %>><%=seatType.getSeatTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>座位信息:</td>
    <td width=70%><input id="orderInfo.seatInfo" name="orderInfo.seatInfo" type="text" size="40" value='<%=orderInfo.getSeatInfo() %>'/></td>
  </tr>

  <tr>
    <td width=30%>总票价:</td>
    <td width=70%><input id="orderInfo.totalPrice" name="orderInfo.totalPrice" type="text" size="8" value='<%=orderInfo.getTotalPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>开车时间:</td>
    <td width=70%><input id="orderInfo.startTime" name="orderInfo.startTime" type="text" size="20" value='<%=orderInfo.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>终到时间:</td>
    <td width=70%><input id="orderInfo.endTime" name="orderInfo.endTime" type="text" size="20" value='<%=orderInfo.getEndTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
