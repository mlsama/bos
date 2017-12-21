package com.itheima.bos.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	/**
	 * @param toMail 
	 * @param subject
	 * @param content
	 */
	public static void sendMail(String toMail,String subject,String content) {
		// 1.创建Session
		// 设置发送参数
		Properties props = new Properties();
		// 发送邮箱的服务地址
		props.setProperty("mail.smtp.host", "smtp.163.com");
		// 是否验证登录
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("Ace931124@163.com", "YingYan931124");
			}
		});
		
		// 开启debug功能，看到整个发送的过程
		session.setDebug(true);
		// 2.设置邮件的参数
		MimeMessage mail = new MimeMessage(session);
		// 寄件人
		try {
			mail.setFrom(new InternetAddress("Ace931124@163.com"));
			// 收件人
			mail.setRecipient(RecipientType.TO, new InternetAddress(toMail));
			// 标题
			mail.setSubject(subject);
			// 内容
			mail.setContent(content, "text/html;charset=utf-8");

			// 3.发送
			Transport.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
