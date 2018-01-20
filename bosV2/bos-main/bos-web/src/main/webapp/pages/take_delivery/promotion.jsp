<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>宣传任务</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript"
	src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/crud.js"></script>
<script type="text/javascript" src="../../js/date.js"></script>
</head>
<body>
	<!-- 搜索框 -->
	<div class="datagrid-toolbar" style="height: 40px;">
		<form id="searchForm" method="post">
			标题:<input type="text" name="title; "/>
			<a id="searchBtn" class="easyui-linkbutton" href="#" icon="icon-search">查询</a>
		</form>
	</div>

	<!-- 列表 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-save'">添加</a> <a id="editBtn" href="#"
			class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a> <a
			id="deleteBtn" href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove'">删除</a>
	</div>

	<script type="text/javascript">
		//action访问名称
		var action = "promotion";

		// 定义列
		var columns = [ [ {
			field : 'id',
			title : '编号',
			width : 100,
			checkbox : true
		}, {
			field : 'title',
			title : '宣传概要（标题）',
			width : 200
		}, {
			field : 'titleImg',
			title : '宣传图片',
			width : 200,
			formatter:function(value,row,index){
				if(value!=null){
					return "<img src='${pageContext.request.contextPath}"+value+"' style='width:150px;height:120px;'/>";
				}else{
					return "";
				}
			}
		}, {
			field : 'startDate',
			title : '发布时间',
			width : 200,
			formatter:function(value,row,index){
				if(value!=null){
					return dateFormat(value);
				}else{
					return "";
				}
			}
		}, {
			field : 'endDate',
			title : '失效时间',
			width : 200,
			formatter:function(value,row,index){
				if(value!=null){
					return dateFormat(value);
				}else{
					return "";
				}
			}
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			formatter:function(value,row,index){
				if(value!=null){
					return value=="1"?"<font color='green'>有效</font>":"<font color='red'>过期</font>";
				}else{
					return "";
				}
			}
		} ] ];

		$("#saveBtn").click(function(){
			window.location.href="promotion_add.jsp";
		});
	</script>
</body>
</html>