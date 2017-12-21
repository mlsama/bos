<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加宣传任务</title>
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
	
<!-- 导入KindEditor的资源文件 -->
<script type="text/javascript" src="../../js/editor/kindeditor-min.js"></script>
<script type="text/javascript" src="../../js/editor/lang/zh_CN.js"></script>
	
</head>
<body>
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				<a id="back" icon="icon-back" href="#" class="easyui-linkbutton" plain="true">返回列表</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="promotionForm" enctype="multipart/form-data"
				action="../../promotion/save.action" method="post">
				<table class="table-edit" width="95%" align="center">
					<tr class="title">
						<td colspan="4">宣传任务</td>
					</tr>
					<tr>
						<td>宣传概要(标题):</td>
						<td colspan="3">
							<input type="text" name="title" id="title" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>活动范围:</td>
						<td>
							<input type="text" name="activeScope" id="activeScope" class="easyui-validatebox" />
						</td>
						<td>宣传图片:</td>
						<td>
							<input type="file" name="titleImgFile" id="titleImg" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>发布时间: </td>
						<td>
							<input type="text" name="startDate" id="startDate" class="easyui-datebox" required="true" />
						</td>
						<td>失效时间: </td>
						<td>
							<input type="text" name="endDate" id="endDate" class="easyui-datebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>宣传内容(活动描述信息):</td>
						<td colspan="3">
							<textarea id="description" name="description" style="width:100%" rows="20"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<script type="text/javascript">
			$(function(){
				$("#back").click(function(){
					window.location.href = "promotion.jsp";
				});
				
				//使用KindEditor编辑器
				 KindEditor.ready(function(K) {
		                window.editor = K.create('#description',{
		                	//修改文件上传的地址
		                	uploadJson:"../../image/upload.action",
		                	//图片空间的查询地址
		                	fileManagerJson : '../../image/manager.action',
		                	//开启图片空间
		                    allowFileManager : true
		                });
		        });
				
				//点击保存，提交任务表单
				$("#save").click(function(){
					//把KindEditor的编辑器的数据同步到textarea中
					editor.sync();
					//直接提交表单
					$("#promotionForm").submit();
				});
			});
		
		</script>
</body>
</html>