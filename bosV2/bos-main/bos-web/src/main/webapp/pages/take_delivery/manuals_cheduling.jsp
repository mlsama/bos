<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人工调度</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/date.js"></script>
</head>
<body>
	
	<!-- 列表 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">人工分单</a>
	</div>
	<!-- 绑定窗口 -->
	<div id="bindResWin" class="easyui-window" data-options="title:'订单绑定快递员',closed:true" style="width:600px;top:50px;left:200px;height:400px;">
	</div>
</body>
<script type="text/javascript">
	$(function() {
		var action = "order";

		var columns = [[ {
			field : 'id',
			checkbox : true,
		},{
			field : 'orderNum',
			title : '订单号',
			width : 280,
			align : 'center'
		},{
			field : 'orderType',
			title : '订单类型',
			width : 80,
			align : 'center',
			formatter:function(){
				return "<font color='green'>人工分单</font>";
			}
		},{
			field : 'status',
			title : '订单状态',
			width : 80,
			align : 'center',
			formatter:function(){
				return "<font color='red'>已下单</font>";
			}
		},{
			field : 'customerId',
			title : '客户编号',
			width : 80,
			align : 'center'
		}, {
			field : 'sendName',
			title : '寄件人姓名',
			width : 80,
			align : 'center'
		}, {
			field : 'sendMobile',
			title : '寄件人手机',
			width : 120,
			align : 'center'
		}, {
			field : 'sendArea',
			title : '寄件人区域',
			width : 120,
			align : 'center',
			formatter : function(value,row,index) {
				if(value){
					return value.province+value.city+value.district;
				}else{
					return "";
				}
			}
		}, {
			field : 'sendCompany',
			title : '寄件人公司',
			width : 120,
			align : 'center'
		}, {
			field : 'sendAddress',
			title : '寄件人详细地址',
			width : 200,
			align : 'center'
		}, {
			field : 'recName',
			title : '收件人姓名',
			width : 80,
			align : 'center'
		} , {
			field : 'recMobile',
			title : '收件人手机',
			width : 120,
			align : 'center'
		} , {
			field : 'recArea',
			title : '收件人区域',
			width : 120,
			align : 'center',
			formatter : function(value,row,index) {
				if(value){
					return value.province+value.city+value.district;
				}else{
					return "";
				}
			}
		} , {
			field : 'recCompany',
			title : '收件人公司',
			width : 120,
			align : 'center'
		} , {
			field : 'recAddress',
			title : '收件人详细地址',
			width : 200,
			align : 'center'
		} , {
			field : 'sendProNum',
			title : '快递产品类型',
			width : 120,
			align : 'center'
		} , {
			field : 'goodsType',
			title : '货物类型',
			width : 120,
			align : 'center'
		} , {
			field : 'payTypeNum',
			title : '付款方式',
			width : 120,
			align : 'center'
		} , {
			field : 'weight',
			title : '重量',
			width : 120,
			align : 'center'
		} , {
			field : 'remark',
			title : '备注',
			width : 200,
			align : 'center'
		} , {
			field : 'sendMobileMsg',
			title : '给小哥捎话',
			width : 200,
			align : 'center'
		} , {
			field : 'orderTime',
			title : '下单时间',
			width : 200,
			align : 'center',
			formatter:function(value){
				if(value){
					return dateFormat(value);
				}else{
					return "";
				}
			}
		}  ]]; 

		$("#list").datagrid({
			url : "../../" + action + "/findUndistributedOrder.action",
			columns : columns,
			pagination : true,
			toolbar : "#toolbar"
		});
		
		//绑定人工分单按钮
		$("#saveBtn").click(function() {
			//判断一下是否选择了一个订单
			var rows = $("#list").datagrid("getSelections");
			if(rows.length != 1){
				$.messager.alert("提示","必须选择一行数据","warning");
				return;
			}
			
			var content = "<iframe src='bindOrderToCourier.jsp?orderId="+rows[0].id+"' style='width:100%;height:100%' frameborder='0'></iframe>";
			//在窗口中嵌入另一个页面
			$("#bindResWin").window({
				content:content
			});
			//打开编辑窗口
			$("#bindResWin").window("open");
		});
	});
</script>
</html>