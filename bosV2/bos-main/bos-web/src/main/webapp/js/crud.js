//页面加载完成后执行
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
			
			//搜索功能
			$("#searchBtn").click(function(){
				//获取整个表单的所有参数
				//serialize():序列化表单的内容为字符串:name=10&minWeight=5&maxWeight=20
				//alert(decodeURIComponent($("#searchForm").serialize()));
				//alert(getFormData("searchForm").name);
				
				//调用datagrid的load方法,把查询请求提交到listByPage.action中
				$("#list").datagrid("load",getFormData("searchForm"));
			});
			
			//点击添加按钮弹出录入窗口
			$("#saveBtn").click(function(){
				//清空表单数据
				$("#editForm").form("clear");
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
			
			//表单数据回显
			$("#editBtn").click(function(){
				//判断只能选择一个（不选，选多个不行）
				//getSelections()方法返回数组，数组里面一个个的对象
				var rows = $("#list").datagrid("getSelections");
				
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
				var rows = $("#list").datagrid("getSelections");
				
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
								$("#list").datagrid("reload");
								
								$.messager.alert("提示","删除成功","info");
							}else{
								$.messager.alert("提示","删除失败，原因："+data.msg,"error");
							}
						},"json");
					}
				});
			});
		});
//提供一个空实现的方法，为了让页面没有这个方法的时候不报错		
function loadEditFormValue(row){}