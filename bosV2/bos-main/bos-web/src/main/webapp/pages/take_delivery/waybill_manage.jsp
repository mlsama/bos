<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>运单管理</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body>
	<!-- 搜索框 -->
	<div class="datagrid-toolbar" style="height: 40px;">
		<form id="searchForm" method="post">
		    运单号:<input name="wayBillNum" style="line-height:26px;border:1px solid #ccc">
		    发货地:<input name="sendAddress" style="line-height:26px;border:1px solid #ccc" >
		    收货地:<input name="recAddress" style="line-height:26px;border:1px solid #ccc" >
		<select class="easyui-combobox" style="width:150px" name="sendProNum">
					<option value="">请选择快递产品类型</option>
					<option value="速运当日">速运当日</option>
					<option value="速运次日">速运次日</option>
					<option value="速运隔日">速运隔日</option>
		</select>			
		<select class="easyui-combobox" style="width:150px" name="signStatus">
					<option value="">请选择运单状态</option>
					<option value="1">待发货</option>
					<option value="2">派送中</option>
					<option value="3">已签收</option>
					<option value="4">异常</option>
		</select>
		</form>
	</div>
	
	<!-- 列表 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">修改</a>
		<a id="cancelBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">作废</a>
		<a id="restoreBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">还原</a>
		<a id="queryBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">查询结果</a>
	</div>
	<!-- 运单编辑 -->
	<!-- 录入窗口 -->
	<div id="editWin" class="easyui-window"
		data-options="title:'运单编辑',closed:true"
		style="width: 900px; top: 50px; left: 200px">
		<!-- 按钮区域 -->
		<div class="datagrid-toolbar">
			<a id="mySaveBtn" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
		</div>
		<!-- 编辑区域 -->
		<div style="width: 95%; margin: 10px auto">
			<form id="editForm" method="post">
				<!-- 隐藏域id -->
				<input type="hidden" name="id"/>
				<div class="table-top">
					<table class="table-edit" width="95%">
						<tr class="title">
							<td colspan="6">单号信息</td>
						</tr>
						<tr>
							<td>订单号</td>
							<td><input type="hidden" name="order.id" id="orderId" /> <input
								type="text" name="order.orderNum" id="orderNum" /></td>
							<td>运单号</td>
							<td><input type="text" name="wayBillNum" id="wayBillNum" />
							</td>
						</tr>
						<tr>
							<td>到达地</td>
							<td><input type="text" name="arriveCity" required="true" /></td>
							<td>产品时限</td>
							<td><select class="easyui-combobox" name="sendProNum">
									<option value="速运当日">速运当日</option>
									<option value="速运次日">速运次日</option>
									<option value="速运隔日">速运隔日</option>
							</select></td>
							<td>配载要求</td>
							<td><select class="easyui-combobox" name="floadreqr">
									<option value="无">无</option>
									<option value="禁航空">禁航空</option>
									<option value="禁铁路航空">禁铁路航空</option>
							</select></td>
						</tr>
						<tr>
							<td>受理单位</td>
							<td><input type="text" name="order.courier.company"
								required="true" /></td>
							<td>快递员</td>
							<td><input type="text" name="order.courier.name"
								required="true" /></td>
						</tr>
					</table>
				</div>
				<div class="table-center" style="margin-top: 15px">
					<div class="col-md-5">
						<table class="table-edit send" width="95%">
							<tr class="title">
								<td colspan="4">寄件人信息</td>
							</tr>
							<tr>
								<td>寄件人</td>
								<td><input type="text" name="sendName" required="true" /></td>
								<td>地址</td>
								<td><input type="text" name="sendAddress" required="true" /></td>
							</tr>
							<tr>
								<td>公司</td>
								<td><input type="text" name="sendCompany" required="true" /></td>
								<td>电话</td>
								<td><input type="text" name="sendMobile" required="true" /></td>
							</tr>
						</table>

						<table class="table-edit receiver" width="95%">
							<tr class="title">
								<td colspan="4">收件人信息</td>
							</tr>
							<tr>
								<td>收件人</td>
								<td><input type="text" name="recName" required="true" /></td>
								<td>地址</td>
								<td><input type="text" name="recAddress" required="true" /></td>
							</tr>
							<tr>
								<td>公司</td>
								<td><input type="text" name="recCompany" required="true" /></td>
								<td>电话</td>
								<td><input type="text" name="recMobile" required="true" /></td>
							</tr>
						</table>
						<table class="table-edit number" width="95%">
							<tr class="title">
								<td colspan="4">货物信息</td>
							</tr>
							<tr>
								<td>原件数</td>
								<td><input type="text" name="num" required="true" /></td>
								<td>实际重量</td>
								<td><input type="text" name="weight" required="true" /></td>
							</tr>

							<tr>
								<td>货物</td>
								<td><input type="text" name="goodsType" required="true" /></td>
								<td>体积</td>
								<td><input type="text" name="vol" required="true" /></td>
							</tr>
						</table>
					</div>
					<div class="col-md-7">
						<table class="table-edit security" width="95%">
							<tr class="title">
								<td colspan="6">包装信息</td>
							</tr>
							<tr>
								<td>保险类型</td>
								<td><select class="easyui-combobox">
										<option value="0">不保险</option>
										<option value="1">委托投保</option>
										<option value="2">自带投保</option>
										<option value="3">保价</option>
								</select></td>
								<td>保险费</td>
								<td><input type="text" name="secuityprice" required="true" /></td>
								<td>声明价值</td>
								<td><input type="text" name="value" required="true" /></td>
							</tr>

							<tr>
								<td>原包装</td>
								<td><select class="easyui-combobox">
										<option value="0">木箱</option>
										<option value="1">纸箱</option>
										<option value="2">快递袋</option>
										<option value="3">其他</option>
								</select></td>
								<td>实际包装</td>
								<td><select class="easyui-combobox">
										<option value="0">木箱</option>
										<option value="1">纸箱</option>
										<option value="2">快递袋</option>
										<option value="3">其他</option>
								</select></td>
							</tr>
							<tr>
								<td>包装费</td>
								<td><input type="text" name="packageprice" required="true" /></td>
								<td>包装要求</td>
								<td><input type="text" name="packageprice" required="true" /></td>
							</tr>
						</table>

						<table class="table-edit max" width="95%">
							<tr>
								<td>实际件数</td>
								<td><input type="text" name="realNum" required="true" /></td>
								<td>计费重量</td>
								<td><input type="text" name="priceWeight" required="true" /></td>
							</tr>
							<tr>
								<td>预收费</td>
								<td><input type="text" name="planprice" required="true" /></td>
								<td><button class="btn btn-default">计算</button></td>
							</tr>
						</table>

						<table class="table-edit money" width="95%">
							<tr class="title">
								<td colspan="6">计费信息</td>
							</tr>
							<tr>
								<td>结算方式</td>
								<td><select class="easyui-combobox">
										<option value="0">现结</option>
										<option value="1">代付</option>
										<option value="2">网络</option>

								</select></td>
								<td>代收款</td>
								<td><input type="text" name="priceWeight" required="true" /></td>
								<td>到付款</td>
								<td><input type="text" name="priceWeight" required="true" /></td>
							</tr>

						</table>
						<table class="table-edit feedback" width="95%">
							<tr class="title">
								<td colspan="4">配送信息</td>
							</tr>
							<tr>
								<td><input type="checkbox">反馈签收</td>
								<td><input type="checkbox">节假日可收货</td>
								<td>送达时限</td>
								<td><input type="text" class="easyui-datebox"
									data-options="editable:false" /></td>
							</tr>

							<tr>
								<td>处理方式</td>
								<td><select class="easyui-combobox">
										<option value="0">无</option>
										<option value="1">不可开箱验货</option>
										<option value="2">开开箱单不可开内包</option>
										<option value="3">可开箱和内包</option>
								</select></td>
								<td>签单返回</td>
								<td><select class="easyui-combobox">
										<option value="0">箱单</option>
										<option value="1">原单</option>
										<option value="2">附原单</option>
										<option value="3">网络派送单</option>
								</select></td>
							</tr>
						</table>
						<table class="table-edit tips" width="95%">
							<tr>
								<td>重要提示</td>
								<td><textarea style="width: 250px; height: 80px;"></textarea></td>
							</tr>

						</table>
					</div>
				</div>
				<div class="clearfix"></div>
			</form>
		</div>
	</div>
	<!-- 作废选项框 -->
	<div id="cancelWin" class="easyui-window" data-options="title:'订单绑定快递员',closed:true" style="width:600px;top:50px;left:200px;height:400px;"></div>
	<script type="text/javascript">
		//action访问名称
		var action="wayBill";
		
		//列字段
		var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'isDelete',
				title : '是否作废',
				width : 80,
				align : 'center',
				formatter:function(value){
					if(value == 0){
						return "<font color='red'>作废</font>";
					}else{
						return "<font color='green'>有效</font>";
					}
				}
			},{
				field : 'wayBillNum',
				title : '运单编号',
				width : 280,
				align : 'center'
			},{
				field : 'sendName',
				title : '寄件人',
				width : 80,
				align : 'center'
			}, {
				field : 'sendMobile',
				title : '寄件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'sendCompany',
				title : '寄件人公司',
				width : 120,
				align : 'center'
			}, {
				field : 'sendAddress',
				title : '寄件人详细地址',
				width : 120,
				align : 'center'
			}, {
				field : 'recName',
				title : '收件人',
				width : 120,
				align : 'center'
			}, {
				field : 'recMobile',
				title : '收件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'sendProNum',
				title : '产品类型',
				width : 200,
				align : 'center'
			},{
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'payTypeNum',
				title : '支付类型',
				width : 200,
				align : 'center'
			}, {
				field : 'weight',
				title : '重量',
				width : 200,
				align : 'center'
			}, {
				field : 'num',
				title : '原件数',
				width : 200,
				align : 'center'
			},  {
				field : 'feeitemnum',
				title : '实际件数',
				width : 200,
				align : 'center'
			},  {
				field : 'actlweit',
				title : '实际重量',
				width : 200,
				align : 'center'
			},  {
				field : 'vol',
				title : '体积',
				width : 200,
				align : 'center'
			}, {
				field : 'floadreqr',
				title : '配载要求',
				width : 200,
				align : 'center'
			}, {
				field : 'wayBillType',
				title : '运单类型',
				width : 80,
				align : 'center',
			}] ];
		$(function(){
			//列表展示
			$("#list").datagrid({
				//后台url地址，该地址必须返回json格式数据(对象或数组)
				url:"../../"+action+"/listByPage.action",
				//列填充
				//field:列属性名称
				columns:columns,
				//是否使用分页展示（默认false）
				pagination:true,
				//绑定工具条
				toolbar:"#toolbar"
			});
			//表单数据回显
			$("#editBtn").click(function(){
				//判断只能选择一个（不选，选多个不行）
				//getSelections()方法返回数组，数组里面一个个的对象
				var rows = $("#list").datagrid("getSelections");
				
				if(rows.length!=1){
					$.messager.alert("提示","修改操作只能选择一行","warning");
					return;
				}
				//清空表单数据
				$("#editForm").form("clear");
				//查询选中行的数据，把数据填充到录入表单
				$("#editForm").form("load","../../"+action+"/get.action?uuid="+rows[0].id);
				//打开窗口
				$("#editWin").window("open");
			});
			//点击保存提交表单数据到后台
			$("#mySaveBtn").click(function(){
				//使用form的submit方法提交表单数据
				$("#editForm").form("submit",{
					//提交表单的url
					url:"../../"+action+"/saveWayBill.action",
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
			//批量作废
			$("#cancelBtn").click(function() {
				var rows = $("#list").datagrid("getSelections");
				if(rows.length == 0){
					$.messager.alert("提示","必须选择一行","waring");
					return;
				}
				var ids = "";
				var idsArray = new Array();
				for(var i = 0; i < rows.length; i++){
					idsArray.push(rows[i].id);
				}
				ids = idsArray.join(",");
				//查询满足条件的所有运单,即订单状态为1或2的
				var content = "<iframe src='cancelWayBill.jsp?ids="+ids+"' style='width:100%;height:100%' frameborder='0'></iframe>";
				//在窗口中嵌入另一个页面
				$("#cancelWin").window({
					content:content
				});
				//打开编辑窗口
				$("#cancelWin").window("open");
			});
			
			$("#restoreBtn").click(function() {
				var rows = $("#list").datagrid("getSelections");
				if(rows.length == 0){
					$.messager.alert("提示","必须选择一行","waring");
					return;
				}
				var ids = "";
				var idsArray = new Array();
				for(var i = 0; i < rows.length; i++){
					idsArray.push(rows[i].id);
				}
				ids = idsArray.join(",");
				
				$.post("../../"+action+"/restoreWayBill.action",{wayBillIds:ids},function(data){
					if(data.success){
						$("#list").datagrid("reload");
						$.messager.alert("提示","还原成功","info");
					}else{
						$.messager.alert("提示","还原失败"+data.msg,"error");
					}
				},"json");
			});
			//绑定查询按钮
			$("#queryBtn").click(function() {
				//调用datagrid的load方法
				$("#list").datagrid("load",getFormData("searchForm"));
			});
		});
	</script>
</body>
</html>