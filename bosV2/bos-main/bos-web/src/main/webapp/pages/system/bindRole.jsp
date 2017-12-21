<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户绑定角色页面</title>
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
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">用户绑定角色</a>
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
		
		//列表展示
		$("#list").datagrid({
			//后台url地址，该地址必须返回json格式数据(对象或数组)
			url:"../../"+action+"/listByPageAndUser.action?userId=${param.userId}",
			//列填充
			//field:列属性名称
			columns:columns,
			//是否使用分页展示（默认false）
			pagination:true,
			//绑定工具条
			toolbar:"#toolbar",
			onLoadSuccess:function(data){
				$(data.rows).each(function(i){
					if(data.rows[i].checked){
						//选中行
						$("#list").datagrid("selectRow",i);
					}
				});
			}
		});
		
		
		//保存用户和角色关系
		$("#saveBtn").click(function(){
			
			//获取用户Id
			var userId = "${param.userId}";
			
			//获取选中的角色id
			var rows = $("#list").datagrid("getSelections");
			
			var roleIds = "";
			
			var roleIdArray = new Array();
			$(rows).each(function(i){
				roleIdArray.push(rows[i].id);
			});
			roleIds = roleIdArray.join(",");
			
			$.post("../../user/bindRoleToUser.action",{userId:userId,roleIds:roleIds},function(data){
				if(data.success){
					//关闭绑定资源窗口
					//$("#bindResWin")这个元素在父页面，操作父页面的元素
					//window.parent: 找到父页面
					window.parent.$("#bindRoleWin").window("close");
					
					window.parent.$.messager.alert("提示","用户绑定角色成功","info");
				}else{
					$.messager.alert("提示","用户绑定角色失败。"+data.msg,"error");
				}
			},"json");
			
		});
		
		
	</script>
</body>
</html>