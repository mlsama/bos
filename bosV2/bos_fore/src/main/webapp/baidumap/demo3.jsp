<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";font-size:14px;}
		#l-map{height:300px;width:100%;}
		#r-result{width:100%;}
	</style>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<title>演示百度LBS云地理编码服务</title>
</head>
<body>
</body>
</html>
<script type="text/javascript">
	$.post("http://api.map.baidu.com/cloudgc/v1?ak=bUiOQDOyAu6gKuLbMeTBs8pP4T42bGk2&address=广州市天河区津安创亿园&callback=?",function(data){
		//JSON.stringify(json对象)  返回 json字符串 
		alert(JSON.stringify(data));
	},"json");
</script>

