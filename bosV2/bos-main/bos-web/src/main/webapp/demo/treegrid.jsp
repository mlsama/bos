<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>演示treegrid</title>
		<link href="favicon.ico" rel="icon" type="image/x-icon" />
		<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
		
		
		<script type="text/javascript">
			$(function() {
				$("#list").treegrid({
					//idField: 代表树节点的ID属性名称（为了让返回的子节点的_parentId查找idField）
					idField:"id",
					//treeField：把哪个属性作为树节点显示的名称
					treeField:"name",
					url:"product2.json",
					columns:[[
						{
							field:"id",
							title:"编号",
							width:100
						},
						{
							field:"name",
							title:"名称",
							width:200
						},
						{
							field:"price",
							title:"价格",
							width:200
						}
					]],
					pagination:true,
					//显示复选框
					checkbox:true,
					//是否级联勾选
					cascadeCheck:false
				});
			});
		</script>
	</head>

	<body>
		
		<table id="list"></table>
		
	</body>

</html>