<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>符合条件的运单</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body>
	<!-- 列表 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="cancelBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">作废</a>
	</div>
	<!-- 作废选项框 -->
	<div id="cancelWin" class="easyui-window" data-options="title:'订单绑定快递员',closed:true" style="width:600px;top:50px;left:200px;height:400px;"></div>
	<script type="text/javascript">
		//action访问名称
		var action="wayBill";
		//列字段
		var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'isDelete',
				title : '是否作废',
				width : 80,
				align : 'center',
				formatter:function(value){
					if(value){
						return value == "0" ? "<font color='green'>作废</font>" :  "<font color='green'>有效</font>";
					}else{
						return "";
					}
				}
			},{
				field : 'wayBillNum',
				title : '运单编号',
				width : 80,
				align : 'center'
			},{
				field : 'sendName',
				title : '寄件人',
				width : 80,
				align : 'center'
			}, {
				field : 'sendMobile',
				title : '寄件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'sendCompany',
				title : '寄件人公司',
				width : 120,
				align : 'center'
			}, {
				field : 'sendAddress',
				title : '寄件人详细地址',
				width : 120,
				align : 'center'
			}, {
				field : 'recName',
				title : '收件人',
				width : 120,
				align : 'center'
			}, {
				field : 'recMobile',
				title : '收件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'sendProNum',
				title : '产品类型',
				width : 200,
				align : 'center'
			},{
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'payTypeNum',
				title : '支付类型',
				width : 200,
				align : 'center'
			}, {
				field : 'weight',
				title : '重量',
				width : 200,
				align : 'center'
			}, {
				field : 'num',
				title : '原件数',
				width : 200,
				align : 'center'
			},  {
				field : 'feeitemnum',
				title : '实际件数',
				width : 200,
				align : 'center'
			},  {
				field : 'actlweit',
				title : '实际重量',
				width : 200,
				align : 'center'
			},  {
				field : 'vol',
				title : '体积',
				width : 200,
				align : 'center'
			}, {
				field : 'floadreqr',
				title : '配载要求',
				width : 200,
				align : 'center'
			}, {
				field : 'wayBillType',
				title : '运单类型',
				width : 80,
				align : 'center',
			}] ];
		
		$(function(){
			//列表展示
			$("#list").datagrid({
				//后台url地址，该地址必须返回json格式数据(对象或数组)
				url:"../../"+action+"/findCanCancelWayWill.action?wayBillIds=${param.ids}",
				//列填充
				//field:列属性名称
				columns:columns,
				//绑定工具条
				toolbar:"#toolbar"
			});
			//批量作废
			$("#cancelBtn").click(function() {
				var rows = $("#list").datagrid("getSelections");
				if(rows.length == 0){
					$.messager.alert("提示","必须选择一行","waring");
					return;
				}
				var ids = "";
				var idsArray = new Array();
				for(var i = 0; i < rows.length; i++){
					idsArray.push(rows[i].id);
				}
				ids = idsArray.join(",");
				
				$.post("../../"+action+"/cancelWayBill.action",{wayBillIds:ids},function(data){
					if(data.success){
						window.parent.$("#cancelWin").window("close");
						window.parent.$.messager.alert("提示","作废成功","info");
						window.parent.$("#list").datagrid("reload");
					}else{
						$.messager.alert("提示","作废失败"+data.msg,"error");
					}
				},"json");
			});
		});
	</script>
</body>
</html>