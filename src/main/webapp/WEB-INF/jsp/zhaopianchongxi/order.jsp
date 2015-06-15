<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>
                   
                    <div class="row">
                        <form class="col-xs-12" action='<c:url value="/order/zhaopianchongxi" />' method="post" enctype="multipart/form-data" onsubmit="return check()">
<!-- PAGE CONTENT BEGINS -->
												
													<c:if test="${ requestScope.error_msg != null }">
													<div class="alert alert-danger">
														<button type="button" class="close" data-dismiss="alert">
															<i class="ace-icon fa fa-times"></i>
														</button>
														<c:out value="${ requestScope.error_msg }" />
														<br />
													</div>
													</c:if>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">客户信息</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">
		
			<div class="row">
				<div class="col-xs-4">
					<label class="label label-xlg label-warning">客户姓名:</label>
					<input type="text" class="" id="customerName" name="customerName" value='<c:out value="${ requestScope.order.customerName }" />' />
				</div>
				<div class="col-xs-4">
					<label class="label label-xlg label-warning">客户电话:</label>
					<input type="text" class="" id="customerPhone" name="customerPhone" value='<c:out value="${ requestScope.order.customerName }" />' />
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-1">
					<label class="label label-xlg label-warning">客户地址:</label>
				</div>
				<div class="col-xs-10">
					<input type="text" class="limited" style="display:block; width:100%; " maxlength="120" id="customerAddress"  name="customerAddress" value='<c:out value="${ requestScope.order.customerAddress }" />' />
				</div>
			</div>

		</div>
	</div>
</div>

<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title">订单信息</h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">			
			<div class="row">
				<div class="col-xs-1">
					<label class="label label-xlg label-warning">照片压缩包</label>
				</div>
				<div class="col-xs-10">
					<input type="file" id="imgRar" name="imgRar" />
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="table-header">订单明细
						<a href="#modal-table"  class="btn btn-warning btn-xs" data-toggle="modal">
							<i class="ace-icon fa fa-plus  bigger-110 icon-only"></i>
						</a>
					
					</div>
					<div>
				<table id="itemsTbl" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>尺寸</th>
							<th>单价</th>
							<th>数量</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
					</div>
				</div>

			</div>
			<hr />
			<div>
				<label for="customerAddress">订单总价</label>
				<div class="input-group">
					<span class="input-group-addon"><i class="ace-icon fa fa-cny"></i></span>
					<input class="form-control input-mask-phone" type="text" id="totalPrice" disabled="disabled"/>
				</div>
			</div>


		</div>
	</div>
</div>
<div style="text-align: center;">
	<h3 class="header smaller lighter grey" style="display:none" id="progress">
			<i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>订单正在提交，请稍候
	</h3>
	<button class="btn btn-lg btn-success" id="submitBtn" type="submit">提交</button>
</div>

<div id="modal-table" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header no-padding">
				<div class="table-header">
					<button type="button" class="close" data-dismiss="modal" >
						<span class="white">&times;</span>
					</button>
					添加新内容
				</div>
			</div>

			<div class="modal-body" style="text-align:center;">
			<!-- 
		<ul id="serviceItems">
		<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
			<li class="service-item" data-size="<c:out value="${ serviceItem.id}"  />" data-price="<c:out value="${ serviceItem.price}"  />" data-name="<c:out value="${ serviceItem.name}" />">
				<c:out value="${ serviceItem.name}" />: <c:out value="${ serviceItem.price}"  /> 元/张
			</li>
		</c:forEach>
		</ul>
		 -->
		 		<label>名称：</label>
		 		<input type="text" id="nameTxt" />
		 		<label>尺寸：</label>
		 		<select  id="serviceItems">
				<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
					<option value=<c:out value="${ serviceItem.id}"  /> >
						<c:out value="${ serviceItem.name}" />(<c:out value="${ serviceItem.price}"  /> 元/张)
					</option>
				</c:forEach>
		 		</select>
		 		<label>数量：</label>
				<input type="text" class="input-mini" id="countTxt" value="1"/>
			</div>

			<div class="modal-footer no-margin-top" style="text-align:center;">
				<button class="btn btn-sm btn-info" data-dismiss="modal" id="addBtn">
					<i class="ace-icon fa fa-plus"></i>
												添加
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div>

<!-- PAGE CONTENT ENDS -->
                        </form><!-- /.col -->
                    </div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>
<!-- page specific plugin scripts -->
<style type="text/css">
ul, li{ text-decoration: none;list-style-type:none }
.service-item{
  float: left;
  padding: 5px;
  margin: 5px;
  border: 1px solid #8B8B8B;
  cursor: pointer;
}

.service-item:HOVER {
	border: 1px solid  red;
}

.service-item.choosed{
	border: 1px solid  red;
}

hr{
	margin: 5px 0px;
}
</style>

<script src="<%= request.getContextPath() %>/portal/js/bootbox.min.js"></script>

<script type="text/javascript">

	var serviceItems = {};
	<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
	serviceItems[<c:out value="${ serviceItem.id}"  />] = {
			name: ' <c:out value="${ serviceItem.name}" /> ',
			price: <c:out value="${ serviceItem.price}" />
	}
	</c:forEach>
	
	
	
	var customerNameTxt = $('#customerName');
	var customerPhoneTxt = $('#customerPhone');
	var customerAddressTxt = $('#customerAddress');
	var imgIpt = $('#imgRar');
	var countIpt = $('#countTxt');
	var nameIpt = $('#nameTxt');
	var itemsTbl = $('#itemsTbl');
	var priceTxt = $('#totalPrice');
	var processBar = $('#progress');
	
	function check(){

		if( !customerNameTxt.val() ){
 			popMsg('客户姓名不能为空！');
 			return false;
 		}
 		
 		if( !customerPhoneTxt.val() ){
 			popMsg('客户电话不能为空！');
 			return false;
 		}
 		
 		if( !customerAddressTxt.val() ){
 			popMsg('客户地址不能为空！');
 			return false;
 		}
 		
 		if( !imgIpt.val() ){
 			popMsg('请选择照片压缩包！');
 			return false;
 		}
 		
 		var items = getOrderItems();
 		if( !items || items.length <= 0 ){
 			popMsg('请添加订单内容！');
 			return false;
 		}

 		processBar.show();
 		$('#submitBtn').hide();
		
		return true;
	}
	
	function popMsg(msg){
		bootbox.dialog({
			message: "<span class='bigger-110'>" + msg + "</span>",
			buttons: 			
			{
				"button" :
				{
					"label" : "<i class='ace-icon fa fa-check'></i>确认",
					"className" : "btn-sm btn-primary"		
				}
			}
		});		
	}

	/**
	 * 计算总价
	 */
	function computeTotal(){

		var total = 0;

		$('#itemsTbl > tbody > tr ').each( function(){
			var itemTotal = $( this ).data('total');
			total += itemTotal;
		});
		priceTxt.val(total.toFixed(2));
	}


	function addNewItem(){
		
		var serviceItemId = $("#serviceItems").val();
		var serviceItem = serviceItems[serviceItemId];
		var price = serviceItem.price;
		var size = serviceItem.name;
		var name = nameIpt.val();
		var count = 0;
		if( !countIpt.val() || isNaN(countIpt.val()) ){
			alert( '请输入一个合法的数字' );
			return;
		} else{
			count = parseInt( countIpt.val() );
		}

		var tb = $('#itemsTbl > tbody ');
		
		var tr = $('<tr></tr>');
		tr.data('total', price*count);
		var tds = '';
		tds += '<td>' + name + '<input type="hidden" name="item_name" value="' + name + '"></td>';
		tds += '<td>' + size + '<input type="hidden" name="item_size" value="' + serviceItemId + '"></td>';
		tds += '<td>' + price + '<input type="hidden" name="item_price" value="' + price + '"></td>';
		tds += '<td name="item_count">' + count + '<input type="hidden" name="item_count" value="' + count + '"></td>';
		tds += '<td><button class="btn btn-xs btn-danger"><i class="ace-icon fa fa-trash-o bigger-120"></i></button></td>';
		tr.html( tds );
		tb.append( tr );
		
		computeTotal() ;
	}

	function getOrderItems(){

		var items = [];

		$('#itemsTbl > tbody > tr ').each( function(){
			var tr = $( this );
			var item = {};
			item.size = tr.attr('size');
			tr.find('td').each( function(idx,ele){
				
				if( idx == 2 ){
					item.amount = $( ele ).html();
				}

			});
			items.push( item );
		});
		return items;
	}

 $(document).ready(function() {
	
	 currentBreadcrumb([ { name:"订单管理", href:"#"}
		, { name:"我要下单", href:"#"} 
		, { name:"数码照片冲洗", href:"#"} ]);
	 
	$('#imgRar').ace_file_input({
			no_file:'请选择压缩包',
			btn_choose:'选择',
			btn_change:'重选',
			droppable:false,
			onchange:null,
			thumbnail:false
	});

 
 	$('.service-item').click(function(){
		$(this).toggleClass('choosed');
	});
 	
 	itemsTbl.on('click', '.btn-danger', function(){
 		$(this).parent().parent().remove();
 		computeTotal() ;
 	});
 	
 	$('#addBtn').click(function(){ 
 		addNewItem() ;
 		computeTotal() ;
 	});
 });
</script>
</html>
