package com.wkmk.weixin.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.Base64Utils;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ImageUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.PublicResourceBundle;
import com.util.string.UUID;
import com.wkmk.cms.bo.CmsNewsColumn;
import com.wkmk.cms.bo.CmsNewsInfo;
import com.wkmk.cms.service.CmsImageInfoManager;
import com.wkmk.cms.service.CmsNewsColumnManager;
import com.wkmk.cms.service.CmsNewsInfoManager;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysMessageInfo;
import com.wkmk.sys.bo.SysMessageUser;
import com.wkmk.sys.bo.SysSmsInfo;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserGiveMoney;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserRole;
import com.wkmk.sys.bo.SysUserTeaching;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysSmsInfoManager;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserGiveMoneyActivityManager;
import com.wkmk.sys.service.SysUserGiveMoneyManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserRoleManager;
import com.wkmk.sys.service.SysUserTeachingManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentBuy;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.service.TkBookContentBuyManager;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkClassInfoManager;
import com.wkmk.tk.service.TkClassUserManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.EmojiFilter;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.sms.SmsSend;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信服务账户绑定接口
 * @version 1.0
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class WeixinAccountIndexAction extends BaseAction {
	
	/**
	 * 注册
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============直接跳转至注册页面需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			//如果用户已经注册并绑定账号，再次点击注册时直接跳转进入，避免出现绑定多个账号的bug
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			if(attention != null && attention.getUserid().intValue() > 1){
				MpUtil.setUserid(request, attention.getUserid().toString());
				return userindex(mapping, form, request, response);
			}
			
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			String flag = Encode.nullToBlank(request.getParameter("flag"));//1表示是通过点击欢迎词后面的注册按钮直接跳转过来
			if("1".equals(flag)){
				nickname = Base64Utils.base64Decoder(nickname);
			}
			nickname = EmojiFilter.filterEmoji(nickname);//过滤微信昵称特殊图标符号
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			//==============直接跳转至注册页面需要用到的参数===============
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			request.setAttribute("usertype", "2");//默认学生
			request.setAttribute("xueduan", "2");//默认初中学段
			request.setAttribute("username", nickname);
			request.setAttribute("unitname", "选择学校");
			
			//拦截处理：如果未获取到openid，则未关注公众号，直接跳转到关注公众号提示页面
			String userjson = null;
			if(attention == null){
				//获取关注公众号客户端用户信息
				String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
				userjson = MpUtil.getUserInfo0(access_token, openid);
				if(userjson == null || "".equals(userjson) || userjson.indexOf("errcode") != -1){
					//当前openid没有关注公众号，无法获取信息【可能是通过微信网页授权进入的】
					return mapping.findForward("subscribetips");
				}
			}
			
			return mapping.findForward("register");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 注册选择学校
	 */
	public ActionForward selectSchool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============返回注册需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			String usertype = Encode.nullToBlank(request.getParameter("usertype"));
			String xueduan = Encode.nullToBlank(request.getParameter("xueduan"));
			String username = Encode.nullToBlank(request.getParameter("username"));
			String loginname = Encode.nullToBlank(request.getParameter("loginname"));
			String password = Encode.nullToBlank(request.getParameter("password"));
			String repassword = Encode.nullToBlank(request.getParameter("repassword"));
			String mobile = Encode.nullToBlank(request.getParameter("mobile"));
			request.setAttribute("usertype", usertype);
			request.setAttribute("xueduan", xueduan);
			request.setAttribute("username", username);
			request.setAttribute("loginname", loginname);
			request.setAttribute("password", password);
			request.setAttribute("repassword", repassword);
			request.setAttribute("mobile", mobile);
			//==============返回注册需要用到的参数===============
			
			String province = Encode.nullToBlank(request.getParameter("province"));
			String city = Encode.nullToBlank(request.getParameter("city"));
			String county = Encode.nullToBlank(request.getParameter("county"));
			String searchname = Encode.nullToBlank(request.getParameter("searchname"));
			SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "schooltype", "=", "1");
			SearchCondition.addCondition(condition, "status", "=", "1");
			if(!"".equals(province)){
				SearchCondition.addCondition(condition, "province", "=", province);
			}
			if(!"".equals(city)){
				SearchCondition.addCondition(condition, "city", "=", city);
			}
			if(!"".equals(county)){
				SearchCondition.addCondition(condition, "county", "=", county);
			}
			if(!"".equals(searchname)){
				SearchCondition.addCondition(condition, "unitname", "like", "%" + searchname + "%");
			}
			
			PageUtil pageUtil = new PageUtil(request);
			String sorderindex = "unitname asc";
			PageList page = manager.getPageSysUnitInfos(condition, sorderindex, pageUtil.getStartCount(), 9);
			
			request.setAttribute("pagelist", page);
			request.setAttribute("province", province);
			request.setAttribute("city", city);
			request.setAttribute("county", county);
			request.setAttribute("searchname", searchname);
			
			String hiddendiv = Encode.nullToBlank(request.getParameter("hiddendiv"));
			if("".equals(hiddendiv)){
				hiddendiv = "0";
			}
			request.setAttribute("hiddendiv", hiddendiv);
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			request.setAttribute("provinceList", provinceList);
			if(!"".equals(province)){
				SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(province);
				List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
				request.setAttribute("cityList", cityList);
			}
			if(!"".equals(city)){
				SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(city);
				List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
				request.setAttribute("countyList", countyList);
			}
			
			return mapping.findForward("selectschool");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 注册选择学校
	 */
	public ActionForward selectDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============返回注册需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			String usertype = Encode.nullToBlank(request.getParameter("usertype"));
			String xueduan = Encode.nullToBlank(request.getParameter("xueduan"));
			String username = Encode.nullToBlank(request.getParameter("username"));
			String loginname = Encode.nullToBlank(request.getParameter("loginname"));
			String password = Encode.nullToBlank(request.getParameter("password"));
			String repassword = Encode.nullToBlank(request.getParameter("repassword"));
			String mobile = Encode.nullToBlank(request.getParameter("mobile"));
			request.setAttribute("usertype", usertype);
			request.setAttribute("xueduan", xueduan);
			request.setAttribute("username", username);
			request.setAttribute("loginname", loginname);
			request.setAttribute("password", password);
			request.setAttribute("repassword", repassword);
			request.setAttribute("mobile", mobile);
			//==============返回注册需要用到的参数===============
			
			String schoolid = Encode.nullToBlank(request.getParameter("schoolid"));
			SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
			SysUnitInfo sui = manager.getSysUnitInfo(schoolid);
			request.setAttribute("unitid", schoolid);
			request.setAttribute("unitname", sui.getUnitname());
			
			return mapping.findForward("register");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 添加注册
	 */
	public ActionForward addRegister(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============直接跳转至注册页面需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			//==============直接跳转至注册页面需要用到的参数===============
			
			//拦截处理：如果未获取到openid，则直接跳转到注册页面重新注册，或跳转到关注公众号提示页面
			//用户与微信绑定
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			//获取关注公众号客户端用户信息
			String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
			String userjson = MpUtil.getUserInfo0(access_token, openid);
			if(userjson == null || "".equals(userjson) || userjson.indexOf("errcode") != -1){
				//当前openid没有关注公众号，无法获取信息【可能是通过微信网页授权进入的】
				return mapping.findForward("subscribetips");
			}
			
			String sex0 = "";
			String language0 = "";
			String city0 = "";
			String province0 = "";
			String country0 = "";
			String headimgurl0 = "";
			try {
				sex0 = MpUtil.getUserInfoValue(userjson, "sex");
				language0 = MpUtil.getUserInfoValue(userjson, "language");
				city0 = MpUtil.getUserInfoValue(userjson, "city");
				province0 = MpUtil.getUserInfoValue(userjson, "province");
				country0 = MpUtil.getUserInfoValue(userjson, "country");
				headimgurl0 = MpUtil.getUserInfoValue(userjson, "headimgurl");
			} catch (Exception e) {
				e.printStackTrace();
				return mapping.findForward("subscribetips");
			}
			
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			String usertype = Encode.nullToBlank(request.getParameter("usertype"));
			String xueduan = Encode.nullToBlank(request.getParameter("xueduan"));
			String username = Encode.nullToBlank(request.getParameter("username"));
			String loginname = Encode.nullToBlank(request.getParameter("loginname"));
			String password = Encode.nullToBlank(request.getParameter("password"));
			String repassword = Encode.nullToBlank(request.getParameter("repassword"));
			String mobile = Encode.nullToBlank(request.getParameter("mobile"));
			String unitid = Encode.nullToBlank(request.getParameter("unitid"));
			String unitname = Encode.nullToBlank(request.getParameter("unitname"));
			request.setAttribute("usertype", usertype);
			request.setAttribute("xueduan", xueduan);
			request.setAttribute("username", username);
			request.setAttribute("loginname", loginname);
			request.setAttribute("mobile", mobile);
			request.setAttribute("unitid", unitid);
			request.setAttribute("unitname", unitname);
			
			if("".equals(openid)){
				return mapping.findForward("welcome");
			}
			
			//自动注册，只需选择学段和用户类型，因为当前情况下，登录名没用，后期加上扫码登录pc端，同时设置初始登录密码，用绑定的手机号登录
			String autoRegister = Encode.nullToBlank(request.getParameter("autoRegister"));
			if("".equals(username)){
				request.setAttribute("errmsg", "请填写真实姓名!");
				return mapping.findForward("register");
			}
			if(!"1".equals(autoRegister)){
				if("".equals(loginname)){
					request.setAttribute("errmsg", "请填写用户名!");
					return mapping.findForward("register");
				}
				if("".equals(password)){
					request.setAttribute("errmsg", "请填写密码!");
					return mapping.findForward("register");
				}
				if(!password.equals(repassword)){
					request.setAttribute("errmsg", "确认密码与密码不一致!");
					return mapping.findForward("register");
				}
			}else {
				loginname = username;
				nickname = username;
				password = "www.lmzyb.com";//默认自动注册初始密码
			}
			//考虑到注册方便，用户注册时不愿意填写手机号，所以注册时取消填写手机号，当需要购买解题微课充值时，需要绑定手机号
			//if("".equals(mobile)){
			//	request.setAttribute("errmsg", "请填写手机号码!");
			//	return mapping.findForward("register");
			//}
			//if(!isMobileNO(mobile)){
			//	request.setAttribute("errmsg", "手机号码格式错误!");
			//	return mapping.findForward("register");
			//}
			if("".equals(unitid)){
				request.setAttribute("errmsg", "请选择学校!");
				return mapping.findForward("register");
			}
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = null;
			if(!"1".equals(autoRegister)){
				userInfo = manager.getUserInfoByLoginName(loginname);
				if(userInfo != null){
					request.setAttribute("errmsg", "当前用户名已被注册!");
					return mapping.findForward("register");
				}
			}
			
			String date = DateTime.getDate();
			
			SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
			//开始注册保存用户
			userInfo = new SysUserInfo();
			userInfo.setLoginname(EmojiFilter.filterEmoji(loginname));
			userInfo.setUsername(EmojiFilter.filterEmoji(username));
			userInfo.setNickname(EmojiFilter.filterEmoji(nickname));
			userInfo.setUsertype(usertype);
			userInfo.setXueduan(xueduan);
			userInfo.setMobile(mobile);
			userInfo.setPassword(MD5.getEncryptPwd(password));
			if("1".equals(sex0)){
				userInfo.setSex("0");
			}else {
				userInfo.setSex("1");
			}
			userInfo.setPhoto(headimgurl);
			userInfo.setStatus("1");
			userInfo.setUnitid(Integer.valueOf(unitid));
			userInfo.setMoney(0F);
			userInfo.setPaypassword("0");
			userInfo.setAuthentication("0");
			manager.addSysUserInfo(userInfo);
			
			SysUserInfoDetail detail = new SysUserInfoDetail();
			detail.setUserid(userInfo.getUserid());
			detail.setQq("");
			detail.setMsn("");
			detail.setBirthday("2008-08-08");
			detail.setTelephone("");
			detail.setCardtype("9");
			detail.setCardno("");
			detail.setNation("1");
			detail.setProvince("110000");
			detail.setCity("110100");
			detail.setCounty("110101");
			detail.setPostcode("100000");
			detail.setAddress("");
			detail.setMood("");
			detail.setDescript("");
			detail.setFlag("1");
			detail.setPwdquestion("");
			detail.setPwdanswer("");
			detail.setCreatedate(date);
			detail.setLastlogin(date);
			detail.setLogintimes(1);
			detail.setUpdatetime(date);
			detail.setEducation("9");
			detail.setJobtitle("9");
			suidm.addSysUserInfoDetail(detail);
			//角色授权
			SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
			SysUserRole userRole = new SysUserRole();
			userRole.setSysUserInfo(userInfo);
			if("1".equals(usertype)){
				userRole.setRoleid(5);//老师
			}else if("2".equals(usertype)){
				userRole.setRoleid(6);//学生
			}else {
				userRole.setRoleid(7);//家长
			}
			surm.addSysUserRole(userRole);
			
			//用户与微信绑定
			if(attention != null){
				attention.setNickname(EmojiFilter.filterEmoji(nickname));
				attention.setOpenid(openid);
				attention.setSex(sex0);
				attention.setLanguage(language0);
				attention.setCity(city0);
				attention.setProvince(province0);
				attention.setCountry(country0);
				attention.setHeadimgurl(headimgurl0);
				attention.setUserid(userInfo.getUserid());
				attention.setRegistertime(date);
				attention.setUnitid(userInfo.getUnitid());
				suam.updateSysUserAttention(attention);
			}else {
				//获取关注公众号客户端用户信息
				attention = new SysUserAttention();
				attention.setNickname(EmojiFilter.filterEmoji(nickname));
				attention.setOpenid(openid);
				attention.setSex(sex0);
				attention.setLanguage(language0);
				attention.setCity(city0);
				attention.setProvince(province0);
				attention.setCountry(country0);
				attention.setHeadimgurl(headimgurl0);
				attention.setCreatedate(date);
				attention.setDeletedate("");
				attention.setUnregistertime("");
				attention.setUserid(userInfo.getUserid());
				attention.setRegistertime(date);
				attention.setUnitid(userInfo.getUnitid());
				suam.addSysUserAttention(attention);
			}
			
			//启动线程，查询是否有注册赠送龙币活动
			final SysUserInfo sysUserInfo = userInfo;
			final SysUserInfoManager suim0 = manager;
			final String userip = IpUtil.getIpAddr(request);
			Runnable runnable = new Runnable() {
				public void run() {
					giveMoneyActivity(sysUserInfo, suim0, userip);
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			
			request.setAttribute("userInfo", userInfo);
			MpUtil.setUserid(request, userInfo.getUserid().toString());
			return mapping.findForward("registerok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	private void giveMoneyActivity(SysUserInfo userInfo, SysUserInfoManager manager, String userip){
		//查询是否有注册赠送龙币活动
		String curdate = DateTime.getDate();
		SysUserGiveMoneyActivityManager sugmam = (SysUserGiveMoneyActivityManager) getBean("sysUserGiveMoneyActivityManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "type", "=", "1");
		SearchCondition.addCondition(condition, "startdate", "<=", curdate);
		SearchCondition.addCondition(condition, "enddate", ">=", curdate);
		List list = sugmam.getSysUserGiveMoneyActivitys(condition, "startdate desc", 0);
		if(list != null && list.size() > 0){
			SysUserGiveMoneyActivity moneyActivity = (SysUserGiveMoneyActivity) list.get(0);
			
			userInfo.setMoney(userInfo.getMoney() + moneyActivity.getMoney());
			manager.updateSysUserInfo(userInfo);
			
			//记录用户可查询记录
			SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
			SysUserMoney sum = new SysUserMoney();
			sum.setTitle("注册活动赠送学币");
			sum.setChangemoney(moneyActivity.getMoney());
			sum.setChangetype(1);
			sum.setLastmoney(userInfo.getMoney());
			sum.setUserid(userInfo.getUserid());
			sum.setUserip(userip);
			sum.setCreatedate(curdate);
			sum.setDescript("注册活动赠送学币");
			summ.addSysUserMoney(sum);
			
			//记录赠送龙币数
			SysUserGiveMoneyManager sugmm = (SysUserGiveMoneyManager) getBean("sysUserGiveMoneyManager");
			SysUserGiveMoney sysUserGiveMoney = new SysUserGiveMoney();
			sysUserGiveMoney.setUserid(userInfo.getUserid());
			sysUserGiveMoney.setUserip(userip);
			sysUserGiveMoney.setMoney(moneyActivity.getMoney());
			sysUserGiveMoney.setCreatedate(curdate);
			sysUserGiveMoney.setType("1");
			sugmm.addSysUserGiveMoney(sysUserGiveMoney);
			
			//给用户发送内部消息
			String msgtitle = "恭喜您注册成功，注册赠送学币" + moneyActivity.getMoney() + "已充值您的账户余额，请注意查收！";
			String msgcontent = "恭喜您注册成功，注册赠送学币" + moneyActivity.getMoney() + "已充值您的账户余额，请注意查收！";
			SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
			SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
			SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, userInfo.getUserid(), smim, smum);
		}
	}
	
	/*
	 * 正则判断是否手机号
	 */
	public static boolean isMobileNO(String mobile){  
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");  
		Matcher m = p.matcher(mobile);  
		return m.matches();  
	} 
	
	/**
	 * 登录绑定
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============直接跳转至注册页面需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			//==============直接跳转至注册页面需要用到的参数===============
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			String loginname = Encode.nullToBlank(request.getParameter("loginname"));
			String password = Encode.nullToBlank(request.getParameter("password"));
			
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			password = MD5.getEncryptPwd(password);
			loginname = EmojiFilter.filterEmoji(loginname);//登录名进行表情筛选去除
			SysUserInfo userInfo = manager.getUserInfoByLoginName(loginname);
			if (userInfo != null && password.equals(userInfo.getPassword())) {
				if(!"1".equals(userInfo.getUsertype()) && !"2".equals(userInfo.getUsertype())){
					request.setAttribute("errmsg", "只能绑定教师或学生账号!");
					return mapping.findForward("login");
				}
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				List list = suam.getSysUserAttentionByUserids(userInfo.getUserid());
				if(list == null || list.size() < 0){//可直接绑定登陆
					SysUserAttention sysUserAttention = suam.getSysUserAttentionByOpenid(openid);
					if(sysUserAttention != null){
						sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
						sysUserAttention.setOpenid(openid);
						sysUserAttention.setSex(sex);
						sysUserAttention.setHeadimgurl(headimgurl);
						sysUserAttention.setRegistertime(DateTime.getDate());
						sysUserAttention.setUserid(userInfo.getUserid());
						sysUserAttention.setUnitid(userInfo.getUnitid());
						suam.updateSysUserAttention(sysUserAttention);
					}else {//此为特殊情况，没有关注公众号
						sysUserAttention = new SysUserAttention();
						sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
						sysUserAttention.setOpenid(openid);
						sysUserAttention.setSex(sex);
						sysUserAttention.setHeadimgurl(headimgurl);
						sysUserAttention.setCreatedate(DateTime.getDate());
						sysUserAttention.setDeletedate("");
						sysUserAttention.setRegistertime("");
						sysUserAttention.setUnregistertime("");
						sysUserAttention.setUserid(1);//默认游客用户
						sysUserAttention.setUnitid(1);
						suam.addSysUserAttention(sysUserAttention);
					}
					
					MpUtil.setUserid(request, userInfo.getUserid().toString());
					if(!"".equals(scanResult)){
						return mapping.findForward("scan");
					}else {
						//return mapping.findForward("userindex");
						try {
							//判断该用户是否为导入的未激活vip
							String vipmark = userInfo.getVipmark();
							if("1".equals(vipmark)){
								//状态改为导入的已激活
								userInfo.setVipmark("2");
								manager.updateSysUserInfo(userInfo);
								//修改educourseuser信息
								EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
								List<SearchModel> condition = new ArrayList<SearchModel>();
								SearchCondition.addCondition(condition, "status", "=", "1");
								SearchCondition.addCondition(condition, "userid", "=", userInfo.getUserid());
								//SearchCondition.addCondition(condition, "usertype", "=", "3");
								SearchCondition.addCondition(condition, "vip", "=", "1");
								List courseUserLists = ecum.getEduCourseUsers(condition, "courseuserid asc", 0);
								if(courseUserLists !=null && courseUserLists.size()>0){
									for(int i=0;i<courseUserLists.size();i++){
										EduCourseUser ecu = (EduCourseUser) courseUserLists.get(i);
										//开始时间为第一次登录时间
										ecu.setStartdate(DateTime.getDate());
										String validitytime = ecu.getValiditytime();
										SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										Calendar now = Calendar.getInstance();  
										now.setTime(new Date());  
										now.set(Calendar.DATE, now.get(Calendar.DATE) + Integer.valueOf(validitytime)); 
										Date nowDate = now.getTime();
										//结束时间为开始时间+有效期
										ecu.setEnddate(format.format(nowDate));
										ecum.updateEduCourseUser(ecu);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						return studentindex(mapping, form, request, response);
					}
				}else {
					List openidlist= new ArrayList();
					for(int i=0;i<list.size();i++){
						SysUserAttention userAttention  = (SysUserAttention) list.get(i);
						openidlist.add(userAttention.getOpenid());
					}
					System.out.println("opentid集合"+openidlist);
					if(!openidlist.contains(openid)){
						System.out.println("用户id"+userInfo.getUserid());
						String special_account = PublicResourceBundle.getProperty("system","special.account");
						String[] accounts = special_account.split("\\|");
						List<String> accountList=Arrays.asList(accounts);
						if(accountList.contains(userInfo.getUserid().toString())){
//							if(30 == userInfo.getUserid().intValue()){
								SysUserAttention sysUserAttention = suam.getSysUserAttentionByOpenid(openid);
								if(sysUserAttention != null){
									sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
									sysUserAttention.setOpenid(openid);
									sysUserAttention.setSex(sex);
									sysUserAttention.setHeadimgurl(headimgurl);
									sysUserAttention.setRegistertime(DateTime.getDate());
									sysUserAttention.setUserid(30);
									sysUserAttention.setUnitid(1);
									suam.updateSysUserAttention(sysUserAttention);
								}else {//此为特殊情况，没有关注公众号
									sysUserAttention = new SysUserAttention();
									sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
									sysUserAttention.setOpenid(openid);
									sysUserAttention.setSex(sex);
									sysUserAttention.setHeadimgurl(headimgurl);
									sysUserAttention.setCreatedate(DateTime.getDate());
									sysUserAttention.setDeletedate("");
									sysUserAttention.setRegistertime("");
									sysUserAttention.setUnregistertime("");
									sysUserAttention.setUserid(30);
									sysUserAttention.setUnitid(1);
									suam.addSysUserAttention(sysUserAttention);
								}
								
								MpUtil.setUserid(request, userInfo.getUserid().toString());
								if(!"".equals(scanResult)){
									return mapping.findForward("scan");
								}else {
									//return mapping.findForward("userindex");
									try {
										//判断该用户是否为导入的未激活vip
										String vipmark = userInfo.getVipmark();
										if("1".equals(vipmark)){
											//状态改为导入的已激活
											userInfo.setVipmark("2");
											manager.updateSysUserInfo(userInfo);
											//修改educourseuser信息
											EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
											List<SearchModel> condition = new ArrayList<SearchModel>();
											SearchCondition.addCondition(condition, "status", "=", "1");
											SearchCondition.addCondition(condition, "userid", "=", userInfo.getUserid());
											//SearchCondition.addCondition(condition, "usertype", "=", "3");
											SearchCondition.addCondition(condition, "vip", "=", "1");
											List courseUserLists = ecum.getEduCourseUsers(condition, "courseuserid asc", 0);
											if(courseUserLists !=null && courseUserLists.size()>0){
												for(int i=0;i<courseUserLists.size();i++){
													EduCourseUser ecu = (EduCourseUser) courseUserLists.get(i);
													//开始时间为第一次登录时间
													ecu.setStartdate(DateTime.getDate());
													String validitytime = ecu.getValiditytime();
													SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													Calendar now = Calendar.getInstance();  
													now.setTime(new Date());  
													now.set(Calendar.DATE, now.get(Calendar.DATE) + Integer.valueOf(validitytime)); 
													Date nowDate = now.getTime();
													//结束时间为开始时间+有效期
													ecu.setEnddate(format.format(nowDate));
													ecum.updateEduCourseUser(ecu);
												}
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									return studentindex(mapping, form, request, response);
								}
							}else{
								request.setAttribute("errmsg", "当前账号已被其他微信号绑定!");
								return mapping.findForward("login");
							}
					}else{
						MpUtil.setUserid(request, userInfo.getUserid().toString());
						if(!"".equals(scanResult)){
							return mapping.findForward("scan");
						}else {
							//return mapping.findForward("userindex");
							try {
								//判断该用户是否为导入的未激活vip
								String vipmark = userInfo.getVipmark();
								if("1".equals(vipmark)){
									//状态改为导入的已激活
									userInfo.setVipmark("2");
									manager.updateSysUserInfo(userInfo);
									//修改educourseuser信息
									EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
									List<SearchModel> condition = new ArrayList<SearchModel>();
									SearchCondition.addCondition(condition, "status", "=", "1");
									SearchCondition.addCondition(condition, "userid", "=", userInfo.getUserid());
									//SearchCondition.addCondition(condition, "usertype", "=", "3");
									SearchCondition.addCondition(condition, "vip", "=", "1");
									List courseUserLists = ecum.getEduCourseUsers(condition, "courseuserid asc", 0);
									if(courseUserLists !=null && courseUserLists.size()>0){
										for(int i=0;i<courseUserLists.size();i++){
											EduCourseUser ecu = (EduCourseUser) courseUserLists.get(i);
											//开始时间为第一次登录时间
											ecu.setStartdate(DateTime.getDate());
											String validitytime = ecu.getValiditytime();
											SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											Calendar now = Calendar.getInstance();  
											now.setTime(new Date());  
											now.set(Calendar.DATE, now.get(Calendar.DATE) + Integer.valueOf(validitytime)); 
											Date nowDate = now.getTime();
											//结束时间为开始时间+有效期
											ecu.setEnddate(format.format(nowDate));
											ecum.updateEduCourseUser(ecu);
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return studentindex(mapping, form, request, response);
						}
					}
				}
			}else{
				request.setAttribute("errmsg", "用户名或密码输入错误!");
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward loginbak(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//==============直接跳转至注册页面需要用到的参数===============
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
			String nickname = Encode.nullToBlank(request.getParameter("nickname"));
			String sex = Encode.nullToBlank(request.getParameter("sex"));
			request.setAttribute("openid", openid);
			request.setAttribute("headimgurl", headimgurl);
			request.setAttribute("nickname", nickname);
			request.setAttribute("sex", sex);
			//==============直接跳转至注册页面需要用到的参数===============
			//通过扫一扫传递过来的参数
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			request.setAttribute("scanResult", scanResult);
			
			String loginname = Encode.nullToBlank(request.getParameter("loginname"));
			String password = Encode.nullToBlank(request.getParameter("password"));
			
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			password = MD5.getEncryptPwd(password);
			loginname = EmojiFilter.filterEmoji(loginname);//登录名进行表情筛选去除
			SysUserInfo userInfo = manager.getUserInfoByLoginName(loginname);
			if (userInfo != null && password.equals(userInfo.getPassword())) {
				if(!"1".equals(userInfo.getUsertype()) && !"2".equals(userInfo.getUsertype())){
					request.setAttribute("errmsg", "只能绑定教师或学生账号!");
					return mapping.findForward("login");
				}
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention userAttention = suam.getSysUserAttentionByUserid(userInfo.getUserid());
				if(userAttention == null){//可直接绑定登陆
					SysUserAttention sysUserAttention = suam.getSysUserAttentionByOpenid(openid);
					if(sysUserAttention != null){
						sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
						sysUserAttention.setOpenid(openid);
						sysUserAttention.setSex(sex);
						sysUserAttention.setHeadimgurl(headimgurl);
						sysUserAttention.setRegistertime(DateTime.getDate());
						sysUserAttention.setUserid(userInfo.getUserid());
						sysUserAttention.setUnitid(userInfo.getUnitid());
						suam.updateSysUserAttention(sysUserAttention);
					}else {//此为特殊情况，没有关注公众号
						sysUserAttention = new SysUserAttention();
						sysUserAttention.setNickname(EmojiFilter.filterEmoji(nickname));
						sysUserAttention.setOpenid(openid);
						sysUserAttention.setSex(sex);
						sysUserAttention.setHeadimgurl(headimgurl);
						sysUserAttention.setCreatedate(DateTime.getDate());
						sysUserAttention.setDeletedate("");
						sysUserAttention.setRegistertime("");
						sysUserAttention.setUnregistertime("");
						sysUserAttention.setUserid(1);//默认游客用户
						sysUserAttention.setUnitid(1);
						suam.addSysUserAttention(sysUserAttention);
					}
					
					MpUtil.setUserid(request, userInfo.getUserid().toString());
					if(!"".equals(scanResult)){
						return mapping.findForward("scan");
					}else {
						//return mapping.findForward("userindex");
						return studentindex(mapping, form, request, response);
					}
				}else {
					//特殊情况：解决重复绑定
					if(openid.equals(userAttention.getOpenid())){
						MpUtil.setUserid(request, userInfo.getUserid().toString());
						if(!"".equals(scanResult)){
							return mapping.findForward("scan");
						}else {
							//return mapping.findForward("userindex");
							return studentindex(mapping, form, request, response);
						}
					}else {
						request.setAttribute("errmsg", "当前账号已被其他微信号绑定!");
						return mapping.findForward("login");
					}
				}
			}else{
				request.setAttribute("errmsg", "用户名或密码输入错误!");
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	/**
	 * 登录成功跳转个人中心
	 */
	public ActionForward userindex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			//==========通过微信自动回复消息跳转过来==========
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			//如果用户已经注册并绑定账号，再次点击注册时直接跳转进入，避免出现绑定多个账号的bug
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			if(attention != null && attention.getUserid().intValue() > 1){
				userid = attention.getUserid().toString();
			}
			if(!"1".equals(userid)){
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				if("1".equals(sysUserInfo.getUsertype())){//老师
					return teacherindex(mapping, form, request, response);
				}
				if("2".equals(sysUserInfo.getUsertype())){//学生
					return studentindex(mapping, form, request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("welcome");
	}
	
	/**
	 * 教师桌面
	 */
	public ActionForward teacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				List list = manager.getTkClassInfoAndBooks(userid);
				request.setAttribute("list", list);
				
				return mapping.findForward("teacher");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 学生桌面
	 */
	public ActionForward student(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				List list = manager.getBookAndClassByUserid(userid);
				
				TkClassInfoManager tcim = (TkClassInfoManager) getBean("tkClassInfoManager");
				List<String> classidList = new ArrayList<String>();
				Map<String, String> classMap = new HashMap<String, String>();
				TkClassInfo tci = null;
				String classid = null;
				Object[] object = null;
				for(int i=0, size=list.size(); i<size; i++){
					object = (Object[]) list.get(i);
					classid = object[3].toString();
					if(!"0".equals(classid) && !classidList.contains(classid)){
						classidList.add(classid);
						tci = tcim.getTkClassInfo(classid);
						classMap.put(classid, tci.getClassname());
					}
				}
				request.setAttribute("list", list);
				request.setAttribute("classMap", classMap);
				return mapping.findForward("student");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 我的账号
	 */
	public ActionForward myAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUnitInfoManager suim = (SysUnitInfoManager) getBean("sysUnitInfoManager");
				SysUserInfo userInfo = manager.getSysUserInfo(userid);
				SysUnitInfo unitInfo = suim.getSysUnitInfo(userInfo.getUnitid());
				request.setAttribute("userInfo", userInfo);
				request.setAttribute("unitInfo", unitInfo);
				return mapping.findForward("myaccount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 退出确认
	 */
	public ActionForward logoutConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			MpUtil.getUserid(request);
			return mapping.findForward("logoutcomfirm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 退出账号
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserAttentionManager manager = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention attention = manager.getSysUserAttentionByUserid(Integer.valueOf(userid));
				if(attention != null){
					attention.setUserid(1);
					//attention.setUnitid(1);
					attention.setUnregistertime(DateTime.getDate());
					manager.updateSysUserAttention(attention);
				}
				
				HttpSession session = request.getSession();
				session.invalidate();
				
				return mapping.findForward("welcome");//需要进入欢迎界面，通过点击按钮获取微信用户基本信息（头像）跳转至登陆界面
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward beforeUpdateNickName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				request.setAttribute("userinfo", user);
				request.setAttribute("usertype", user.getUsertype());
				return mapping.findForward("editnickname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward saveNickName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String nickname=Encode.nullToBlank(request.getParameter("nickname"));
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				user.setUsername(nickname);
				manager.updateSysUserInfo(user);
				return userInfoManager(mapping,form,request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 修改绑定手机号码
	 */
	public ActionForward beforeUpdateMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				request.setAttribute("userinfo", user);
				request.setAttribute("usertype", user.getUsertype());
				request.setAttribute("redirecturl", redirecturl);
				return mapping.findForward("beforemobile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 保存绑定手机号码
	 */
	public void saveMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				
				String smsid=Encode.nullToBlank(request.getParameter("smsid"));
				SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SysSmsInfo sms=smsmanager.getSysSmsInfo(smsid);
				Date codevalidtime=format.parse(sms.getCodevalidtime());
				Date nowdate=new Date();
				String result="";
				if(nowdate.before(codevalidtime)){
					String mobile=Encode.nullToBlank(request.getParameter("mobile"));
					user.setMobile(mobile);
					user=manager.updateSysUserInfo(user);
					result="1";
				}else{
					result="2";
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改密码
	 */
	public ActionForward beforeUpdatePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String flag = Encode.nullToBlank(request.getParameter("flag"));
				String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));//表示从购买解题微课首次购买需设置支付密码处跳转过来
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				request.setAttribute("userinfo", user);
				request.setAttribute("usertype", user.getUsertype());
				if("1".equals(flag)){
					request.setAttribute("redirecturl", DES.getEncryptPwd(redirecturl));
				}else {
					request.setAttribute("redirecturl", redirecturl);
				}
				return mapping.findForward("updatepwd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 保存密码
	 */
	public void updateSavePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String result="-1";
				String newpassword = Encode.nullToBlank(request.getParameter("newpassword"));
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo userInfo = manager.getSysUserInfo(userid);
				//修改支付密码统一用短信验证
//				if(!"0".equals(userInfo.getPaypassword())){
//					String oldpassword = Encode.nullToBlank(request.getParameter("oldpassword"));
//					oldpassword = MD5.getEncryptPwd(oldpassword);
//					if(!oldpassword.equals(userInfo.getPaypassword())){
//						request.setAttribute("errmsg", "旧密码输入错误!");
//						return mapping.findForward("updatepwd");
//					}
//				}else{
					String smsid = Encode.nullToBlank(request.getParameter("smsid"));
					SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SysSmsInfo sms=smsmanager.getSysSmsInfo(smsid);
					Date codevalidtime=format.parse(sms.getCodevalidtime());
					Date nowdate=new Date();
					if(nowdate.before(codevalidtime)){
						result="1";
					}
//				}
				userInfo.setPaypassword(MD5.getEncryptPwd(newpassword));
				userInfo=manager.updateSysUserInfo(userInfo);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户信息
	 */
	public ActionForward beforeUpdateInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = manager.getSysUserInfo(userid);
				request.setAttribute("sysUserInfo", sysUserInfo);
				return mapping.findForward("updateinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 修改用户信息
	 */
	public ActionForward updateSaveInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = manager.getSysUserInfo(userid);
				request.setAttribute("sysUserInfo", sysUserInfo);
				
				String xueduan = Encode.nullToBlank(request.getParameter("xueduan"));
				String username = Encode.nullToBlank(request.getParameter("username"));
				String mobile = Encode.nullToBlank(request.getParameter("mobile"));
				if("".equals(username)){
					request.setAttribute("errmsg", "真实姓名不能为空!");
					return mapping.findForward("updateinfo");
				}
				if("".equals(mobile)){
					request.setAttribute("errmsg", "手机号码不能为空!");
					return mapping.findForward("updateinfo");
				}
				if(!isMobileNO(mobile)){
					request.setAttribute("errmsg", "手机号码格式错误!");
					return mapping.findForward("updateinfo");
				}
				sysUserInfo.setXueduan(xueduan);
				sysUserInfo.setUsername(username);
				sysUserInfo.setMobile(mobile);
				manager.updateSysUserInfo(sysUserInfo);
				
				request.setAttribute("msg", "用户信息修改保存成功!");
				return mapping.findForward("updateinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 关于我们
	 */
	public ActionForward aboutus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			MpUtil.getUserid(request);
			String linkurl = null;
			try {
				linkurl = PublicResourceBundle.getProperty("system", "aboutus.link.url");
			} catch (Exception e) {
			}
			if(linkurl != null && !"".equals(linkurl)){
				ActionForward gotourl = new ActionForward(linkurl);
				gotourl.setPath(linkurl);
				gotourl.setRedirect(true);
				return gotourl;
			}else {
				return mapping.findForward("aboutus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//修改个人头像
	public void saveUserPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);			
			SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
			SysUserInfo user=usermanager.getSysUserInfo(userid);
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			String savepath = + user.getUnitid() + "/user/"+DateTime.getDate("yyyy")+DateTime.getDate("MM")+"/";
			String filepath = rootpath+"/upload/"+savepath;
			if(!FileUtil.isExistDir(filepath)){
				FileUtil.creatDir(filepath);
			}
			
	        String filename = UUID.getNewUUID()+".jpg";
			String mediaId = Encode.nullToBlank(request.getParameter("serverPhotoIds"));//照片附件
			if(!"".equals(mediaId)){
				long fsize = MpUtil.downloadMedia(mediaId, rootpath, "/upload/"+savepath+filename);
				if(fsize > 0){
					//压缩头像
					ImageUtil.generateThumbnailsSubImage(filepath + filename, rootpath + "/upload/" + savepath + "/120x120_" + filename, 120, 120, true);
					
					user.setPhoto(savepath + "/120x120_" + filename);
					usermanager.updateSysUserInfo(user);
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(user.getPhoto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//================================龙门作业宝2.0功能界面=============================
	/**
	 * 首页
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				if(sysUserInfo != null){
					//检查当前用户是否被禁用，查看禁用原因
					SysUserDisable userDisable = null;
					if("2".equals(sysUserInfo.getStatus())){
						userDisable = getSysUserDisable(sysUserInfo.getUserid());
						if(userDisable != null){
							String enddate = userDisable.getEndtime();//如果已到禁用截止时间，自动启用
							String curdate = DateTime.getDate();
							if(DateTime.getCompareToDate(enddate, curdate) <= 0){
								sysUserInfo.setStatus("1");//启用
								suim.updateSysUserInfo(sysUserInfo);
							}
						}
					}
					
					if("1".equals(sysUserInfo.getStatus())){
						if("1".equals(sysUserInfo.getUsertype())){//老师
							return teacherindex(mapping, form, request, response);
						}
						if("2".equals(sysUserInfo.getUsertype())){//学生
							return studentindex(mapping, form, request, response);
						}
					}else {
						//用户状态非正常，无法登陆使用，给出提示
						String promptinfo = null;
						if("0".equals(sysUserInfo.getStatus())){
							promptinfo = "您的账号还没审核，请与管理员联系!";
						}else if("2".equals(sysUserInfo.getStatus())){
							if(userDisable != null){
								promptinfo = userDisable.getDescript();
							}else {
								promptinfo = "您的账号已被禁用，请与管理员联系!";
							}
						}else if("3".equals(sysUserInfo.getStatus())){
							promptinfo = "您的账号已被删除，请与管理员联系!";
						}
						request.setAttribute("promptinfo", promptinfo);
						request.setAttribute("status", sysUserInfo.getStatus());
						return mapping.findForward("loginstatetips");
					}
				}else {
					//跳转到登录界面
					return mapping.findForward("welcome");
				}
			}else {
				//跳转到登录界面
				return mapping.findForward("welcome");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	private SysUserDisable getSysUserDisable(Integer userid){
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", userid);
		List list = manager.getSysUserDisables(condition, "endtime desc", 1);
		if(list != null && list.size() > 0){
			return (SysUserDisable) list.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * 教师首页，可查看电子教案
	 */
	public ActionForward teacherindex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				//======================首页banner=========================
				List images = (List) CacheUtil.getObject("CmsImageInfo_List");
				if(images == null || images.size() == 0){
					String orderby="orderindex desc,createdate desc";
					CmsImageInfoManager imgmanager=(CmsImageInfoManager)getBean("cmsImageInfoManager");
					List<SearchModel> condition=new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "status", "=", "1");
					images = imgmanager.getCmsImageInfos(condition, orderby, 6);
					CacheUtil.putObject("CmsImageInfo_List", images, 7*24*3600);//默认缓存7天，直到后台数据改变
				}
				request.setAttribute("images", images);
				
				List newslist = (List) CacheUtil.getObject("CmsNewsInfo_List_index");
				if(newslist == null || newslist.size() == 0){
					CmsNewsInfoManager newsmanager=(CmsNewsInfoManager)getBean("cmsNewsInfoManager");
					List<SearchModel> condition=new ArrayList<SearchModel>();
					String orderby="recommand desc,happendate desc";
					SearchCondition.addCondition(condition, "status", "=", "1");
					//SearchCondition.addCondition(condition, "happendate", "<", DateTime.getDate());
					newslist = newsmanager.getCmsNewsInfos(condition, orderby, 6);
					CacheUtil.putObject("CmsNewsInfo_List_index", newslist, 7*24*3600);//默认缓存7天，直到后台数据改变
				}
				request.setAttribute("newslist", newslist);
				
				//查询未读消息
				SysMessageUserManager smum = (SysMessageUserManager)getBean("sysMessageUserManager");
				String unread = smum.getUnreadMessageCount(userid);
				request.setAttribute("unread", unread);
				return mapping.findForward("teacherindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 学生首页，不可查看电子教案，可查看英语听力
	 */
	public ActionForward studentindex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				//======================首页banner=========================
				List images = (List) CacheUtil.getObject("CmsImageInfo_List");
				if(images == null || images.size() == 0){
					String orderby="orderindex desc,createdate desc";
					CmsImageInfoManager imgmanager=(CmsImageInfoManager)getBean("cmsImageInfoManager");
					List<SearchModel> condition=new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "status", "=", "1");
					images = imgmanager.getCmsImageInfos(condition, orderby, 6);
					CacheUtil.putObject("CmsImageInfo_List", images, 7*24*3600);//默认缓存7天，直到后台数据改变
				}
				request.setAttribute("images", images);
				
				List newslist = (List) CacheUtil.getObject("CmsNewsInfo_List_index");
				if(newslist == null || newslist.size() == 0){
					CmsNewsInfoManager newsmanager=(CmsNewsInfoManager)getBean("cmsNewsInfoManager");
					List<SearchModel> condition=new ArrayList<SearchModel>();
					String orderby="recommand desc,happendate desc";
					SearchCondition.addCondition(condition, "status", "=", "1");
					//SearchCondition.addCondition(condition, "happendate", "<", DateTime.getDate());
					newslist = newsmanager.getCmsNewsInfos(condition, orderby, 6);
					CacheUtil.putObject("CmsNewsInfo_List_index", newslist, 7*24*3600);//默认缓存7天，直到后台数据改变
				}
				request.setAttribute("newslist", newslist);
				
				//查询未读消息
				SysMessageUserManager smum = (SysMessageUserManager)getBean("sysMessageUserManager");
				String unread = smum.getUnreadMessageCount(userid);
				request.setAttribute("unread", unread);
				return mapping.findForward("studentindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 首页资讯
	 */
	public ActionForward indexNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				List newslist = (List) CacheUtil.getObject("CmsNewsInfo_List_index");
				if(newslist == null || newslist.size() == 0){
					CmsNewsInfoManager newsmanager=(CmsNewsInfoManager)getBean("cmsNewsInfoManager");
					List<SearchModel> condition=new ArrayList<SearchModel>();
					String orderby="recommand desc,happendate desc";
					SearchCondition.addCondition(condition, "status", "=", "1");
					//SearchCondition.addCondition(condition, "happendate", "<", DateTime.getDate());
					newslist = newsmanager.getCmsNewsInfos(condition, orderby, 6);
					CacheUtil.putObject("CmsNewsInfo_List_index", newslist, 7*24*3600);//默认缓存7天，直到后台数据改变
				}
				request.setAttribute("newslist", newslist);
				return mapping.findForward("indexnews");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 导航
	 */
	public ActionForward daohang(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				//判断用户类型
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";//老师
				if("2".equals(sysUserInfo.getUsertype())){//学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
				//查询未读消息
				SysMessageUserManager smum = (SysMessageUserManager)getBean("sysMessageUserManager");
				String unread = smum.getUnreadMessageCount(userid);
				request.setAttribute("unread", unread);
				return mapping.findForward("daohang");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//资讯栏目列表
	public ActionForward getNewsColumn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				CmsNewsColumnManager columnmanager=(CmsNewsColumnManager)getBean("cmsNewsColumnManager");
				List<SearchModel> condition=new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "status", "=", "1");
				SearchCondition.addCondition(condition, "columntype", "=", "1");
				List<CmsNewsColumn> columnlist=columnmanager.getCmsNewsColumns(condition, "", 0);
				request.setAttribute("columnlist", columnlist);
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("newscolumnlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//栏目下的资讯列表
	public ActionForward getNewsByColumn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				String columnid=Encode.nullToBlank(request.getParameter("columnid"));
				request.setAttribute("columnid", columnid);
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("newsinfolist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//ajax刷新资讯列表
	public void getNewsByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
				String userid=Encode.nullToBlank(request.getParameter("userid"));
				String columnid=Encode.nullToBlank(request.getParameter("columnid"));
				int pagesize=10;int pagenum=Integer.parseInt(Encode.nullToBlank(request.getParameter("pagenum")));int pagecount=pagesize*pagenum;
				StringBuffer result=new StringBuffer();
				CmsNewsInfoManager newsinfomanager=(CmsNewsInfoManager)getBean("cmsNewsInfoManager");
				List<SearchModel> condition=new ArrayList<SearchModel>();
				if(!"".equals(columnid)){
					SearchCondition.addCondition(condition, "cmsNewsColumn.columnid", "=", Integer.parseInt(columnid));
				}
				SearchCondition.addCondition(condition, "status", "=", "1");
				//SearchCondition.addCondition(condition, "happendate", "<", DateTime.getDate());
				List<CmsNewsInfo> newsinfolist=newsinfomanager.getPageCmsNewsInfos(condition, "", pagecount, pagesize).getDatalist();
				for (CmsNewsInfo news : newsinfolist) {
					result.append("<div class=\"library\">"+
								        "<a href=\"/weixinAccountIndex.app?method=getNews&newsid="+news.getNewsid()+"&userid="+userid+"\">"+
											"<div class=\"library_main\">"+
												"<p class=\"library_main_p\">"+news.getTitle()+"</p>"+
											"</div>"+
									        "<div class=\"library_bottom\">"+                
									          "<p  class=\"library_bottom_left\">发布者："+news.getAuthor()+"</p>"+
									          "<p  class=\"library_bottom_right\">浏览次数："+news.getHits()+"</p>"+
									        "</div>"+  
								        "</a>"+
								    "</div>");
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result.toString());			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	//获取资讯详情
	public ActionForward getNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String newsid=Encode.nullToBlank(request.getParameter("newsid"));
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				CmsNewsInfoManager manager=(CmsNewsInfoManager)getBean("cmsNewsInfoManager");
				CmsNewsInfo newsinfo=manager.getCmsNewsInfo(newsid);
				newsinfo.setHits(newsinfo.getHits()+1);
				manager.updateCmsNewsInfo(newsinfo);
				request.setAttribute("newsinfo", newsinfo);
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("newsinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//个人中心--跳转
	public ActionForward getPersonalCenter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo userinfo=manager.getSysUserInfo(userid);
				if(userinfo != null){
					//检查当前用户是否被禁用，查看禁用原因
					SysUserDisable userDisable = null;
					if("2".equals(userinfo.getStatus())){
						userDisable = getSysUserDisable(userinfo.getUserid());
						if(userDisable != null){
							String enddate = userDisable.getEndtime();//如果已到禁用截止时间，自动启用
							String curdate = DateTime.getDate();
							if(DateTime.getCompareToDate(enddate, curdate) <= 0){
								userinfo.setStatus("1");//启用
								manager.updateSysUserInfo(userinfo);
							}
						}
					}
					
					if("1".equals(userinfo.getStatus())){
						if(!userinfo.getPhoto().startsWith("http://")){
							userinfo.setPhoto("/upload/" + userinfo.getPhoto());
						}
						request.setAttribute("userinfo", userinfo);
						request.setAttribute("usertype", userinfo.getUsertype());
						//查询未读消息
						SysMessageUserManager smum = (SysMessageUserManager)getBean("sysMessageUserManager");
						String unread = smum.getUnreadMessageCount(userid);
						request.setAttribute("unread", unread);
						return mapping.findForward("personalcenter");
					}else {
						//用户状态非正常，无法登陆使用，给出提示
						String promptinfo = null;
						if("0".equals(userinfo.getStatus())){
							promptinfo = "您的账号还没审核，请与管理员联系!";
						}else if("2".equals(userinfo.getStatus())){
							if(userDisable != null){
								promptinfo = userDisable.getDescript();
							}else {
								promptinfo = "您的账号已被禁用，请与管理员联系!";
							}
						}else if("3".equals(userinfo.getStatus())){
							promptinfo = "您的账号已被删除，请与管理员联系!";
						}
						request.setAttribute("promptinfo", promptinfo);
						request.setAttribute("status", userinfo.getStatus());
						return mapping.findForward("loginstatetips");
					}
				}else {
					return mapping.findForward("welcome");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//个人中心--个人信息管理
		public ActionForward userInfoManager(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			try {
				String userid=MpUtil.getUserid(request);
				if(!"1".equals(userid)){
					String xueduan=Encode.nullToBlank(request.getParameter("xueduan"));
					SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
					SysUserInfo userinfo=manager.getSysUserInfo(userid);
					if(!"".equals(xueduan)){
						userinfo.setXueduan(xueduan);
						manager.updateSysUserInfo(userinfo);
					}
					if(!userinfo.getPhoto().startsWith("http://")){
						userinfo.setPhoto("/upload/" + userinfo.getPhoto());
					}
					request.setAttribute("userinfo", userinfo);
					request.setAttribute("usertype", userinfo.getUsertype());
					return mapping.findForward("user_manager");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("error");
		}
		
	public ActionForward myCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("mycourse");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//个人中心--已购微课
	public void getMyCourseByAjax(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String userid=Encode.nullToBlank(request.getParameter("userid"));
            String searchuserid=MpUtil.getUserid(request);
            String type=Encode.nullToBlank(request.getParameter("type"));//购买类型，1解题微课，2举一反三微课
            int pagesize=10;int pagenum=Integer.parseInt(Encode.nullToBlank(request.getParameter("pagenum")));int pagestat=pagesize*pagenum;
            TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
            TkBookContentFilmManager bcfm=(TkBookContentFilmManager)getBean("tkBookContentFilmManager");
            TkBookInfoManager bookmanager=(TkBookInfoManager)getBean("tkBookInfoManager");
            TkBookContentManager contentmanager=(TkBookContentManager)getBean("tkBookContentManager");
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "type", "=", type);
            SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(searchuserid));
            SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
            List<TkBookContentBuy> list = tbcbm.getPageTkBookContentBuys(condition, "createdate desc", pagestat, pagesize).getDatalist();
            StringBuffer result=new StringBuffer();
            for (TkBookContentBuy buy : list) {//循环购买记录查询相关数据
                TkBookInfo bookinfo=bookmanager.getTkBookInfo(buy.getBookid());//作业本
                TkBookContent bookcontent=contentmanager.getTkBookContent(buy.getContentid());//章节
                TkBookContent parentcontent=contentmanager.getTkBookContentByContentno(bookcontent.getParentno(), bookcontent.getBookid().toString());//父章节
                List<VwhFilmInfo> filmlist=bcfm.getFilmByBookContent(buy.getBookid(),buy.getContentid());
                VwhFilmInfo film=filmlist.get(0);
                String typename="1".equals(type)?"解题微课":"举一反三";
                result.append("<a href=\"/weixinVod.app?method=play&userid="+userid+"&bookid="+buy.getBookid()+"&bookcontentid="+buy.getContentid()+"&type="+type+"&searchtype=1\">"+
                                "<div class=\"buy_main_moudle\">"+
                                    "<img src=\"/upload/"+film.getSketch()+"\" />"+
                                    "<div class=\"buy_main_moudle_font\">"+
                                        "<p  class=\"buy_main_moudle_font_p\">【"+typename+"】</p>"+
                                        "<p class=\"buy_main_moudle_font_p01\">"+bookinfo.getTitle()+"("+bookinfo.getFlags()+Constants.getCodeTypevalue("version", bookinfo.getVersion())+")/"+parentcontent.getTitle()+"/"+bookcontent.getTitle()+"</p>"+
                                        "<p class=\"buy_main_moudle_font_p02\">微课数量：<span>"+filmlist.size()+"</span></p>"+
                                    "</div>"+
                                "</div>"+
                             "</a>");
            }
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//消息列表
	public ActionForward messageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("messagelist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//ajax刷新消息列表
	public void messageListByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			String desuserid=Encode.nullToBlank(request.getParameter("userid"));
			if(!"1".equals(userid)){
				int pagesize=10;
				int pagenum=Integer.parseInt(Encode.nullToBlank(request.getParameter("pagenum")));
				int start=pagenum*pagesize;
				SysMessageUserManager manager = (SysMessageUserManager)getBean("sysMessageUserManager");
				List<SearchModel> condition=new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				List<SysMessageUser> page=manager.getPageSysMessageUsers(condition, "sysMessageInfo.createdate desc", start, pagesize).getDatalist();
				StringBuffer result=new StringBuffer();
				SysMessageInfo message = null;
				for (SysMessageUser user : page) {
					message = user.getSysMessageInfo();
					result.append(
					        "<a href=\"/weixinAccountIndex.app?method=getMessageInfo&messageuserid="+user.getMessageuserid()+"&userid="+desuserid+"\">"+				          
						        "<div class=\"news_list\">"+
						    	"<p class=\"news_date\">"+message.getCreatedate()+"</p>"+
						        "<div class=\"news_list_main\">"+
						        	"<p class=\"news_list_main_p\">"+("0".equals(user.getIsread())?"<span style=\"color:#ff0000;font-size:10px;\">【未读】</span>":"")+message.getTitle()+"</p>"+
						        	"<p class=\"news_list_main_p01 news_list_main_p02\"></p>"+
						            "<div class=\"news_list_main_a\">"+
						            	"<p>查看详情</p>"+
						                "<img src=\"/weixin/images/broadcast_more.png\" />"+
						            "</div>"+
						        "</div>"+
						    "</div>"+
					        "</a>");
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//消息详情
	public ActionForward getMessageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String messageuserid=Encode.nullToBlank(request.getParameter("messageuserid"));
				SysMessageUserManager manager = (SysMessageUserManager) getBean("sysMessageUserManager");
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysMessageUser sysMessageUser = manager.getSysMessageUser(messageuserid);
				SysMessageInfo message=sysMessageUser.getSysMessageInfo();
				if("0".equals(sysMessageUser.getIsread())){
					sysMessageUser.setReadtime(DateTime.getDate());
					sysMessageUser.setIsread("1");
					manager.updateSysMessageUser(sysMessageUser);
				}
				request.setAttribute("messageinfo", message);
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("messageinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}	
	
	//客服中心
	public ActionForward serverCenter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("servercenter");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}	
	
	//变更手机号码获取验证码
	public void getCodeByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String userid=MpUtil.getUserid(request);
			String mobile=Encode.nullToBlank(request.getParameter("mobile"));
			String sixrandom=getSixRandom();
			String result="1";
			String smsid="";
			int smscount=smsmanager.getMobileCountMessage(userid);
			if(smscount<=Constants.CODETYPE_MOBILE_SMSCOUNT){
				if(!"".equals(mobile)){
					String content="申请变更手机号，验证码:"+sixrandom+"(5分钟内有效)，如非本人操作请忽略。";
					if(SmsSend.send(mobile, content)){
						SysSmsInfo sms=new SysSmsInfo();
						sms.setUserid(Integer.parseInt(userid));
						sms.setCode(sixrandom);
						sms.setState("1");
						sms.setType("2");
						sms.setContent(content);
						sms.setMobile(mobile);
						sms.setCreatedate(format.format(new Date()));
						sms.setCodevalidtime(getCodeValidtime(format.format(new Date()),format));
						sms=smsmanager.addSysSmsInfo(sms);
						smsid=sms.getSmsid().toString();
					}else{
						result="0";
					}
					
				}
			}else{
				result="-1";
			}
			JSONObject json=new JSONObject();
			json.put("result", result);
			json.put("sixrandom", sixrandom);
			json.put("smsid", smsid);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//修改支付密码获取验证码
	public void getCodeByAjax1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
			SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String userid=MpUtil.getUserid(request);
			SysUserInfo user=usermanager.getSysUserInfo(userid);
			String mobile=user.getMobile();
			String sixrandom=getSixRandom();
			String result="1";
			String smsid="";
			int smscount=smsmanager.getPasswordCountMessage(userid);
			if(smscount<=Constants.CODETYPE_PAYPASSWORD_SMSCOUNT){
				if(!"".equals(mobile)){
					String content="申请修改支付密码，验证码:"+sixrandom+"(5分钟内有效)，如非本人操作请忽略。";
					if(SmsSend.send(mobile, content)){
						SysSmsInfo sms=new SysSmsInfo();
						sms.setUserid(Integer.parseInt(userid));
						sms.setCode(sixrandom);
						sms.setState("1");
						sms.setType("3");
						sms.setContent(content);
						sms.setMobile(mobile);
						sms.setCreatedate(format.format(new Date()));
						sms.setCodevalidtime(getCodeValidtime(format.format(new Date()),format));
						sms=smsmanager.addSysSmsInfo(sms);
						smsid=sms.getSmsid().toString();
					}else{
						result="0";
					}
					
				}
			}else{
				result="-1";
			}
			JSONObject json=new JSONObject();
			json.put("result", result);
			json.put("sixrandom", sixrandom);
			json.put("smsid", smsid);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取六位随机数
	private String getSixRandom(){
		Random random = new Random();
		String result="";
		for(int i=0;i<6;i++){
			result+=random.nextInt(10);
		}
		return result;
	}
		
	//获取验证码有效时间(短信发送之后五分钟内)
	private String getCodeValidtime(String createdate,SimpleDateFormat format){
		long curren = System.currentTimeMillis();	
		curren += 5 * 60 * 1000;	
		Date da = new Date(curren);	
		return format.format(da);
	}
	
	//客服中心
	public ActionForward tip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				return mapping.findForward("tip");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

		//跳转到教学设置界面
	public ActionForward setUserTeaching(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserTeachingManager manager=(SysUserTeachingManager)getBean("sysUserTeachingManager");
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=usermanager.getSysUserInfo(userid);
				List teachinglist=manager.getFiveTeaching(userid);
				for (int i=0;i<teachinglist.size();i++) {
					Object[] obj=(Object[])teachinglist.get(i);
					Map m=new HashMap();
					m.put("teachingid", obj[0]);
					m.put("subjectname", obj[1]);
					m.put("gradename", obj[2]);
					request.setAttribute("m"+i, m);
				}
				request.setAttribute("teachinglist", teachinglist);
				request.setAttribute("usertype", user.getUsertype());
				return mapping.findForward("userteaching");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//跳转到教学设置界面
	public ActionForward getSubjectInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String teachingid=Encode.nullToBlank(request.getParameter("teachingid"));
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				EduGradeInfoManager grademanager=(EduGradeInfoManager)getBean("eduGradeInfoManager");
				SysUserInfo user=usermanager.getSysUserInfo(userid);
				List subjectlist=grademanager.getAllSubjectByXueduanid(Integer.parseInt(user.getXueduan()));
				if(!"".equals(teachingid)){
					SysUserTeachingManager manager=(SysUserTeachingManager)getBean("sysUserTeachingManager");
					SysUserTeaching teaching=manager.getSysUserTeaching(teachingid);
					List gradelist=grademanager.getAllGradeBySubjectAndUnitType(teaching.getSubjectid(), user.getXueduan());
					request.setAttribute("teaching", teaching);
					request.setAttribute("gradelist", gradelist);
				}
				request.setAttribute("subjectlist", subjectlist);
				request.setAttribute("usertype", user.getUsertype());
				request.setAttribute("teachingid", teachingid);
				return mapping.findForward("subjectgrade");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//ajax获取年级信息
	public void getGradeByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String subjectid=Encode.nullToBlank(request.getParameter("subjectid"));
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				EduGradeInfoManager grademanager=(EduGradeInfoManager)getBean("eduGradeInfoManager");
				SysUserInfo user=usermanager.getSysUserInfo(userid);
				if(!"".equals(subjectid)){
					List<EduGradeInfo> gradelist=grademanager.getAllGradeBySubjectAndUnitType(Integer.parseInt(subjectid), user.getXueduan());
					StringBuffer result=new StringBuffer();
					for (EduGradeInfo grade : gradelist) {
						result.append("<a href=\"javascript:selectGrade("+grade.getGradeid()+")\" id=\"g"+grade.getGradeid()+"\">"+grade.getGradename()+"</a>");
					}
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(result.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ajax获取年级信息
	public void gradeExistsByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			int result=0;
			if(!"1".equals(userid)){
				String subjectid=Encode.nullToBlank(request.getParameter("subjectid"));
				String gradeid=Encode.nullToBlank(request.getParameter("gradeid"));
				SysUserTeachingManager manager=(SysUserTeachingManager)getBean("sysUserTeachingManager");
				boolean isExists=manager.gradeExists(userid, subjectid, gradeid);
				if(isExists){
					result=1;
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//跳转到教学设置界面
	public ActionForward saveTeaching(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String teachingid=Encode.nullToBlank(request.getParameter("teachingid"));
				String subjectid=Encode.nullToBlank(request.getParameter("subjectid"));
				String gradeid=Encode.nullToBlank(request.getParameter("gradeid"));
				SysUserTeachingManager manager=(SysUserTeachingManager)getBean("sysUserTeachingManager");
				boolean isExists=manager.gradeExists(userid, subjectid, gradeid);
				if("".equals(teachingid)){
					List list=manager.getSysUserTeachings(Integer.parseInt(userid));
					if(list.size()<5&&!isExists){
						SysUserTeaching teaching=new SysUserTeaching();
						teaching.setSubjectid(Integer.parseInt(subjectid));
						teaching.setGradeid(Integer.parseInt(gradeid));
						teaching.setUserid(Integer.parseInt(userid));
						manager.addSysUserTeaching(teaching);
					}
				}else{
					if(!isExists){
						SysUserTeaching teaching=manager.getSysUserTeaching(teachingid);
						teaching.setSubjectid(Integer.parseInt(subjectid));
						teaching.setGradeid(Integer.parseInt(gradeid));
						manager.updateSysUserTeaching(teaching);
					}
				}
				return setUserTeaching(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 
	  * 方法描述：取消微信绑定前
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-4-18 下午1:39:23
	 */
	public ActionForward beforeClearAttention(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager manager=(SysUserInfoManager)getBean("sysUserInfoManager");
				SysUserInfo user=manager.getSysUserInfo(userid);
				request.setAttribute("userinfo", user);
				return mapping.findForward("clearattention");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 
	  * 方法描述取消微信绑定
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-4-18 下午1:47:58
	 */
	public ActionForward clearAttention(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String code = request.getParameter("code");
			String accessjson = MpUtil.getAccessTokenByCode(MpUtil.APPID, MpUtil.APPSECRET, code);
			// 获取当前用户微信号
			String openid = MpUtil.getAccessTokenValue(accessjson, "openid");
			if (openid != null && !"".equals(openid)) {
					//根据openid去取是否绑定用户
					SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
					List list = suam.getSysUserAttentionByOpenids(openid);
					if(list !=null && list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							SysUserAttention model = (SysUserAttention) list.get(i);
								suam.delSysUserAttention(model);//删除绑定关系
						}
					}
					//欢迎页面，绑定
					return mapping.findForward("welcome");
			}else {
				//如果用户没有关注公众号，而直接用微信自身扫一扫扫描二维码时，无法获取到openid
				return mapping.findForward("tips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}