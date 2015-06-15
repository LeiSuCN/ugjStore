<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	
	Service service = Service.ZHAOPIANCHONGXI;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<link rel="stylesheet" type="text/css" href="<%=root %>/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=root %>/style/header.css">
<style type="text/css">
body, div, ul, li{ padding: 0px; margin: 0px; }
ul, li{ text-decoration: none;list-style-type:none }

#itemsTbl th,td{ text-align: center; }

.container_header{
	width: 1024px;
}

.container{
	width: 1024px;
}
</style>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
<script src="<%=root %>/js/jquery-ui.js"></script>
<script src="<%=root %>/js/header.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container_header">
	<span><%= service.NAME %>协议预览</span>
</div>
<div class="container">
    <div style="width:100%; text-align:right;border-bottom: 1px solid #F40;">
	<iframe style="width:100%; height:600px; border:0px" src="<%=root %>/contract/<%=service.CODE %>/template/html"></iframe>
    </div>

	<DIV style="width:600px; margin:10px auto;">
		<input type="checkbox" class="contactCb">我已阅读该协议
		<input type="checkbox" class="contactCb">我同意该协议内容条款	
	</DIV>
	<DIV style="width:600px; margin:10px auto; text-align:center;">
		<button id="goBtn">继续</button>
	</DIV>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">


    $(document).ready(function() {

    	var goBtn = $('#goBtn');

    	goBtn.attr('disabled','true');

    	$('.contactCb').change(function(){

    		var agree = true;

    		$('.contactCb').each(function(){
    			if( !$(this).is(':checked') )agree=false;
    		});


    		if( agree == true ){
    			goBtn.removeAttr('disabled');
    		} else{
    			goBtn.attr('disabled','true');
    		}

    	});
    	
    	goBtn.click(function(){
    		location.href = "<%=root%>/contract/<%=service.CODE %>/main";
    	})

    });
</script>
</html>