<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
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
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>�鿴������Ϣ</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><%=orderInfo.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>�û�:</td>
    <td width=70%>
      <select name="orderInfo.userObj.user_name" disabled>
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
    <td width=30%>������Ϣ:</td>
    <td width=70%>
      <select name="orderInfo.trainObj.seatId" disabled>
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
    <td width=30%>����:</td>
    <td width=70%><%=orderInfo.getTrainNumber() %></td>
  </tr>

  <tr>
    <td width=30%>ʼ��վ:</td>
    <td width=70%>
      <select name="orderInfo.startStation.stationId" disabled>
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
    <td width=30%>�յ�վ:</td>
    <td width=70%>
      <select name="orderInfo.startStation.stationId" disabled>
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
    <td width=30%>��������:</td>
        <% java.text.DateFormat startDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=startDateSDF.format(orderInfo.getStartDate()) %></td>
  </tr>

  <tr>
    <td width=30%>ϯ��:</td>
    <td width=70%>
      <select name="orderInfo.seatType.seatTypeId" disabled>
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
    <td width=30%>��λ��Ϣ:</td>
    <td width=70%><%=orderInfo.getSeatInfo() %></td>
  </tr>

  <tr>
    <td width=30%>��Ʊ��:</td>
    <td width=70%><%=orderInfo.getTotalPrice() %></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=orderInfo.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>�յ�ʱ��:</td>
    <td width=70%><%=orderInfo.getEndTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
