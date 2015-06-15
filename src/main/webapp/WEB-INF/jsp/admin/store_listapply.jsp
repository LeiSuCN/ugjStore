<!DOCTYPE html>
<%@page import="com.uguanjia.o2o.Store"%>
<%@page import="com.uguanjia.o2o.meta.ContractStatus"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.uguanjia.o2o.Contract"%>
<%@page import="java.util.List"%>
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
                                   <h3 class="header smaller lighter blue">优管家门店列表</h3>
                                    <div class="row" style="margin: 10px 0;">										
										<label for="storenum" class="label label-lg label-pink arrowed-right" style="float:left">门店编号</label>
										<div class="col-xs-4">
												<input type="text" id="storenum" name="storenum"  class="form-control"  value="<c:out value="${condition.storeId }"  />"/>
                                    	</div>
                                    	
 										<div class="col-xs-1">
											<button class="btn btn-info btn-xs" id="search_btn">
												<i class="ace-icon fa fa-check-square-o  bigger-110 icon-only"></i>
											</button>
                                    	</div>
 									</div>                                  
                                    <div class="table-header">
                                        查询结果
                                    </div>

                                    <!-- <div class="table-responsive"> -->

                                    <!-- <div class="dataTables_borderWrap"> -->
                                    <div>
                                        <table id="stores_table" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
													<th>门店名称</th>
													<th>门店区域</th>
													<th>门店地址</th>
													<th>法人代表</th>
													<th>手机号码</th>
													<th></th>
                                                </tr>
                                            </thead>

                                            <tbody>
                                            <c:forEach  var="store" items="${requestScope.stores }">
                                            	<tr>
                                            		<td><c:out value="${store.name }" /></td>
                                            		<td><c:out value="${store.area }" /></td>
                                            		<td><c:out value="${store.address }" /></td>
                                            		<td><c:out value="${store.legalPerson }" /></td>
                                            		<td><c:out value="${store.phonenumber }" /></td>
                                            		<td>
                                            			<div class="hidden-sm hidden-xs action-buttons">
                                                            <a class="blue" href="<c:url value="/admin/store/detail/apply/${store.id }" />">
                                                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
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

        var storeNum = $("#storenum");
        var searchBtn = $('#search_btn');
        	
        	function jump(){
        		var storeId = storeNum.val();
        		var href = "?storeId=" + storeId;
        		window.location.href = href;
        	}
        
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"门店管理", href:"#"},{ name:"门店审核", href:"#"} ]);
 
            	if(storeNum.val() == "0" ) storeNum.val("");
             	
            	searchBtn.click(jump);
            	
                var oTable1 = 
                $('#stores_table')
                //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                .dataTable( {
                    bAutoWidth: false,
                    "aoColumns": [
                      null, null, null, null, null,{ "bSortable": false }
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
