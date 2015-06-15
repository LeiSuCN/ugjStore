<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>U管家 - 门店分布</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<link rel="stylesheet" type="text/css" href="<%=root %>/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=root %>/style/header.css">
<style type="text/css">
body, div, ul, li{ padding: 0px; margin: 0px; }
ul, li{ text-decoration: none;list-style-type:none }
#map_container{ 
	height: 600px;
}
.store-area-select{
	width: 150px;
	
}

.container_header {
	width: 1000px;
}

.container_header .ui-selectmenu-button{
	border-radius: 0px;
	border: 0px;
	margin: 0.5em 0;
}

.container {
	width: 1000px;
	height: 600px;
	padding-bottom: 0px;
}

#store_list_container {
	float:left;
	width:200px;
}

#store_list li{
	font-family: Arial, Helvetica, sans-serif;
	COLOR: #1A75CF;
	margin: 0.5em;
	border-bottom: 1px solid rgba(54, 54, 54, 0.2);
}

#store_list  .store-name{
	font-size: 12px;
	cursor: pointer;
	font-weight: bold;
}

#store_list  .store-name:hover{
	text-decoration: underline;
}

#store_list  .store-address{
	font-size: 11px;
	padding-left: 0.5em;
}

#map_container{
	float:right;
	width:800px;
}

</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=nsNo6385eG8L0jfcaFPVThXX"></script>
<script src="<%=root %>/js/jquery-1.11.2.min.js"></script>
<script src="<%=root %>/js/jquery-ui.js"></script>
<script src="<%=root %>/js/header.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container_header">
	<div style="font-size: 0px;">
	<select id="store_area_province" class="store-area-select">
		<option value="44">广东省</option>
	</select>
	<select  id="store_area_city" class="store-area-select">
		<option value="03">深圳市</option>
	</select>
	<select id="store_area_sub" class="store-area-select">
		<option value="01">宝安区</option>
		<option value="02">南山区</option>
		<option value="03">福田区</option>
		<option value="04">龙岗区</option>
		<option value="05">罗湖区</option>
		<option value="06">龙华新区</option>
	</select>
	</div>
</div>
<div class="container">
	<div id="store_list_container">
		<ul id="store_list">
		
		</ul>
	</div>
	<div id="map_container">
	</div>
</div>
<%@include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
<script type="text/javascript">
var map;
var markers = {};
var clickedStore;
var clickedStoreIcon  = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
										offset: new BMap.Size(10, 25), // 指定定位位置
										imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
									});
var defaultStoreIcon  = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
										offset: new BMap.Size(10, 25), // 指定定位位置
										imageOffset: new BMap.Size(0, 0 - 11 * 25) // 设置图片偏移
									});

var $storeListContainer;
var $selAreaProvince;
var $selAreaCity;
var $selAreaSub;

function initialize(){
	$storeListContainer = $('#store_list');
	$selAreaProvince = $('#store_area_province');
	$selAreaCity = $('#store_area_city');
	$selAreaSub = $('#store_area_sub');
	
	$selAreaProvince.selectmenu();
	$selAreaCity.selectmenu();
	$selAreaSub.selectmenu({
		change: function( event, data ){
			refreshMap( data.item.value );
		}
	});
	
	$selAreaProvince.next('.ui-selectmenu-button').css({
		'border-top-left-radius': '8px',
		'border-bottom-left-radius': '8px'
	});
	
	$selAreaSub.next('.ui-selectmenu-button').css({
		'border-top-right-radius': '8px',
		'border-bottom-right-radius': '8px'
	});
}

function refreshMap( subAreaCode ){
	
	if( !subAreaCode )return;
	
	clearMarkers();
	
	$storeListContainer.empty();
	
	var areaCode = $selAreaProvince.val() + $selAreaCity.val() +subAreaCode;
	
	var url = '<%= root %>/public/store/area/' + areaCode + '0000/list';
	
	$.get(url, {}, function(resp){
		
		if( resp && resp.data ){
			
			var maxLat = -999;
			var minLat = 999;
			var maxLon = -999;
			var minLon = 999;
			
			for( var i = 0 ; i < resp.data.length ; i++ ){
				var store = resp.data[i];
				var point = new BMap.Point(store.lon, store.lat);
		        var marker=new BMap.Marker(point,{icon:defaultStoreIcon});
				map.addOverlay(marker);              // 将标注添加到地图中
				
				markers[store.id] = marker;
				
				if( store.lat > maxLat ){
					maxLat = store.lat;
				}
				if( store.lat < minLat  ){
					minLat = store.lat;
				}
				if( store.lon > maxLon ){
					maxLon = store.lon;
				}
				if( store.lon < minLon  ){
					minLon = store.lon;
				}
				
				// 创建store list  item
				var html = "<li><span class='store-name'>" + store.name +"</span><br><span class='store-address'>" + store.address +"</span></li>";
				
				var $html = $(html);
				
				 $html.data('store_info', store );
				 
				 $html.find('.store-name').click(function(){
					 moveToStoreLocation($(this).parent().data('store_info'));
				 });
				
				$storeListContainer.append(  $html );
			}
			
			if( maxLat != -999 &&  minLat != 999 && maxLon != -999 && minLon != 999 ){
				var centerLat = (maxLat + minLat ) / 2;
				var centerLon =  (maxLon + minLon ) / 2;
				console.log( centerLon + ',' + centerLat );
				 map.setCenter( new BMap.Point(centerLon, centerLat));
			}
		}
	} );
}

function clearMarkers(){
	
	for( var i in markers ){
		map.removeOverlay(markers[i]); 
	}
	
	markers = [];
	clickedStore = false;
}

function moveToStoreLocation( storeInfo ){
	 var storePoint = new BMap.Point(storeInfo.lon, storeInfo.lat);
	 map.setCenter(storePoint);
	 markers[storeInfo.id].setIcon(clickedStoreIcon);
	 if( clickedStore ){
		 markers[clickedStore.id].setIcon(defaultStoreIcon);
	 }
	 
	 clickedStore = storeInfo;
}


    $(document).ready(function() {
    	map = new BMap.Map("map_container");
    	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    	var point = new BMap.Point(114.065931,22.550059);
    	map.centerAndZoom(point, 11);
    	initialize();
    	refreshMap('01');
    });
</script>
</html>