<%@page import="com.uguanjia.o2o.Contract"%>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	Service service = Service.ZHAOPIANCHONGXI;
	Contract contract = (Contract)request.getAttribute("contract");
	boolean existed = (contract != null);
	String isCanEdit = contract == null ? "" : "disabled='disabled'";
	if( contract == null )contract = new Contract();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - <%= service.NAME %> - 协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<link rel="stylesheet" type="text/css" href="<%=root %>/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=root %>/style/header.css">
<style type="text/css">
body, div, ul, li{ padding: 0px; margin: 0px; }
ul, li{ text-decoration: none;list-style-type:none }

.contract-content-container{
	padding-top:10px;
	padding-left:30px;
}

.contract-content-container div{
	margin-top:10px;
}

.contract-content-container  label{
	display: inline-block;
	width: 100px;
}

.contract-content-container  .min-image{
	height: 24px;
}

</style>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
<script src="<%=root %>/js/jquery-ui.js"></script>
<script src="<%=root %>/js/header.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container_header">
	<span><%= service.NAME %>协议</span>
</div>
<div class="container">
	<form action="<%=root %>/contract/<%= service.CODE %>/apply" method="post" enctype="multipart/form-data" onsubmit="return checkinput()" class="contract-content-container">
		<div style="text-align: center;">
		<%
			if( "".equals(isCanEdit) ){
				out.print("<input type='submit' value='提交'>");
			} else {
				out.print("<label style='color:red; text-align:center; display:block;width:100%;'>协议正在审核中</label>");
			}
		
		%>
		</div>
	</form>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">

    $(document).ready(function() {
    });
</script>
</html>