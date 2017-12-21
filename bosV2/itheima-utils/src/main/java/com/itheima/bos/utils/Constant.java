package com.itheima.bos.utils;

/**
 * 常量类
 * 
 * @author lenovo
 *
 */
public class Constant {

	// http://localhost:9081/crm-web
	// CRM系统访问常量
	public static final String CRM_HOST = "http://localhost:9081";
	public static final String CRM_CONTEXT = "/crm-web";
	public static final String CRM_URL = CRM_HOST + CRM_CONTEXT;

	// BOS前端系统访问常量
	public static final String BOS_FORE_HOST = "http://localhost:9082";
	public static final String BOS_FORE_CONTEXT = "/bos_fore";
	public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;

	// BOS后端系统访问常量
	public static final String BOS_BACK_HOST = "http://localhost:9080";
	public static final String BOS_BACK_CONTEXT = "/bos-web";
	public static final String BOS_BACK_URL = BOS_BACK_HOST + BOS_BACK_CONTEXT;

}
