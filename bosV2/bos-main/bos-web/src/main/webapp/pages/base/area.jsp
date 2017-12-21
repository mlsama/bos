<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>区域</title>
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
		省:<input type="text" name="province"/>
		市:<input type="text" name="city"/>
		区:<input type="text" name="district"/>
		<input id="searchBtn" type="button" value="搜索"/> 
	</form>
	
	<!-- 工具条 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		<a id="importBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">Excel导入</a>
		<a id="exportBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">Excel导出</a>
	</div>

	<!-- 列表展示 -->
	<table id="list"></table>
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'区域添加修改',closed:true" style="width:600px;top:50px;left:200px">
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
							<td>省</td>
							<td>
								<input type="text" name="province" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>市</td>
							<td>
								<input type="text" name="city" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>区</td>
							<td>
								<input type="text" name="district" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>邮编</td>
							<td>
								<input type="text" name="postcode" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>简码</td>
							<td>
								<input type="text" name="shortcode" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>城市编码</td>
							<td>
								<input type="text" name="citycode" class="easyui-validatebox" required="true" />
							</td>
						</tr>
					</table>
		  </form>
		  </div>
	</div>
	
	<!-- excel导入的窗口 -->
	<div id="importWin" class="easyui-window" data-options="width:400,height:200,title:'Excel导入窗口',closed:true">
		<form>
			请选择excel文件：<input type="file" name="excelFile" id="fileId"/><br/>
			<a id="startImport" class="easyui-linkbutton" href="#" icon="icon-save">开始导入</a>
		</form>
	</div>
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "area"
		
		var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'province',
				title : '省',
				width : 120,
				align : 'center'
			}, {
				field : 'city',
				title : '市',
				width : 120,
				align : 'center'
			}, {
				field : 'district',
				title : '区',
				width : 120,
				align : 'center'
			}, {
				field : 'postcode',
				title : '邮编',
				width : 120,
				align : 'center'
			}, {
				field : 'shortcode',
				title : '简码',
				width : 120,
				align : 'center'
			}, {
				field : 'citycode',
				title : '城市编码',
				width : 200,
				align : 'center'
			} ] ];
		
		
			//点击导入按钮，弹出导入窗口
			$("#importBtn").click(function(){
				$("#importWin").window("open");
			});
			
			//开始上传excel文件
			$("#startImport").click(function(){
				//判断文件的后缀名是否为xls或xlsx
				//获取到选择的文件名称
				var filename = $("#fileId").val();
				
				//编写正则表达式
				//.: 点代表一个任意字符
				// + ： 加号代表一个或者多个
				var reg = /^.+\.(xls|xlsx)$/;
				
				if(!reg.test(filename)){
					//文件后缀有误
					$.messager.alert("提示","文件后缀必须为.xls或.xlsx","warning");
					return;
				}
				
				//打开进度条
				$.messager.progress({
					title:"提示",
					msg:"正在导入中..."
				});
				
				//使用jQuery的ajaxFileUpload异步上传excel文件
				$.ajaxFileUpload({
					//上传文件的地址
					url:"../../area/batchImport.action",
					//上传文件的file组件的id： <input type="file" name="excelFile" id="fileId"/>
					fileElementId:"fileId",
					//dataType: 服务器返回数据类型
					dataType:"json",
					//success: 服务器的回调函数
					success:function(data){ //data: 服务器返回的数据
						//关闭进度条
						$.messager.progress("close");
						
						if(data.success){
							//刷新datagrd
							$("#list").datagrid("reload");
							
							//关闭excel导入窗口
							$("#importWin").window("close");
							
							$.messager.alert("提示","excel导入成功","info");
						}else{
							$.messager.alert("提示","excel导入失败,"+data.msg,"error");
						}
					},
					//error: 上传失败时回调函数
					error:function(e){
						$.messager.alert("提示","上传失败,"+e,"error");
					}
				});
				
			});
			
			//excel导出提交请求到后台
			$("#exportBtn").click(function(){
				//注意：不要使用异步提交
				//直接提交搜索表单
				$("#searchForm").submit();
			});
	</script>
	
</body>
</html>