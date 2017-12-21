<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>演示datagrid的行编辑</title>
		<link href="favicon.ico" rel="icon" type="image/x-icon" />
		<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
		
		
		<script type="text/javascript">
			$(function() {
				$("#list").datagrid({
					url:"product.json",
					columns:[[
						{
							field:"id",
							title:"编号",
							width:100,
							editor:{
								//type：该列使用哪种编辑框
								type:"validatebox",
								//options：该属性用于配置type编辑框里面的属性
								options:{
									required:true
								}
							}
						},
						{
							field:"name",
							title:"名称",
							width:200,
							editor:{
								//type：该列使用哪种编辑框
								type:"validatebox",
								//options：该属性用于配置type编辑框里面的属性
								options:{
									required:true
								}
							}
						},
						{
							field:"price",
							title:"价格",
							width:200,
							editor:{
								//type：该列使用哪种编辑框
								type:"validatebox",
								//options：该属性用于配置type编辑框里面的属性
								options:{
									required:true
								}
							}
						}
					]],
					pagination:true,
					toolbar:"#toolbar",
					//实现单选
					singleSelect:true
				});
				
				//全局变量
				var currentRowIndex;
				//开始编辑
				$("#beginEditBtn").click(function(){
					//获取当前选择行
					//获取当前选择行对象
					var row = $("#list").datagrid("getSelections")[0];
					//获取当前行对象的索引
					currentRowIndex = $("#list").datagrid("getRowIndex",row);
					
					//开始编辑第一行
					$("#list").datagrid("beginEdit",currentRowIndex);
				});
				
				//结束编辑
				$("#endEditBtn").click(function(){
					//结束编辑第一行
					$("#list").datagrid("endEdit",currentRowIndex);
				});
				
				//取消编辑
				$("#cancelEditBtn").click(function(){
					//取消编辑第一行
					$("#list").datagrid("cancelEdit",currentRowIndex);
				});
				
				//添加行
				$("#addRowBtn").click(function(){
					//appendRow:追加到最后一行
					//$("#list").datagrid("appendRow",{});
				
					//insertRow
					$("#list").datagrid("insertRow",{
						//index:需要插入行的位置(从0开始的索引)
						index:0,
						row:{}
					});
					
					//开始编辑
					$("#list").datagrid("beginEdit",0);
					
					currentRowIndex = 0;
				});
				
				//删除行
				$("#deleteRowBtn").click(function(){
					
					//获取当前选择行
					//获取当前选择行对象
					var row = $("#list").datagrid("getSelections")[0];
					//获取当前行对象的索引
					var rowIndex = $("#list").datagrid("getRowIndex",row);
					
					$("#list").datagrid("deleteRow",rowIndex);
				});
				
			});
		</script>
	</head>

	<body>
		
		<table id="list"></table>
		
		<!-- 工具条 -->
	<div id="toolbar">
		<a id="beginEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">开始编辑</a>
		<a id="endEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">结束编辑</a>
		<a id="cancelEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">取消编辑</a>
		<a id="addRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加行</a>
		<a id="deleteRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">删除行</a>
	</div>
	</body>

</html>