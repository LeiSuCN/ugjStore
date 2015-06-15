<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>注册</title>

		<meta name="description" content="用户注册" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-ie.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace.onpage-help.css" />

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%= request.getContextPath() %>/portal/js/html5shiv.js"></script>
		<script src="<%= request.getContextPath() %>/portal/js/respond.min.js"></script>
		<![endif]-->
		
		<style type="text/css">
			.form-group{ clear: both; margin-bottom: 40px; }
		</style>
	</head>
	<body class="login-layout light-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container" style="width:800px;">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">O2O</span>
									<span class="white" id="id-text2">综合服务平台</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; 深圳市优管家商务管理有限公司</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">

								<div id="signup-box" class="signup-box widget-box no-border visible">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												新用户注册
											</h4>

											<form action="<%= request.getContextPath() %>/public/reregister" method="post" enctype="multipart/form-data" onsubmit="return register()">
												<fieldset>
												
													<c:if test="${ requestScope.error_msg != null }">
													<div class="alert alert-danger">
														<button type="button" class="close" data-dismiss="alert">
															<i class="ace-icon fa fa-times"></i>
														</button>
														<c:out value="${ requestScope.error_msg }" />
														<br />
													</div>
													</c:if>
													
													<div class="form-group">
														<label for="storename" class="col-xs-12 col-sm-2 control-label no-padding-right">门店名称</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="storename" name="storename" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.name }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="cainiao" class="col-xs-12 col-sm-2 control-label no-padding-right">菜鸟编号（可选）</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="cainiao" name="cainiao" class="width-100" 
																	value='<c:out value="${requestScope.cainiao }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="registrationNo" class="col-xs-12 col-sm-2 control-label no-padding-right">工商营业执照编号</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id=registrationNo name="registrationNo" rules="empty int length 15" class="width-100 input-check" 
																	value='<c:out value="${store.registrationNo}" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="legalPerson" class="col-xs-12 col-sm-2 control-label no-padding-right">法定代表人</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="legalPerson" name="legalPerson" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.legalPerson}" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="phonenumber" class="col-xs-12 col-sm-2 control-label no-padding-right">电话</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="phonenumber" name="phonenumber" rules="empty mobile" class="width-100 input-check" 
																	value='<c:out value="${store.phonenumber }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group" style="  margin-bottom: 70px;">
														<label for="address" class="col-xs-12 col-sm-2 control-label no-padding-right">地址</label>
														<div class="col-xs-12 col-sm-8">
														
															<select id="address_province" name="address_province">
																<option value="44">广东省</option>
															</select>
															<select  id="address_city" name="address_city">
																<option value="03">深圳市</option>
															</select>
															<select id="address_area" name="address_area">
																<option value="01">宝安区</option>
																<option value="02">南山区</option>
																<option value="03">福田区</option>
																<option value="04">龙岗区</option>
																<option value="05">罗湖区</option>
																<option value="06">龙华新区</option>
															</select>
															
														
															<span class="block input-icon input-icon-right">
																<input type="text" id="address" name="address" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.address }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label class="col-xs-12 col-sm-2 control-label no-padding-right">GPS位置</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="location" name="location" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.lon }" />, <c:out value="${store.lat }" />' />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>	
													
													<div class="form-group hide" id="bm" style="height:400px;" >
													</div>	
													
													<div class="form-group">
														<label for="alipay" class="col-xs-12 col-sm-2 control-label no-padding-right">支付宝</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="alipay" name="alipay" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.alipay }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="accountName" class="col-xs-12 col-sm-2 control-label no-padding-right">银行账户户名</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="accountName" name="accountName" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.account.name }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="accountNumber" class="col-xs-12 col-sm-2 control-label no-padding-right">账号</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="accountNumber" name="accountNumber" rules="empty int" class="width-100 input-check" 
																	value='<c:out value="${store.account.number }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<div class="form-group">
														<label for="bank" class="col-xs-12 col-sm-2 control-label no-padding-right">开户行</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="bank" name="bank" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.account.bank }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<div class="form-group">
														<label for="qq" class="col-xs-12 col-sm-2 control-label no-padding-right">QQ</label>
														<div class="col-xs-12 col-sm-8">
															<span class="block input-icon input-icon-right">
																<input type="text" id="qq" name="qq" rules="empty" class="width-100 input-check" 
																	value='<c:out value="${store.qq }" />'  />
																<i class="ace-icon fa fa-pencil-square-o"></i>
															</span>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<div class="form-group">
														<label for="idcard_a" class="col-xs-12 col-sm-2 control-label no-padding-right">法人身份证（正面）</label>
														<div class="col-xs-6 col-sm-6">
															<input type="file" id="idcard_a" name="idcard_a" class="ugj-input-file" />
														</div>
														<div class="col-xs-2 col-sm-2">
															<a class="btn btn-xs" target="_blank" href=" <c:url value="/store/detail/scanning/idcard_a" /> ">查看原图</a>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="idcard_b" class="col-xs-12 col-sm-2 control-label no-padding-right">法人身份证（反面）</label>
														<div class="col-xs-6 col-sm-6">
															<input type="file" id="idcard_b" name="idcard_b" class="ugj-input-file" />
														</div>
														<div class="col-xs-2 col-sm-2">
															<a class="btn btn-xs">查看原图</a>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<div class="form-group">
														<label for="license" class="col-xs-12 col-sm-2 control-label no-padding-right">营业执照</label>
														<div class="col-xs-6 col-sm-6">
															<input type="file" id="license" name="license" class="ugj-input-file" />
														</div>
														<div class="col-xs-2 col-sm-2">
															<a class="btn btn-xs">查看原图</a>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<div class="form-group">
														<label for="store_a" class="col-xs-12 col-sm-2 control-label no-padding-right">店面照片（外景）</label>
														<div class="col-xs-6 col-sm-6">
															<input type="file" id="store_a" name="store_a" class="ugj-input-file" />
														</div>
														<div class="col-xs-2 col-sm-2">
															<a class="btn btn-xs">查看原图</a>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>
													
													<div class="form-group">
														<label for="store_b" class="col-xs-12 col-sm-2 control-label no-padding-right">店面照片（内景）</label>
														<div class="col-xs-6 col-sm-6">
															<input type="file" id="store_b" name="store_b" class="ugj-input-file" />
														</div>
														<div class="col-xs-2 col-sm-2">
															<a class="btn btn-xs">查看原图</a>
														</div>
														<div class="help-block col-xs-12 col-sm-reset inline"></div>
													</div>

													<label class="block center">
														<input type="checkbox" class="ace" id="aggreeChk" />
														<span class="lbl">
															我接受并同意
															<a target="_blank" href="<%= request.getContextPath() %>/file/ugj.doc">优管家协议</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">重置</span>
														</button>

														<button type="submit" class="width-65 pull-right btn btn-sm btn-success" disabled="disabled">
															<span class="bigger-110">注册</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="<%= request.getContextPath() %>/public/my" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												返回登录
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
       <script src="<%= request.getContextPath() %>/portal/js/bootstrap.min.js"></script>

        <!-- ace scripts -->
        <script src="<%= request.getContextPath() %>/portal/js/ace.min.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/ace-elements.min.js"></script>

        <!-- inline scripts related to this page -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace.onpage-help.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/js/themes/sunburst.css" />

        <script type="text/javascript"> ace.vars['base'] = '..'; </script>
        <script src="<%= request.getContextPath() %>/portal/js/ace/ace.onpage-help.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/rainbow.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/generic.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/html.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/css.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/javascript.js"></script>
        
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=nsNo6385eG8L0jfcaFPVThXX"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/js/locationPicker.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		
			function errorMsg(formGroup, msg){
				
				formGroup.removeClass('has-success').addClass('has-error');
				
				formGroup.find('.ace-icon').removeClass('fa-check-circle').addClass('fa-times-circle');
				
				formGroup.find('.help-block').html(msg);
				
			}
			
			function succMsg( formGroup ){
				
				formGroup.removeClass('has-error').addClass('has-success');
				formGroup.find('.ace-icon').removeClass('fa-times-circle').addClass('fa-check-circle');
				formGroup.find('.help-block').empty();
				
				
			}

			function inputCheck( ipt ){

					var result = false;

					var $ipt = $(ipt);

					var $formGroup = $ipt.parents('.form-group');

					var rules = $ipt.attr( 'rules' );

					if( !rules ) return;

					rules = rules.split(' ');

					if( rules.length  == 0 ) return;

					for( var i = 0 ; i < rules.length ; i++ ){

						var rule = rules[i];

						var error = false;

						var val = $ipt.val();

						if( rule == 'empty' ){
							if( !val )
								error = "不能为空！";
						} else if( rule == 'int' ){

							if( parseInt(val) != val ){
								error = "必须为整数数字！";
							}

						} else if( rule == 'length' ){

							var len = parseInt(rules[++i]);

							if( val.length != len ){
								error = "长度必须为" + len + "！";
							}
						} else if( rule == 'mobile' ){

							
							if( parseInt(val) != val || val.length != 11){
								error = "非法的手机号码格式！";
							}
						}

						if( error ){	
							result = false;						
							errorMsg( $formGroup, error );							
							break;
						} else{
							result = true;
							succMsg($formGroup);
						}
					}

					return result;
			}

			function register(){
				
				//return true;
				
				$('button[type=submit]').attr('disabled', 'disabled');
				
				$('button[type=submit]').find('span').html('资料正在提交,请稍后...');

				var errorCount = 0;

				$('.input-check').each(function(idx, ele){

					var $ipt = $(this);

					if( !inputCheck( $ipt ) ) errorCount++;

				});


				return errorCount == 0;
			}
		
			var cpoint;
			
			var locationPicker;
			
			function toCenter(){
				locationPicker.setCenter(cpoint);
			}
			
			jQuery(function($) {
				$('.ugj-input-file').ace_file_input({
					no_file:'重新上传扫描件',
					btn_choose:'选择',
					btn_change:'重选',
					droppable:false,
					onchange:null,
					thumbnail:false //| true | large
					//whitelist:'gif|png|jpg|jpeg'
					//blacklist:'exe|php'
					//onchange:''
					//
			
				});
				
				$('.input-check').blur( function(){

					var $ipt = $(this);

					inputCheck( $ipt );
				});

				$('#aggreeChk').change(function(){

					var isAggreed = $(this).is(':checked');

					if( isAggreed ){
						$('button[type=submit]').removeAttr('disabled');
					} else{
						$('button[type=submit]').attr('disabled', 'disabled');
					}

				});
				
				
				cpoint = new BMap.Point(<c:out value="${store.lon}" />, <c:out value="${store.lat}" />);
				
				locationPicker = new LocationPicker('bm',{
					pick: function(point){
						locationPicker.setCenter(point);
						$('#location').val( point.lng + ', ' + point.lat);
					},
					center: cpoint,
					zoom: 20
				});
				
				locationPicker.pick( cpoint );
				
				var adjust = true;
				
				$('#location').click(function(e){
					e.preventDefault();
					
					var mc = $("#bm");
					
					if( mc.hasClass('hide') ){
						mc.removeClass('hide');
					} else{
						mc.addClass('hide');
					}
					
					if( adjust ){
						adjust = false;
						setTimeout(" toCenter() ",1500);
					}
				});
				
				<c:forEach var="field" items="${requestScope.error_fields }">
				inputCheck( $('input[name=<c:out value="${field}" />]') );
				</c:forEach>
			});
		</script>
	</body>
</html>
