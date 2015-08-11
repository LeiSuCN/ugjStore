<!DOCTYPE html>
<%@page import="java.util.Map"%>
<%@page import="com.uguanjia.o2o.service.xiaobaitu.XbtService"%>
<%@page import="com.uguanjia.o2o.service.xiaobaitu.XbtServiceItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>
                   
                    <div class="row">
						<form class="col-xs-12" action='<c:url value="/order/xiaobaitu" />' method="post" enctype="multipart/form-data" onsubmit="return check()">
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
			<div class="control-group">
				<label class="label label-xlg label-warning">包装要求:</label>
					<div class="radio" style="display:inline-block">
						<label>
							<input name="pack" type="radio" class="ace"  value="袋装"/>
							<span class="lbl"> 袋装</span>
						</label>
					</div>
					<div class="radio" style="display:inline-block">
						<label>
							<input name="pack" type="radio" class="ace"  value="盒装"/>
							<span class="lbl"> 盒装</span>
						</label>
					</div>
			</div>
			<div class="control-group">
				<label class="label label-xlg label-warning">支付方式:</label>
					<div class="radio" style="display:inline-block">
						<label>
							<input name="payment" type="radio" class="ace" value="1" />
							<span class="lbl"> 转账</span>
						</label>
					</div>
					<div class="radio" style="display:inline-block">
						<label>
							<input name="payment" type="radio" class="ace" value="2" />
							<span class="lbl"> 洗衣券</span>
						</label>
					</div>
					<div class="radio" style="display:inline-block">
						<label>
							<input name="payment" type="radio" class="ace" value="3" />
							<span class="lbl"> 洗衣卡号</span>
						</label>
					</div>
			</div>
			<div class="control-group">
				<label class="label label-xlg label-warning">支付凭证:</label>
					<div class="radio" style="display:inline-block">
						<label>
							<input type="text" id="voucher" name="voucher" style="width:30em" placeholder="转账流水号，支付宝流水号，洗衣券号，洗衣卡号等"/>
						</label>
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
							<th>单价</th>
							<th>数量</th>
							<th>总价</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
					</div>
				</div>

			</div>
						
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
				<select id="cateSel" class=""></select>
				<select id="subSel" class=""></select>
				<input type="text" class="input-mini" id="countTxt" placeholder="数量"/>
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
<script src="<%= request.getContextPath() %>/portal/js/bootbox.min.js"></script>

<script type="text/javascript">
<%
XbtService service = (XbtService)request.getAttribute("service");
Map<Integer, XbtServiceItem> categories = service.getItems();
%>
//服务项目
var _category = {
		   
		   <%
		   boolean isFirstCategory = true;
			for( XbtServiceItem category : categories.values() ){
				
				if( isFirstCategory ){
					isFirstCategory = false;
				} else{
					out.print(",");
				}
				
				out.println("'" + category.getId() +  "' : {");
				out.println("    id  : " + category.getId() + "000,");
				out.println("    name  : '" + category.getName() + "',");
				out.println("    sub : [");
				
				boolean isFirstItem = true;
				for( XbtServiceItem item : category.getItems().values() ){
					
					if( isFirstItem ){
						isFirstItem = false;
					} else{
						out.print(",");
					}
					out.println("{ id: '" + item.getId() + "', name:'" + item.getName() + "', price:" + item.getPrice() + " }");
				}
				out.println("    ]");
				out.println("}");
			}
		   %>
};
	
	var customerNameTxt = $('#customerName');
	var customerPhoneTxt = $('#customerPhone');
	var customerAddressTxt = $('#customerAddress');
	var packRdo = $('input[name=pack]');
	var payRdo = $('input[name=payment]');
	var voucherTxt = $('#voucher');
	var cateSel = $('#cateSel');
	var subSel  = $('#subSel');
	var countIpt = $('#countTxt');
	var itemsTbl = $('#itemsTbl');
	var priceTxt = $('#totalPrice');

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
 		
 		if( !voucherTxt.val() ){
 			popMsg('支付凭证不能为空！');
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
	 * 创建类别select的options
	 */
	function createCategorySelectOptions( sele ){

		var sele = $( sele );

		sele.empty();

		for( var cateId in _category ){
			var option = $('<option value="' + cateId + '">' + _category[cateId].name + '</option>');

			sele.append( option );
		}
	}

	/**
	 * 更新项目选择select的option
	 */
	function createSubSelectOptions( cateId, sele ){

		var sele = $( sele );
		var cate = _category[cateId];

		if( !cate ){
			return;
		} 

		sele.empty();

		for( var i = 0 ; i < cate.sub.length ; i++ ){
			var sub = cate.sub[i];
			var option = $('<option value="' + sub.id + '">' + sub.name + '</option>');

			sele.append( option );
		}
	}

	/**
	 * 加入新的衣物
	 */
	function addNewItem(){

		var sub = getSubItem(subSel.val());

		var count = 0;
		if( !countIpt.val() || isNaN(countIpt.val()) ){
			alert( '请输入一个合法的数字' );
			return;
		} else{
			count = parseInt( countIpt.val() );
		}

		var tb = $('#itemsTbl > tbody ');

		var tr = $('<tr></tr>');
		tr.attr('item', sub.id);
		var tds = '';
		tds += '<td>' + sub.name + '<input type="hidden" name="item_name" value="' + sub.id + '" /></td>';
		tds += '<td>' + sub.price + '</td>';
		tds += '<td>' + count + '<input type="hidden" name="item_count" value="' + count + '" /></td>';
		tds += '<td class="price-count">' + ( sub.price * count ) + '</td>';
		tds += '<td><button class="btn btn-xs btn-danger"><i class="ace-icon fa fa-trash-o bigger-120"></i></button></td>';
		tr.html( tds );
		tb.append( tr );
	}

	/**
	 * 计算总价
	 */
	function computeTotal(){

		var total = 0;

		$('#itemsTbl > tbody > tr > td.price-count').each( function(){
			var priceTd = $( this );
			total += parseInt( priceTd.html() );
		});
		priceTxt.val(total);
	}

	/**
	 * 根据子项Id获取子项配置
	 */
	function getSubItem( subId ){

		if( !subId ) return false;

		var len = subId.length;
		
		var cateId = subId.substr(0, len - 3);

		if( _category[cateId] ){
			var sub = _category[cateId].sub;
			for( var i = 0 ; i < sub.length ; i++ ){
				if( sub[i].id == subId )return sub[i];
			}
		}

		return false;
	}

	function getOrderItems(){

		var items = [];

		$('#itemsTbl > tbody > tr ').each( function(){
			var tr = $( this );
			var item = {};
			item.name = tr.attr('item');
			tr.find('td').each( function(idx,ele){
				
				if( idx == 2 ){
					item.count = $( ele ).html();
				}

			});
			items.push( item );
		});
		return items;
	}

 $(document).ready(function() {
	
	 currentBreadcrumb([ { name:"订单管理", href:"#"}
		, { name:"我要下单", href:"#"} 
		, { name:"小白兔专业干洗", href:"#"} ]);
 
 	createCategorySelectOptions( cateSel );
 	createSubSelectOptions( cateSel.val(), subSel );

 	cateSel.change( function() { createSubSelectOptions( cateSel.val(), subSel ); });
 	
 	itemsTbl.on('click', '.btn-danger', function(){
 		$(this).parent().parent().remove();
 		computeTotal() ;
 	});
 	
 	$('#addBtn').click(function(){ 
 		addNewItem() ;
 		computeTotal() ;
 	});
 
 	$('#submitBtn1').click(function(){
  		
 		var order = {};
 		order.customerName = customerNameTxt.val();
 		order.customerPhonenumber = customerPhoneTxt.val();
 		order.customerAddress = customerAddressTxt.val();
 		order.payment = $('input[name=pay]:checked').val();
 		order.pack = $('input[name=pack]:checked').val();
			order.items = getOrderItems();
 		$.ajax({
				type: "POST",
				dataType: 'json',
				contentType: 'application/json; charset=utf-8',
				url: '<c:url  value="/order/xiaobaitu/create" />',
				data: JSON.stringify(order),
				success: function( resp){
					
					if( resp && resp.code == 0 ){
						 var r = confirm("订单提交成功！");
		  				 if( r == true ){
		  				 	location.href = "<c:url  value="/store/order/detail/" />" + resp.message;
		  				 }
					} else{
						alert( resp.message )
					}
				}
			});
 		
 		return false;
 	});

 });
</script>
</html>
