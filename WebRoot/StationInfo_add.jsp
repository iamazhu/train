<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加站点信息</TITLE> 
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
    var stationName = document.getElementById("stationInfo.stationName").value;
    if(stationName=="") {
        alert('请输入站点名称!');
        return false;
    }
    var connectPerson = document.getElementById("stationInfo.connectPerson").value;
    if(connectPerson=="") {
        alert('请输入联系人!');
        return false;
    }
    var postcode = document.getElementById("stationInfo.postcode").value;
    if(postcode=="") {
        alert('请输入邮编!');
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
    <s:form action="StationInfo/StationInfo_AddStationInfo.action" method="post" id="stationInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>站点名称:</td>
    <td width=70%><input id="stationInfo.stationName" name="stationInfo.stationName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><input id="stationInfo.connectPerson" name="stationInfo.connectPerson" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="stationInfo.telephone" name="stationInfo.telephone" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>邮编:</td>
    <td width=70%><input id="stationInfo.postcode" name="stationInfo.postcode" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>通讯地址:</td>
    <td width=70%><input id="stationInfo.address" name="stationInfo.address" type="text" size="20" /></td>
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
