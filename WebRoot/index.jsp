<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>zrx��Ʊ��Ʊϵͳ-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<!-- 
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û���Ϣ</a></li> 
			 -->
			 <li><a href="<%=basePath %>StationInfo/StationInfo_FrontQueryStationInfo.action" target="OfficeMain">վ����Ϣ</a></li> 
			<li><a href="<%=basePath %>TrainInfo/TrainInfo_FrontQueryTrainInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<!-- 
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>GuestBook/GuestBook_FrontQueryGuestBook.action" target="OfficeMain">������Ϣ</a></li> 
			 -->
			<li><a href="<%=basePath %>NewsInfo/NewsInfo_FrontQueryNewsInfo.action" target="OfficeMain">���Ź���</a></li> 
			<li><a href="<%=basePath %>UserInfo/UserInfo_RegisterUserInfoView.action" target="OfficeMain">�û�ע��</a></li> 
		</ul>
		<br />
	</div> 
	
	
	<div id="loginBar">
	  <%
	  
	  	String user_name = (String)session.getAttribute("user_name"); 
	    if(user_name==null){ 
	  %>
	  <form action="<%=basePath %>login/login_CheckFrontLogin.action">
		�û�����
		<input type=text name="userName" size="12"/>&nbsp;&nbsp;
		���룺
		<input type=password name="password" size="12"/>&nbsp;&nbsp;
		 
		<input type=submit value="��¼" />
		</form>
	  
	  <%} else { %>
	    <ul>
	        <li><a href="<%=basePath %>OrderInfo/OrderInfo_QueryMyOrderInfo.action" target="OfficeMain">���ҵĶ�����</a></li> 
	    	<li><a href="<%=basePath %>GuestBook/GuestBook_QueryMyGuestBook.action" target="OfficeMain">���ҵ����ԡ�</a></li> 
	    	<li><a href="<%=basePath %>UserInfo/UserInfo_SelfModifyUserInfoQuery.action?user_name=<%=user_name %>" target="OfficeMain">���޸ĸ�����Ϣ��</a></li>
	    	<li><a href="<%=basePath %>login/login_LoginOut.action">���˳���½��</a></li>
	    </ul> 
	 <% } %> 
		
	</div> 
	
	
	
	
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p><font color=red>2013���������1�������α�ҵ���</font>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
