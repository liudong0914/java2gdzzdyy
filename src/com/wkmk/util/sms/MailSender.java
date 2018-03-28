package com.wkmk.util.sms;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSender{
	
	private String sender;
	private JavaMailSenderImpl javaMailSenderImpl;
	
	public MailSender(){
		try {
			this.sender = new InternetAddress(MimeUtility.encodeText("进步学堂技术服务")+"<service@wkmk.com>").toString();
			this.javaMailSenderImpl = new JavaMailSenderImpl();
			this.javaMailSenderImpl.setHost("smtp.exmail.qq.com");
			
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.auth", "true");
			this.javaMailSenderImpl.setJavaMailProperties(properties);
			
			this.javaMailSenderImpl.setPort(25);
			this.javaMailSenderImpl.setUsername("service@wkmk.com");
			this.javaMailSenderImpl.setPassword("java8345");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JavaMailSenderImpl getJavaMailSenderImpl() {
		return javaMailSenderImpl;
	}
	
	public void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
		this.javaMailSenderImpl = javaMailSenderImpl;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getemailcontent(HttpServletRequest httpServletRequest, String title, String content) {
		String http = httpServletRequest.getScheme();
		String severname = httpServletRequest.getServerName();
		int port = httpServletRequest.getServerPort();	
		
		StringBuffer buf = new StringBuffer();
		if(content.indexOf("src=") != -1) {
			String[] photoarrays = content.split("src=");	
			for(int i=0; i<photoarrays.length-1; i++) {
				//System.out.println("photoarrays:" + photoarrays[i]);
				if(i != 0) {
					if(port != 80) {
						buf.append(photoarrays[i].substring(1)).append("src=").append("\"").append(http).append("://").append(severname).append(":").append(port);
					}else {
						buf.append(photoarrays[i].substring(1)).append("src=").append("\"").append(http).append("://").append(severname);
					}					
				}else {
					if(port != 80) {
						buf.append(photoarrays[i]).append("src=").append("\"").append(http).append("://").append(severname).append(":").append(port);
					}else {
						buf.append(photoarrays[i]).append("src=").append("\"").append(http).append("://").append(severname);
					}					
				}
			}
			buf.append(photoarrays[photoarrays.length-1].substring(1));
		}else {
			buf.append(content);
		}
		return buf.toString();
	}
}
