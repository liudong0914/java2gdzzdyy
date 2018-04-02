package com.wkmk.util.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SmsSend {
	
	/*
	 *http://sms.webchinese.cn/User/?action=sms
	 */
	public static boolean send(String mobile, String content) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
			NameValuePair[] data = { new NameValuePair("Uid", "gdzzdy"),
					new NameValuePair("Key", "d41d8cd98f00b204e980"),//暂时屏蔽，等中德申请短信平台账号
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
		SmsSend.send("13716692817", "申请修改支付密码，验证码:127128(5分钟内有效)，如非本人操作请忽略。"); 
	}

}
