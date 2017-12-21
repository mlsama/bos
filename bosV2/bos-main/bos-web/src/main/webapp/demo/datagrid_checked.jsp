<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>演示datagrid的默认勾选效果</title>
		<link href="favicon.ico" rel="icon" type="image/x-icon" />
		<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
		
		
		<script type="text/javascript">
			$(function() {
				$("#list").datagrid({
					url:"product3.json",
					columns:[[
						{
							field:"id",
							title:"编号",
							width:100,
							checkbox:true
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
					toolbar:"#toolbar",
					//绑定onLoadSuccess事件
					onLoadSuccess:function(data){ // 当前datagrid返回的数据（包括rows和total）
						//遍历所有行
						$(data.rows).each(function(i){
							//选中勾选过的行
							if(data.rows[i].checked){
								//选中行
								$("#list").datagrid("selectRow",i);
							}
						
						});
					}
				});
			
			});
		</script>
	</head>

	<body>
		
		<table id="list"></table>
	
	</body>

</html>