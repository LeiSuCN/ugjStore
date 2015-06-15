<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/admin_header_menu.jsp" %>


<jsp:include page="/WEB-INF/jsp/common/store_detail_content.jsp" />
                    
<div class="row">
	<div class="col-xs-12">
	<!-- PAGE CONTENT BEGINS --> 
				<form class="form-horizontal" role="form" method="post" action='  <c:url value="/admin/store/servicecount/add" /> '>
									<input type="hidden" name="storeId" value=" <c:out value="${store.id }" /> ">
									<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right">开通账号</label>

									<div class="col-sm-11">
										<!-- #section:elements.form.input-icon -->
										<select name="type">
											<option value="1">小白兔专业干洗</option>
											<option value="2">照片冲洗</option>
											<option value="3">顺丰快递</option>
											<option value="4">菜鸟驿站</option>
											<option value="5">壹网通</option>
										</select>
										<span class="input-icon">
											<input type="text" id="account" name="account" placeholder="账号"/>
											<i class="ace-icon fa fa-user blue"></i>
										</span>

										<span class="input-icon input-icon-right">
											<input type="text" id="password" name="password" placeholder="密码" />
											<i class="ace-icon fa fa-key green"></i>
										</span>

										<button class="btn btn-info" type="submit">
											<i class="ace-icon fa fa-users"></i>
											新增
										</button>
										<!-- /section:elements.form.input-icon -->
									</div>
								</div>
				</form>
	
 	<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->                  
<%@include file="/WEB-INF/jsp/common/admin_footer_js.jsp" %>

<!-- page specific plugin scripts -->
<script type="text/javascript">
$(document).ready(function() {
	
	currentBreadcrumb([ { name:"门店管理", href:"#"}
		, { name:"门店查询", href:' <c:url value="/admin/store/list" /> '} 
		, { name:" <c:out value="${store.id }" /> ", href:' # '} ]);
	
});
</script>
</html>
