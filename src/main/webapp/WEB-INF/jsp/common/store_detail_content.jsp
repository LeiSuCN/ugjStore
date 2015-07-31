<%@page import="com.uguanjia.o2o.manger.OperatorUtils"%>
<%@page import="com.uguanjia.o2o.meta.Roles"%>
<%@page import="com.uguanjia.o2o.Operator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://o2o.ugj.com/jsp/jstl/process" prefix="p" %>
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
														<label for="storename" class="col-xs-12 col-sm-2 control-label no-padding-right">门店名称</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="storename" name="storename" class="width-100"  disabled="disabled"
																	value='<c:out value="${store.name }" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="legalPerson" class="col-xs-12 col-sm-2 control-label no-padding-right">负责人姓名</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="legalPerson" name="legalPerson" class="width-100" disabled="disabled" 
																	value='<c:out value="${store.legalPerson}" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="phonenumber" class="col-xs-12 col-sm-2 control-label no-padding-right">负责人电话</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="phonenumber" name="phonenumber" class="width-100" disabled="disabled" 
																	value='<c:out value="${store.phonenumber }" />'  />
															</span>
														</div>
													</div>
																										
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="alipay" class="col-xs-12 col-sm-2 control-label no-padding-right">负责人支付宝</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="alipay" name="alipay" class="width-100" disabled="disabled" 
																	value='<c:out value="${store.alipay }" />'  />
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
													<!-- 经纬度 -->
													<div style="clear: both;padding-top: 0.5em;  " style="  margin-bottom: 70px;">
														<label for="address" class="col-xs-12 col-sm-2 control-label no-padding-right">经纬度</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="address" name="address" class="" disabled="disabled"
																	value='<c:out value="${store.lon }" />, <c:out value="${store.lat }" />'  />
																	
																<a href=' <c:url value="/public/store/location?lng=${store.lon }&&lat=${store.lat }" /> ' target='_blank' style="text-decoration: underline;">
																	<i class="ace-icon fa fa-globe"></i> 查看地图
																</a>
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="bank" class="col-xs-12 col-sm-2 control-label no-padding-right">负责人身份证（正面）</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																
																<% if( OperatorUtils.getCurrentOperator() != null && OperatorUtils.getCurrentOperator().hasRole(Roles.ROLE_ADMIN) ) {%>
																<a href='<c:url value="/admin/store/detail/${store.id }/scanning/idcard_a" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/admin/store/detail/${store.id }/scanning/idcard_a" /> '  style="height:60px;">
																</a>
																
																<% } else { %>
																<a href='<c:url value="/store/detail/scanning/idcard_a" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/store/detail/scanning/idcard_a" /> '  style="height:60px;">
																</a>
																<% } %>
																
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="bank" class="col-xs-12 col-sm-2 control-label no-padding-right">负责人身份证（反面）</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																
																<% if( OperatorUtils.getCurrentOperator() != null && OperatorUtils.getCurrentOperator().hasRole(Roles.ROLE_ADMIN) ) {%>
																<a href='<c:url value="/admin/store/detail/${store.id }/scanning/idcard_b" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/admin/store/detail/${store.id }/scanning/idcard_b" /> '  style="height:60px;">
																</a>
																
																<% } else { %>
																<a href='<c:url value="/store/detail/scanning/idcard_b" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/store/detail/scanning/idcard_b" /> '  style="height:60px;">
																</a>
																<% } %>
																
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="bank" class="col-xs-12 col-sm-2 control-label no-padding-right">店面照片（外景）</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																
																<% if( OperatorUtils.getCurrentOperator() != null && OperatorUtils.getCurrentOperator().hasRole(Roles.ROLE_ADMIN) ) {%>
																<a href='<c:url value="/admin/store/detail/${store.id }/scanning/store_a" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/admin/store/detail/${store.id }/scanning/store_a" /> '  style="height:60px;">
																</a>
																
																<% } else { %>
																<a href='<c:url value="/store/detail/scanning/store_a" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/store/detail/scanning/store_a" /> '  style="height:60px;">
																</a>
																<% } %>
																
															</span>
														</div>
													</div>
													
													<div style="clear: both;padding-top: 0.5em;  ">
														<label for="bank" class="col-xs-12 col-sm-2 control-label no-padding-right">店面照片（内景）</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																
																<% if( OperatorUtils.getCurrentOperator() != null && OperatorUtils.getCurrentOperator().hasRole(Roles.ROLE_ADMIN) ) {%>
																<a href='<c:url value="/admin/store/detail/${store.id }/scanning/store_b" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/admin/store/detail/${store.id }/scanning/store_b" /> '  style="height:60px;">
																</a>
																
																<% } else { %>
																<a href='<c:url value="/store/detail/scanning/store_b" /> ' target="_blank">
																	<img alt="" src=' <c:url value="/store/detail/scanning/store_b" /> '  style="height:60px;">
																</a>
																<% } %>
																
															</span>
														</div>
													</div>
													
													<div style="clear: both"></div>
		</div><!-- /.widget-main -->
	</div>
</div>

<c:forEach var="serviceAccount" items="${requestScope.store.serviceAccounts }">
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><p:getServiceName id="${serviceAccount.type}" /></h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main form-horizontal">
													<div style="clear: both;  ">
														<label class="col-xs-12 col-sm-2 control-label no-padding-right">账号</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text"  class="width-100"  disabled="disabled"
																	value='<c:out value="${serviceAccount.account }" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;  ">
														<label class="col-xs-12 col-sm-2 control-label no-padding-right">密码</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right" style="padding-top: 0.5em;">
																<input type="text" class="width-100" disabled="disabled" 
																	value='<c:out value="${serviceAccount.password}" />'  />
															</span>
														</div>
													</div>
													
													<div style="clear: both;  "></div>
		</div><!-- /.widget-main -->
	</div>
</div>
</c:forEach>

	<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
