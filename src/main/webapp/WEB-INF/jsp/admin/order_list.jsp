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
                                   <h3 class="header smaller lighter blue">优管家订单列表</h3>
                                   
									<div class="row" style="margin: 10px 0;">
										<label for="from" class="label label-lg label-pink arrowed-right" style="float:left">起始时间</label>
										<div class="col-xs-2">
											<div class="input-group input-group-sm">
												<input type="text" id="from" name="from"  class="form-control"  value="<fmt:formatDate value="${condition.dateFrom }"  type="date" pattern="yyyy-MM-dd" />"/>
												<span class="input-group-addon">
													<i class="ace-icon fa fa-calendar"></i>
												</span>
                                   			</div>
                                    	</div>
                                    	
                                    	<label for="to" class="label label-lg label-pink arrowed-right" style="float:left">截至时间</label>
 										<div class="col-xs-2">
											<div class="input-group input-group-sm">
												<input type="text" id="to" name="to"  class="form-control"   value="<fmt:formatDate value="${condition.dateTo }"  type="date" pattern="yyyy-MM-dd" />"/>
												<span class="input-group-addon">
													<i class="ace-icon fa fa-calendar"></i>
												</span>
                                   			</div>
                                    	</div>
                                    	
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
                                    	
                                    	<label for="serviceType" class="label label-lg label-pink arrowed-right" style="float:left">订单状态</label>
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
                                   </div>
                                   
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
                                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>订单号</th>
                                                    <th>门店编号</th>
                                                    <th>订单类型</th>
                                                    <th>创建时间</th>
                                                    <th>订单状态</th>
                                                    <th>客户姓名</th>
                                                    <th>金额</th>
                                                    <th>返利</th>
                                                    <th></th>
                                                </tr>
                                            </thead>

                                            <tbody>
											<c:forEach var="order" items="${requestScope.orders }">
                                                <tr>
                                                    <td><c:out value="${order.id }" /></td>
                                                    <td><c:out value="${order.store }" /></td>
													<td><p:getServiceName id="${order.type}" /></td>
                                                    <td><fmt:formatDate value="${order.createTime }"  type="both" pattern="yyyy-MM-dd HH:mm" /></td>
                                                    <td><span data-order_status="${order.status}"  class="label"><p:getStatusName status="${order.status}" /></span></td>
                                                    <td><c:out value="${order.customerName }" /></td>
                                                    <td><c:out value="${order.revenue }" /></td>
                                                    <td><c:out value="${order.profit }" /></td>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                            <a class="blue" href="<c:url value="/admin/order/detail/${order.id }" />">
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
        
        var dateFrom = $( "#from" );
        var dateTo = $( "#to" );
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
        		var df = dateFrom.val();
        		var dt = dateTo.val();
        		var type = serviceType.val();
        		var status =  orderStatus.val();
        		var storeId = storeNum.val();
        		var href = "?dateFrom=" + df + "&dateTo=" + dt + "&type=" + type + "&status=" + status + "&storeId=" + storeId;
        		window.location.href = href;
        	}
        
        
            jQuery(function($) {
            	
            	currentBreadcrumb([ { name:"订单管理", href:"#"}, { name:"订单查询", href:"#"} ]);
            	
            	orderStatus.val(<c:out value="${requestScope.condition.status }" />);
            	serviceType.val(<c:out value="${requestScope.condition.type }" />);
            	
            	var datepickerOptions = {
    					showOtherMonths: true,
    					selectOtherMonths: false,
           		     	dateFormat: "yy-mm-dd",
            		   // changeMonth: true,
            		    numberOfMonths: 1	
            	};
            	
            	dateFrom.datepicker($.extend({},datepickerOptions, {
            	      onClose: function( selectedDate ) {
            	    	  dateTo.datepicker( "option", "minDate", selectedDate );
            	      }
            	 }));
            	
            	dateTo.datepicker($.extend({},datepickerOptions, {
            	      onClose: function( selectedDate ) {
            	    	  dateFrom.datepicker( "option", "maxDate", selectedDate );
            	      }
            	 }));

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
                      null, null, null, null,null, null, null, null,
                      { "bSortable": false }
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
