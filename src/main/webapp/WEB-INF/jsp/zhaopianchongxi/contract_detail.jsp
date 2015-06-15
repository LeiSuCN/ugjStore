<%@page import="com.uguanjia.o2o.Contract"%>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@page import="com.uguanjia.o2o.meta.Roles"%>
<%@page import="com.uguanjia.o2o.Operator"%>
<%@page import="com.uguanjia.o2o.meta.ContractStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	Service service = Service.ZHAOPIANCHONGXI;
	Contract contract = (Contract)request.getAttribute("contract");
	Operator operator = (Operator)request.getAttribute("operator");
	boolean existed = (contract != null);
	if( contract == null )contract = new Contract();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 小白兔干洗 - 协议</title>
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
	<span>小白兔协议</span>
</div>
<div class="container">
	<div>
		<div style="text-align: center;">
		
		<%
			if( operator.hasRole(Roles.ROLE_ADMIN) &&  contract.getStatus() == ContractStatus.APPLY  ){
		%>
			<%@include file="/WEB-INF/jsp/admin/contract_approve_sub.jsp" %>
		<%
			}  else if(  contract.getStatus() == ContractStatus.ACCEPT  ){
		%><label style='color:red; text-align:center; display:block;width:100%;'>协议已经通过审核</label><% 
			} else {
		%><label style='color:red; text-align:center; display:block;width:100%;'>协议正在审核中</label><% 
			}
		
		%>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">

	var root = '<%=root%>';
	var contractId = <%= contract.getId() %>;

    $(document).ready(function() {
    });
</script>
</html>