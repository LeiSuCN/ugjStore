<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.meta.OrderProcess"%>
<%@page import="com.uguanjia.o2o.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://o2o.ugj.com/jsp/jstl/process" prefix="p" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/admin_header_menu.jsp" %>
<div class="row">
	<div class="col-xs-12">
	<!-- PAGE CONTENT BEGINS -->
                                   <h3 class="header smaller lighter blue">菜鸟驿站订单统计</h3>

                                    <div class="row" style="margin: 10px 0;">
                                    	<label for="time" class="label label-lg label-pink arrowed-right" style="float:left">业务类型</label>
 										<div class="col-xs-2">
											<select id="time" name="time" >
												<option value=201503>201503</option>
											</select>
                                    	</div>
                                    	
										<label for="code" class="label label-lg label-pink arrowed-right" style="float:left">菜鸟编号</label>
										<div class="col-xs-4">
												<input type="text" id="code" name="code"  class="form-control"  value="<c:out value="${requestScope.code }"  />"/>
                                    	</div>
                                    	
 										<div class="col-xs-1">
											<button class="btn btn-info btn-xs" id="search_btn">
												<i class="ace-icon fa fa-check-square-o  bigger-110 icon-only"></i>
											</button>
                                    	</div>
 										<div class="col-xs-1">
											<button class="btn btn-info btn-xs" id="dl_btn">
												<i class="ace-icon fa fa-cloud-download  bigger-110 icon-only"></i>
											</button>
                                    	</div>
 									</div>  

                                   
                                    <div class="table-header">统计结果</div>

                                    <!-- <div class="dataTables_borderWrap"> -->
                                    <div>
                                        <table id="result-table" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>月份</th>
                                                    <th>菜鸟编号</th>
                                                    <th>数量</th>
                                                    <th>总计</th>
                                                    <th></th>
                                                </tr>
                                            </thead>

                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>



	<!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/admin_footer_js.jsp" %>
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
        
        	function jump(){
                //var time = $("#time").val();
                var code = $("#code").val();
        		var href = "?code=" + code;
        		window.location.href = href;
        	}
        
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"订单管理", href:"#"}, { name:"订单统计", href:"#"}, { name:"菜鸟驿站", href:"#"} ]);
            	
                $('#result-table')
                .dataTable( {
                    bAutoWidth: false,
                    "aoColumns": [
                                  { "bSortable": false }, { "bSortable": false }, { "bSortable": false }, { "bSortable": false } 
                                  , { "bSortable": false }
                                ],
                    "bFilter":false,
                    "bLengthChange":false,
                    "iDisplayLength":10,
                    "oLanguage": {
                    	"sLengthMenu": "显示 _MENU_ 条",
                    	"sSearch": "搜索:",
                        "sInfo": "_START_ 至 _END_ , 共_TOTAL_条"
                    },
                    "bProcessing": true,
            		"bServerSide": true,
                    "sAjaxSource": "<c:url value="/admin/statistics/cainiao/a?code=${requestScope.code}"/>",
                 } );
                
                
                $('#search_btn').click(jump);
                
                $('#dl_btn').click(function(){
                	var time = $("#time").val();
                	window.open('<c:url value="/admin/statistics/cainiao/"/>' + time + '/download');
                })
            });
        </script>        
 

</html>
