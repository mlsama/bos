<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色绑定资源页面</title>
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
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">角色绑定资源</a>
	</div>

	<script type="text/javascript">
		var action = "resources";	
	
		//使用treegrid展示资源列表
		$("#list").treegrid({
			idField:"id",
			treeField:"name",
			url:"../../resources/listByPageAndRole.action?roleId=${param.roleId}",
			columns:[[
				{
					field : "name",
					title : "资源名称",
					width : 200
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
			pageList:[100,200,500],
			//显示复选框
			checkbox:true,
			//取消级联勾选
			cascadeCheck:false
		});
		
		//保存角色和资源的关系
		$("#saveBtn").click(function(){
			/*条件：
				1）当前角色ID（1个）
				2）选中的资源ID（n个)
			*/
			//获取当前角色ID
			var roleId = "${param.roleId}"

			var resIds = "";
			var resIdArray = new Array();
			//获取选中的资源ID
			//获取treegrid的所有行
			var rows = $("#list").treegrid("getChildren");
			//遍历
			$(rows).each(function(i){
				//如果有checked属性值就代表勾选上了
				if(rows[i].checked){
					resIdArray.push(rows[i].id);
				}
			});
			// 12,13,15
			resIds = resIdArray.join(",");
			
			//把角色id和资源id传递到后台
			$.post("../../role/bindResToRole.action",{roleId:roleId,resIds:resIds},function(data){
				if(data.success){
					//关闭绑定资源窗口
					//$("#bindResWin")这个元素在父页面，操作父页面的元素
					//window.parent: 找到父页面
					window.parent.$("#bindResWin").window("close");
					
					window.parent.$.messager.alert("提示","角色绑定资源成功","info");
				}else{
					$.messager.alert("提示","角色绑定资源失败。"+data.msg,"error");
				}
			},"json");
		});
		
		
		
	</script>
</body>
</html>