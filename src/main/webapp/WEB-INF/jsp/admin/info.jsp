<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/admin_header_menu.jsp" %>
                    
                    <div class="row">
                        <div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->
<div class="alert alert-block alert-success">
	<button type="button" class="close" data-dismiss="alert">
		<i class="ace-icon fa fa-times"></i>
	</button>

	<i class="ace-icon fa fa-check green"></i>
	<c:out value="${ requestScope.msg }" />
</div>
<!-- PAGE CONTENT ENDS -->
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/admin_footer_js.jsp" %>
<!-- page specific plugin scripts -->

</html>
