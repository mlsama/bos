<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=bUiOQDOyAu6gKuLbMeTBs8pP4T42bGk2"></script>
	<title>地图展示</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(113.434439,23.13526), 15);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	map.disableDragging(); //禁止拖拽
	
	//添加标注，弹跳动画
	var marker = new BMap.Marker(new BMap.Point(113.434439,23.13526));  // 创建标注
	map.addOverlay(marker);               // 将标注添加到地图中
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	
	//添加文字
	var label = new BMap.Label("欢迎报读传智播客",{offset:new BMap.Size(20,-10)});
	marker.setLabel(label);
	
	//获取两点坐标的直线距离
	/*
	var pointA = new BMap.Point(113.434439,23.13526);  // 创建点坐标A--大渡口区
	var pointB = new BMap.Point(113.328551,23.137856);  // 创建点坐标B--江北区
	alert('两点距离是：'+(map.getDistance(pointA,pointB)).toFixed(2)+' 米。');  //获取两点距离,保留小数点后两位
	*/
</script>
