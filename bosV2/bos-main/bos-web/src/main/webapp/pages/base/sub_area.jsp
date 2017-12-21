<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分区</title>
<!-- 引入easyui的资源文件 （5个）-->
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/crud.js"></script>

<!-- 导入ajaxFileUpload资源 -->
<script type="text/javascript" src="../../js/ajaxfileupload/ajaxfileupload.js"></script>
</head>
<body>
	<!-- 搜索 -->
	<form id="searchForm" action="../../area/batchExport.action" method="post">
		关键词:<input type="text" name="keyWords"/>
		辅助关键词:<input type="text" name="assistKeyWords"/>
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
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'分区编辑',closed:true" style="width:700px;top:50px;left:200px">
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
							<td>所属区域</td>
							<td>
							<select id="areaId" name="area.id" class="easyui-combobox" required="true"
								data-options="url:'../../area/list.action',valueField:'id',textField:'showName'" style="width: 200px;"></select>	
							</td>
							<td>所属定区</td>
							<td>
							<select id="fixedAreaId" name="fixedArea.id" class="easyui-combobox" required="true"
								data-options="url:'../../fixedArea/list.action',valueField:'id',textField:'fixedAreaName'" style="width: 200px;"></select>
							</td>
						</tr>
						<tr>
							<td>起始号</td>
							<td>
								<input type="text" name="startNum" class="easyui-validatebox" required="true" />
							</td>
							<td>中止号</td>
							<td>
								<input type="text" name="endNum" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>关键词</td>
							<td>
								<input type="text" name="keyWords" class="easyui-validatebox" required="true" />
							</td>
							<td>辅助关键词</td>
							<td>
								<input type="text" name="assistKeyWords" class="easyui-validatebox" required="true" />
							</td>
						</tr>
					</table>
		  </form>
		  </div>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "subArea"
		
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'area.province',
				title : '省',
				width : 120,
				align : 'center',
				formatter:function(value,row,index){
					if(row.area!=null && row.area.province!=null){
						return row.area.province;
					}else{
						return "";
					}
				}
			}, {
				field : 'area.city',
				title : '市',
				width : 120,
				align : 'center',
				formatter:function(value,row,index){
					if(row.area!=null && row.area.city!=null){
						return row.area.city;
					}else{
						return "";
					}
				}
				
			}, {
				field : 'area.district',
				title : '区',
				width : 120,
				align : 'center',
				formatter:function(value,row,index){
					if(row.area!=null && row.area.district!=null){
						return row.area.district;
					}else{
						return "";
					}
				}
				
			},{
				field : 'fixedArea',
				title : '所属定区',
				width : 120,
				align : 'center',
				formatter:function(value,row,index){
					if(value!=null){
						return value.fixedAreaName;
					}else{
						return "";
					}
				}
			},  {
				field : 'startNum',
				title : '起始号',
				width : 100,
				align : 'center'
			}, {
				field : 'endNum',
				title : '终止号',
				width : 100,
				align : 'center'
			} , {
				field : 'keyWords',
				title : '关键字',
				width : 120,
				align : 'center'
			}, {
				field : 'assistKeyWords',
				title : '辅助关键字',
				width : 100,
				align : 'center'
			} ] ];
		
		  //覆盖loadEditFormValaue方法
		  function loadEditFormValue(row){
			  //回显区域
			  $("#areaId").combobox("setValue",row.area.id);
			  //回显定区
			  $("#fixedAreaId").combobox("setValue",row.fixedArea.id);
		  }
	</script>
	
</body>
</html>