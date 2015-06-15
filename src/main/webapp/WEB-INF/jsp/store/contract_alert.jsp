<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>
                    
                    <div class="row">
                        <div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<%
	Service service = (Service)request.getAttribute("service");
%>

<div class="alert alert-warning">
	<i class="ace-icon fa fa-exclamation-triangle red"></i>
		您还没有与优管家签订<%= service.NAME %>协议，不能使用该功能，如果要开通该功能，请<a href="<%=request.getContextPath()%>/store/contract/preview/<%= service.ID %>">签订协议</a>
</div>
<!-- PAGE CONTENT ENDS -->
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>
<!-- page specific plugin scripts -->
 <script type="text/javascript">
 jQuery(function($) {
 	currentBreadcrumb([ { name:"合同管理", href:"#"} ]);
 });
 </script>
</html>
