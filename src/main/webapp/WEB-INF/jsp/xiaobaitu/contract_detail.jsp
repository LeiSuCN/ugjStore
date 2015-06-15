<%@page import="com.uguanjia.o2o.Operator"%>
<%@page import="com.uguanjia.o2o.meta.Roles"%>
<%@page import="com.uguanjia.o2o.meta.ContractStatus"%>
<%@page import="com.uguanjia.o2o.service.xiaobaitu.XiaobaituFiles"%>
<%@page import="com.uguanjia.o2o.service.xiaobaitu.XiaobaituContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	XiaobaituContract contract = (XiaobaituContract)request.getAttribute("contract");
	Operator operator = (Operator)request.getAttribute("operator");
	boolean existed = (contract != null);
	if( contract == null )contract = new XiaobaituContract();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 小白兔干洗 - 协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<link rel="stylesheet" type="text/css" href="<%=root %>/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=root %>/style/header.css">
<style type="text/css">
body, div, ul, li{ padding: 0px; margin: 0px; }
ul, li{ text-decoration: none;list-style-type:none }

.contract-content-container{
	padding-top:10px;
	padding-left:30px;
}

.contract-content-container div{
	margin-top:10px;
}

.contract-content-container  label{
	display: inline-block;
	width: 100px;
}

.contract-content-container  .min-image{
	height: 24px;
}

</style>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
<script src="<%=root %>/js/jquery-ui.js"></script>
<script src="<%=root %>/js/header.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container_header">
	<span>小白兔协议</span>
</div>
<div class="container">
	<form action="<%=root %>/contract/xiaobaitu/apply" method="post" enctype="multipart/form-data" onsubmit="return checkinput()" class="contract-content-container">
		<div>
			<label for="yifang">乙方</label>：<input name="yifang"  id="yifang"  type="text"  value='<%=contract.getYiFang() %>'  />
		</div>
		<div>
			<label for="gongshangzhucehao">工商注册号</label>：<input name="gongshangzhucehao" type="text" value='<%=contract.getGongShangZhuCeHao() %>'  />
		</div>
		<div>
			<label for="fadingdaibiaoren">法定代表人</label>：<input name="fadingdaibiaoren" type="text" value='<%=contract.getFaDingDaiBiaoRen() %>'   />
		</div>
		<div>
			<label for="dianhua">电话</label>：<input name="dianhua" type="text"  value='<%=contract.getDianHua() %>'   />
		</div>
		<div>
			<label for="dizhi">地址</label>：<input name="dizhi" type="text"  value='<%=contract.getDiZhi() %>'   />
		</div>
		<div>
			<label for="youbian">邮编</label>：<input name="youbian" type="text"  value='<%=contract.getYouBian() %>'   />
		</div>
		<div>
			<label for=yingyezhizhao>营业执照</label>：
			<img alt="" src="<%= root %>/contract/xiaobaitu/<%= contract.getId()  %>/scanning/<%= XiaobaituFiles.YINGYEZHIZHAO_CODE %>" class="min-image">
		</div>
		<div>
			<label for=farenshenfenzheng>法人身份证</label>：<input name="farenshenfenzheng" type="file" />
		</div>
		<div style="text-align: center;">
		
		<%
			if( operator.hasRole(Roles.ROLE_ADMIN) &&  contract.getStatus() == ContractStatus.APPLY  ){
		%>
			<%@include file="/WEB-INF/jsp/admin/contract_approve_sub.jsp" %>
		<%
			}  else if(  contract.getStatus() == ContractStatus.ACCEPT  ){
		%><label style='color:red; text-align:center; display:block;width:100%;'>协议已经通过审核</label><% 
			} else {
		%><label style='color:red; text-align:center; display:block;width:100%;'>协议正在审核中</label><% 
			}
		
		%>
		</div>
	</form>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">

	var root = '<%=root%>';
	var contractId = <%= contract.getId() %>;

	function checkinput(){
		if( !$('#yifang').val() ){
			alert( '乙方名称不能为空！' );
			return false;
		}
		
		return true;
	}


    $(document).ready(function() {
    	
    	$('.contract-content-container').find('input[type=text]').addClass('ui-widget-content ui-corner-all').css({
    		width:'600px'
    	});
    	
    	$('.contract-content-container').find('input[type=text]').each(function(){
    		if( $(this).val() == 'null' ) $(this).val("");
    	})

    	var goBtn = $('#goBtn');

    	goBtn.attr('disabled','true');

    	$('.contactCb').change(function(){

    		var agree = true;

    		$('.contactCb').each(function(){
    			if( !$(this).is(':checked') )agree=false;
    		});


    		if( agree == true ){
    			goBtn.removeAttr('disabled');
    		} else{
    			goBtn.attr('disabled','true');
    		}

    	});

    });
</script>
</html>