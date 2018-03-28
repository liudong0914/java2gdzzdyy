package com.util.service.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.util.encrypt.DESUtil;
import com.util.encrypt.MD5;

/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Oct 12, 20163:49:57 PM
 */
public class Test {

	public static void main(String[] args) {
		String sessionid = login();
		//getInfo(sessionid);
	}
	
	private static String login(){
		//String uriAPI = "http://192.168.0.14/api/app/appWeikeService.action";
		//String uriAPI = "http://114.55.40.50/api/app/appWeikeService.action";
		String uriAPI = "http://127.0.0.1:8080/api/app/appService.action";
		/*建立HTTPost对象*/
		HttpPost httpRequest = new HttpPost(uriAPI);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
		params.add(new BasicNameValuePair("module", "1001")); 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("loginname", "admin");
		jsonObject.put("password", MD5.getEncryptPwd("123456"));
		jsonObject.put("client", "android");
		System.out.println(jsonObject.toString());
		String requestStr = DESUtil.encrypt(jsonObject.toString());
		System.out.println(requestStr);
		params.add(new BasicNameValuePair("params", requestStr));
		
		try {
			/* 添加请求参数到请求对象*/
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/*发送请求并等待响应*/
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			/*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200) { 
				/*读返回数据*/
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				System.out.println("》》》》》》》》》》》》strResult=="+strResult);
				String resultStr = DESUtil.decrypt(strResult);
				System.out.println("resultStr>>>>>>"+resultStr);
				JSONObject jsonObj = JSONObject.fromObject(resultStr);
				//System.out.println("result="+jsonObj.getString("result"));
				return jsonObj.getString("sessionid");
			} else { 
				System.out.println("strResult=="+"Error Response: "+httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return "";
	}
	
	private static void getInfo(String sessionid){
		//String uriAPI = "http://192.168.0.14/api/app/appWeikeService.action";
		String uriAPI = "http://114.55.40.50/api/app/appWeikeService.action";
		//String uriAPI = "http://127.0.0.1:8080/api/app/appWeikeService.action";
		/*建立HTTPost对象*/
		HttpPost httpRequest = new HttpPost(uriAPI);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
		params.add(new BasicNameValuePair("module", "2001")); 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sessionid", sessionid);
		jsonObject.put("bookid", "1");
		//jsonObject.put("title", "第二十一章");
		String requestStr = DESUtil.encrypt(jsonObject.toString());
		System.out.println(requestStr);
		params.add(new BasicNameValuePair("params", requestStr));
		
		try {
			/* 添加请求参数到请求对象*/
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/*发送请求并等待响应*/
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			/*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200) { 
				/*读返回数据*/
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				System.out.println("》》》》》》》》》》》》strResult=="+strResult);
				String resultStr = DESUtil.decrypt(strResult);
				System.out.println("resultStr>>>>>>"+resultStr);
				JSONObject jsonObj = JSONObject.fromObject(resultStr);
				if(jsonObj.toString().indexOf("result") != -1){
					System.out.println("result="+jsonObj.getString("result"));
				}
				if(jsonObj.toString().indexOf("result0") != -1){
					System.out.println("result0="+jsonObj.getString("result0"));
				}
			} else { 
				System.out.println("strResult=="+"Error Response: "+httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
