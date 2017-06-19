<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>zrx火车票订票系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<!-- 
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户信息</a></li> 
			 -->
			 <li><a href="<%=basePath %>StationInfo/StationInfo_FrontQueryStationInfo.action" target="OfficeMain">站点信息</a></li> 
			<li><a href="<%=basePath %>TrainInfo/TrainInfo_FrontQueryTrainInfo.action" target="OfficeMain">车次信息</a></li> 
			<!-- 
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">订单信息</a></li> 
			<li><a href="<%=basePath %>GuestBook/GuestBook_FrontQueryGuestBook.action" target="OfficeMain">留言信息</a></li> 
			 -->
			<li><a href="<%=basePath %>NewsInfo/NewsInfo_FrontQueryNewsInfo.action" target="OfficeMain">新闻公告</a></li> 
			<li><a href="<%=basePath %>UserInfo/UserInfo_RegisterUserInfoView.action" target="OfficeMain">用户注册</a></li> 
		</ul>
		<br />
	</div> 
	
	
	<div id="loginBar">
	  <%
	  
	  	String user_name = (String)session.getAttribute("user_name"); 
	    if(user_name==null){ 
	  %>
	  <form action="<%=basePath %>login/login_CheckFrontLogin.action">
		用户名：
		<input type=text name="userName" size="12"/>&nbsp;&nbsp;
		密码：
		<input type=password name="password" size="12"/>&nbsp;&nbsp;
		 
		<input type=submit value="登录" />
		</form>
	  
	  <%} else { %>
	    <ul>
	        <li><a href="<%=basePath %>OrderInfo/OrderInfo_QueryMyOrderInfo.action" target="OfficeMain">【我的订单】</a></li> 
	    	<li><a href="<%=basePath %>GuestBook/GuestBook_QueryMyGuestBook.action" target="OfficeMain">【我的留言】</a></li> 
	    	<li><a href="<%=basePath %>UserInfo/UserInfo_SelfModifyUserInfoQuery.action?user_name=<%=user_name %>" target="OfficeMain">【修改个人信息】</a></li>
	    	<li><a href="<%=basePath %>login/login_LoginOut.action">【退出登陆】</a></li>
	    </ul> 
	 <% } %> 
		
	</div> 
	
	
	
	
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p><font color=red>2013级软件开发1班钟荣鑫毕业设计</font>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
