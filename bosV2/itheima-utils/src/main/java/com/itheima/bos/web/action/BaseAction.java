package com.itheima.bos.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.bos.service.BaseService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	// 使用模型驱动接收页面参数
	private T model;
	//编写反射代码获取model类型，创建model对象
	
	//把result作为成员变量
	protected Map<String, Object> result = new HashMap<String, Object>();
	
	public BaseAction(){
		//获取当前运行的Action类型
		Class clz = this.getClass(); //StandardAction.class
		
		//获取泛型父类： BaseAction<Standard>
		Type type = clz.getGenericSuperclass();
		
		//强制类型
		ParameterizedType pt = (ParameterizedType)type; 
		
		//获取泛型父类里面的泛型参数
		Class modelClass = (Class)pt.getActualTypeArguments()[0];
		
		//创建对象
		try {
			model = (T) modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public T getModel() {
		return model;
	}

	// 注入Service
	private BaseService<T> baseService;
	public void setBaseService(BaseService<T> baseService) {
		this.baseService = baseService;
	}


	/**
	 * 查询所有数据
	 * 
	 * @throws Exception
	 */
	@Action("list")
	public String list() throws Exception {
		// 查询所有数据集合
		List<T> list = baseService.findAll();

		// 前端页面使用的easyui的datagrid，提供一个json数组
		return writeJson(list);
	}

	// 使用属性驱动接收page和rows
	private Integer page;
	private Integer rows;

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}

	public Integer getRows() {
		return rows;
	}


	/**
	 * 分页+条件查询
	 * 
	 * @throws Exception
	 */
	@Action("listByPage")
	public String listByPage() throws Exception {

		Specification<T> spec = buildSpecification();

		// 创建分页对象
		Pageable pageable = new PageRequest(page - 1, rows);

		// 查询数据
		Page<T> pageBean = baseService.findAll(spec, pageable);

		// 取出总记录数
		long totalCount = pageBean.getTotalElements();
		// 取出当前页的数据列表
		List<T> list = pageBean.getContent();

		// 使用Map集合转换key-value形式
		//Map<String, Object> result = new HashMap<String, Object>();

		result.put("total", totalCount);
		result.put("rows", list);

		return writeJson(result);
	}

	protected abstract Specification<T> buildSpecification();

	// 把对象转换为json字符串
	protected String writeJson(Object result) throws IOException {
		// 把result转换为json
		String json = JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);

		// 把json字符串返回给页面
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置回显数据的编码
		response.setCharacterEncoding("utf-8");
		//response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(json);

		// NONE:代表不返回视图
		return NONE;
	}

	/**
	 * 保存方法
	 * 
	 * @throws Exception
	 */
	@Action("save")
	public String save() throws Exception {
		
		// 已经使用模型驱动接收了页面参数
		// 调用业务
		try {
			baseService.save(model);

			// 成功 {success:true}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			// 失败 {success:false,msg:失败原因}
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}

	// 接收uuid
	private Long uuid;

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Long getUuid() {
		return uuid;
	}


	/**
	 * 查询一个对象
	 * 
	 * @throws Exception
	 */
	@Action("get")
	public String get() throws Exception {
		// 调用业务
		T standard = baseService.findOne(uuid);
		// 把对象转换为json
		return writeJson(standard);
	}

	// 接收ids参数
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@Action("delete")
	public String delete() throws Exception {
		//Map<String, Object> result = new HashMap<String, Object>();
		try {
			baseService.delete(ids);

			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
}
