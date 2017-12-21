<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>快递员</title>
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

<!-- 导入bootstrap资源 -->
<link rel="stylesheet" href="../../css/bootstrap.css">
<link rel="stylesheet" href="../../css/bootstrap-theme.css">
<script src="../../js/bootstrap/bootstrap.js"></script>

<style>
.fileinput-button {
	position: relative;
	display: inline-block;
	overflow: hidden;
}

.fileinput-button input {
	position: absolute;
	right: 0px;
	top: 0px;
	opacity: 0;
	-ms-filter: 'alpha(opacity=0)';
	font-size: 200px;
}
</style>

</head>
<body>
	<!-- 搜索 -->
	<form id="searchForm" action="../../courier/batchExport.action" method="post">
		工号:<input type="text" name="courierNum"/>
		姓名:<input type="text" name="name"/>
		电话号码:<input type="text" name="telephone"/>
		<input id="searchBtn" type="button" value="搜索"/> 
	</form>
	
	<!-- 工具条 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
		<a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		<a id="importBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">Excel导入</a>
		<a id="exportBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">导出</a>
	</div>

	<!-- 列表展示 -->
	<table id="list"></table>
	
	<!-- 编辑表单 -->
	<div id="editWin" class="easyui-window" data-options="title:'快递员编辑',closed:true" style="width:700px;top:50px;left:200px">
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
							<td>快递员工号</td>
							<td>
								<input type="text" name="courierNum" class="easyui-validatebox" required="true" />
							</td>
							<td>姓名</td>
							<td>
								<input type="text" name="name" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>手机</td>
							<td>
								<input type="text" name="telephone" class="easyui-validatebox" required="true" />
							</td>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>查台密码</td>
							<td>
								<input type="text" name="checkPwd" class="easyui-validatebox" required="true" />
							</td>
							<td>PDA号码</td>
							<td>
								<input type="text" name="pda" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>取派标准</td>
							<td>
								<select name="standard.id" id="standardId" class="easyui-combobox" required="true"
								  data-options="url:'../../standard/list.action',valueField:'id',textField:'name'" style="width:150px"></select>
							</td>
							<td>上班时间</td>
							<td>
								<select name="pickTime.id" id="pickTimeId" class="easyui-combobox" required="true"
								  data-options="url:'../../pickTime/list.action',valueField:'id',textField:'name'" style="width:150px"></select>
							</td>
						</tr>
					</table>
		  </form>
		  </div>
	</div>
	
	
	<!-- excel导入的窗口 -->
	<div id="importWin" class="easyui-window"
		data-options="width:300,height:160,title:'Excel导入窗口',closed:true">
		<form>
			<div align="center">
				<span class="btn btn-success fileinput-button"> <span>请选择Excel文件</span>
					<input type="file" name="excelFile" id="fileId">
				</span> <br><br>
				<a id="startImport" class="btn btn-success fileinput-button" href="#">开始导入</a>
		</form>
	</div>
	
	
	<script type="text/javascript">
		//当前模型的action名称
		var action = "courier"
		
		//当前模型的列属性
		var columns = [ [ {
			field : 'id',
			checkbox : true,
		},{
			field : 'courierNum',
			title : '工号',
			width : 80,
			align : 'center'
		},{
			field : 'name',
			title : '姓名',
			width : 80,
			align : 'center'
		}, {
			field : 'telephone',
			title : '手机号',
			width : 120,
			align : 'center'
		}, {
			field : 'checkPwd',
			title : '查台密码',
			width : 120,
			align : 'center'
		}, {
			field : 'pda',
			title : 'PDA号',
			width : 120,
			align : 'center'
		}, {
			field : 'standard',
			title : '取派标准',
			width : 120,
			align : 'center',
			//value: 当前的列值
			//row:当前行对象
			//index：行索引，从0开始
			formatter:function(value,row,index){
				if(value!=null){
					return value.name;
				}else{
					return "";
				}
			}
		}, {
			field : 'company',
			title : '所属单位',
			width : 200,
			align : 'center'
		}, {
			field : 'pickTime',
			title : '上班时间',
			width : 200,
			align : 'center',
			formatter:function(value,row,index){
				if(value!=null){
					return value.name;
				}else{
					return "";
				}
			}
		} ] ];
		
		function loadEditFormValue(row){
			//清空收派标准
			$("#standardId").combobox("clear");
			//回显快递员的选项默认值
			if(row.standard!=null){
				$("#standardId").combobox("setValue",row.standard.id);
			}
			//清空收派时间
			$("#pickTimeId").combobox("clear");
			//回显收派时间的选项默认值
			if(row.pickTime!=null){
				$("#pickTimeId").combobox("setValue",row.pickTime.id);
			}
		}
		
		//点击导入按钮，弹出导入窗口
		$("#importBtn").click(function(){
			$("#importWin").window("open");
		});
		
		//开始上传excel文件
		$("#startImport").click(function(){
			//判断文件的后缀名是否为xls或xlsx
			//获取到选择的文件名称
			var filename=$("#fileId").val();
			
			//编写正则表达式
			var reg=/^.+\.(xls|xlsx)$/;
			if(!reg.test(filename)){
				//文件后缀有误
				$.messager.alert("提示","文件后缀必须为.xls或.xlsx","warning");
				return;
			}
			
			//打开进度条
			$.messager.progress({
				title:"提示",
				msg:"正在导入..."
			});
			
			//使用jQuery的ajaxFileUpload异步上传excel文件
			$.ajaxFileUpload({
				
				//上传文件的地址url
				url:"../../courier/batchImport.action",
				
				//上传文件的file组件的id
				fileElementId:"fileId",
				
				//dataType: 服务器返回数据类型
				dataType:"json",
				
				//success: 服务器的回调函数
				success:function(data){
					//关闭进度条
					$.messager.progress("close");
					if(data.success){
						//刷新datagrd
						$("#list").datagrid("reload");
						//关闭excel导入窗口
						$("#importWin").window("close");
						$.messager.alert("提示","excel导入成功","info");
					}else{
						$.messager.alert("提示","excel导入失败","error");
					}
				},
				//error: 上传失败时回调函数
				error:function(e){
					$.messager.alert("提示","上传失败",+e,"error");
				}
			
			});
			
		});
		
		//excel导出
		$("#exportBtn").click(function(){
			$("#searchForm").submit();	
		});
		
	</script>
	
</body>
</html>