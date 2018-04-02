package com.wkmk.pay.tencent.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.wkmk.pay.tencent.common.Configure;
import com.wkmk.pay.tencent.common.Signature;
import com.wkmk.pay.tencent.common.XMLParser;

/**
 * 与微信支付API调用接口工具
 * @author zhangxd
 */
public class APIUtil {

	/**
	 * 获取统一下单prepay_id
	 * @param openid 用户唯一标示
	 * @param userip 用户IP地址
	 * @param body 商品描述
	 * @param out_trade_no 订单号，业务系统自身生成
	 * @param total_fee 商品订单总金额，单位分
	 * @param trade_type 交易类型
	 * @return String 服务器返回的xml字符串
	 */
	public static String getPrepayid(String openid, String userip, String body, String out_trade_no, int total_fee, String nonce_str, String trade_type){
		//获取sign签名，调用工具生成
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appid", Configure.getAppid());
		map.put("body", body);
		map.put("device_info", "WEB");
		//map.put("limit_pay", "no_credit");
		map.put("mch_id", Configure.getMchid());
		map.put("nonce_str", nonce_str);
		map.put("notify_url", Configure.NOTIFY_URL);
		if(openid != null && !"".equals(openid)){
			map.put("openid", openid);
		}
		map.put("out_trade_no", out_trade_no);
		map.put("spbill_create_ip", userip);
		map.put("total_fee", total_fee);
		map.put("trade_type", trade_type);
		String sign = Signature.getSign(map);
		
		String result = null;
        HttpPost httpPost = new HttpPost(Configure.UNIFIED_ORDER_API);

        //拼接API接口需要的xml格式字符串，注意此处拼接顺序按字母ASCII码从小到大排序（字典序）
        //【特别说明】以下除了sign，其他所有数据都需要按键值方式拼接字符生成签名，拼接顺序按字母ASCII码从小到大排序（字典序）
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("<appid>").append(Configure.getAppid()).append("</appid>");
        xml.append("<body>").append(body).append("</body>");
        xml.append("<device_info>WEB</device_info>");
        //xml.append("<limit_pay>no_credit</limit_pay>");//指定不能使用信用卡支付
        xml.append("<mch_id>").append(Configure.getMchid()).append("</mch_id>");
        xml.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
        xml.append("<notify_url>").append(Configure.NOTIFY_URL).append("</notify_url>");
        if(openid != null && !"".equals(openid)){
        	xml.append("<openid>").append(openid).append("</openid>");
        }
        xml.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
        xml.append("<spbill_create_ip>").append(userip).append("</spbill_create_ip>");
        xml.append("<total_fee>").append(total_fee).append("</total_fee>");
        xml.append("<trade_type>").append(trade_type).append("</trade_type>");
        xml.append("<sign>").append(sign).append("</sign>");
        xml.append("</xml>");
        //System.out.println(xml.toString());
        
        String postDataXML = xml.toString();
        try {
        	//添加请求参数到请求对象
        	StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            
        	//取得HttpClient对象
        	HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            /*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			}
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        } finally {
            httpPost.abort();
        }

        return result;
	}
	
	/**
	 * 获取提现需要传递的参数签名sign
	 * @param openid 用户唯一标示
	 * @param userip 用户IP地址
	 * @param body 商品描述
	 * @param out_trade_no 订单号，业务系统自身生成
	 * @param total_fee 商品订单总金额（即提现金额），单位分
	 * @param nonce_str 随机字符串，不长于32位
	 * @param username 微信用户绑定银行卡真实姓名
	 * @return String 返回sign签名字符串
	 */
	public static String getTransfersSign(String openid, String userip, String body, String out_trade_no, int total_fee, String nonce_str, String username){
		//获取sign签名，调用工具生成
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("amount", total_fee);
		//map.put("check_name", "OPTION_CHECK");
		map.put("check_name", "NO_CHECK");
		map.put("desc", body);
		map.put("mch_appid", Configure.getAppid());
		map.put("mchid", Configure.getMchid());
		map.put("nonce_str", nonce_str);
		if(openid != null && !"".equals(openid)){
			map.put("openid", openid);
		}
		map.put("partner_trade_no", out_trade_no);
		map.put("re_user_name", username);
		map.put("spbill_create_ip", userip);
		String sign = Signature.getSign(map);
		
        return sign;
	}
	
	/**
	 * 获取提现需要传递的参数xml
	 * @param openid 用户唯一标示
	 * @param userip 用户IP地址
	 * @param body 商品描述
	 * @param out_trade_no 订单号，业务系统自身生成
	 * @param total_fee 商品订单总金额（即提现金额），单位分
	 * @param nonce_str 随机字符串，不长于32位
	 * @param username 微信用户绑定银行卡真实姓名
	 * @return String 返回参数xml字符串
	 */
	public static String getTransfersXml(String openid, String userip, String body, String out_trade_no, int total_fee, String nonce_str, String username, String sign){
        //拼接API接口需要的xml格式字符串，注意此处拼接顺序按字母ASCII码从小到大排序（字典序）
        //【特别说明】以下除了sign，其他所有数据都需要按键值方式拼接字符生成签名，拼接顺序按字母ASCII码从小到大排序（字典序）
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("<amount>").append(total_fee).append("</amount>");
        //提现无需验证用户真实姓名，除非每次提现都需要用户填写当前微信账号绑定的真实身份姓名，否则提现会失败
        //xml.append("<check_name>OPTION_CHECK</check_name>");
        xml.append("<check_name>NO_CHECK</check_name>");
        xml.append("<desc>").append(body).append("</desc>");
        xml.append("<mch_appid>").append(Configure.getAppid()).append("</mch_appid>");
        xml.append("<mchid>").append(Configure.getMchid()).append("</mchid>");
        xml.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
        if(openid != null && !"".equals(openid)){
        	xml.append("<openid>").append(openid).append("</openid>");
        }
        xml.append("<partner_trade_no>").append(out_trade_no).append("</partner_trade_no>");
        xml.append("<re_user_name>").append(username).append("</re_user_name>");
        xml.append("<spbill_create_ip>").append(userip).append("</spbill_create_ip>");
        xml.append("<sign>").append(sign).append("</sign>");
        xml.append("</xml>");
        String postDataXML = xml.toString();

        return postDataXML;
	}
	
	public static void main(String[] args) throws Exception {
		//生成订单号
		String returnxml = APIUtil.getPrepayid("ovPt101tGRlPobNbpGSgh-82au8M", "106.2.207.106", "微课慕课网财富值在线充值", "20161028100338150533", 1, "1122334455667788", "JSAPI");
		System.out.println(returnxml);
		boolean check = Signature.checkIsSignValidFromResponseString(returnxml);
		Map<String,Object> map = XMLParser.getMapFromXML(returnxml);
		//返回页面需要的参数
		StringBuffer str = new StringBuffer();
		str.append(map.get("nonce_str")).append(";").append(map.get("prepay_id")).append(";").append(map.get("sign"));
		System.out.println(str.toString());
	}
	
}
