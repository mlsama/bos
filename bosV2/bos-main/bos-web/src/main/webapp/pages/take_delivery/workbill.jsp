<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工单管理</title>
<!-- 引入easyui的资源文件 （5个）-->
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/date.js"></script>
</head>
<body>
	<!-- 搜索 -->
	<form id="searchForm" action="" method="post">
		订单号:<input type="text" name="order.orderNum"/>
		创建时间范围:从<input type="text" name="startTime" class="easyui-datebox"/>
		到<input type="text" name="endTime" class="easyui-datebox"/>
		快递员:<input type="text" name="courierId.name"/>
		<input id="searchBtn" type="button" value="搜索"/> 
	</form>
	<!-- 工具条 -->
	<div id="toolbar">
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改工作单</a>
	</div>
	<!-- 列表展示 -->
	<table id="list"></table>
	
	<!-- 录入窗口 -->
	<div id="editWin" class="easyui-window"
		data-options="title:'收派标准编辑',closed:true"
		style="width: 600px; top: 50px; left: 200px">
		<!-- 按钮区域 -->
		<div class="datagrid-toolbar">
			<a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
		</div>
		<!-- 编辑区域 -->
		<div>
			<form id="editForm" method="post">
				<%-- 设计一个隐藏域，携带id --%>
				<input type="hidden" name="id" />
				<table width="80%" align="center" class="table-edit">
					<tr>
						<td>备注</td>
						<td>
							<textarea rows="5" cols="25" name="remark"></textarea>
						</td>
					</tr>
					<tr>
						<td>快递员</td>
						<td><select id="courierId" name="courierId.id" class="easyui-combobox"
							data-options="url:'../../courier/list.action',valueField:'id',textField:'name'"
							style="width: 150px"></select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		//当前模型的action名称
		var action = "workBill";

		//当前模型的列属性
		var columns = [ [
				{
					field : 'id',
					checkbox : true,
				},
				{
					field : 'pickstate',
					title : '取件状态',
					width : 80,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == '1') {
							return "<select style='color:blue;' name='pickstate' onchange=get(this)><option value='1'selected='selected'>未取件</option><option value='2'>已取件</option><option value='3'>已取消</option></select>";
						} else if (value == '2') {
							return "<select style='color:green;' name='pickstate' onchange=get(this)><option value='1'>未取件</option><option value='2' selected='selected'>已取件</option><option value='3'>已取消</option></select>";
						} else if (value == '3') {
							return "<select style='color:red;' name='pickstate' onchange=get(this)><option value='1'>未取件</option><option value='2' >已取件</option><option value='3' selected='selected'>已取消</option></select>";
						} else {
							return "";
						}
					}
				}, {
					field : 'buildtime',
					title : '工作单创建时间',
					width : 150,
					align : 'center',
					formatter : function(value, row, index) {
						if (value != null) {
							return dateFormat(value);
						} else {
							return "";
						}
					}
				}, {
					field : 'remark',
					title : '备注',
					width : 240,
					align : 'center'
				}, {
					field : 'order',
					title : '订单编号',
					width : 280,
					align : 'center',
					formatter : function(value, row, index) {
						if (value) {
							return value.orderNum;
						} else {
							return "";
						}
					}

				}, {
					field : 'courierId',
					title : '快递员',
					width : 120,
					align : 'center',
					formatter : function(value, row, index) {
						if (value) {
							return value.name;
						} else {
							return "";
						}
					}
				} ] ];
		$(function() {
			//列表展示
			$("#list").datagrid({
				//后台url地址，该地址必须返回json格式数据(对象或数组)
				url : "../../" + action + "/listByPage.action",
				//列填充
				//field:列属性名称
				columns : columns,
				//是否使用分页展示（默认false）
				pagination : true,
				toolbar : "#toolbar"
			});
			
			//搜索功能
			$("#searchBtn").click(function() {
				//获取整个表单的所有参数
				//调用datagrid的load方法
				$("#list").datagrid("load", getFormData("searchForm"));
			});

			//点击修改取件状态按钮
			$("#editBtn").click(function() {
				//getSelections()方法返回数组，数组里面一个个的对象
				var rows = $("#list").datagrid("getSelections");
				if (rows.length != 1) {
					$.messager.alert("提示", "修改取件状态操作有且只能选择一行", "warning");
					return;
				}
				//清空一下表单
				$("#editForm").form("clear");
				//回显表单数据
				loadEditFormValue(rows[0]);
				$("#editForm").form("load","../../"+action+"/get.action?uuid="+rows[0].id);
				//打开窗口
				$("#editWin").window("open");
			});
			
			//修改后的保存按钮
			$("#save").click(function() {
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
							//刷新datagrid
							$("#list").datagrid("reload");
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
		});

		//改变工作单状态的change事件
		function get(status) {
			var rows = $("#list").datagrid("getSelections");
			if (rows.length != 1) {
				$.messager.alert("提示", "修改工作单状态只能选择一行", "warning");
				return;
			}
			//发送异步请求改变状态
			$.post("../../" + action + "/changeStatus.action", {
				id : rows[0].id,
				pickstate : $(status).val()
			}, function(data) {
				if (data.success) {
					$.messager.alert("提示", "修改状态成功", "info");
					$("#list").datagrid("reload");
				} else {
					$.messager.alert("提示", "修改状态失败," + data.msg, "error");
				}
			}, "json")
		}
		
		//下拉框回显
		function loadEditFormValue(row){
			//清空收派标准
			$("#courierId").combobox("clear");
			//回显快递员的选项默认值
			if(row.courierId !=null){
				$("#courierId").combobox("setValue",row.courierId.id);
			}
		}
	</script>
	
</body>
</html>