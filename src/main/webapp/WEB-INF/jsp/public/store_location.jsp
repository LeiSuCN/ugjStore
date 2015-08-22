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
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=fb41f5a7451770da0be1e646d4cf9087"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/locationPicker.js"></script>
<script type="text/javascript">

	$(document).ready(function(){

		var point = {lat:<c:out value="${requestScope.lng}" />, lon: <c:out value="${requestScope.lat}" />};

		var locationPicker = new LocationPicker('bm',{
			enableClick: false,
			enableSearch: false,
			mouseCursor:'',
			center: point,
			zoom: 20
		});
		
		locationPicker.pick(new AMap.LngLat(point.lat, point.lon));
	});
</script>
</html>