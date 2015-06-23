<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_quick_order.jsp" %>

    <style type="text/css">
    #container{ font-size: 16px; font-family: 'Microsoft Yahei',Arial;  }

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
    }

    .category-list-item:hover{
    	border: 2px solid #f0ad4e;
    }

    .category-list-item.checked{
    	border: 2px solid #5cb85c;
    	font-weight: bold;
    	background: #5cb85c;
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

    </style>
    
	<div class="row" style="max-width:800px; margin:0 auto;" id="container">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">姓名</span>
						<input type="text" class="form-control">
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">联系电话</span>
						<input type="text" class="form-control">
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">服务日期</span>
						<input type="text" class="form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<select class="form-control" style="display:inline-block; width:30%; ">
						<option>广东省</option>
					</select>
					<select class="form-control" style="display:inline-block; width:30%; ">
						<option>深圳市</option>
					</select>
					<select class="form-control" style="display:inline-block; width:30%; ">
						<option>南山区</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<input class="form-control" type="text" placeholder="地址">
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
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>空调 - 窗机</td>
								<td>100元/台</td>
								<td>3</td>
							</tr>
							<tr>
								<td>2</td>
								<td>空调 - 挂机(内机)</td>
								<td>100元/台</td>
								<td>1</td>
							</tr>
							<tr>
								<td>3</td>
								<td>空调 - 柜机(全套)</td>
								<td>200元/台</td>
								<td>1</td>
							</tr>							
						</tbody>
					</table>
				</div>
			</div><!-- /.row -->

			<div class="row">
				<div class="col-md-12">
					<button class="category btn btn-primary" type="button" data-category="aircondition">空调清洗</button>
					<button class="category btn btn-primary" type="button" data-category="rangehood">油烟机清洗</button>
					<button class="category btn btn-primary" type="button">洗衣机清洗</button>
					<button class="category btn btn-primary" type="button">饮水机清洗</button>
					<button class="category btn btn-primary" type="button">冰箱清洗</button>
					<button class="category btn btn-primary" type="button">电脑清洗</button>
					<button class="category btn btn-primary" type="button">热水器清洗</button>
				</div>
			</div>

			<div class="category-list row" id="category_list_aircondition">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12" style="text-align:center;">
							<button type="button" class="btn-addTo btn btn-success">
							  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>加入
							</button>
						</div>
					</div><!-- /.row -->
					<div class="row">
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">窗机</span>
								<span class="item-price">100元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">挂机(内机)</span>
								<span class="item-price">100元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">挂机(全套)</span>
								<span class="item-price">150元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">柜机(内机)</span>
								<span class="item-price">150元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
					</div><!-- /.row -->
					<div class="row">
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">柜机(全套)</span>
								<span class="item-price">200元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">天花机(内机)</span>
								<span class="item-price">250元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">天花机(全套)</span>
								<span class="item-price">300元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">风管机(内机)</span>
								<span class="item-price">300元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
					</div><!-- /.row -->
				</div>
			</div><!-- /.category_list -->

			<div class="category-list row" id="category_list_rangehood">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12" style="text-align:center;">
							<button type="button" class="btn-addTo btn btn-success">
							  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>加入
							</button>
						</div>
					</div><!-- /.row -->
					<div class="row">
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">中式</span>
								<span class="item-price">150元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">欧式</span>
								<span class="item-price">150元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">侧吸式</span>
								<span class="item-price">150元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="category-list-item">
								<span class="item-name">酒店式</span>
								<span class="item-price">1000元/台</span>
								<div class="input-group">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">+</button>
									</span>
									<input type="text" class="form-control" value="1">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">-</button>
									</span>
								</div>
							</div>
						</div>
					</div><!-- /.row -->
				</div>
			</div><!-- /.category_list -->

		</div>
	</div>
<%@include file="/WEB-INF/jsp/common/store_footer_quick_order.jsp" %>
<script type="text/javascript">
</script>
</html>