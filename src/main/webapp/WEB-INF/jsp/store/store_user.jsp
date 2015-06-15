<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/jsp/common/store_header_menu.jsp" %>

<c:if test="${ requestScope.error_msg != null }">
<div class="row">
	<div class="alert alert-danger">
		<button type="button" class="close" data-dismiss="alert">
			<i class="ace-icon fa fa-times"></i>
		</button>
		<c:out value="${ requestScope.error_msg }" />
		<br />
	</div>
</div><!-- /.row -->
</c:if>
                    
                    <div class="row">
                        <div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												修改密码
											</h4>

											<div class="space-6"></div>

											<form class="form-horizontal" action="<c:url value="/store/user/pwd" />" method="post">
												<fieldset>
													<div class="form-group">
														<label class="col-sm-5 control-label no-padding-right" for="old">请输入旧密码</label>

														<div class="col-sm-6">
															<input type="password" id="old" name="old" class="col-xs-10 col-sm-6" />
														</div>
													</div>
													
													<div class="form-group">
														<label class="col-sm-5 control-label no-padding-right" for="new">请输入新密码</label>

														<div class="col-sm-6">
															<input type="password" id="new" name="new" class="col-xs-10 col-sm-6" />
														</div>
													</div>

													<div class="space"></div>

													<div class="clearfix center">
														<button type="submit" class="width-35 btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">确认修改</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
<!-- PAGE CONTENT ENDS -->
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    
<%@include file="/WEB-INF/jsp/common/store_footer_js.jsp" %>
<!-- page specific plugin scripts -->

</html>
