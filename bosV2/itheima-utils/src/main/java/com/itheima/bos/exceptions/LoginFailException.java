package com.itheima.bos.exceptions;

/**
 * 登录异常
 * @author lenovo
 *
 */
public class LoginFailException extends Exception{

	public LoginFailException(String msg){
		super(msg);
	}
}
