<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.TrainInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加订单信息</TITLE> 
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
    <TD align="left" vAlign=top >
    <s:form action="OrderInfo/OrderInfo_AddOrderInfo.action" method="post" id="orderInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>用户:</td>
    <td width=70%>
      <select name="orderInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getRealName() %></option>
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
      %>
          <option value='<%=trainInfo.getSeatId() %>'><%=trainInfo.getSeatId() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>车次:</td>
    <td width=70%><input id="orderInfo.trainNumber" name="orderInfo.trainNumber" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>始发站:</td>
    <td width=70%>
      <select name="orderInfo.startStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
      %>
          <option value='<%=stationInfo.getStationId() %>'><%=stationInfo.getStationName() %></option>
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
      %>
          <option value='<%=stationInfo.getStationId() %>'><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>开车日期:</td>
    <td width=70%><input type="text" readonly id="orderInfo.startDate"  name="orderInfo.startDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>席别:</td>
    <td width=70%>
      <select name="orderInfo.seatType.seatTypeId">
      <%
        for(SeatType seatType:seatTypeList) {
      %>
          <option value='<%=seatType.getSeatTypeId() %>'><%=seatType.getSeatTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>座位信息:</td>
    <td width=70%><input id="orderInfo.seatInfo" name="orderInfo.seatInfo" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>总票价:</td>
    <td width=70%><input id="orderInfo.totalPrice" name="orderInfo.totalPrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>开车时间:</td>
    <td width=70%><input id="orderInfo.startTime" name="orderInfo.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>终到时间:</td>
    <td width=70%><input id="orderInfo.endTime" name="orderInfo.endTime" type="text" size="20" /></td>
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
