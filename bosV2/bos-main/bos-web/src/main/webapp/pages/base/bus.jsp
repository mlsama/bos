<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>班车管理</title>
<!-- 引入easyui的资源文件-->
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
		线路类型:<input type="text" name="wayType"/>
		车牌号:<input type="text" name="busNum"/>
		<input id="searchBtn" type="button" value="搜索"/> 
	</form>
	
	<!-- 工具条 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
	</div>

	<!-- 列表展示 -->
	<table id="list"></table>

	<!-- 录入窗口 -->
	<div id="editWin" class="easyui-window"
		data-options="title:'收派标准编辑',closed:true"
		style="width: 600px; top: 50px; left: 200px">
		<!-- 按钮区域 -->
		<div class="datagrid-toolbar">
			<a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
		</div>
		<!-- 编辑区域 -->
		<div>
			<form id="editForm" method="post">
				<%-- 设计一个隐藏域，携带id --%>
				<input type="hidden" name="id" />
				<table width="80%" align="center" class="table-edit">
					<tr>
						<td>线路类型</td>
						<td>
							<select name="way.id" id="wayId" class="easyui-combobox" required="true"
							  data-options="url:'../../way/list.action',valueField:'id',textField:'wayType'" style="width:150px"></select>
						</td>
					</tr>
					<tr>
						<td>车牌号</td>
						<td><input type="text" name="busNum"
							class="easyui-validatebox"
							data-options="required:true,missingMessage:'请输入车牌号'" /></td>
					</tr>
					<tr>
						<td>承运商</td>
						<td><input type="text" name="provider"
							class="easyui-validatebox"
							data-options="required:true,missingMessage:'请输入承运商'" /></td>
					</tr>
					<tr>
						<td>车型</td>
						<td><input type="text" name="busType"
							class="easyui-validatebox"
							data-options="required:true,missingMessage:'请输入车型'" /></td>
					</tr>
					<tr>
						<td>司机</td>
						<td><input type="text" name="driver"
							class="easyui-validatebox"
							data-options="required:true,missingMessage:'请输入司机'" /></td>
					</tr>
					<tr>
						<td>电话</td>
						<td><input type="text" name="telephone"
							class="easyui-numberbox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>吨位</td>
						<td><input type="text" name="ton" class="easyui-numberbox"
							data-options="required:true" /></td>
					</tr>
					<tr>
							<td>备注</td>
							<td>
								<textarea rows="5" cols="25" name="remark"></textarea>
							</td>
						</tr>
				</table>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		//当前模型的action名称
		var action = "bus";

		//当前模型的列属性
		var columns = [ [ {
			field : "id",
			checkbox : true
		}, {
			field : "way",
			title : "线路类型",
			width : 100,
			formatter:function(value){
				if(value != null){
					return value.wayType;
				}else{
					return "";
				}
			}
		}, {
			field : "busNum",
			title : "车牌号",
			width : 100
		}, {
			field : "provider",
			title : "承运商",
			width : 100
		}, {
			field : "busType",
			title : "车型",
			width : 100
		}, {
			field : "driver",
			title : "司机",
			width : 100
		}, {
			field : "telephone",
			title : "联系方式",
			width : 100
		}, {
			field : "ton",
			title : "吨位",
			width : 100
		}, {
			field : "remark",
			title : "备注",
			width : 200
		} ] ];
		
		//编辑下拉框回显
		function loadEditFormValue(row){
			//清空班车线路
			$("#wayId").combobox("clear");
			//回显班车线路的选项默认值
			if(row.way!=null){
				$("#wayId").combobox("setValue",row.way.id);
			}
		}
	</script>
</body>
</html>