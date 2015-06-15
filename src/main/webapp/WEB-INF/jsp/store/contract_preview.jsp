<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>

<%
	String root = request.getContextPath();
	Service service = (Service)request.getAttribute("service");
%>
                    
                    <div class="row">
                        <div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->
	<iframe style="width:100%; height:600px; border:0px" src="<%=root %>/store/contract/<%=service.CODE %>/html"></iframe>
<!-- PAGE CONTENT ENDS -->
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    
                     <div class="row">
                        <div class="col-xs-12 center">
<!-- PAGE CONTENT BEGINS -->
		<input type="checkbox" class="ace ace-checkbox-2 contactCb" />
		<label class="lbl">我已阅读该协议</label>
		
		<input type="checkbox" class="ace ace-checkbox-2 contactCb"/>
		<label class="lbl">我同意该协议内容条款</label>
		
		<button id="goBtn" class="btn btn-primary btn-sm">提交</button>
<!-- PAGE CONTENT ENDS -->
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>
<!-- page specific plugin scripts -->
 <script type="text/javascript">
 jQuery(function($) {
 	currentBreadcrumb([ { name:"合同管理", href:"#"},{ name:"合同预览", href:"#"},  { name:'<%=service.NAME%>', href:"#"}]);
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
		location.href = "<%=root%>/store/contract/apply/<%=service.ID %>";
	});
});
 </script>
</html>
