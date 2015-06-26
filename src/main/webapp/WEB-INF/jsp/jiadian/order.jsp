<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_quick_order.jsp" %>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/jquery-ui.min.css" />
	 <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/jquery.ui.datepicker.min.css" />
    <style type="text/css">
    
    body{
    	  background: #FDAA25;
    	    padding-top: 2em;
    }
    	
    #container{ 
    	font-size: 16px; 
    	font-family: 'Microsoft Yahei',Arial; 
    	max-width:1000px; 
    	min-width:800px; 
    	margin:0 auto; 
    	border-radius:.5em;
    	background: #19A0D9;
    	-webkit-box-box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
    	-moz-box-box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
    	box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
    }
    
    #container .row{
    	margin-top: .5em;
    	margin-bottom: .5em;
    }
    
    #container .table{
      background: #B2A7A4;
  		font-size: .8em;
  	}

    .category{/*类目选择*/
    }

    .category-list{
    	display: none;
    }

    .category-list-item{
    	font-size: 12px;
		border: 2px solid #b3b3b3;
		border-radius: .5em;
		padding: .5em;
		cursor: pointer;
		background: #A7C3D2;
    }

    .category-list-item:hover{
    	border: 2px solid #f0ad4e;
    }

    .category-list-item.checked{
    	border: 2px solid #5cb85c;
    	font-weight: bold;
    	color: #5cb85c;
    	background: #F0B061
    }

    .category-list-item .item-name{
    	padding-left: 1em;
  		float: left;
    }

    .category-list-item .item-price{
    	padding-right: 1em;
  		float: right;
    }

    .category-list-item input{
    	text-align: center;
    }
    
    .table .glyphicon-minus{
    	background:red;
    	color:white;
    	cursor:pointer;
    }
    
    #list_total{
    	text-align:center;
    }
    
    #list_total span{
      	text-decoration: underline;
    }
    
    #ui-datepicker-div{
    	background: white;
    }

    </style>
    
	<div class="row"  id="container">
		<form class="col-md-12" action='<c:url value="/order/jiadian" />' method="post" enctype="multipart/form-data" onsubmit="return check()">
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">客户姓名</span>
						<input type="text" class="form-control" name="customerName">
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">联系电话</span>
						<input type="text" class="form-control" name="customerPhone">
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">服务日期</span>
						<input type="text" class="form-control" name="serviceDate" id="serviceDate">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon">详细地址</span>
						<input type="text" class="form-control" name="customerAddress">
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<table  class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>种类</th>
								<th>单价</th>
								<th>数量</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>			
						</tbody>
					</table>
				</div>
			</div><!-- /.row -->
			<div class="row">
				<div class="col-md-12">
					<div id="list_total"><label>共计：</label><span>0</span>    <button type="submit" class="btn btn-primary  btn-xs">提交订单</button></div>
				</div>
			</div><!-- /.row -->
			<div class="row">
				<div class="col-md-12" id="category_choose_area">
					<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
						<button class="category btn" type="button" data-category="<c:out value="${serviceItem.id }" />"><c:out value="${serviceItem.name }" /></button>
					</c:forEach>
				</div>
			</div>

			<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
			<div class="category-list row" id='category_list_<c:out value="${serviceItem.id }" />'>
				<div class="col-md-12">
					<div class="row"><div class="col-md-12">
					<c:forEach var="subItem" items="${serviceItem.sub }">
						<div style="width:30%;display:inline-block;">
							<div class="category-list-item" data-item-id='<c:out value="${subItem.id}" />'>
								<span class="item-name"><c:out value="${subItem.name }" /></span>
								<span class="item-price"><c:out value="${subItem.price }" />元/台</span>
								<div class="input-group" style="clear: both;">
									<span class="input-group-btn">
										<button class="btn btn-default btn-value-auto btn-value-auto-sub" type="button">-</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default btn-value-auto btn-value-auto-add" type="button">+</button>
									</span>
								</div>
							</div>
						</div>
					</c:forEach>
					</div></div>
					<div class="row">
						<div class="col-md-12" style="text-align:center;">
							<button type="button" class="btn-addTo btn btn-success">
							  <span class="glyphicon glyphicon-ok"></span>加入
							</button>
						</div>
					</div><!-- /.row -->
					
				</div>
			</div><!-- /.category_list -->
			</c:forEach>	
		</form>
	</div>
<%@include file="/WEB-INF/jsp/common/store_footer_quick_order.jsp" %>
<script type="text/javascript">
var jiadianServiceItems = {};
<c:forEach var="serviceItem" items="${requestScope.serviceItems }">
jiadianServiceItems['<c:out value="${serviceItem.id }" />']={ name:'<c:out value="${serviceItem.name }" />', sub:{} };
<c:forEach var="subItem" items="${serviceItem.sub }">
jiadianServiceItems['<c:out value="${serviceItem.id }" />']['sub']['<c:out value="${subItem.id }" />'] = { id:<c:out value="${subItem.id }" />, name:'<c:out value="${subItem.name }" />', price:<c:out value="${subItem.price }" />};
</c:forEach>
</c:forEach>
</script>
<script type="text/javascript">

	$.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );

	//
	// 提交前的审核
	//
	function check(){
		
	}

	//
	// 显示类目下的具体项目
	// 
	function showCategoryList( category ){
		console.log( category );
		// 1.隐藏所有
		$('.category-list').hide();
		// 2.显示本类目
		$('#category_list_' + category).show();
	}
	
	//
	// 获取当前的清洗类目
	//
	function getCurrentCategory(){
		var categoryName = $('#category_choose_area').find('.category.btn-success').html();
		return categoryName;
	}
	
	//
	// 计算总额
	//
	function countTotal(){
		var total = 0;
		$('.table > tbody > tr').each( function(i, tr){
			$tr = $(tr);
			var itemId = $tr.data('item-id');
			var item = getItemById(itemId);
			var count = parseInt( $tr.find('.item-count').html() );
			total += count*item.price;
		});
		return total;
	}
	
	//
	// 刷新总额
	//
	function refreshTotal(){
		var total = countTotal();
		$('#list_total').find('span').html( ' ' + total + ' ' );		
	}
	
	//
	// 刷新编号
	//
	function refreshNo(){
		$('.table > tbody > tr').each( function(i, tr){
			$tr = $(tr);
			$tr.find('td:first').html( i+1 );
		});
	}
	
	//
	//
	//
	function getItemById(itemId){
		
		itemId = parseInt( itemId );
		var item = false;
		// 100及以上的为子项目
		if( itemId > 99 ){
			var categoryId = ( itemId - itemId%100 ) / 100;
			var category = jiadianServiceItems[categoryId];
			if( category ){
				item = category.sub[itemId];
			}
		} else{
			item = jiadianServiceItems[itemId];
		}
		
		return item;
	}

	//
	// 将选择的项目加入到清单中去
	// items: [{id:xxx, name:yyyy, price:zzzz, count:nnnnn}...]
	//
	function addToList( items ){
		
		var category = getCurrentCategory();

		var appendHtml = '';

		var currentRowCount = $('.table > tbody > tr').length;
		$.each( items, function(i, item){
			appendHtml += '<tr data-item-id="' + item.id + '">';
			appendHtml += '<td>' + (currentRowCount + i + 1) + '</td>';
			appendHtml += '<td>' + category + ' - ' + item.name + '<input type="hidden" name="item_name" value="' + item.id + '" /></td>';
			appendHtml += '<td>' + item.price + '</td>';
			appendHtml += '<td class="item-count">' + item.count + '<input type="hidden" name="item_count" value="' + item.count + '" /></td>';
			appendHtml += '<td><span class="glyphicon glyphicon-minus img-circle"></span></td>';
			appendHtml += '</tr>';
		});

		$('.table > tbody').append( appendHtml );
		
		refreshTotal();
	}
	
	$(function(){
		//
		//
		//
		$('#serviceDate').datepicker({
			showOtherMonths: true,
			selectOtherMonths: false,
		    dateFormat: "yy-mm-dd",
		    numberOfMonths: 1	
		});
		// 类目按钮
		$('.category').click(function(){
			var checkedClass = 'btn-success';// 选中样式
			var $this = $(this);
			if( $this.hasClass(checkedClass) ){
				return;
			}
			$('.category').removeClass(checkedClass);
			$this.addClass(checkedClass);

			var category = $this.data('category');
			
			// 清除之前的选择

			showCategoryList( category );
		});

		// 
		// 选择清洁项目
		// 
		$('.btn-value-auto').click(function(e){
			var $this = $(this);
			var $ipt = $this.parent().parent().find('input');
			var value = parseInt( $ipt.val() );
			
			if( $this.hasClass('btn-value-auto-sub') && value > 0 ){
				value = value - 1;
			} else if( $this.hasClass('btn-value-auto-add') ){
				value = value + 1;
			}
			
			$ipt.val( value );
			
			e.stopPropagation();
		});
		
		//
		// 自增自减按钮
		//
		$('.category-list-item').click(function(){
			$(this).toggleClass( 'checked' );
		});

		// 
		// 加入清单
		// 
		$('.btn-addTo').click(function(){

			var categoryList = $(this).parents('.category-list');
			var checkedItems = categoryList.find('.checked');

			var items = [];

			$.each( checkedItems, function(i, checkedItem){
				var item = {};
				var checkedItem = $(checkedItem);
				// {id:xxx, name:yyyy, price:zzzz, count:nnnnn}
				item.id = checkedItem.data('item-id');
				item.name = checkedItem.find('.item-name').html();
				item.price = checkedItem.find('.item-price').html();
				item.count = checkedItem.find('input').val();
				items.push(item);
			});

			if( items.length > 0 )
				addToList( items );
		});
		
		//
		// 删除清单
		//
		$( document ).on('click', '.glyphicon-minus', function(){
			$(this).parents('tr').remove();
			refreshNo();
			refreshTotal();
		});
		
	});
</script>
</html>