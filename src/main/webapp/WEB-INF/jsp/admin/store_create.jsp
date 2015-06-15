<%@page import="com.uguanjia.o2o.Store"%>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.uguanjia.o2o.meta.ContractStatus"%>
<%@page import="com.uguanjia.o2o.Contract"%>
<%@page import="java.util.List"%>
<%@page import="com.uguanjia.o2o.manger.QueryCondition"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 门店管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<link rel="stylesheet" type="text/css" href="<%=root %>/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=root %>/style/header.css">
<style type="text/css">
body, div, ul, li{ padding: 0px; margin: 0px; }
ul, li{ text-decoration: none;list-style-type:none }
button{cursor: pointer;}

.store-info-item{
	padding: 2em 0 0 2em;
}

.store-info-item input {
	width: 600px;
}
</style>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
<script src="<%=root %>/js/jquery-ui.js"></script>
<script src="<%=root %>/js/header.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container_header">
	<span>创建门店</span>
</div>
<div class="container">
	<div>
		<div class="store-info-item">
			<label>门店编号</label>：<input type="text" value="" id="store_id">
		</div>
		<div class="store-info-item">
			<label>门店名称</label>：<input type="text" value="" id="store_name">
		</div>
		<div class="store-info-item">
			<label>固定电话</label>：<input type="text" value=""  id="store_fixed_phone">
		</div>
		<div class="store-info-item">
			<label>手机号码</label>：<input type="text" value=""  id="store_mobile_phone">
		</div>
		<div class="store-info-item">
			<label>门店地址</label>：<input type="text" value="" id="store_address">
		</div>
		<div class="store-info-item">
			<label>位置经度</label>：<input type="text" value="" id="store_lon">
		</div>
		<div class="store-info-item">
			<label>位置维度</label>：<input type="text" value="" id="store_lat">
		</div>
		<div style="text-align: center; margin-top: 2em;">
			<button id="submit">确定</button>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">

var defaultDataKey = '__default__';

function submit(){
	
	var info = {};
	info['id'] = $('#store_id').val();
	info['name'] = $('#store_name').val();
	info['fixedPhonenumber'] = $('#store_fixed_phone').val();
	info['mobilePhonenumber'] = $('#store_mobile_phone').val();
	info['address'] = $('#store_address').val();
	info['lat'] = $('#store_lat').val();
	info['lon'] = $('#store_lon').val();
	
	$.post( '<%=root%>/admin/store/create', info, function(resp){
		
		if( resp ){
			
			if( resp.code == 0 ){
				alert('创建门店信息成功');
				window.location.href = '<%=root%>/admin/store/detail/' + info['id'];
			} else{
				alert(resp.message );
			}
			
		} else{
			alert( '创建门店信息失败' );
		}
		
	} );
	
	console.log( info );
}

function saveDefaultValue(){
	
	$('.store-info-item  input').each(function(){
		var $this = $(this);
		if( $this.val() == 'null' )
			$this.val('');
		
		$this.data(defaultDataKey, $this.val() );
	});
}

    $(document).ready(function() {
    	saveDefaultValue();
    	$('#submit').click(submit);
    });
</script>
</html>