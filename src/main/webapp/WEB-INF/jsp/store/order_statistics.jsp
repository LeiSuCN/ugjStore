<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.meta.OrderProcess"%>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://o2o.ugj.com/jsp/jstl/process" prefix="p" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>
<div class="row">
	<div class="col-xs-12">
	<!-- PAGE CONTENT BEGINS -->
                                   <h3 class="header smaller lighter blue">菜鸟驿站订单统计</h3>
                                   
                                    <div class="table-header">统计结果</div>

                                    <!-- <div class="dataTables_borderWrap"> -->
                                    <div>
                                        <table id="result-table" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>月份</th>
                                                    <th>数量</th>
                                                    <th>总计</th>
                                                    <th></th>
                                                </tr>
                                            </thead>

                                            <tbody>
											<c:forEach var="ms" items="${requestScope.statistics }">
                                                <tr>
                                                    <td><c:out value="${ms.time }" /></td>
                                                    <td><c:out value="${ms.amount }" /></td>
                                                    <td><c:out value="${ms.total }" /></td>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                        	<!-- 
                                                            <a class="blue" href=" <c:url value="/store/statistics/cainiao/detail/${ms.time }/download "/> "  title="明细" target="_blank">
                                                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
                                                            </a>
                                                             -->
                                                             <a class="blue" href=" <c:url value="/store/statistics/cainiao/detail/${ms.time }/download "/> " title="下载" target="_blank">
                                                                <i class="ace-icon fa fa-cloud-download bigger-130"></i>
                                                            </a>
                                                        </div>
                                                     </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>



	<!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/jquery-ui.min.css" />
		
		<!-- page specific plugin scripts -->
		<script src="<%= request.getContextPath() %>/portal/js/jquery.dataTables.min.js"></script>
		<script src="<%= request.getContextPath() %>/portal/js/jquery.dataTables.bootstrap.js"></script>
		<script src="<%= request.getContextPath() %>/portal/js/jquery-ui.min.js"></script>
		<script src="<%= request.getContextPath() %>/portal/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%= request.getContextPath() %>/portal/js/jquery.ui.datepicker-zh-CN.js"></script>


        <!-- inline scripts related to this page -->
        <script type="text/javascript">
        
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"订单管理", href:"#"}, { name:"订单统计", href:"#"}, { name:"菜鸟驿站", href:"#"} ]);
            	
                $('#result-table')
                .dataTable( {
                    bAutoWidth: false,
                    "aoColumns": [
                                  { "bSortable": false }, { "bSortable": false }, { "bSortable": false }, { "bSortable": false } 
                                ],
                    "oLanguage": {
                    	"sLengthMenu": "显示 _MENU_ 条",
                    	"sSearch": "搜索:",
                        "sInfo": "_START_ 至 _END_ , 共_TOTAL_条"
                    }
                 } );
            });
        </script>        
 

</html>
