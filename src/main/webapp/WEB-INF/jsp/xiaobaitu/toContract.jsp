<%@page import="com.uguanjia.o2o.service.xiaobaitu.XiaobaituContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 小白兔干洗 - 协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
</head>
<body>

	<h1>您还没有与优管家签订小白兔协议，不能使用该功能，如果想使用该功能
	，请<a href="<%=root%>/contract/xiaobaitu/preview">签订协议</a></h1>

</body>
</html>