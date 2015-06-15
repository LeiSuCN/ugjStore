<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.meta.OrderStatus"%>
<%@page import="com.uguanjia.o2o.Order"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/admin_header_menu.jsp" %>


<c:if test="${ requestScope.error_msg != null }">
<div class="row">
	<div class="alert alert-danger">
		<button type="button" class="close" data-dismiss="alert">
			<i class="ace-icon fa fa-times"></i>
		</button>
		<c:out value="${ requestScope.error_msg }" />
		<br />
	</div>
</div><!-- /.row -->
</c:if>


<jsp:include page="/WEB-INF/jsp/common/store_detail_content.jsp" />

<c:if test="${ store.status == 2 }">
<div class="row">
	<div class="alert alert-danger">
		该门店的申请已经被驳回，请及时通知店主修改申请信息！
		<br />
	</div>
</div><!-- /.row -->
</c:if>

<c:if test="${ store.status ==1 }">
<div class="row">
	<div class="col-xs-12" style="text-align: center;">
	<!-- PAGE CONTENT BEGINS -->
		<form action='  <c:url value="/admin/store/accept/${store.id }" /> ' id='approval_form' method="post">
		<label>备注：</label><input id="comment" name="comment" type="text"/>
		<button type="submit" id="submitBtnY">通过</button>
		<button type="submit" id="submitBtnN">驳回</button>
		</form>
	<!-- PAGE CONTENT ENDS -->
    </div><!-- /.col-xs-12 -->
</div><!-- /.row -->
</c:if>
                    
<%@include file="/WEB-INF/jsp/common/admin_footer_js.jsp" %>

<!-- page specific plugin scripts -->
<script type="text/javascript">

function approval(result){
	document.getElementById('approval_form').action = 
		document.getElementById('approval_form').action + '/' + result;
	
}

$(document).ready(function() {
	
	currentBreadcrumb([ { name:"门店管理", href:"#"}
		, { name:"门店审核", href:"#"}  ]);
	
	
	$('#submitBtnY').click(function(){
		approval(1);
	});
	$('#submitBtnN').click(function(){
		approval(2);
	});
	
});
</script>
</html>
