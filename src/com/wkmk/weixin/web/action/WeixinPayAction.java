package com.wkmk.weixin.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.pay.tencent.common.Configure;
import com.wkmk.pay.tencent.common.RandomStringGenerator;
import com.wkmk.pay.tencent.common.Signature;
import com.wkmk.pay.tencent.common.XMLParser;
import com.wkmk.pay.tencent.util.APIUtil;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserPayTrade;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserGiveMoneyActivityManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserPayTradeManager;
import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkTextBookBuyManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;
import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 * 微信支付接口
 * @version 1.0
 */
public class WeixinPayAction extends BaseAction {
	
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				//根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";//老师
				if("2".equals(sysUserInfo.getUsertype())){//学生
					usertype = "2";
				}
				if(!sysUserInfo.getPhoto().startsWith("http://")){
					sysUserInfo.setPhoto("/upload/" + sysUserInfo.getPhoto());
				}
				request.setAttribute("usertype", usertype);
				request.setAttribute("sysUserInfo", sysUserInfo);
				
				//查询用户消费记录
				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				List list = summ.getSysUserMoneys(condition, "moneyid desc", 10);
				request.setAttribute("list", list);
				
				return mapping.findForward("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * ajax加载微课评价
	 */
	public ActionForward getAjaxUserMoney(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			if("".equals(pagenum)){
				pagenum = "1";
			}
			int startcount = Integer.valueOf(pagenum).intValue()*10;
			
			SysUserMoneyManager manager = (SysUserMoneyManager) getBean("sysUserMoneyManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
			
			PageList pageList = manager.getPageSysUserMoneys(condition, "moneyid desc", startcount, 10);
			List list = pageList.getDatalist();
			
			StringBuffer str = new StringBuffer();
			if(list != null && list.size() > 0){
				SysUserMoney money = null;
				for(int i=0, size=list.size(); i<size; i++){
					money = (SysUserMoney) list.get(i);
					str.append("<div class=\"recharge_main\"><div class=\"recharge_main_font\">");
					if(money.getChangetype().intValue() == -1){
						str.append("<p class=\"recharge_main_p\">-</p>");
					}else if(money.getChangetype().intValue() == 1){
						str.append("<p class=\"recharge_main_p recharge_main_p02\">+</p>");
					}
					str.append("<p class=\"recharge_main_font_p\">").append(money.getChangemoney()).append("学币</p>");
					str.append("<p class=\"recharge_main_font_p01\">").append(money.getCreatedate()).append("</p>");
					str.append("</div><p class=\"recharge_main_font_p02\">").append(money.getDescript()).append("</p></div>");
				}
			}
			
			pw = response.getWriter();
			pw.write(str.toString());
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}
	
	public ActionForward money2account(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			//通过userid获取openid，支付需要，如果没有，则退出登录
			SysUserAttentionManager manager = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention sysUserAttention = manager.getSysUserAttentionByUserid(Integer.valueOf(userid));
			if(sysUserAttention == null){
				//用户没有对应的openid，需要重新注册
				return mapping.findForward("welcome");
			}
			request.setAttribute("openid", sysUserAttention.getOpenid());
			
			//获取当前是否有充值活动，按最新设置时间为准
			String curdate = DateTime.getDate();
			SysUserGiveMoneyActivityManager sugmam = (SysUserGiveMoneyActivityManager) getBean("sysUserGiveMoneyActivityManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1' and (a.type='2' or a.type='3') and 1='1");
			SearchCondition.addCondition(condition, "startdate", "<=", curdate);
			SearchCondition.addCondition(condition, "enddate", ">=", curdate);
			List list = sugmam.getSysUserGiveMoneyActivitys(condition, "startdate desc", 0);
			if(list != null && list.size() > 0){
				SysUserGiveMoneyActivity moneyActivity = (SysUserGiveMoneyActivity) list.get(0);
				request.setAttribute("moneyActivity", moneyActivity);
			}
			
			return mapping.findForward("money2account");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * ajax获取统一支付接口返回的prepay_id
	 */
	public ActionForward getPrepayid(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String activityid = Encode.nullToBlank(request.getParameter("activityid"));
			String money = Encode.nullToBlank(request.getParameter("money"));//输入的元(如：1.58元)，传递的参数是分
			int total_fee = Float.valueOf(Float.valueOf(money).floatValue() * 100).intValue();
			float amount = Float.valueOf(money);
			if(!"".equals(activityid)){
				SysUserGiveMoneyActivityManager sugmam = (SysUserGiveMoneyActivityManager) getBean("sysUserGiveMoneyActivityManager");
				SysUserGiveMoneyActivity moneyActivity = sugmam.getSysUserGiveMoneyActivity(activityid);
				if("3".equals(moneyActivity.getType())){//充值打折
					amount = amount * moneyActivity.getDiscount() / 10;
					total_fee = Float.valueOf(total_fee * moneyActivity.getDiscount() / 10).intValue();
				}
			}
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String body = Encode.nullToBlank(request.getParameter("body"));
			String nonce_str = Encode.nullToBlank(request.getParameter("nonce_str"));
			String userip = IpUtil.getIpAddr(request);
			
			//生成订单号
			String out_trade_no = DateTime.getDate("yyyyMMddHHmmssS") + RandomStringGenerator.getRandomNumberStringByLength(3);
			String returnxml = APIUtil.getPrepayid(openid, userip, body, out_trade_no, total_fee, nonce_str, "JSAPI");
			System.out.println("支付"+returnxml);
			boolean check = Signature.checkIsSignValidFromResponseString(returnxml);
			System.out.println(check);
			if(check){
				String tradetype = Encode.nullToBlank(request.getParameter("tradetype"));
				String priceid = Encode.nullToBlank(request.getParameter("priceid"));//作业解题微课定价
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));//在线答疑
				String coursefilmid = Encode.nullToBlank(request.getParameter("coursefilmid"));//课程微课
				String textbookid = Encode.nullToBlank(request.getParameter("textbookid"));//教材订购
				String totalnum = Encode.nullToBlank(request.getParameter("totalnum"));//订购教材总数
			    String recipientname = Encode.nullToBlank(request.getParameter("recipientname"));//收件人姓名
		        String recipientphone = Encode.nullToBlank(request.getParameter("recipientphone"));//收件人电话
		        String recipientaddress = Encode.nullToBlank(request.getParameter("recipientaddress"));//收件人地址
				
				//插入支付单信息
				SysUserPayTradeManager manager = (SysUserPayTradeManager) getBean("sysUserPayTradeManager");
				SysUserPayTrade model = new SysUserPayTrade();
				model.setOuttradeno(out_trade_no);
				model.setPrice(new Float(money));
				model.setQuantity(1);
				model.setAmount(amount);
				model.setPaytype("wxpay");
				model.setBody(body);
				model.setCreatedate(DateTime.getDate());
				model.setUserip(IpUtil.getIpAddr(request));
				model.setState("0");
				model.setUserid(Integer.valueOf(userid));
				if("1".equals(tradetype)){
					model.setSubject("微信充值:进步学堂,订单号:" + out_trade_no);
					model.setTradetype("1");
					model.setTradetypeid("0");
				}else if("5".equals(tradetype)){
					EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
					EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
					
					model.setSubject("微信支付:购买课程微课【" + eduCourseFilm.getTitle() + "】,订单号:" + out_trade_no);
					model.setTradetype("5");
					model.setTradetypeid(coursefilmid);
				}else if("6".equals(tradetype)){
					TkTextBookInfoManager ttbm=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
					TkTextBookInfo textBookInfo = ttbm.getTkTextBookInfo(textbookid);
					  //记录教材购买记录
	                TkTextBookBuyManager ttbbm=(TkTextBookBuyManager)getBean("tkTextBookBuyManager");
	                TkTextBookBuy ttbb = new TkTextBookBuy();
	                ttbb.setTextbookid(textBookInfo.getTextbookid());
	                ttbb.setPrice(textBookInfo.getPrice());
	                ttbb.setDiscount(textBookInfo.getDiscount());
	                ttbb.setSellprice(textBookInfo.getSellprice());
	                ttbb.setCreatedate(DateTime.getDate());
	                ttbb.setTotalnum(Integer.valueOf(totalnum));
	                ttbb.setTotalprice(new Float(money));
	                ttbb.setBuyuserid(Integer.valueOf(userid));
	                ttbb.setRecipientname(recipientname);
	                ttbb.setRecipientphone(recipientphone);
	                ttbb.setRecipientaddress(recipientaddress);
	                ttbb.setIsdelivery("0");
	                ttbb.setStatus("0");
	                ttbbm.addTkTextBookBuy(ttbb);
	                //记录教材销量
	                textBookInfo.setSellcount(textBookInfo.getSellcount()+Integer.valueOf(totalnum));
	                ttbm.updateTkTextBookInfo(textBookInfo);
	                
	                model.setSubject("微信支付:订购教材【" + textBookInfo.getTextbookname()+ "】,订单号:" + out_trade_no);
					model.setTradetype("6");
					model.setTradetypeid(ttbb.getTextbookbuyid().toString());//将订购单id传入
				}
				manager.addSysUserPayTrade(model);
				
				Map<String,Object> map = XMLParser.getMapFromXML(returnxml);
				System.out.println(map);
				//生成支付js方法需要的加密签名【签名规则和上面获取prepayid一样】
				String timestamp = Encode.nullToBlank(request.getParameter("timestamp"));
				//获取sign签名，调用工具生成【注意参数和js方法保持一致】，注意此处参与前面的参数和js方法参数大小写不一致
				Map<String,Object> signmap = new HashMap<String,Object>();
				signmap.put("appId", Configure.getAppid());
				signmap.put("timeStamp", timestamp);
				signmap.put("nonceStr", nonce_str);
				signmap.put("package", "prepay_id=" + map.get("prepay_id"));
				signmap.put("signType", "MD5");
				String paySign = Signature.getSign(signmap);
				
				//返回页面需要的参数
				StringBuffer str = new StringBuffer();
				str.append(map.get("prepay_id")).append(";").append(paySign).append(";").append(out_trade_no);
				System.out.println(str.toString());
				
				pw = response.getWriter();
				pw.write(str.toString());
			}else {
				pw = response.getWriter();
				pw.write("0");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}
	
	/**
	 * 支付成功，js支付接口返回消息，跳转成功界面
	 */
	public ActionForward paySuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			String out_trade_no = Encode.nullToBlank(request.getParameter("out_trade_no"));
			
			SysUserPayTradeManager manager = (SysUserPayTradeManager) getBean("sysUserPayTradeManager");
			SysUserPayTrade sysUserPayTrade = manager.getSysUserPayTradeByOuttradeno(out_trade_no);
			if(sysUserPayTrade != null && userid.equals(sysUserPayTrade.getUserid().toString())){
				if("0".equals(sysUserPayTrade.getState())){
					sysUserPayTrade.setState("1");//如果最终状态为1，则表示交易异常，最终没有通知回调地址
					sysUserPayTrade.setOuttradeno(out_trade_no);
					manager.updateSysUserPayTrade(sysUserPayTrade);
				}
			}
			
			String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));
			if(!"".equals(redirecturl)){
				response.sendRedirect(redirecturl);
				return null;
			}else {
				//直接跳转到充值首页，查看财富值变化
				return index(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}