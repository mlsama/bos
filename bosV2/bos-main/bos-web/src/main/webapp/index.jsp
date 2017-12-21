<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>BOS管理系统 首页</title>
		<link href="favicon.ico" rel="icon" type="image/x-icon" />
		<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
		
		<!-- 导入ztree的资源文件 -->
		<script type="text/javascript" src="js/ztree/js/jquery.ztree.all.min.js"></script>
		<link rel="stylesheet" type="text/css" href="js/ztree/css/zTreeStyle/zTreeStyle.css">
		
		<script type="text/javascript">
			$(function() {
				
				//生成系统菜单树
				//1.设置全局参数
				/*
				var setting = {
					//date属性：设置数据格式	
					data: {
						//simpleData属性: 是否为简单json 
						simpleData: {
							//enable属性；是否开启简单json
							enable: true,
							//树节点的id名称
							idKey:"treeId",
							//树节点的父节点id名称
							pIdKey:"parentId"
						}
					}
				};
				
				//2.准备json数据
				var zNodes = [
				       {treeId:1,parentId:0,name:"基础数据模块",open:true},
				       {treeId:11,parentId:1,name:"收派标准管理",icon:"js/ztree/css/zTreeStyle/img/diy/7.png"},
				       {treeId:12,parentId:1,name:"快递员管理",url:"http://www.baidu.com"},
				       {treeId:2,parentId:0,name:"系统管理模块"},
				       {treeId:21,parentId:2,name:"用户管理"},
				       {treeId:22,parentId:2,name:"角色管理"}
				 ];
				
				//3.生成树
				//参数一：树摆放的元素
				//参数二：全局参数变量
				//参数三：json数据
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				*/
				
				var setting = {
						data: {
							simpleData: {
								enable: true,
								//修改父id的名称
								pIdKey:"_parentId"
							}
						},
						//绑定回调函数
						callback:{
							onClick:onClickTreeNode
						}
				};
				
				//异步方式加载bos系统的菜单数据
				
				/* $.post("data/menu.json",function(data){ // data：后台返回数据
					$.fn.zTree.init($("#treeDemo"), setting, data);
				},"json"); */
				
				
				$.post("user/findMyMenus.action",function(data){ // data：后台返回数据
					$.fn.zTree.init($("#treeDemo"), setting, data);
				},"json");
			
				
				// 页面加载后 右下角 弹出窗口
				window.setTimeout(function(){
					$.messager.show({
						title:"消息提示",
						msg:'欢迎登录，超级管理员！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
						timeout:5000
					});
				},3000);
				
				$("#btnCancel").click(function(){
					$('#editPwdWindow').window('close');
				});
				
				$("#btnEp").click(function(){
					alert("修改密码");
				});
				
				// 设置全局变量 保存当前正在右键的tabs 标题 
				var currentRightTitle  ;
			});
			
			/*******顶部特效 *******/
			/**
			 * 更换EasyUI主题的方法
			 * @param themeName
			 * 主题名称
			 */
			changeTheme = function(themeName) {
				var $easyuiTheme = $('#easyuiTheme');
				var url = $easyuiTheme.attr('href');
				var href = url.substring(0, url.indexOf('themes')) + 'themes/'
						+ themeName + '/easyui.css';
				$easyuiTheme.attr('href', href);
				var $iframe = $('iframe');
				if ($iframe.length > 0) {
					for ( var i = 0; i < $iframe.length; i++) {
						var ifr = $iframe[i];
						$(ifr).contents().find('#easyuiTheme').attr('href', href);
					}
				}
			};
			// 退出登录
			function logoutFun() {
				$.messager
				.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
					if (isConfirm) {
						location.href = 'user/logout.action';
					}
				});
			}
			// 修改密码
			function editPassword() {
				$('#editPwdWindow').window('open');
			}
			// 版权信息
			function showAbout(){
				$.messager.alert("bos v2.0综合物流管理平台","设计: 传智播客<br/> 管理员邮箱: itcast_search@163.com <br/>");
			}
			
			//点击树的节点后触发的函数
			//event:事件对象  event.pageX event.pageY
			//treeId:点击的树节点的id
			//treeNode:点击的树节点对象
			function onClickTreeNode(event,treeId,treeNode){
				//树节点的名称
				var name = treeNode.name;
				//获取pageUrl属性，就是连接的url地址
				var pageUrl = treeNode.pageUrl;
				
				//判断面板是否已经存在
				if( $("#tabs").tabs("exists",name)){
					//存在
					//选择面板
					$("#tabs").tabs("select",name);
				}else{
					//不存在
					//创建面板
					$("#tabs").tabs("add",{
						//标题
						title:name,
						//关闭按钮
						closable:true,
						//content:"内容"
						//href:"pages/base/standard.jsp"
						/*
						 content: 
							 	1）写的具体的内容
							 	2）利用html(iframe标签)内容来嵌套一个页面
						 href: 
							 	1）可以编写一个url地址，加载其他页面内容
							 	2）href加载的页面只能加载页面的body里面的内容（body以外不加载）
						*/
						content:"<iframe src='"+pageUrl+"' style='width:100%;height:100%' frameborder='0'></iframe>"
					});
				}				
			}
			
		</script>
	</head>

	<body class="easyui-layout">
		<!-- 顶部 -->
		<div data-options="region:'north',border:false" style="height:70px;padding:10px;">
			<div>
				<img src="./images/logo.png" border="0">
			</div>
			<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
				[<strong>超级管理员</strong>]，欢迎你！您使用[<strong>192.168.1.100</strong>]IP登录！
			</div>
			<div style="position: absolute; right: 5px; bottom: 10px; ">
				<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
				<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
			</div>
			<!-- 更换皮肤 -->
			<div id="layout_north_pfMenu" style="width: 120px; display: none;">
				<div onclick="changeTheme('default');">default</div>
				<div onclick="changeTheme('gray');">gray</div>
				<div onclick="changeTheme('black');">black</div>
				<div onclick="changeTheme('bootstrap');">bootstrap</div>
				<div onclick="changeTheme('metro');">metro</div>
			</div>
			<!-- 控制面板 -->
			<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
				<div onclick="editPassword();">修改密码</div>
				<div onclick="showAbout();">联系管理员</div>
				<div class="menu-sep"></div>
				<div onclick="logoutFun();">退出系统</div>
			</div>
		</div>
		
		
		<!-- 左边菜单 -->
		<div data-options="region:'west',split:true,title:'菜单导航'" style="width:200px">
			<!-- 加载菜单树 -->
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		
		
		<!-- 中间编辑区域 -->
		<div data-options="region:'center'">
			<div id="tabs" fit="true" class="easyui-tabs" border="false">
				<div title="消息中心" id="subWarp" style="width:100%;height:100%;overflow:hidden">
					<iframe src="message.jsp" style="width:100%;height: 100%" frameborder="0"></iframe>
				</div> 
			</div>
		</div>
		
		<!-- 底部版权 -->
		<div data-options="region:'south',border:false" style="height:50px;padding:10px;">
			<table style="width: 100%;">
				<tbody>
					<tr>
						<td style="width: 400px;">
							<div style="color: #999; font-size: 8pt;">
								BOSv2.0综合物流管理平台 | Powered by <a href="http://www.itcast.cn/">传智播客</a>
							</div>
						</td>
						<td style="width: *;" class="co1"><span id="online" style="background: url(./images/online.png) no-repeat left;padding-left:18px;margin-left:3px;font-size:8pt;color:#005590;">在线人数:1</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!--修改密码窗口-->
		<div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false" maximizable="false" icon="icon-save" style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<table cellpadding=3>
						<tr>
							<td>新密码：</td>
							<td>
								<input id="txtNewPass" type="Password" class="txt01" />
							</td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td>
								<input id="txtRePass" type="Password" class="txt01" />
							</td>
						</tr>
					</table>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
					<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
				</div>
			</div>
		</div>

		<div id="mm" class="easyui-menu" style="width:120px;">
			<div data-options="name:'Close'">关闭当前窗口</div>
			<div data-options="name:'CloseOthers'">关闭其它窗口</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-cancel',name:'CloseAll'">关闭全部窗口</div>
		</div>

	</body>

</html>