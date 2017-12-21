<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>定区</title>
<!-- 引入easyui的资源文件 （5个）-->
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/crud.js"></script>
</head>
<body>
	<!-- 搜索 -->
	<form id="searchForm" action="" method="post">
		定区名称:<input type="text" name="fixedAreaName"/>
		<input id="searchBtn" type="button" value="搜索"/> 
	</form>
	
	<!-- 工具条 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		<a id="customerBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关联客户</a>
	</div>

	<!-- 列表展示 -->
	<table id="list"></table>
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'定区添加修改',closed:true" style="width:600px;top:20px;left:200px">
			<!-- 按钮区域 -->
			<div class="datagrid-toolbar">
				<a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
			</div>
			<!-- 编辑区域 -->
			<div>
			<form id="editForm" method="post">
			<!--提供隐藏域 装载id -->
			<input type="hidden" name="id" />
			<table class="table-edit" width="80%" align="center">
						<tr>
							<td>定区名称</td>
							<td>
								<input type="text" name="fixedAreaName" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>快递负责人</td>
							<td>
							<select id="courierId" name="courier.id" class="easyui-combobox" required="true"
								data-options="url:'../../courier/list.action',valueField:'id',textField:'name'" style="width: 150px;"></select>
							</td>
						</tr>
						<tr>
							<td>联系电话</td>
							<td>
								<input name="telephone" class="easyui-validatebox" required="true"/>
							</td>
						</tr>
					</table>
		  </form>
		  </div>
		  
	</div>
	
	<!-- 关联客户窗口 -->
	<div class="easyui-window" title="关联客户窗口" id="customerWin" modal="true"
			collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 700px;height: 300px;">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="customerForm" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="3">关联客户</td>
						</tr> 
						<tr>
							<td>
								<!-- 多选的下拉框 -->
								<select id="associationSelect" multiple="multiple" size="10" style="width: 300px;"></select>
							</td>
							<td>
								<input type="button" value="》》" id="toRight">
								<br/>
								<input type="button" value="《《" id="toLeft">
							</td>
							<td>
								<select id="noassociationSelect" name="customerIds" multiple="multiple" size="10" style="width: 300px;"></select>
							</td>
						</tr>
						<tr>
							<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联客户</a> </td>
						</tr>
					</table>
				</form>
			</div>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "fixedArea"
		
		//当前模型的列属性
		var columns = [ [ {
			field : 'id',
			title : '编号',
			width : 80,
			align : 'center',
			checkbox:true
		},{
			field : 'fixedAreaName',
			title : '定区名称',
			width : 120,
			align : 'center'
		}, {
			field : 'courier',
			title : '快递负责人',
			width : 120,
			align : 'center',
			formatter:function(value){
				if(value!=null){
					return value.name;
				}else{
					return "";
				}
			}
		}, {
			field : 'telephone',
			title : '联系电话',
			width : 120,
			align : 'center'
		} ] ];
		
		
		//覆盖loadEditFormValue，用于回显comobox的默认值
		function loadEditFormValue(row){
			$("#courierId").combobox("setValue",row.courier.id);
		}
		
		//打开定区关联客户的窗口
		$("#customerBtn").click(function(){
			//判断只能选择一个定区
			var rows = $("#list").datagrid("getSelections");
			if(rows.length!=1){
				$.messager.alert("提示","只能选择一个定区","warning");
				return;
			}
			
			//抓取未关联客户
			$.post("../../fixedArea/findNoAssociateCustomers.action",function(data){
				//填充右边的select
				//遍历data数组
				//each():是jQuery的each遍历方法
				//$(data): 把data转为jQuery对象
				//清空select:empty()方法
				$("#noassociationSelect").empty();
				$(data).each(function(i){
					//append():在某个标签里面追加元素
					$("#noassociationSelect").append("<option value='"+data[i].id+"'>"+data[i].username+"("+data[i].address+")</option>");
				});
			},"json");
			
			//抓取已关联客户
			$.post("../../fixedArea/findHasAssoicateCustomers",{uuid:rows[0].id},function(data){
				//填充左边的select
				//遍历data数组
				//each():是jQuery的each遍历方法
				//$(data): 把data转为jQuery对象
				//清空select:empty()方法
				$("#associationSelect").empty();
				$(data).each(function(i){
					//append():在某个标签里面追加元素
					$("#associationSelect").append("<option value='"+data[i].id+"'>"+data[i].username+"("+data[i].address+")</option>");
				});
			},"json");
			
			$("#customerWin").window("open");
		});
		
		
		//左边到右边
		$("#toRight").click(function(){
			//左边select的选中的option追加到右边的select
			$("#associationSelect option:selected").appendTo("#noassociationSelect");
		});
		
		//右边到左边
		$("#toLeft").click(function(){
			//左边select的选中的option追加到右边的select
			$("#noassociationSelect option:selected").appendTo("#associationSelect");
		});
		
		//绑定客户与定区的关系
		$("#associationBtn").click(function(){
			
			//获取左边的select的客户的id
			//把左边的select全选  : prop()修改属性
			$("#associationSelect option").prop("selected","selected");
			//val():选中的option的值
			var customerIdArray = $("#associationSelect").val();
			
			var customerId = customerIdArray.join(",");; // 1,2,3
			
			//获取定区id
			var rows = $("#list").datagrid("getSelections");
			var fixedAreaId = rows[0].id;
			
			$.post("../../fixedArea/associateCustomerToFixedArea.action",{uuid:fixedAreaId,customerId:customerId},function(data){
				if(data.success){
					$("#customerWin").window("close");
					
					$.messager.alert("提示","绑定成功","info");
				}else{
					$.messager.alert("提示","绑定失败."+data.msg,"error");
				}
			},"json");
		});
		
	</script>
	
</body>
</html>