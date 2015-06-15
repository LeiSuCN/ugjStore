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

									<div class="alert alert-block alert-success">
										<button type="button" class="close" data-dismiss="alert">
											<i class="ace-icon fa fa-times"></i>
										</button>

										<p>
											<strong>
												<i class="ace-icon fa fa-check"></i>
												您的注册申请已经被受理，请您耐心等待管理员审核!
											</strong>
										</p>

									</div>
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
        <script src="<%= request.getContextPath() %>/portal/js/ace-elements.min.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/ace.min.js"></script>

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

				var errorCount = 0;

				$('.input-check').each(function(idx, ele){

					var $ipt = $(this);

					if( !inputCheck( $ipt ) ) errorCount++;

				});


				return errorCount == 0;
			}
		
			jQuery(function($) {
				$('.ugj-input-file').ace_file_input({
					style:'well',
					btn_choose:'请上传扫描件',
					btn_change:null,
					no_icon:'ace-icon fa fa-cloud-upload',
					droppable:true,
					thumbnail:'small'//large | fit
					//,icon_remove:null//set null, to hide remove/reset button
					/**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
					/**,before_remove : function() {
						return true;
					}*/
					,
					preview_error : function(filename, error_code) {
						//name of the file that failed
						//error_code values
						//1 = 'FILE_LOAD_FAILED',
						//2 = 'IMAGE_LOAD_FAILED',
						//3 = 'THUMBNAIL_FAILED'
						//alert(error_code);
					}
			
				}).on('change', function(){
					//console.log($(this).data('ace_input_files'));
					//console.log($(this).data('ace_input_method'));
					var $ipt = $(this);

					inputCheck( $ipt );
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
				
				<c:forEach var="field" items="${requestScope.error_fields }">
				inputCheck( $('input[name=<c:out value="${field}" />]') );
				</c:forEach>
			});
		</script>
	</body>
</html>
