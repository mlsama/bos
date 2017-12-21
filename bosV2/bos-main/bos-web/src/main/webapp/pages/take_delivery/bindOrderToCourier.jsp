<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>绑定快递员</title>
<!-- 引入easyui的资源文件-->
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body>
	<!-- 列表展示 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">订单关联快递员</a>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "courier";
		
		//当前模型的列属性
		var columns = [ [ {
			field : 'id',
			checkbox : true,
		},{
			field : 'courierNum',
			title : '工号',
			width : 80,
			align : 'center'
		},{
			field : 'pickTime',
			title : '上班时间',
			width : 80,
			align : 'center',
			formatter:function(value,row,index){
				if(value!=null){
					return value.name;
				}else{
					return "";
				}
			}
		},{
			field : 'name',
			title : '姓名',
			width : 80,
			align : 'center'
		}, {
			field : 'telephone',
			title : '手机号',
			width : 120,
			align : 'center'
		}, {
			field : 'checkPwd',
			title : '查台密码',
			width : 120,
			align : 'center'
		}, {
			field : 'pda',
			title : 'PDA号',
			width : 120,
			align : 'center'
		}, {
			field : 'standard',
			title : '取派标准',
			width : 120,
			align : 'center',
			//value: 当前的列值
			//row:当前行对象
			//index：行索引，从0开始
			formatter:function(value,row,index){
				if(value!=null){
					return value.name;
				}else{
					return "";
				}
			}
		}, {
			field : 'company',
			title : '所属单位',
			width : 200,
			align : 'center'
		} ] ];
		
		$(function(){
			//列表展示
			$("#list").datagrid({
				//后台url地址，该地址必须返回json格式数据(对象或数组)
				url:"../../"+action+"/findCourierByOrder.action?uuid=${param.orderId}",
				//列填充
				//field:列属性名称
				columns:columns,
				//绑定工具条
				toolbar:"#toolbar"
			});
			
			$("#saveBtn").click(function() {
				//判断一下是否选择了一个快递员
				var rows = $("#list").datagrid("getSelections");
				if(rows.length != 1){
					$.messager.alert("提示","必须选择一行数据","warning");
					return;
				}
				
				//发送异步请求
				$.post("../../order/bindOrderToCourier.action",{id:"${param.orderId}",uuid:rows[0].id},function(data){
					if(data.success){
						window.parent.$("#bindResWin").window("close");
						//刷新页面
						window.parent.$("#list").datagrid("reload");
						window.parent.$.messager.alert("提示","人工分单成功","info");
					}else{
						$.messager.alert("提示","人工分单失败"+data.msg,"error");
					}
				},"json")
			});
		});
	</script>
	
</body>
</html>