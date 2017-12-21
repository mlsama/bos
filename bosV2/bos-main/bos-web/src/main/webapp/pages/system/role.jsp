<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色管理</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/crud.js"></script>
</head>
<body>
	<!-- 搜索框 -->
	<div>
		<form id="searchForm" method="post">
			角色名：<input type="text" name="name"/>
			<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</form>
	</div>
	
	<!-- 列表显示 -->
	<table id="list"></table>
	
	<!-- 按钮列表 -->
	<div id="toolbar">
		<shiro:hasPermission name="role_add_btn">
			<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加</a>
		</shiro:hasPermission>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
		<shiro:hasPermission name="role_delete_btn">
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		</shiro:hasPermission>
		<a id="bindResBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">绑定资源</a>
	</div>
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'角色编辑',closed:true" style="width:600px;top:50px;left:200px">
			<!-- 按钮区域 -->
			<div class="datagrid-toolbar">
				<a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
			</div>
			<!-- 编辑区域 -->
			<div>
			<form id="editForm" method="post">
			<!-- 提供隐藏域ID -->
			<input type="hidden" name="id"/>
			<table width="80%" align="center" class="table-edit">
						<tr>
							<td>角色名</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>角色关键词</td>
							<td>
								<input type="text" name="keyword" 
										class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>备注</td>
							<td>
								<textarea rows="5" cols="25" name="description"></textarea>
							</td>
						</tr>
		  </table>
		  </form>
		  </div>
	</div>
	
	<!-- 绑定资源窗口 -->
	<div id="bindResWin" class="easyui-window" data-options="title:'资源绑定',closed:true" style="width:600px;top:50px;left:200px;height:400px;">
	</div>

	<script type="text/javascript">
		//当前模块的action
		var action = "role";
		
		//表格的列数据
		var columns = [ [ {
			field : "id",
			checkbox : true
		}, {
			field : "name",
			title : "角色名",
			width : 120,
			align : "center"
		}, {
			field : "keyword",
			title : "角色关键字",
			width : 120,
			align : "center"
		}, {
			field : "description",
			title : "备注",
			width : 120,
			align : "center"
		} ] ];
		
		//点击绑定资源按钮，弹出窗口
		$("#bindResBtn").click(function(){
			//判断只能选择一个角色
			var rows = $("#list").datagrid("getSelections");
			if(rows.length!=1){
				$.messager.alert("提示","只能选择一个角色","warning");
				return;
			}
			
			var content = "<iframe src='bindRes.jsp?roleId="+rows[0].id+"' style='width:100%;height:100%' frameborder='0'></iframe>";
			//在窗口中嵌入另一个页面
			$("#bindResWin").window({
				content:content
			});
			
			//弹出绑定资源的窗口（在新窗口嵌入另一个页面）
			$("#bindResWin").window("open");
		});
		
	</script>
</body>
</html>