<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>消息中心</title>
<link href="favicon.ico" rel="icon" type="image/x-icon" />
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">

<!-- portal资源 -->
<script type="text/javascript" src="js/easyui/ext/jquery.portal.js"></script>
<link rel="stylesheet" type="text/css" href="js/easyui/ext/portal.css">

<script type="text/javascript">
	$(function(){
		//第二步：创建portal门户
		$("#portal").portal({
			//去除门户边框
			border:false
		});
		
		//第三步：
		//定义4个面板
		var panels = [
			{
				title:"公共栏",
				height:220,
				//是否可以折叠
				collapsible: true,
				href:"pages/portal/gonggao.html"
			},
			{
				title:"代办事宜",
				height:220,
				//是否可以折叠
				collapsible: true,
				href:"pages/portal/daiban.html"
			},
			{
				title:"预警信息",
				height:220,
				//是否可以折叠
				collapsible: true,
				href:"pages/portal/yujing.html"
			},
			{
				title:"系统BUG反馈",
				height:220,
				//是否可以折叠
				collapsible: true ,
				href:"pages/portal/bug.html"
			}
		];
		
		//创建面板，追加面板到portal
		for(var i=0;i<panels.length;i++){
			
			//创建面板
			var p = $('<div></div>').appendTo('body');    
			p.panel(panels[i]);    
			     
			//追加面板到portal   
			$('#portal').portal('add', {    
			    panel: p,    
			    columnIndex: i%2    
			}); 
		}
	});
</script>

</head>
<body>

<!-- 第一步：制作一个div -->
<div id="portal" style="width:1000px;height:500px;">   
    <div style="width:50%"></div>   
    <div style="width:50%"></div>   
</div>  

</body>
</html>