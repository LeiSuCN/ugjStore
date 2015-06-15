<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.meta.OrderStatus"%>
<%@page import="com.uguanjia.o2o.Order"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/admin_header_menu.jsp" %>

<jsp:include page="/WEB-INF/jsp/${requestScope.serviceCode}/order_detail_content.jsp" />

<%
	Order order =  (Order)request.getAttribute("order");
	if( order.getStatus() == OrderStatus.NEW)
	{
%>
<div class="row">
	<div class="col-xs-12" style="text-align: center;">
	<!-- PAGE CONTENT BEGINS -->
		<label>备注：</label><input id="submitComment" type="text"/>
		<button id="submitBtn">审核通过</button>
	<!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
<%
	}
%>
                    
<%@include file="/WEB-INF/jsp/common/admin_footer_js.jsp" %>

<!-- page specific plugin scripts -->
<script type="text/javascript">
$(document).ready(function() {
	
	currentBreadcrumb([ { name:"订单管理", href:"#"}
		, { name:"订单查询", href:"<c:url value="/admin/order/list" />"} 
		, { name:"<c:out value="${order.id }" />", href:"#"} ]);
	
	$('#submitBtn').click(function(){

		var accept = {};
		
		accept.comment = $('#submitComment').val();
		accept.result = "2";

		$.ajax({
  			type: "POST",
  			dataType: 'json',
  			contentType: 'application/json; charset=utf-8',
  			url: '<c:url value="/admin/order/accept/${order.id }" />',
  			data: JSON.stringify(accept),
  			success: function( resp){
  				 var r = confirm("操作成功！");
  				 if( r == true ){
  				 	location.reload(true);
  				 }
  			}
		});
	});
});
</script>
</html>
