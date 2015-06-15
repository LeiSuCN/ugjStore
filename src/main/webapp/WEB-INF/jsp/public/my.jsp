<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>优管家</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../style/base.css">
<style type="text/css">
.login-area-outer{
	background: url(<%= request.getContextPath() %>/style/images/bknd_secondary_nav.png) no-repeat left top;

	width: 600px;
	margin: 60px auto;
	height: 300px;
	padding-left: 20px;
}
.login-area-inner{
	background: url(<%= request.getContextPath() %>/style/images/bknd_secondary_nav1.png) no-repeat right bottom;
	height: 300px;
	padding-right: 20px;
}

.inputName{
	width: 45%;
	text-align: right;
	padding: 20px 0px;
}

.inputValue{
	text-align: left;
}

.inputValue input{
	width: 160px;
}
</style>
<script src="<%= request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
</head>
<body>
<div class="container" style="margin-top:3em;">
	<div class="outerWrapper">
		<div class="innerWrapper">
			<div class="header">
				<div class="headerLogo">
					<img src="<%= request.getContextPath() %>/style/images/logo.png">
				</div>
			</div>
			<div class="MainMenu">
				<ul>
					<li><a href="<%= request.getContextPath() %>/index.html">首页</a></li>
				</ul>
				<ul>
					<li><a  href="<%= request.getContextPath() %>/about.html">关于优管家</a></li>
				</ul>
				<ul>
					<li><a href="<%= request.getContextPath() %>/map.html">查找门店</a></li>
				</ul>
				<ul>
					<li><a href="<%= request.getContextPath() %>/service.html">顾客服务</a></li>
				</ul>
				<ul>
					<li><a href="<%= request.getContextPath() %>/notice.html">公告</a></li>
				</ul>
				<ul>
					<li><a href="<%= request.getContextPath() %>/contactus.html">联系我们</a></li>
				</ul>
				<ul>
					<li><a class="now">我的优管家</a></li>
				</ul>
			</div>
			<div class="mainInfo" style="background: #FDB812;padding:0px;height:450px;">
				<c:if test="${requestScope.ugj_user != null }">
					<div style="width: 400px;position: relative;  top: 80px; margin: 0 auto;">
					<table style="border: 0px; padding: 0px; width: 100%">
						<tr>
							<!--<td>用户名：</td>-->
							<td colspan="2"><c:out value="${requestScope.ugj_user.username }" /></td>
						</tr>
						<tr>
							<td colspan="2"><a href='<c:url value="/public/index" />'>进入系统主页</a></td>
						</tr>
						<tr>
							<td colspan="2"><a href='<c:url value="/j_spring_security_logout" />'>退出登录</a></td>
						</tr>
					</table>
					</div>
				</c:if>
				<c:if test="${requestScope.ugj_user== null }">
							<form action="<c:url value="/j_spring_security_check" />" method="post" style="position: relative;  top: 80px;">
								<table border=0 cellpadding=0 cellspacing=0 width="100%">
									<tr>
										<td class="inputName">用户名:</td>
										<td class="inputValue">
											<input type="text" name="j_username" >
										</td>
									</tr>
									<tr>
										<td class="inputName">密码:</td>
										<td class="inputValue">
											<input type="password" name="j_password" >
										</td>
									</tr>
									<tr>
										<td colspan="2" style="text-align:center">
											<input type="submit" value="登录">
											&nbsp;&nbsp;&nbsp;&nbsp;<a href="register" >注册</a>
										</td>
									</tr>
									<c:if test="${param.error != null }">
									<tr>
										<td colspan="2" style="text-align:center">
											<div style=" color:red; text-align:center; ">用户名或密码错误！</div>
										</td>
									</tr>
									</c:if>
								</table>								
							</form>
				</c:if>
			</div>
			<div class="footer">
				<div class="footerContent">Copyright©  2013-2020    All Rights Reserved</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>