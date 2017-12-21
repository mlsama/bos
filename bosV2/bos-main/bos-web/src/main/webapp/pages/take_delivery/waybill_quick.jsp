<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>运单快速录入</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body class="easyui-layout">

	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="addRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加一行</a>
		<a id="cancelBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">取消编辑</a>
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">保存</a>
	</div>
	
	<div region="center" border="false">
			<table id="list"></table>
	</div>
	
	<script type="text/javascript">
		$(function(){
			$("#list").datagrid({
				iconCls : 'icon-forward',
				fit : true,
				border : true,
				striped : true,
				pagination : true,
				toolbar : "#toolbar",
				url :  "",
				idField : 'id',
				columns : columns,
				//注册事件
				onAfterEdit:function(index, row, changes){  //row:当前编辑完成后的行对象
					//保存运单的数据
										
					$.post("../../wayBill/save.action",row,function(data){
						if(data.success){
							$.messager.alert("提示","运单快速录入成功","info");
						}else{
							$.messager.alert("提示","运单快速录入失败，"+data.msg,"error");
						}
					},"json");
					
				}
			});
			
			
		});
		
		// 定义列
		var columns = [ [ {
			field : 'wayBillNum',
			title : '运单编号',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'arriveCity',
			title : '到达地',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		},{
			field : 'goodsType',
			title : '产品',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'num',
			title : '件数',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'weight',
			title : '重量',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'floadreqr',
			title : '配载要求',
			width : 220,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}] ];
		
		var currentRow;
	
		//添加一行
		$("#addRowBtn").click(function(){
			//判断是否已经取消编辑，如果没有取消编辑，就无法加上新行
			if(currentRow!=undefined){
				return;
			}
			
			//添加到第一行
			$("#list").datagrid("insertRow",{
				index:0,
				row:{}
			});
			
			currentRow = 0;
			//开始编辑
			$("#list").datagrid("beginEdit",currentRow);
		});
		
		//取消编辑
		$("#cancelBtn").click(function(){
			$("#list").datagrid("cancelEdit",0);
			
			currentRow = undefined;
		});
	
		//保存
		$("#saveBtn").click(function(){
			//结束编辑
			$("#list").datagrid("endEdit",0);
			
			currentRow = undefined;
			
		});
	</script>
</body>
</html>