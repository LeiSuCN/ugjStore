<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.meta.ContractStatus"%>
<%@page import="java.util.List"%>
<%@page import="com.uguanjia.o2o.Contract"%>
<%@page import="java.text.SimpleDateFormat"%>
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
                                   <h3 class="header smaller lighter blue">优管家合同列表</h3>
                                   
                                    <div class="table-header">
                                        查询结果
                                    </div>

                                    <!-- <div class="table-responsive"> -->

                                    <!-- <div class="dataTables_borderWrap"> -->
                                    <div>
                                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
													<th>创建时间</th>
													<th>合同类型</th>
													<th>当前状态</th>
                                                </tr>
                                            </thead>

                                            <tbody>
<%
	List<Contract> contracts = (List<Contract>)request.getAttribute("contracts");
	SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	for( Contract contract: contracts ){
		Service service = Service.getById(contract.getType());
		out.print("<tr>");
		out.print("<td>" + sdf.format(contract.getTime()) + "</td>");
		out.print("<td>" + Service.getById(contract.getType()).NAME + "</td>");
		out.print("<td><span class='label'  data-contract_status='" + contract.getStatus() + "'>" + ContractStatus.getName(contract.getStatus()) + "</span></td>");
		out.print("</tr>");
	}
%>

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

        <!-- inline scripts related to this page -->
        <script type="text/javascript">
        
        	function resetStatusLabelColor(){
        		
        		$('.label').each(function(idx, ele){
        			var $ele = $(ele);
        			var status = $ele.data('contract_status');
        			if( status ){
        				if( status == "1"){
        					$ele.addClass('label-info');
        				} else if( status == "2"){
        					$ele.addClass('label-success');
        				}
        			}
        		});
        		
        	}
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"合同管理", href:"#"} ]);
            	
            	
            	resetStatusLabelColor();
              	
                var oTable1 = 
                $('#sample-table-2')
                .dataTable( {
                    bAutoWidth: false,
                    "aoColumns": [
                      null, { "bSortable": false } , { "bSortable": false }
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
