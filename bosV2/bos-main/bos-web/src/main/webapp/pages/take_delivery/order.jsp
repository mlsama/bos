<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单查询</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
<script type="text/javascript" src="../../js/crud.js"></script>
<script type="text/javascript" src="../../js/date.js"></script>
<link rel="stylesheet" href="../../js/layer/css/layui.css" />
<script type="text/javascript" src="../../js/layer/layui.js" ></script>
</head>
<body>
	

	<div style="width:100%;margin:10px auto">
	
			<form id="searchForm" method="post" 
				>
			<div class="table-top">
				<table class="table-edit" width="100%">
					<tr class="title">
						<td colspan="6">订单搜索</td>
					</tr>
					<tr>
						<td>订单号</td>
						<td>
							<input type="hidden" name="order.id" id="orderId" />
							<input type="text" name="orderNum" id="orderNum" style="border-radius:10px;"/>
							
						</td>
						<td>下单时间</td>
						<td>
							开始时间：<input id="startTime" name="startTime" type="text" class="easyui-datebox" editable="false"></input>  
							结束时间：<input id="endTime" name="endTime" type="text" class="easyui-datebox" editable="false"></input>  
							<input id="Rest" class="layui-btn layui-btn-primary layui-btn-radius" type="button" value="重置"></input>
						</td>
						
					</tr>
					<tr>
						<td>产品时限</td>
						<td >
							<select class="easyui-combobox" name="sendProNum" style="width:120px">
									<option value="任意快递类型">请选择快递类型</option>
									<option value="速运当日">速运当日</option>
									<option value="速运次日">速运次日</option>
									<option value="速运隔日">速运隔日</option>
								</select>
						</td>
						<td>订单状态</td>
						<td>
							<select class="easyui-combobox" name="status" style="width:120px">
									<option value="0">请选择订单状态</option>
									<option value="1">已下单</option>
									<option value="2">分单成功</option>
									<option value="3">已取件</option>
									<option value="4">运输中</option>
									<option value="5">派送中</option>
									<option value="6">已签收</option>
									<option value="7">已取消</option>
									 
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div class="table-center" style="margin-top:15px">
				<div class="col-md-5">
					<table class="table-edit send" width="100%">
						<tr class="title">
							<td colspan="6">寄件人信息搜索</td>
						</tr>
						<tr>
							<td width="160px",height="32px;">寄件人</td>
							<td><input type="text" name="sendName" style="border-radius:10px;" /></td>
							
						</tr>
					</table>

					<table class="table-edit receiver" width="100%">
						<tr class="title">
							<td colspan="6">收件人信息搜索</td>
						</tr>
						<tr>
							<td width="160px",height="32px">收件人</td>
							<td width="250px"><input type="text" name="recName" style="border-radius:10px;" /></td>
							<td width="100px">手机</td>
							<td><input type="text" name="recMobile" style="border-radius:10px;" /></td>
						</tr>
						
					</table>
					
				</div>
				
			</div>
			<div class="clearfix"></div>
			</form>
			
			<fieldset class="layui-elem-field site-demo-button">
				<div align="center">
					<input id="searchBtn" class="layui-btn layui-btn-primary layui-btn-radius" type="button" value="查询订单"></input>
					<input id="cancelBtn" class="layui-btn layui-btn-primary layui-btn-radius" type="button" value="取消订单"></input>
				</div>
			   
			</fieldset>
		</div>
	
	<!-- 列表 -->
	<table id="list"></table>

	<script type="text/javascript">
		//action访问名称
		var action="order";
		
		//列字段
		var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'orderNum',
				title : '订单号',
				width : 250,
				align : 'center'
			},{
				field : 'customerId',
				title : '客户编号',
				width : 80,
				align : 'center'
			}, {
				field : 'sendName',
				title : '寄件人姓名',
				width : 120,
				align : 'center'
			}, {
				field : 'sendMobile',
				title : '寄件人手机',
				width : 120,
				align : 'center'
			}, {
				field : 'sendAddress',
				title : '寄件人详细地址',
				width : 170,
				align : 'center'
			}, {
				field : 'sendArea',
				title : '寄件人区域(省市区)',
				width : 170,
				align : 'center',
				formatter:function(value,row,index){
					return row.sendArea.province+row.sendArea.city+row.sendArea.district;
				}
			},{
				field : 'sendCompany',
				title : '寄件人公司',
				width : 170,
				align : 'center'
				
			},{
				field : 'sendAddress',
				title : '寄件人详细地址',
				width : 170,
				align : 'center'
				
			},{
				field : 'recName',
				title : '收件人',
				width : 120,
				align : 'center'
			}, {
				field : 'recMobile',
				title : '收件人手机',
				width : 120,
				align : 'center'
			}, {
				field : 'recArea',
				title : '收件人区域（省市区）',
				width : 120,
				align : 'center',
				formatter:function(value,row,index){
					return row.recArea.province+row.recArea.city+row.recArea.district;
				}
			},{
				field : 'recCompany',
				title : '收件人公司',
				width : 200,
				align : 'center'
			},{
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
				field : 'goodsType',
				title : '货物类型',
				width : 80,
				align : 'center',
			},{
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
				field : 'remark',
				title : '备注',
				width : 200,
				align : 'center'
			},  {
				field : 'sendMobileMsg',
				title : '给小哥捎话',
				width : 200,
				align : 'center'
			},  {
				field : 'orderType',
				title : '订单类型',
				width : 200,
				align : 'center',
				formatter: function(value,row,index){
						// 1：自动分单      2：人工分单
					return value==1?"自动分单":"人工分单";
				}
			},  {
				field : 'status',
				title : '订单状态',
				width : 200,
				align : 'center',
				formatter:function(value,row,index){
					/* <option value="1">已下单</option>
					<option value="2">分单成功</option>
					<option value="3">已取件</option>
					<option value="4">运输中</option>
					<option value="5">派送中</option>
					<option value="6">已签收</option>
					<option value="7">已取消</option> */
					if(value==1){
						return "已下单";
					}else if(value==2){
						return "分单成功";
					}else if(value==3){
						return "已取件";
					}else if(value==4){
						return "运输中";
					}else if(value==5){
						return "派送中";
					}else if(value==6){
						return "已签收";
					}else if(value==7){
						return "<font color='red'>已取消</font>";
					}
				}
			}, {
				field : 'orderTime',
				title : '下单时间',
				width : 200,
				align : 'center',
				formatter :function(value,row,index){
					var date = dateFormat(value);
					return date;
				}
			
				
			}] ];
		/*订单号orderNum，订单状态status，customerId  */
		$("#cancelBtn").click(function(){
			var rows = $("#list").datagrid("getSelections");
			if(rows.length!=1){
				$.messager.alert("提示","只能选择一行","info");
				return ;
			}
		
			var orderNum = rows[0].orderNum;
			var status = rows[0].status;
			var customerId = rows[0].customerId;
			
			
			if(status==1 || status==2){
				$.messager.confirm('确认','您确认要删除这个订单吗？',function(value){    
				    if (value){    
						 $.post("../../order/cancelOrder.action",{orderNum:orderNum,status:status,customerId:customerId},function(data){
							if(data.success){
								$.messager.alert("提示","取消订单成功","info");
								$("#list").datagrid("reload");
							}else{
								$.messager.alert("错误","取消订单失败","error");
							}
						},"json"); 
				    }    
				}); 
				
			}else{
				var state ;
				if(status==1){
					 state = "已下单";
				}else if(status==2){
					 state = "分单成功";
				}else if(status==3){
					 state = "取件了,不能取消订单";
				}else if(status==4){
					 state = "哥,都在运送中了,别取消了";
				}else if(status==5){
					state = "派送中了,不能取消订单";
				}else if(status==6){
					state = "已经签收了,不能取消订单";
				}else if(status==7){
					state = "这个订单已经取消了,不能再次取消";
				}
				$.messager.alert("提示",state,"info");
			}
		});
		
		$("#Rest").click(function(){
			$("#searchForm").form("reset");
			


			$("#list").datagrid("load",{});
		});
		
		$("#list").datagrid({   
		    rowStyler:function(index,row){   
		        if (row.status==7){  
		        	
		            return 'background-color:green;';   
		        }   
		    }   
		});
		
	
		
	</script>
</body>
</html>