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
    //��ȡ���е�userObj��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�trainObj��Ϣ
    List<TrainInfo> trainInfoList = (List<TrainInfo>)request.getAttribute("trainInfoList");
    //��ȡ���е�startStation��Ϣ
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
   
    //��ȡ���е�seatType��Ϣ
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��Ӷ�����Ϣ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var trainNumber = document.getElementById("orderInfo.trainNumber").value;
    if(trainNumber=="") {
        alert('�����복��!');
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
    <td width=30%>�û�:</td>
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
    <td width=30%>������Ϣ:</td>
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
    <td width=30%>����:</td>
    <td width=70%><input id="orderInfo.trainNumber" name="orderInfo.trainNumber" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ʼ��վ:</td>
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
    <td width=30%>�յ�վ:</td>
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
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="orderInfo.startDate"  name="orderInfo.startDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>ϯ��:</td>
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
    <td width=30%>��λ��Ϣ:</td>
    <td width=70%><input id="orderInfo.seatInfo" name="orderInfo.seatInfo" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>��Ʊ��:</td>
    <td width=70%><input id="orderInfo.totalPrice" name="orderInfo.totalPrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="orderInfo.startTime" name="orderInfo.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�յ�ʱ��:</td>
    <td width=70%><input id="orderInfo.endTime" name="orderInfo.endTime" type="text" size="20" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
