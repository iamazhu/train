<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    StationInfo stationInfo = (StationInfo)request.getAttribute("stationInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�վ����Ϣ</TITLE>
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
    var stationName = document.getElementById("stationInfo.stationName").value;
    if(stationName=="") {
        alert('������վ������!');
        return false;
    }
    var connectPerson = document.getElementById("stationInfo.connectPerson").value;
    if(connectPerson=="") {
        alert('��������ϵ��!');
        return false;
    }
    var postcode = document.getElementById("stationInfo.postcode").value;
    if(postcode=="") {
        alert('�������ʱ�!');
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
    <TD align="left" vAlign=top ><s:form action="StationInfo/StationInfo_ModifyStationInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="stationInfo.stationId" name="stationInfo.stationId" type="text" value="<%=stationInfo.getStationId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>վ������:</td>
    <td width=70%><input id="stationInfo.stationName" name="stationInfo.stationName" type="text" size="20" value='<%=stationInfo.getStationName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><input id="stationInfo.connectPerson" name="stationInfo.connectPerson" type="text" size="20" value='<%=stationInfo.getConnectPerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="stationInfo.telephone" name="stationInfo.telephone" type="text" size="30" value='<%=stationInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ʱ�:</td>
    <td width=70%><input id="stationInfo.postcode" name="stationInfo.postcode" type="text" size="20" value='<%=stationInfo.getPostcode() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ͨѶ��ַ:</td>
    <td width=70%><input id="stationInfo.address" name="stationInfo.address" type="text" size="20" value='<%=stationInfo.getAddress() %>'/></td>
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
