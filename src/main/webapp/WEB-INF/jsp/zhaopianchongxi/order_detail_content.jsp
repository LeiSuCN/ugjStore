<%@page import="com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrderItem"%>
<%@page import="com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrder"%>
<%@page import="com.uguanjia.o2o.meta.OrderProcessActivity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<div class="col-xs-12">
	<!-- PAGE CONTENT BEGINS -->
	<%
	ZhaopianchongxiOrder contentOrder = (ZhaopianchongxiOrder)request.getAttribute("order");
	%>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">客户信息</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">
			<div class="row">
				<div class="col-xs-3">
					<label for="customerName" class="label label-xlg label-warning">客户姓名:</label>
					<span><%= contentOrder.getCustomerName() %></span>
				</div>
				<div class="col-xs-3">
					<label for="customerName" class="label label-xlg label-warning">客户电话:</label>
					<span><%= contentOrder.getCustomerPhonenumber() %></span>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<label class="label label-xlg label-warning">客户地址:</label>
					<span><%= contentOrder.getCustomerAddress() %></span>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">订单信息</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">			
			<div class="row">
				<div class="col-xs-12">
					<label class="label label-xlg label-warning">照片文件:</label>
					<a href='<c:url value="/order/zhaopianchongxi/${order.id }/photos" />' target="_blank">
						<c:out value="${order.id }.rar" />
					</a>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="table-header">订单明细</div>
					<div>
				<table id="itemsTbl" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>尺寸</th>
							<th>单价</th>
							<th>数量</th>
						</tr>
					</thead>
					<tbody>
				<%
					List<ZhaopianchongxiOrderItem> items = contentOrder.getItems();
				
					for( ZhaopianchongxiOrderItem item : items ){
						
						float price = item.getPrice();
						
						out.print("<tr>");
						out.print("<td>" + item.getName() + "</td>");
						out.print("<td>" + item.getSize() + "</td>");
						out.print("<td>" +  price  + "</td>");
						out.print("<td>" + item.getAmount() + "</td>");
						out.print("</tr>");
					}
				%>
					</tbody>
				</table>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-3">
					<label class="label label-xlg label-warning">订单总价:</label>
					<span>￥<c:out value="${order.revenue }" /></span>
				</div>
				<div class="col-xs-3">
					<label class="label label-xlg label-warning">订单预计收益:</label>
					<span>￥<c:out value="${order.profit }" /></span>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">操作记录</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">			
			<div>
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>操作时间</th>
							<th>操作人员</th>
							<th>操作记录</th>
							<th>操作备注</th>
						</tr>
					</thead>
					<tbody>
<%
	SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	List<OrderProcessActivity> processes = contentOrder.getProcesses();
	for( OrderProcessActivity process: processes ){
%>
<tr>
<%
	out.print("<td>" + sdf.format(process.getTime()) + "</td>");
	out.print("<td>" + process.getOperator() + "</td>");
	out.print("<td>" +process.getStatus() + "</td>");
	out.print("<td>" +process.getComment() + "</td>");
%>
</tr>
<%	
	}
%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
	<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
