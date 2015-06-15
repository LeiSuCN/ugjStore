<!DOCTYPE html>
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
                                   <h3 class="header smaller lighter blue">优管家合同列表</h3>
                                    <div class="row" style="margin: 10px 0;">
                                    	<label for="serviceType" class="label label-lg label-pink arrowed-right" style="float:left">业务类型</label>
 										<div class="col-xs-2">
											<select id="serviceType" name="serviceType" >
											<option value=0>全部</option>
											<%
												Service[] services = Service.values();
												for(Service service: services)
												{
													out.print("<option value=" + service.ID + ">" + service.NAME + "</option>");
												}
											%>
											</select>
                                    	</div>
                                    	
                                    	<label for="serviceType" class="label label-lg label-pink arrowed-right" style="float:left">合同状态</label>
 										<div class="col-xs-1">
											<select id="orderStatus" name="orderStatus" >
												<option value=0>全部</option>
												<%
													OrderProcess.Status[] processes = OrderProcess.Status.values();
													for(OrderProcess.Status status: processes)
													{
														out.print("<option value=" + status.id + ">" + status.name + "</option>");
													}
												%>
											</select>
                                    	</div>
                                   
										
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
                                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
													<th>门店编号</th>
													<th>创建时间</th>
													<th>合同类型</th>
													<th>当前状态</th>
													<th></th>
                                                </tr>
                                            </thead>

                                            <tbody>
<%
	List<Contract> contracts = (List<Contract>)request.getAttribute("contracts");
	SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	for( Contract contract: contracts ){
		Service service = Service.getById(contract.getType());
		String detailUrl = request.getContextPath() + "/admin/contract/" + contract.getId() + "/accept/";
		out.print("<tr>");
		out.print("<td>" + contract.getStoreId() + "</td>");
		out.print("<td>" + sdf.format(contract.getTime()) + "</td>");
		out.print("<td>" + Service.getById(contract.getType()).NAME + "</td>");
		out.print("<td><span class='label' data-order_status='"+ contract.getStatus() + "'>" + ContractStatus.getName(contract.getStatus()) + "</span></td>");
		out.print("<td>");
		out.print("<div class='hidden-sm hidden-xs action-buttons'>");
		out.print("<a class='blue' href='" + detailUrl + "1'>");
		out.print("<i class='ace-icon fa fa-check green bigger-130'></i> </a> ");
		out.print("<a class='blue' href='" + detailUrl + "0'>");
		out.print("<i class='ace-icon fa fa-times red bigger-130'></i> </a> ");
		out.print("</div>");
  		out.print("</td>");
		out.print("</tr>");
	}
%>                                            </tbody>
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
        
        var serviceType = $("#serviceType");
        var orderStatus = $("#orderStatus");
        var storeNum = $("#storenum");
        var searchBtn = $('#search_btn');
        
        $.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );
        
        	function resetStatusLabelColor(){
        		
        		$('.label').each(function(idx, ele){
        			var $ele = $(ele);
        			var status = $ele.data('order_status');
        			if( status ){
        				if( status == "1"){
        					$ele.addClass('label-info');
        				} else if( status == "2"){
        					$ele.addClass('label-success');
        				}
        			}
        		});
        		
        	}
        	
        	function jump(){
        		var type = serviceType.val();
        		var status =  orderStatus.val();
        		var storeId = storeNum.val();
        		var href = "?type=" + type + "&status=" + status + "&storeId=" + storeId;
        		window.location.href = href;
        	}
        
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"合同管理", href:"#"} ]);
            	
            	orderStatus.val(<c:out value="${requestScope.condition.status }" />);
            	serviceType.val(<c:out value="${requestScope.condition.type }" />);

            	if(storeNum.val() == "0" ) storeNum.val("");
            	
            	resetStatusLabelColor();
            	
            	searchBtn.click(jump);
            	
                var oTable1 = 
                $('#sample-table-2')
                //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                .dataTable( {
                    bAutoWidth: false,
                    "bSort" : false,// 排序
                    "aoColumns": [
                      null, null, null, null,{ "bSortable": false }
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
