package com.wkmk.util.sms;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class TextBookOrderSendMail {
	private static void sendMsg(String clientSendString){
		try {
			System.out.println("开始发送邮件");
			
			TextBookOrderSenderMailImpl mailSender = new TextBookOrderSenderMailImpl();
			JavaMailSenderImpl senderImpl = mailSender.getJavaMailSenderImpl();
			
			// 设定收件人、寄件人、主题与内文
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "gbk");
			messageHelper.setFrom(mailSender.getSender());
			//messageHelper.setTo("5471946@qq.com");
			messageHelper.setTo("liud@dukeliud.cn");
			messageHelper.setSubject("(测试数据)教材订购成功，请尽快发货处理！");	
			
			messageHelper.setText("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\"></head><body>广东省中职德育云平台教材订购成功，请尽快安排教材发货服务。</br>订单数据：</br>" + clientSendString + "</br></body></html>", true);	
			
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
	public void testSendMsg(){
		sendMsg("教材名称:把立德树人制度化;</br>订购总数:100;</br>订购总价:1000;</br>收件人:张三;</br>收件人电话:13051120665;</br>收件地址:北京市西城区");
		System.out.println("测试邮件已发送===========");
	}
	public static void main(String[] args) {
		sendMsg("教材名称:把立德树人制度化;</br>订购总数:100;</br>订购总价:1000;</br>收件人:张三;</br>收件人电话:13051120665;</br>收件地址:北京市西城区");
	}
}
