<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Location Picker</title>
</head>
<body>
	<div id="bm" style="width:800px; height:400px; "></div>
</body>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=nsNo6385eG8L0jfcaFPVThXX"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/locationPicker.js"></script>
<script type="text/javascript">

	$(document).ready(function(){

		var point = new BMap.Point(<c:out value="${requestScope.lng}" />, <c:out value="${requestScope.lat}" />);

		var locationPicker = new LocationPicker('bm',{
			enableClick: false,
			enableSearch: false,
			mouseCursor:'',
			center: point,
			zoom: 20
		});

		
		locationPicker.pick( point );
	});
</script>
</html>