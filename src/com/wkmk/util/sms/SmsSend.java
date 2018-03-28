package com.wkmk.util.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SmsSend {
	
	/*
	 *公司测试账号
	 *http://sms.webchinese.cn/User/?action=sms
	 *账号：北京成功合力   密码84650630
	 *子账号：jbxt      密码84650630
	 */
	public static boolean send(String mobile, String content) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
			NameValuePair[] data = { new NameValuePair("Uid", "jbxt"),
					new NameValuePair("Key", "eb179491669eed90c5a1"),
					new NameValuePair("smsMob", mobile),
					new NameValuePair("smsText", content) };
			post.setRequestBody(data);
			client.executeMethod(post);
			//Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			//System.out.println("statusCode:" + statusCode);
//			for (Header h : headers) {
//				System.out.println(h.toString());
//			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            //System.out.println(result);
			post.releaseConnection();
			if ("1".equals(result)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		SmsSend.send("15201212201", "申请修改支付密码，验证码:127128(5分钟内有效)，如非本人操作请忽略。"); 
	}

}
