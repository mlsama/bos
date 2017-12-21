<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资源管理</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body>
	<!-- 列表显示 -->
	<table id="list"></table>
	
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
	</div>
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'资源编辑',closed:true" style="width:600px;top:50px;left:200px">
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
							<td>资源名称</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>授权码</td>
							<td>
								<input type="text" name="grantKey" 
									class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>资源类型</td>
							<td>
								<select name="resourceType" class="easyui-combobox" data-options="editable:false,width:150">
			                        <option value="0">菜单</option>
			                        <option value="1">按钮</option>
			                    </select>
							</td>
						</tr>
						<tr>
							<td>菜单页面路径</td>
							<td>
								<input type="text" name="pageUrl" 
										class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>菜单图标</td>
							<td>
								<input type="text" name="icon" />
							</td>
						</tr>
						<tr>
							<td>排序</td>
							<td>
								<input name="seq" value="0"  class="easyui-numberspinner"  required="required" data-options="editable:false">
							</td>
						</tr>
						<tr>
							<td>是否展示</td>
							<td>
								<input type="radio" name="open" value="true"/>展开
								<input type="radio" name="open" value="false"/>不展开
							</td>
						</tr>
						<tr>
							<td>上级资源</td>
							<td>
								<select id="parentResId" name="pid"></select>
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

	<script type="text/javascript">
		var action = "resources";	
	
		//使用treegrid展示资源列表
		$("#list").treegrid({
			idField:"id",
			treeField:"name",
			url:"../../resources/listByPage.action",
			columns:[[
				{
					field : "name",
					title : "资源名称",
					width : 120
				},
				{
					field : "grantKey",
					title : "授权码",
					width : 120,
					align : "center"
				},
				{
					field : "pageUrl",
					title : "菜单链接",
					width : 120,
					align : "center"
				},
				{
					field : "resourceType",
					title : "资源类型",
					width : 120,
					align : "center"
				}
			]],
			pagination:true,
			toolbar:"#toolbar",
			//修改pageSize
			pageSize:200,
			//修改pageList
			pageList:[100,200,500]
		});
		
		//点击添加按钮弹出录入窗口
		$("#saveBtn").click(function(){
			//清空表单数据
			$("#editForm").form("clear");
			
			//加载上级资源的下拉框
			$("#parentResId").combobox({
				url:"../../resources/list.action",
				valueField:"id",
				textField:"name"
			});
			
			//打开窗口
			$("#editWin").window("open");
		});
		
		
		//点击保存提交表单数据到后台
		$("#save").click(function(){
			//使用form的submit方法提交表单数据
			$("#editForm").form("submit",{
				//提交表单的url
				url:"../../"+action+"/save.action",
				//表单提交前回调
				onSubmit:function(){
					//检查当前表单是否所有验证都通过了。如果都通过了，则提交表单，有一个不通过，不提交表单；
					return $("#editForm").form("validate");
				},
				//提交成功后回调
				success:function(data){ // data: 后端返回的数据
					//把data字符串转换为js的json对象 : var json对象 = eval(json格式的string) 
					data = eval("("+data+")");
					
					if(data.success){
						//成功
						//清空表单数据
						$("#editForm").form("clear");							
						//刷新datagrid
						$("#list").treegrid("reload");
						//关闭录入窗口
						$("#editWin").window("close");
						
						//提示成功
						$.messager.alert("提示","保存成功","info");
					}else{
						//失败
						$.messager.alert("提示","保存失败，原因："+data.msg,"error");
					}
				}
			});
		});
		
		//表单数据回显
		$("#editBtn").click(function(){
			//判断只能选择一个（不选，选多个不行）
			//getSelections()方法返回数组，数组里面一个个的对象
			var rows = $("#list").treegrid("getSelections");
			
			if(rows.length!=1){
				$.messager.alert("提示","修改操作只能选择一行","warning");
				return;
			}
			
			//查询选中行的数据，把数据填充到录入表单
			$("#editForm").form("load","../../"+action+"/get.action?uuid="+rows[0].id);
			
			//设计专门用于回显默认值的方法
			loadEditFormValue(rows[0]);
			
			//打开窗口
			$("#editWin").window("open");
		});
		
		//删除
		$("#deleteBtn").click(function(){
			//判断至少选择一行
			var rows = $("#list").treegrid("getSelections");
			
			if(rows.length==0){
				$.messager.alert("提示","删除操作至少选择一行","warning");
				return;
			}
			
			//判断是否要删除
			$.messager.confirm("提示","确定要删除吗?删除后不能恢复啦",function(value){
				if(value){
					//点击了确定按钮

					//获取选中行的所有id   格式： ids  1,2,3
					var ids = "";
					
					//创建数组
					var idArray = new Array();
					for(var i=0;i<rows.length;i++){
						//把每个id放入数组  [1,2,3]
						idArray.push(rows[i].id);  
					}
					//join(): 取出数组的每个元素，使用逗号拼接起来,返回字符串 1,2,3
					ids = idArray.join(",");
					
					//把ids参数传递给后台
					$.post("../../"+action+"/delete.action",{ids:ids},function(data){
						if(data.success){
							//刷新datagrid
							$("#list").treegrid("reload");
							
							$.messager.alert("提示","删除成功","info");
						}else{
							$.messager.alert("提示","删除失败，原因："+data.msg,"error");
						}
					},"json");
				}
			});
		});
		
		//用于回显默认值
		function loadEditFormValue(row){
			//先加载上级资源的下拉框
			$("#parentResId").combobox({
				url:"../../resources/list.action",
				valueField:"id",
				textField:"name"
			});
			//再给combobox赋值
			$("#parentResId").combobox("setValue",row._parentId);
		}
		
	</script>
</body>
</html>