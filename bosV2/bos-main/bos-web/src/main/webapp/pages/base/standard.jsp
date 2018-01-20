<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>收派标准</title>
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
		收派标准名称:<input type="text" name="name"/>
		最小重量:<input type="text" name="minWeight"/>
		最大重量:<input type="text" name="maxWeight"/>
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
	<div id="editWin" class="easyui-window" data-options="title:'收派标准编辑',closed:true" style="width:600px;top:50px;left:200px">
			<!-- 按钮区域 -->
			<div class="datagrid-toolbar">
				<a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
			</div>
			<!-- 编辑区域 -->
			<div>
			<form id="editForm" method="post">
			<%-- 设计一个隐藏域，携带id --%>
			<input type="hidden" name="id"/>
			<table width="80%" align="center" class="table-edit">
						<tr>
							<td>收派标准名称</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true,missingMessage:'请输入标准名称'"/>
							</td>
						</tr>
						<tr>
							<td>最小重量</td>
							<td>
								<input type="text" name="minWeight" 
										class="easyui-numberbox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>最大重量</td>
							<td>
								<input type="text" name="maxWeight" class="easyui-numberbox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>最小长度</td>
							<td>
								<input type="text" name="minLength" class="easyui-numberbox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>最大长度</td>
							<td>
								<input type="text" name="maxLength" class="easyui-numberbox" data-options="required:true" />
							</td>
						</tr>
		  </table>
		  </form>
		  </div>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "standard"
		
		//当前模型的列属性
		var columns = [[
					     {field : 'id',
                             title : '编号',
                             width : 80,
                             align : 'center',
                             checkbox:true},
					     {field:"name",title:"收派标准名称",width:200},     
					     {field:"minWeight",title:"最小重量",width:100},     
					     {field:"maxWeight",title:"最大重量",width:100},     
					     {field:"minLength",title:"最小长度",width:200}, 
					     {field:"maxLength",title:"最大长度",width:200}
		]];
	</script>
	
</body>
</html>