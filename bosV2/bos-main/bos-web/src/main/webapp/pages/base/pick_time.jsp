<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>收派时间管理</title>
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
		时间名称:<input type="text" name="name"/>
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
	<div id="editWin" class="easyui-window" data-options="title:'收派时间编辑',closed:true" style="width:700px;top:50px;left:200px">
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
							<td>时间名称:</td>
							<td>
								<input type="text" name="name" class="easyui-validatebox" required="true" />
							</td>
							<td>平常上班时间</td>
							<td>
								<input id="usualOnId" name="usualOn" class="easyui-timespinner"  style="width:80px;"   
      								  required="required" data-options="min:'06:00',showSeconds:false" />  
							</td>
						</tr>
						<tr>
							<td>平常下班时间</td>
							<td>
								<input id="usualOnId" name="usualDown" class="easyui-timespinner"  style="width:80px;"   
      								  required="required" data-options="min:'18:00',showSeconds:false" />  
								
							</td>
							<td>周六上班时间</td>
							<td>
							<input id="usualOnId" name="saturdayOn" class="easyui-timespinner"  style="width:80px;"   
      								  required="required" data-options="min:'00:00',showSeconds:false" />
							</td>
						</tr>
						<tr>
							<td>周六下班时间</td>
							<td>
								<input id="usualOnId" name="saturdayDown" class="easyui-timespinner"  style="width:80px;"   
      								 required="required"  data-options="min:'00:00',showSeconds:false" />
							</td>
							<td>周日上班时间</td>
							<td>
								<input id="usualOnId" name="weekOn" class="easyui-timespinner"  style="width:80px;"   
      								 required="required" data-options="min:'00:00',showSeconds:false" />
							</td>
						</tr>
						<tr>
							<td>周日下班时间</td>
							<td>
							<input id="usualOnId" name="weekDown" class="easyui-timespinner"  style="width:80px;"   
      								required="required" data-options="min:'00:00',showSeconds:false" />
							</td>
						</tr>
					</table>
		  </form>
		  </div>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "pickTime";
		
		//当前模型的列属性
		var columns = [ [ {
			field : 'id',
			checkbox : true
		},{
			field : 'name',
			title : '时间名称',
			width : 80,
			align : 'center'
		},{
			field : 'usualOn',
			title : '平常上班时间',
			width : 80,
			align : 'center'
		}, {
			field : 'usualDown',
			title : '平常下班时间',
			width : 120,
			align : 'center'
		}, {
			field : 'saturdayOn',
			title : '周六上班时间',
			width : 120,
			align : 'center',
			formatter:function(value,row,index){
				if(value != null && value== '00:00'){
					return '休息';
				}
				else{
					return value;
				}
			}
		}, {
			field : 'saturdayDown',
			title : '周六下班时间',
			width : 120,
			align : 'center',
			formatter:function(value,row,index){
				if(value != null && value== '00:00'){
					return '休息';
				}
				else{
					return value;
				}
			}
		}, {
			field : 'weekOn',
			title : '周日上班时间',
			width : 120,
			align : 'center',
			formatter:function(value,row,index){
				if(value != null && value== '00:00'){
					return '休息';
				}
				else{
					return value;
				}
			}
		}, {
			field : 'weekDown',
			title : '周日下班时间',
			width : 120,
			align : 'center',
			formatter:function(value,row,index){
				if(value != null && value== '00:00'){
					return '休息';
				}
				else{
					return value;
				}
			}
		}] ];
		
	</script>
	
</body>
</html>