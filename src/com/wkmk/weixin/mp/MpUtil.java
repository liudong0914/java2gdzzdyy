package com.wkmk.weixin.mp;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.pay.tencent.common.RandomStringGenerator;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.weixin.bo.MpMessageInfo;
import com.wkmk.weixin.bo.MpMessageInfoEvent;
import com.wkmk.weixin.bo.MpMessageInfoEventScan;
import com.wkmk.weixin.bo.MpMessageInfoImage;
import com.wkmk.weixin.bo.MpMessageInfoText;
import com.wkmk.weixin.bo.MpMessageInfoVoice;

public class MpUtil {
	//测试微信公众号配置,师科作业宝：APPSECRET,TOKEN,HOMEPAGE
//	public static final String APPID = "wx1acfb7045a4747f3";
//	public static final String APPSECRET = "9e67f186469b53c4a8a2b1bc860c53b0";
//	public static final String TOKEN = "wkmk";
//	public static final String APPNAME = "师科作业宝";
//	public static final String APPSNAME = "师科作业宝";
//	public static final String HOMEPAGE = "http://school2.javadev.cn";
	
	//成功力-职业课程学习平台
	public static final String APPID = "wx0d1caac7050b1b62";
	public static final String APPSECRET = "6c61f7bcdd120505f2ce1d5563a34ba1";
	public static final String TOKEN = "mooc";
	public static final String APPNAME = "职业课程学习平台";
	public static final String APPSNAME = "职业课程学习平台";
	public static final String HOMEPAGE = "http://www.hxnlmooc.com";
	
	/**
	 * 获取用户id
	 * userid在传递过程中，最好加密传递，避免通过浏览器查看时猜出路径规则
	 */
	public static String getUserid(HttpServletRequest request) {
		String userid = Encode.nullToBlank(request.getParameter("userid"));
		if("".equals(userid)){
			userid = Encode.nullToBlank(request.getAttribute("userid"));
		}
		if("".equals(userid)){
			userid = DES.getEncryptPwd("1");//默认游客
		}
		request.setAttribute("userid", userid);
		
		//基本很多页面都有扫一扫功能，为了保证统一调用
		//wxjs需要的数据【url地址必须动态获取】
		//Map<String, String> jsticket = MpUtil.sign(MpUtil.HOMEPAGE + "/appHomeworkAction.fo?method=bookscan&userid=" + userid);
		/*String url=request.getScheme() + "://";   
        url += request.getHeader("host");   
        url += request.getRequestURI();   
        if(request.getQueryString()!=null){
        	url += "?" + request.getQueryString();
        }*/
		String url = request.getRequestURL().toString() + "?" + request.getQueryString();
		Map<String, String> jsticket = MpUtil.sign(url);
		request.setAttribute("jsticket", jsticket);
		
		String uid = DES.getDecryptPwd(userid);
		if(uid == null){
			uid = "1";
		}
		return uid;
	}
	
	/**
	 * 设置用户id
	 * userid在传递过程中，最好加密传递，避免通过浏览器查看时猜出路径规则
	 */
	public static void setUserid(HttpServletRequest request, String userid) {
		request.setAttribute("userid", DES.getEncryptPwd(userid));
	}
	
	/**
	 * 获取授权码
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getAccessToken(String appid, String appsecret) {
		//access_token存入缓存，有效期7200秒
		String access_token = CacheUtil.get("access_token_" + appid + "_" + appsecret);
		if (access_token == null || "".equals(access_token)) {
			String httpUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
			// HttpGet连接对象
			HttpGet httpRequest = new HttpGet(httpUrl);
			String charset = "UTF-8";
			try {
				// 取得HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 请求HttpCLient，取得HttpResponse
				HttpResponse httpResponse = httpclient.execute(httpRequest);
				// 请求成功
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = httpResponse.getEntity();
					if (entity != null) {
						String json = EntityUtils.toString(entity, charset);
						
						//缓存数据有效期2小时，实际有效期为118分钟
						access_token = getAccessTokenValue(json, "access_token");
						CacheUtil.putOfMinutes("access_token_" + appid + "_" + appsecret, access_token, 118);
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return access_token;
	}
	
	public static String getJsTicket(String appid, String appsecret) {
		//jsapi_ticket存入缓存，有效期7200秒
		String jsapi_ticket = CacheUtil.get("jsapi_ticket_" + appid + "_" + appsecret);
		if(jsapi_ticket == null || "".equals(jsapi_ticket)){
			String access_token = getAccessToken(appid, appsecret);
			String httpUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
			// HttpGet连接对象
			HttpGet httpRequest = new HttpGet(httpUrl);
			String charset = "UTF-8";
			try {
				// 取得HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 请求HttpCLient，取得HttpResponse
				HttpResponse httpResponse = httpclient.execute(httpRequest);
				// 请求成功
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = httpResponse.getEntity();
					if (entity != null) {
						String json = EntityUtils.toString(entity, charset);
						
						//缓存数据有效期2小时，实际有效期为118分钟
						jsapi_ticket = getAccessTokenValue(json, "ticket");
						CacheUtil.putOfMinutes("jsapi_ticket_" + appid + "_" + appsecret, jsapi_ticket, 118);//
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return jsapi_ticket;
	}

	/**
	 * 通过网页授权获取AccessToken
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static String getRefreshAccessToken(String appid, String refreshtoken) {
		String httpUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appid + "&grant_type=refresh_token&refresh_token=" + refreshtoken;

		// HttpGet连接对象
		HttpGet httpRequest = new HttpGet(httpUrl);
		String charset = "UTF-8";
		String json = null;
		try {
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					json = EntityUtils.toString(entity, charset);
					//System.out.println("json=" + json);
				}
			}
		} catch (Exception e2) {
			json = null;
		}
		return json;
	}

	/**
	 * 通过网页授权code获得accesstoken
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static String getAccessTokenByCode(String appid, String appsecret, String code) {
		String httpUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appsecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		// HttpGet连接对象
		HttpGet httpRequest = new HttpGet(httpUrl);
		String charset = "UTF-8";
		String json = null;
		try {
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					json = EntityUtils.toString(entity, charset);
					System.out.println("getAccessTokenByCodejson=" + json);
				}
			}
		} catch (Exception e2) {
			json = null;
		}
		return json;
	}

	/**
	 * 获取AccessToken中的值
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getAccessTokenValue(String json, String key) {
		String value = "";
		try {
			if(json != null && !"".equals(json)){
				JSONObject jsonObj = new JSONObject(json);
				value = jsonObj.getString(key);
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
	
	public static int getAccessTokenValue1(String json, String key) {
		int value = 0;
		try {
			if(json != null && !"".equals(json)){
				JSONObject jsonObj = new JSONObject(json);
				value = jsonObj.getInt(key);
			}
		} catch (Exception e) {
			value = 0;
		}
		return value;
	}
	
	/**
	 * 通过全局Access Token获取用户基本信息，每日限额：5000000次
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static String getUserInfo0(String access_token, String openid) {
		String httpUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
		// HttpGet连接对象
		HttpGet httpRequest = new HttpGet(httpUrl);
		String charset = "UTF-8";
		String json = null;
		try {
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					json = EntityUtils.toString(entity, charset);
					//System.out.println("json=" + json);
				}
			}
		} catch (Exception e2) {
			json = null;
		}
		return json;
	}

	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static String getUserInfo(String access_token, String openid) {
		String httpUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
		// HttpGet连接对象
		HttpGet httpRequest = new HttpGet(httpUrl);
		String charset = "UTF-8";
		String json = null;
		try {
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					json = EntityUtils.toString(entity, charset);
					//System.out.println("json=" + json);
				}
			}
		} catch (Exception e2) {
			json = null;
		}
		return json;
	}

	/**
	 * 获取用户的信息
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getUserInfoValue(String json, String key) {
		String value = "";
		try {
			if(json != null && !"".equals(json)){
				//System.out.println(json);
				JSONObject jsonObj = new JSONObject(json);
				value = jsonObj.getString(key);
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}

	/**
	 * 根据消息xml获取对象
	 * @param messagejson
	 * @return
	 */
	public static MpMessageInfo getMessageInfo(String messagexml) {
		MpMessageInfo mmi = new MpMessageInfo();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));

			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
		} catch (Exception e) {
		}
		return mmi;
	}

	/**
	 * 根据消息xml获取文本对象
	 * @param messagexml
	 * @return
	 */
	public static MpMessageInfoText getMessageInfoText(String messagexml) {
		MpMessageInfoText mmi = new MpMessageInfoText();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));
			String Content = Encode.nullToBlank(root.elementText("Content"));
			String MsgId = Encode.nullToBlank(root.elementText("MsgId"));

			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
			mmi.setContent(Content);
			mmi.setMsgId(MsgId);
		} catch (Exception e) {

		}
		return mmi;
	}
	
	/**
	 * 根据消息xml获取图片对象
	 * @param messagexml
	 * @return
	 */
	public static MpMessageInfoImage getMessageInfoImage(String messagexml) {
		MpMessageInfoImage mmi = new MpMessageInfoImage();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));
			String PicUrl = Encode.nullToBlank(root.elementText("PicUrl"));
			String MediaId = Encode.nullToBlank(root.elementText("MediaId"));
			String MsgId = Encode.nullToBlank(root.elementText("MsgId"));

			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
			mmi.setMsgId(MsgId);
			mmi.setPicUrl(PicUrl);
			mmi.setMediaId(MediaId);
		} catch (Exception e) {

		}
		return mmi;
	}
	
	/**
	 * 根据消息xml获取音频对象
	 * @param messagexml
	 * @return
	 */
	public static MpMessageInfoVoice getMessageInfoVoice(String messagexml) {
		MpMessageInfoVoice mmi = new MpMessageInfoVoice();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));
			String Format = Encode.nullToBlank(root.elementText("Format"));
			String MediaId = Encode.nullToBlank(root.elementText("MediaId"));
			String MsgId = Encode.nullToBlank(root.elementText("MsgId"));

			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
			mmi.setMsgId(MsgId);
			mmi.setFormat(Format);
			mmi.setMediaId(MediaId);
		} catch (Exception e) {

		}
		return mmi;
	}
	
	/**
	 * 根据消息xml获取事件对象
	 * @param messagexml
	 * @return
	 */
	public static MpMessageInfoEvent getMessageInfoEvent(String messagexml) {
		MpMessageInfoEvent mmi = new MpMessageInfoEvent();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));
			String Event = Encode.nullToBlank(root.elementText("Event"));
			String EventKey = Encode.nullToBlank(root.elementText("EventKey"));
			String Ticket = Encode.nullToBlank(root.elementText("Ticket"));
			
			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
			mmi.setEvent(Event);
			mmi.setEventKey(EventKey);
			mmi.setTicket(Ticket);
		} catch (Exception e) {

		}
		return mmi;
	}
	
	/**
	 * 根据消息xml获取扫码事件对象
	 * @param messagexml
	 * @return
	 */
	public static MpMessageInfoEventScan getMessageInfoEventScan(String messagexml) {
		MpMessageInfoEventScan mmi = new MpMessageInfoEventScan();

		try {
			Document document = null;
			try {
				document = DocumentHelper.parseText(messagexml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return null;
			}
			Element root = document.getRootElement();
			String fromUsername = Encode.nullToBlank(root.elementText("FromUserName")); // 取得发送者
			String toUsername = Encode.nullToBlank(root.elementText("ToUserName")); // 取得接收者
			String userMsgType = Encode.nullToBlank(root.elementText("MsgType"));
			String CreateTime = Encode.nullToBlank(root.elementText("CreateTime"));
			String Event = Encode.nullToBlank(root.elementText("Event"));
			String EventKey = Encode.nullToBlank(root.elementText("EventKey"));
			String Ticket = Encode.nullToBlank(root.elementText("Ticket"));
			Element scanCodeInfo = root.element("ScanCodeInfo");
			String ScanType = Encode.nullToBlank(scanCodeInfo.elementText("ScanType"));
			String ScanResult = Encode.nullToBlank(scanCodeInfo.elementText("ScanResult"));
			
			mmi.setMsgType(userMsgType);
			mmi.setCreateTime(CreateTime);
			mmi.setFromUserName(fromUsername);
			mmi.setToUserName(toUsername);
			mmi.setEvent(Event);
			mmi.setEventKey(EventKey);
			mmi.setTicket(Ticket);
			mmi.setScanType(ScanType);
			mmi.setScanResult(ScanResult);
		} catch (Exception e) {

		}
		return mmi;
	}

	/**
	 * 主动发送图片
	 * @param access_token
	 * @param openid
	 * @param media_id
	 */
	public static void sendMessageImage(String access_token, String openid, String media_id) {
		String httpUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
		// HttpPost连接对象
		HttpPost httpPost = new HttpPost(httpUrl);
		String charset = "UTF-8";
		try {
			// String json = jsonObject.toString();
			// json="{"msgtype":"image","image":"{\"media_id\":\"ovd0-l5HzRxVmHbNwqfEJEiWV_AxMAY1HMM1tvTb-mTfkTES9sdE9GgllFS7kEPy\"}","touser":"oukqIs9biN-lI-h1LEECX2-ua1lY"}";
			String json = "{\"touser\":// \"" + openid
					+ "\",\"msgtype\":\"image\",\"image\":{\"media_id\":\""
					+ media_id + "\"}}";

			StringEntity se = new StringEntity(json, "UTF-8");
			httpPost.setEntity(se);

			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = "";
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					strResult = EntityUtils.toString(entity, charset);
				}
				System.out.println(strResult);
			}
		} catch (Exception e2) {
		}
	}

	/**
	 * 主动发送文本
	 * @param access_token
	 * @param openid
	 * @param text
	 */
	public static void sendMessageText(String access_token, String openid, String text) {
		String httpUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
		// HttpPost连接对象
		HttpPost httpPost = new HttpPost(httpUrl);
		String charset = "UTF-8";
		try {
			String json = "{\"touser\": \"" + openid
					+ "\",\"msgtype\":\"text\",\"text\":{\"content\": \""
					+ text + "\"}}";
			//System.out.println(json);

			StringEntity se = new StringEntity(json, "UTF-8");
			httpPost.setEntity(se);
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = "";
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					strResult = EntityUtils.toString(entity, charset);
				}
				//System.out.println(strResult);
			}
		} catch (Exception e2) {
		}
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr
	 * @return
	 */
	public static String GenerateImage(String imgStr) { 
		// 图像数据为空
		if (imgStr == null) {
			return null;
		} else {
			String path = UUID.getNewUUID() + ".jpg";
			String imgFilePath = "C:\\" + path;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				// 生成jpeg图片
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
				return path;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 生成文本返回信息
	 * @param toName
	 * @param fromName
	 * @param content
	 * @return
	 */
	public static String getXMLText(String ToUserName, String FromUserName, String Content) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";
		String msgType = "text";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, msgType, Content);
		return data;
	}
	
	/**
	 * 生成图片返回信息
	 * @param ToUserName
	 * @param FromUserName
	 * @param MediaId
	 * @return
	 */
	public static String getXMLImage(String ToUserName, String FromUserName, String MediaId) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName>"
				+ "<CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType>"
				+ "<Image><MediaId><![CDATA[%5$s]]></MediaId></Image></xml>";
		String msgType = "image";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, msgType, MediaId);
		return data;
	}
	
	/**
	 * 回复语音消息
	 * @param ToUserName
	 * @param FromUserName
	 * @param MediaId
	 * @return
	 */
	public static String getXMLVoice(String ToUserName, String FromUserName, String MediaId) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName>"
				+ "<CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType>"
				+ "<Voice><MediaId><![CDATA[%5$s]]></MediaId></Voice></xml>";
		String msgType = "voice";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, msgType, MediaId);
		return data;
	}
	
	
	/**
	 * 回复视频消息
	 * @param ToUserName
	 * @param FromUserName
	 * @param MediaId
	 * @return
	 */
	public static String getXMLVideo(String ToUserName, String FromUserName, String MediaId,String Title,String Description) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName>"
				+ "<CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType>"
				+ "<Video><MediaId><![CDATA[%5$s]]></MediaId><Title><![CDATA[%6$s]]></Title><Description><![CDATA[%7$s]]></Description></Video></xml>";
		String msgType = "video";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, msgType, MediaId,Title,Description);
		return data;
	}
	
	/**
	 * 回复音乐消息
	 * @param ToUserName
	 * @param FromUserName
	 * @param MediaId
	 * @param title
	 * @param descript
	 * @return
	 */
	public static String getXMLMusic(String ToUserName, String FromUserName, String Title,String Description,String MusicUrl,String HQMusicUrl,String MediaId) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName>"
				+ "<CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType>"
				+ "<Music><Title><![CDATA[%5$s]]></Title><Description><![CDATA[%6$s]]></Description><MusicUrl><![CDATA[%7$s]]></MusicUrl><HQMusicUrl><![CDATA[%8$s]]></HQMusicUrl><ThumbMediaId><![CDATA[%9$s]]></ThumbMediaId></Music></xml>";
		String msgType = "music";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, msgType, Title,Description,MusicUrl,HQMusicUrl,MediaId);
		return data;
	}
	
	/**
	 * 回复图文消息
	 * @param ToUserName
	 * @param FromUserName
	 * @param Title
	 * @param articles
	 * @return
	 */
	public static String getXMLNews(String ToUserName, String FromUserName, String Title, List articles) {
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml>"+"<ToUserName><![CDATA[%1$s]]></ToUserName>"
			+ "<FromUserName><![CDATA[%2$s]]></FromUserName>"
			+ "<CreateTime>%3$s</CreateTime>"
			+ "<MsgType><![CDATA[news]]></MsgType>"
			+ "<ArticleCount>1</ArticleCount>" + "<Articles>"
			+ "<item>" + "<Title><![CDATA[%4$s]]></Title>"
			+ "<Description><![CDATA[%5$s]]></Description>"
			+ "<PicUrl><![CDATA[%6$s]]></PicUrl>"
			+ "<Url><![CDATA[%7$s]]></Url>" + "</item>"
			+ "</Articles>" + "</xml> ";
		String msgType = "news";
		// 当回复文本内容时
		//data = xml.format(xml, ToUserName, FromUserName, time, msgType, Title,Description,MusicUrl,HQMusicUrl,MediaId);
		return data;
	}
	
	/**
	 * 回复单条图文新闻
	 * @param ToUserName 接收微信用户
	 * @param FromUserName 开发者微信账号
	 * @param Title 图文新闻标题
	 * @param descript 图文新闻描述
	 * @param picurl 图片链接地址
	 * @param url 点击图文跳转链接地址
	 * @return
	 */
	public static String getXMLOneNews(String ToUserName, String FromUserName, String Title, String descript, String picurl, String url) {
		StringBuffer data = new StringBuffer();
		data.append("<xml><ToUserName><![CDATA[").append(ToUserName).append("]]></ToUserName><FromUserName><![CDATA[").append(FromUserName).append("]]></FromUserName>");
		data.append("<CreateTime>").append(new Date().getTime()).append("</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>1</ArticleCount>");
		data.append("<Articles><item><Title><![CDATA[").append(Title).append("]]></Title><Description><![CDATA[").append(descript).append("]]></Description>");
		data.append("<PicUrl><![CDATA[").append(picurl).append("]]></PicUrl><Url><![CDATA[").append(url).append("]]></Url></item></Articles></xml>");
		return data.toString();
		/*
		String data = "";
		String time = new Date().getTime() + "";
		String xml = "<xml>"+"<ToUserName><![CDATA[%1$s]]></ToUserName>"
			+ "<FromUserName><![CDATA[%2$s]]></FromUserName>"
			+ "<CreateTime>%3$s</CreateTime>"
			+ "<MsgType><![CDATA[news]]></MsgType>"
			+ "<ArticleCount>1</ArticleCount>" + "<Articles>"
			+ "<item>" + "<Title><![CDATA[%4$s]]></Title>"
			+ "<Description><![CDATA[%5$s]]></Description>"
			+ "<PicUrl><![CDATA[%6$s]]></PicUrl>"
			+ "<Url><![CDATA[%7$s]]></Url>" + "</item>"
			+ "</Articles>" + "</xml> ";
		// 当回复文本内容时
		data = xml.format(xml, ToUserName, FromUserName, time, Title,descript,picurl,url);
		return data;
		*/
	}

	/**
	 * 字符串加密
	 * @param decript
	 * @return
	 */
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检查签名
	 * @param decript
	 * @return
	 */
	public static boolean checkSignature(HttpServletRequest request) {
		try {
			String signature = Encode.nullToBlank(request.getParameter("signature"));
			String timestamp = Encode.nullToBlank(request.getParameter("timestamp"));
			String nonce = Encode.nullToBlank(request.getParameter("nonce"));
			
			//System.out.println("signature=" + signature);
			//System.out.println("timestamp=" + timestamp);
			//System.out.println("nonce=" + nonce);

			// 形成数组
			String[] array = { MpUtil.TOKEN, timestamp, nonce };
			Arrays.sort(array); // 进行排序
			// 合并成字符串
			String data = "";
			for (int i = 0; i < array.length; i++) {
				data += array[i];
			}
			// 用sha1加密字符串
			String str = MpUtil.SHA1(data);
			//System.out.println("str=" + str);
			if (signature.equals(str)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Map<String, String> sign(String url) {
        Map<String, String> ret = new HashMap<String, String>();
        //微信支付随机数长度不超过32
        //String nonce_str = create_nonce_str();
        String nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        String jsapi_ticket = MpUtil.getJsTicket(MpUtil.APPID, MpUtil.APPSECRET);
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return java.util.UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

	/**
	 * 获取转入流
	 * @param in
	 * @return
	 */
	public static String readStreamParameter(ServletInputStream in) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	/** 
	 * 获取媒体文件【返回媒体文件大小】
	 * @param accessToken  接口访问凭证 
	 * @param media_id  媒体文件id 
	 * 目前多媒体文件下载接口的频率限制为10000次/天，如需要调高频率，请邮件weixin-open@qq.com,邮件主题为【申请多媒体接口调用量】，请对你的项目进行简单描述，附上产品体验链接，并对用户量和使用量进行说明。
	 * */ 
	public static long downloadMedia(String mediaId, String rootpath, String filepath) { 
		String mediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + getAccessToken(APPID, APPSECRET) + "&media_id=" + mediaId; 
		HttpURLConnection conn = null;
		try { 
		    URL url = new URL(mediaUrl);
		    conn = (HttpURLConnection) url.openConnection();
		    conn.setDoInput(true);
		    conn.setRequestMethod("GET");
		    conn.setConnectTimeout(30000);
		    conn.setReadTimeout(30000);
		    InputStream stream = conn.getInputStream();
		    //System.out.println(conn.getHeaderField("Content-Type"));
		    //System.out.println(conn.getHeaderField("Content-disposition"));
		    //System.out.println(conn.getHeaderField("Content-Length"));
		    
		    OutputStream bos = new FileOutputStream(rootpath + filepath);
		    long totalByte = 0;
		    int bytesRead = 0;
	        byte[] buffer = new byte[8192];
	        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	          bos.write(buffer, 0, bytesRead); //将文件写入服务器
	          totalByte = totalByte + bytesRead;
	        }
	        bos.close();
	        stream.close();
		    
		    return totalByte;
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally { 
			if(conn != null){ 
				conn.disconnect(); 
			} 
		} 
		return 0; 
	}
	
	//----------------------------------------------------------------------------------------
	/**
	 * 根据userid获取openid
	 * @param access_token
	 * @param agentid
	 * @param userid
	 * @return
	 */
	public static String getOpenid(String access_token, String agentid,
			String mpuserid) {
		String data = "";
		String httpUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token="
				+ access_token;
		// HttpPost连接对象
		HttpPost httpPost = new HttpPost(httpUrl);
		String charset = "UTF-8";
		try {
			String json = "{\"userid\":\"" + mpuserid + "\",\"agentid\":\""
					+ agentid + "\"}";
			System.out.println(json);
			StringEntity se = new StringEntity(json, "UTF-8");
			httpPost.setEntity(se);
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					String returnjson = EntityUtils.toString(entity, charset);
					data = getAccessTokenValue(returnjson, "openid");
				}
			}
		} catch (Exception e2) {
		}
		return data;
	}

	/**
	 * 根据openid获取userid
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static String getMpUserid(String access_token, String openid) {
		if(access_token == null || "".equals(access_token)){
			access_token = getAccessToken(APPID, APPSECRET);
		}
		String data = "";
		String httpUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token="
				+ access_token;
		// HttpPost连接对象
		HttpPost httpPost = new HttpPost(httpUrl);
		String charset = "UTF-8";
		try {
			String json = "{\"openid\":\"" + openid + "\"}";
			System.out.println(json);
			StringEntity se = new StringEntity(json, "UTF-8");
			httpPost.setEntity(se);
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					String returnjson = EntityUtils.toString(entity, charset);
					System.out.println(returnjson);
					data = getAccessTokenValue(returnjson, "userid");
				}
			}
		} catch (Exception e2) {
		}
		return data;
	}

	/**
	 * 通过网页授权code获得accesstoken
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static String getMpUseridByCode(String code) {
		String userid = "";
		String access_token = getAccessToken(APPID, APPSECRET);
		String httpUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="
				+ access_token + "&code=" + code;
		// HttpGet连接对象
		HttpGet httpRequest = new HttpGet(httpUrl);
		String charset = "UTF-8";
		String json = null;
		try {
			// 取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 请求HttpCLient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					json = EntityUtils.toString(entity, charset);
					userid = getAccessTokenValue(json, "UserId");
				}
			}
		} catch (Exception e2) {
			userid = "";
		}
		return userid;
	}

	/**
	 * 获取用户的Userid
	 * 
	 * @param request
	 * @return
	 */
	public static String getMpUserid(HttpServletRequest request) {
		String code = request.getParameter("code");
		String mpuserid = getMpUseridByCode(code);
		return mpuserid;
	}

	public static void main(String[] args) {
//		String accesstoken = "LFuzNMiG7EORHBIAtVLi4ACk82BXDfsGY5jU4As121y5H_eu-oGaYmJdqj3tvSpBBckoYzeqb-rpl2-LPllUCyRgDKA-YVLMM-61FVIL_gE";
//		 String json=MpUtil.getAccessToken(MpUtil.APPID,MpUtil.APPSECRET);
//		// String
//		// json="{\"access_token\":\"Z-UoTLqwievIOVYhYTOKYMAQpnURxnx82Abt45fE9G--0exIXJ9Kmu3YT1SZbUrItLSJsmqtECIJdfe98ebUU5vHZS8YqmvwBijo5BNoAp0\",\"expires_in\":7200}";
//		accesstoken=MpUtil.getAccessTokenValue(json, "access_token");
//		// accesstoken=MpUtil.getAccessTokenByCode(MpUtil.APPID,"0214f04b7567ca318af1cfcf5fbee78m");
//		System.out.println(accesstoken);
//
//		//MpUtil.sendMessageImage(accesstoken, "oukqIs9biN-lI-h1LEECX2-ua1lY", "ovd0-l5HzRxVmHbNwqfEJEiWV_AxMAY1HMM1tvTb-mTfkTES9sdE9GgllFS7kEPy");
//		 MpUtil.sendMessageText(accesstoken,"oIq4Bs98gUd5IXD3saltrlFQVNiU","上午好");
//
//		// Test.mpget("ed106b638babd350038a4dd53e436b0ec290c0d6","2014-09-12","ttt","ok");
//		// Test.getaccess_token(APPID, APPSECRET);
//		// MpUtil.getmenu(accesstoken);
//
//		// Test.post();
//		// Test.create_org();
//		// Test.get_orgs();
//		System.out.println(DES.getEncryptPwd("17"));  //4EDD693B60E8CF2A  老师
//		System.out.println(DES.getEncryptPwd("18"));  //F0BEF456C935DA70    学生
//		System.out.println(DES.getEncryptPwd("6"));  //8D956DFA61F0A037      学生
//		System.out.println(DES.getEncryptPwd("25"));  //00034694A3E9BC20  老师
//		System.out.println(MD5.getEncryptPwd("123456"));
		String encryptPwd = MD5.getEncryptPwd("123456");
		System.out.println(encryptPwd);
		
		//String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
		//String userjson = MpUtil.getUserInfo0(access_token, "oIq4Bs98gUd5IXD3saltrlFQVNiU");
		//System.out.println(userjson);
	}
}