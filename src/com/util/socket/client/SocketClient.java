package com.util.socket.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.util.string.PublicResourceBundle;
import com.wkmk.util.sms.MailSender;

/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Jan 3, 201711:21:04 AM
 */
public class SocketClient {
	
	private static String open = null;
	private static String serverip = null;
	private static Integer serverport = null;
	
	static {
		try {
			if(serverip == null && serverport == null){
				open = PublicResourceBundle.getProperty("system", "socket.server.open");
				serverip = PublicResourceBundle.getProperty("system", "socket.server.ip");
				serverport = Integer.valueOf(PublicResourceBundle.getProperty("system", "socket.server.port")).intValue();
			}
		} catch (Exception e) {
			serverip = "127.0.0.1";
			serverport = 2728;
		}
	}

	/** 
	 * 客户端Socket构造方法 
	 */  
	public SocketClient(String clientString) {
		try {
			if("1".equals(open)){
				// 向本机的2728端口发出客户请求  
				Socket client = new Socket(serverip, serverport);  
			  
				//System.out.println("Established a connection...");  
			  
				// 由Socket对象得到输出流,并构造PrintWriter对象  
				PrintWriter out = new PrintWriter(client.getOutputStream());  
			  
				// 由Socket对象得到输入流,并构造相应的BufferedReader对象  
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));  
			  
			    // 将从系统标准输入读入的字符串输出到Server  
			    out.println(clientString);  
			  
			    // 刷新输出流,使Server马上收到该字符串  
			    out.flush();  
			  
			    // 从Server读入一字符串，并打印到标准输出上
			    String server_result = in.readLine();
			    if(!"1".equals(server_result)){//交互失败
			    	//调用订单自动化管理系统失败，需要人工参与处理，无法用定时处理，因为可能会立刻产生数据的变更（如被抢单等）
			    	
			    	//给运维人员发生审核短信和邮件提醒
			    	//启动线程
					final String clientSendString = clientString;
					Runnable runnable = new Runnable() {
						public void run() {
							sendMsg(clientSendString);
						}
					}; 
					new Thread(runnable).start();
			    }
			  
			    out.close(); // 关闭Socket输出流  
			    in.close(); // 关闭Socket输入流  
			    client.close(); // 关闭Socket 
			}
		} catch (Exception e) {  
			//给运维人员发生审核短信和邮件提醒
	    	//启动线程
			final String clientSendString = clientString;
			Runnable runnable = new Runnable() {
				public void run() {
					sendMsg(clientSendString);
				}
			}; 
			new Thread(runnable).start();
			System.out.println("Error. " + e);  
		}  
	}
	
	private void sendMsg(String clientSendString){
		try {
			//System.out.println("开始发送邮件");
			//SmsSend.send("15201212201", "订单自动化管理系统交互失败，请尽快排查系统服务。");
			
			MailSender mailSender = new MailSender();
			JavaMailSenderImpl senderImpl = mailSender.getJavaMailSenderImpl();
			
			// 设定收件人、寄件人、主题与内文
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "gbk");
			messageHelper.setFrom(mailSender.getSender());
			messageHelper.setTo("zhangxd@edutech.com.cn");
			messageHelper.setSubject("【进步学堂】订单自动化管理系统交互失败");	
			
			messageHelper.setText("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\"></head><body>进步学堂订单自动化管理系统交互失败，请尽快排查系统服务。交互数据：" + clientSendString + "</body></html>", true);	
			
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
	
//	public static void main(String[] args) {  
//		//new SocketClient("product_keyid_keyvalue_livetime_action");  
//	}
}
