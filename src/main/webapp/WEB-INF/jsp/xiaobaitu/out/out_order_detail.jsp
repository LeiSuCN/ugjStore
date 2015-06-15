<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@page import="com.uguanjia.o2o.meta.OrderStatus"%>
<%@page import="com.uguanjia.o2o.Order"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://o2o.ugj.com/jsp/jstl/process" prefix="p" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/out_header_menu.jsp" %>
													<c:if test="${ requestScope.error_msg != null }">
													<div class="alert alert-danger">
														<button type="button" class="close" data-dismiss="alert">
															<i class="ace-icon fa fa-times"></i>
														</button>
														<c:out value="${ requestScope.error_msg }" />
														<br />
													</div>
													</c:if>
													
<div class="row">
	<div class="col-xs-12">
	<!-- PAGE CONTENT BEGINS -->
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">门店信息</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main form-horizontal">
													<div style="clear: both;padding-top: 0.5em; ">
														<label for="storeid" class="col-xs-12 col-sm-2 control-label no-padding-right">门店编号</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="storeid" name="storeid" class="width-100"  disabled="disabled"
																	value='<c:out value="${store.id }" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em; ">
														<label for="storename" class="col-xs-12 col-sm-2 control-label no-padding-right">门店名称</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="storename" name="storename" class="width-100"  disabled="disabled"
																	value='<c:out value="${store.name }" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="legalPerson" class="col-xs-12 col-sm-2 control-label no-padding-right">法定代表人</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="legalPerson" name="legalPerson" class="width-100" disabled="disabled" 
																	value='<c:out value="${store.legalPerson}" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="phonenumber" class="col-xs-12 col-sm-2 control-label no-padding-right">电话</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="phonenumber" name="phonenumber" class="width-100" disabled="disabled" 
																	value='<c:out value="${store.phonenumber }" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  " style="  margin-bottom: 70px;">
														<label for="address" class="col-xs-12 col-sm-2 control-label no-padding-right">区域</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="address" name="address" class="width-100" disabled="disabled"
																	value='<p:getAreaName area="${store.area }"/>'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  " style="  margin-bottom: 70px;">
														<label for="address" class="col-xs-12 col-sm-2 control-label no-padding-right">地址</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="address" name="address" class="width-100" disabled="disabled"
																	value='<c:out value="${store.address }" />'  />
															</span>
														</div>
													</div>

													
													<div style="clear: both"></div>
		</div><!-- /.widget-main -->
	</div>
</div>				

	<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->											

<jsp:include page="/WEB-INF/jsp/xiaobaitu/order_detail_content.jsp" />

<%
	Order order =  (Order)request.getAttribute("order");
	if( order.getStatus() == OrderStatus.ACCEPT)
	{
%>
<div class="row">
	<div class="col-xs-12" style="text-align: center;">
	<!-- PAGE CONTENT BEGINS -->
		<form action="   <c:url value="/out/order/xiaobaitu/close/${order.id }" />    " method="post">
		<input type="hidden" value="99">
		<label>备注：</label><input name="comment" type="text"/>
		<button id="submitBtn" type="submit">关闭</button>
		</form>
	<!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
<%
	}
%>
                    
<%@include file="/WEB-INF/jsp/common/out_footer_js.jsp" %>

<!-- page specific plugin scripts -->
<script type="text/javascript">
$(document).ready(function() {
	
	currentBreadcrumb([ { name:"订单管理", href:"#"}
		, { name:"订单查询", href:"<c:url value="/store/order/list" />"} 
		, { name:"<c:out value="${order.id }" />", href:"#"} ]);
	
});
</script>
</html>
