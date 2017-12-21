package com.itheima.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * JavaMail演示代码
 * @author lenovo
 *
 */
public class MailDemo {

	public static void main(String[] args) throws Exception, MessagingException {
		//1.创建Session
		//设置发送参数
		Properties props = new Properties();
		
		//发送邮箱的服务地址
		props.setProperty("mail.smtp.host", "smtp.sina.com");
		//是否验证登录
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ericxu_12345@sina.com","eric12345");
			}
			
		});
		
		//开启debug功能，看到整个发送的过程
		session.setDebug(true);
		
		//2.设置邮件的参数
		MimeMessage mail = new MimeMessage(session);
		//寄件人
		mail.setFrom(new InternetAddress("ericxu_12345@sina.com"));
		//收件人
		mail.setRecipient(RecipientType.TO, new InternetAddress("42912810@qq.com"));
		//标题
		mail.setSubject("测试邮件");
		//内容
		mail.setContent("测试邮件内容<br/><a href='http://gz.itcast.cn'>链接</a>", "text/html;charset=utf-8");
		
		
		//3.发送
		Transport.send(mail);
	}
}
