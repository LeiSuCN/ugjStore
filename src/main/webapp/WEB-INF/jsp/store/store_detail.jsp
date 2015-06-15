<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>


<jsp:include page="/WEB-INF/jsp/common/store_detail_content.jsp" />
 
           
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>

<!-- page specific plugin scripts -->
<script type="text/javascript">
$(document).ready(function() {
	
	currentBreadcrumb([ { name:"门店信息", href:"#"} ]);
	
});
</script>
</html>
