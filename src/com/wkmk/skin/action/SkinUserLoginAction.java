package com.wkmk.skin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 前台登陆
 */
public class SkinUserLoginAction extends BaseAction {

	public ActionForward slogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));
		request.setAttribute("redirecturl", redirecturl);
		String user = getCookie(request);
		if (user != null && !"".equals(user)) {
			String[] users = user.split("-;-");
			request.setAttribute("loginname", users[0]);
			request.setAttribute("password", "******");
			request.setAttribute("hiddenpwd", DES.getEncryptPwd(users[1]));
			request.setAttribute("r", "1");
		} else {
			request.setAttribute("loginname", "");
			request.setAttribute("password", "");
			request.setAttribute("hiddenpwd", "");
			request.setAttribute("r", "");
		}
		String mappingurl = "/sys/login2/login.jsp";
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}
	
	public ActionForward slogout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));
		String mappingurl = "/index.html";
		ActionForward gotourl = null;
		HttpSession session = request.getSession();
		if(redirecturl != null && !"".equals(redirecturl) && !redirecturl.contains(".do")){
			mappingurl = redirecturl;
			gotourl = new ActionForward(mappingurl);
			gotourl.setPath(mappingurl);
			gotourl.setRedirect(true);
		}else {
			mappingurl = "/index.html";
			gotourl = new ActionForward(mappingurl);
			gotourl.setPath(mappingurl);
			gotourl.setRedirect(true);
		}
		
		session.removeAttribute("s_userid");
		session.removeAttribute("s_sysuserinfo");
		session.removeAttribute("s_sysuserinfodetail");
		session.removeAttribute("s_unitid");
		session.removeAttribute("s_sysunitinfo");
		session.removeAttribute("isAdmin");
		
		return gotourl;
	}

	/**
	 * 用户登录
	 */
	public ActionForward sklogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/sys/login2/login.jsp";
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);

		String redirecturl = Encode.nullToBlank(request.getParameter("redirecturl"));
		request.setAttribute("redirecturl", redirecturl);

		String loginname = Encode.nullToBlank(request.getParameter("loginname"));
		String password = Encode.nullToBlank(request.getParameter("password"));
		HttpSession session = request.getSession();
		if (loginname == null || "".equals(loginname)) {
			request.setAttribute("promptinfo", "用户名不能为空!");
			return slogin(mapping, form, request, response);
		} else if (password == null || "".equals(password)) {
			request.setAttribute("promptinfo", "密码不能为空!");
			return slogin(mapping, form, request, response);
		}

		String validate = Encode.nullToBlank(request.getParameter("validate"));
		String randomcode = (String) session.getAttribute("randomcode");
		if (validate == null || !validate.toLowerCase().equals(randomcode.toLowerCase())) {
			request.setAttribute("promptinfo", "验证码不对,请重试!");
			return slogin(mapping, form, request, response);
		}

		try {
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = manager.getUserInfoByLoginName(loginname);
			if (userInfo != null) {
				if ("******".equals(password)) {
					String hiddenpwd = Encode.nullToBlank(request.getParameter("hiddenpwd"));
					password = DES.getDecryptPwd(hiddenpwd);
				}
				String md5password = MD5.getEncryptPwd(password);
				if (!md5password.equals(userInfo.getPassword())) { // 密码不对
					request.setAttribute("promptinfo", "您输入的密码不正确，请重输!");
					return slogin(mapping, form, request, response);
				}
				
				//检查当前用户是否被禁用，查看禁用原因
				SysUserDisable userDisable = null;
				if("2".equals(userInfo.getStatus())){
					userDisable = getSysUserDisable(userInfo.getUserid());
					if(userDisable != null){
						String enddate = userDisable.getEndtime();//如果已到禁用截止时间，自动启用
						String curdate = DateTime.getDate();
						if(DateTime.getCompareToDate(enddate, curdate) <= 0){
							userInfo.setStatus("1");//启用
							manager.updateSysUserInfo(userInfo);
						}
					}
				}
				
				if ("0".equals(userInfo.getStatus())) { // 未审核
					request.setAttribute("promptinfo", "您的账号还没审核，请与管理员联系!");
					return gotourl;
				} else if ("2".equals(userInfo.getStatus())) { // 被禁用
					if(userDisable != null){
						request.setAttribute("promptinfo", userDisable.getDescript());
					}else {
						request.setAttribute("promptinfo", "您的账号已被禁用，请与管理员联系!");
					}
					return gotourl;
				} else if ("3".equals(userInfo.getStatus())) { // 被删除
					request.setAttribute("promptinfo", "您的账号已被删除，请与管理员联系!");
					return gotourl;
				} else {
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
						List list = ecum.getEduCourseUsers(condition, "courseuserid asc", 0);
						if(list !=null && list.size()>0){
							for(int i=0;i<list.size();i++){
								EduCourseUser ecu = (EduCourseUser) list.get(i);
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
					// 选中记住用户信息，重新设置cookie信息
					setCookie(loginname, password, request, response);
					
					setSession(session, userInfo);
	    	        
					// 加入日志
					addLog(request, "用户" + userInfo.getLoginname() +"(" + userInfo.getUsername()+")登录成功!");
					if(redirecturl != null && !"".equals(redirecturl)){
						response.sendRedirect(redirecturl);
						return null;
					}else {
//						if("1".equals(userInfo.getUsertype()) || "2".equals(userInfo.getUsertype())){
//							response.sendRedirect("/pcenter.do?method=index");
//							return null;
//						}else {
//							response.sendRedirect("/main.action");
//							return null;
//						}
					    if(!"0".equals(userInfo.getUsertype())){
					        //课程个人中心
	                        response.sendRedirect("/courseCenter.do?method=index");
	                        return null;
                      }else {
                          response.sendRedirect("/main.action");
                          return null;
                      }
					  
					}
				}
			} else {
				request.setAttribute("promptinfo", "输入的用户名不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("promptinfo", "登录出现错误,请检查后重试!");
			return gotourl;
		}
		return gotourl;
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
	
	private void setSession(HttpSession session, SysUserInfo userInfo){
		Integer s_unitid = userInfo.getUnitid();
		
		//更新用户扩展信息
		Integer userid = userInfo.getUserid();
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		SysUserInfoDetail sysUserInfoDetail = suidm.getSysUserInfoDetail(userid);
		
		// 更新登录相关信息
		sysUserInfoDetail.setLastlogin(DateTime.getDate());
		sysUserInfoDetail.setLogintimes(new Integer(sysUserInfoDetail.getLogintimes().intValue() + 1));
		suidm.updateSysUserInfoDetail(sysUserInfoDetail);
		
		session.setAttribute("s_sysuserinfo", userInfo);
		session.setAttribute("s_sysuserinfodetail", sysUserInfoDetail);
		session.setAttribute("s_userid", userid);
		session.setAttribute("pagesize", "10");
		
		// 获取单位信息
		SysUnitInfoManager unitInfoManager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
		SysUnitInfo sui = unitInfoManager.getSysUnitInfo(s_unitid);
		session.setAttribute("s_sysunitinfo", sui);
		session.setAttribute("s_unitid", s_unitid);
		
		//判断当前用户是否为当前系统的产品管理员，或是单位管理员【学校管理员】
        String isAdmin = "0";
        session.setAttribute("isAdmin", isAdmin);
	}
	
	private void setCookie(String loginname, String password, HttpServletRequest request, HttpServletResponse response) {
		String r = Encode.nullToBlank(request.getParameter("r"));
		if ("1".equals(r)) {
			String loginuservalue = DES.getEncryptPwd(loginname + "-;-" + password);
			Cookie cookies[] = request.getCookies();
			Cookie c = null;
			String temp = null;
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equals("cookie_user")) {
					c.setValue(loginuservalue);
					c.setMaxAge(60 * 60 * 24 * 30);
					response.addCookie(c);
					temp = "1";
					break;
				}
			}
			if (!"1".equals(temp)) {
				c = new Cookie("cookie_user", loginuservalue);
				c.setMaxAge(60 * 60 * 24 * 30);
				c.setPath("/");
				response.addCookie(c);
			}
		} else {
			Cookie cookies[] = request.getCookies();
			Cookie c = null;
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equals("cookie_user")) {
					c.setMaxAge(0);
					response.addCookie(c);
					break;
				}
			}
		}
	}

	private String getCookie(HttpServletRequest request) {
		String user = "";
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			Cookie c = null;
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equals("cookie_user")) {
					user = DES.getDecryptPwd(c.getValue());
					break;
				}
			}
		}
		return user;
	}

	//=============================================二维码扫码登录====================================
	private String getBaiduShortUrl(String longUrl){
		String uriAPI = "http://dwz.cn/create.php";
		/*建立HTTPost对象*/
		HttpPost httpRequest = new HttpPost(uriAPI);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
		params.add(new BasicNameValuePair("url", longUrl)); 
		
		try {
			/* 添加请求参数到请求对象*/
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/*发送请求并等待响应*/
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			/*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200) { 
				/*读返回数据*/
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				JSONObject jsonObj = JSONObject.fromObject(strResult);
				if(jsonObj != null){
					String shortUrl = jsonObj.getString("tinyurl");
					return shortUrl;
				}
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取生成二维码的连接地址
	 */
	public ActionForward getQrCodeStr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			String randomcode = MD5.getEncryptPwd(DateTime.getDate("yyyyMMddHHmmssS") + RandomUtils.nextLong());
			String longUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + MpUtil.APPID + "&redirect_uri=" + MpUtil.HOMEPAGE + "/weixinLoginPc.app%3Frandomcode=" + randomcode + "&response_type=code&scope=snsapi_userinfo&state=account#wechat_redirect";
			//调用百度api生成短连接地址
			String shortUrl = getBaiduShortUrl(longUrl);//短连接地址，生成二维码用，识别快
			
			result = shortUrl + ";" + randomcode;
			
			//同时把随机数放到缓存中，供微信客户端验证
			CacheUtil.putObject("randomcode_" + randomcode, randomcode, 60);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(result != null && !"".equals(result)){
				out.print(result);
			}else {
				out.print("");
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取客户端扫码状态
	 */
	public ActionForward getScanStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String status = null;
		try {
			String randomcode = Encode.nullToBlank(request.getParameter("randomcode"));
			
			String randomcodeValue = (String) CacheUtil.getObject("randomcode_" + randomcode);
			if(randomcodeValue == null || "".equals(randomcodeValue)){
				status = "0";//二维码已过期
			}
			
			String validationValue = (String) CacheUtil.getObject("validation_success_" + randomcode);
			if(randomcode.equals(validationValue)){
				status = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(status != null && !"".equals(status)){
				out.print(status);
			}else {
				out.print("");
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取客户端扫码成功后点击确定按钮状态
	 */
	public ActionForward getLoginStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String userid = null;
		try {
			String randomcode = Encode.nullToBlank(request.getParameter("randomcode"));
			
			String validationValue = (String) CacheUtil.getObject("validation_success_" + randomcode);
			if(validationValue == null || "".equals(validationValue)){
				userid = "0";//扫码成功，但没有点确认按钮，时间过期
			}
			
			String loginValue = (String) CacheUtil.getObject("login_success_" + randomcode);;
			if(loginValue != null && !"".equals(loginValue)){
				//加密传递
				userid = DES.getEncryptPwd(loginValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(userid != null && !"".equals(userid)){
				out.print(userid);
			}else {
				out.print("");
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 扫码自动登录
	 */
	public ActionForward scanAutoLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/sys/login2/login.jsp";
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);

		try {
			String des_userid = Encode.nullToBlank(request.getParameter("userid"));
			des_userid = DES.getDecryptPwd(des_userid);
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = manager.getSysUserInfo(des_userid);
			if (userInfo != null) {
				//检查当前用户是否被禁用，查看禁用原因
				SysUserDisable userDisable = null;
				if("2".equals(userInfo.getStatus())){
					userDisable = getSysUserDisable(userInfo.getUserid());
					if(userDisable != null){
						String enddate = userDisable.getEndtime();//如果已到禁用截止时间，自动启用
						String curdate = DateTime.getDate();
						if(DateTime.getCompareToDate(enddate, curdate) <= 0){
							userInfo.setStatus("1");//启用
							manager.updateSysUserInfo(userInfo);
						}
					}
				}
				
				if ("0".equals(userInfo.getStatus())) { // 未审核
					request.setAttribute("promptinfo", "您的账号还没审核，请与管理员联系!");
					return gotourl;
				} else if ("2".equals(userInfo.getStatus())) { // 被禁用
					if(userDisable != null){
						request.setAttribute("promptinfo", userDisable.getDescript());
					}else {
						request.setAttribute("promptinfo", "您的账号已被禁用，请与管理员联系!");
					}
					return gotourl;
				} else if ("3".equals(userInfo.getStatus())) { // 被删除
					request.setAttribute("promptinfo", "您的账号已被删除，请与管理员联系!");
					return gotourl;
				} else {
					HttpSession session = request.getSession();
					setSession(session, userInfo);
	    	        
					// 加入日志
					addLog(request, "用户" + userInfo.getLoginname() +"(" + userInfo.getUsername()+")登录成功!");
					if("1".equals(userInfo.getUsertype()) || "2".equals(userInfo.getUsertype())){
						//response.sendRedirect("/pcenter.do?method=index");
						response.sendRedirect("/courseCenter.do?method=index");
						return null;
					}else {
						response.sendRedirect("/main.action");
						return null;
					}
				}
			} else {
				request.setAttribute("promptinfo", "输入的用户不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("promptinfo", "登录出现错误,请检查后重试!");
			return gotourl;
		}
		return gotourl;
	}
	
	/**
	 * 教师资格认证地址跳转
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/sys/login2/login.jsp";
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		
		try {
			String key = Encode.nullToBlank(request.getParameter("key"));//有效期5分钟
			if(!"".equals(key)){
				Integer userid = (Integer) CacheUtil.getObject(key);
				if(userid != null && userid.intValue() > 1){
					SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
					SysUserInfo userInfo = manager.getSysUserInfo(userid);
					if(userInfo != null){
						//检查当前用户是否被禁用，查看禁用原因
						SysUserDisable userDisable = null;
						if("2".equals(userInfo.getStatus())){
							userDisable = getSysUserDisable(userInfo.getUserid());
							if(userDisable != null){
								String enddate = userDisable.getEndtime();//如果已到禁用截止时间，自动启用
								String curdate = DateTime.getDate();
								if(DateTime.getCompareToDate(enddate, curdate) <= 0){
									userInfo.setStatus("1");//启用
									manager.updateSysUserInfo(userInfo);
								}
							}
						}
						
						if ("0".equals(userInfo.getStatus())) { // 未审核
							request.setAttribute("promptinfo", "您的账号还没审核，请与管理员联系!");
							return gotourl;
						} else if ("2".equals(userInfo.getStatus())) { // 被禁用
							if(userDisable != null){
								request.setAttribute("promptinfo", userDisable.getDescript());
							}else {
								request.setAttribute("promptinfo", "您的账号已被禁用，请与管理员联系!");
							}
							return gotourl;
						} else if ("3".equals(userInfo.getStatus())) { // 被删除
							request.setAttribute("promptinfo", "您的账号已被删除，请与管理员联系!");
							return gotourl;
						} else {
							HttpSession session = request.getSession();
							setSession(session, userInfo);
							
							// 加入日志
							addLog(request, "用户" + userInfo.getLoginname() +"(" + userInfo.getUsername()+")认证登录成功!");
							if("1".equals(userInfo.getUsertype())){
								response.sendRedirect("/pcenter.do?method=main&mark=3");
								return null;
							}else {
								response.sendRedirect("/index.htm");
								return null;
							}
						}
					}else {
						//当前教师资格认证地址已失效，请通过龙门作业宝微信公众号重新获取。
						request.setAttribute("promptinfo", "认证地址失效，请扫码登录申请认证!");
						return gotourl;
					}
				}else {
					//当前教师资格认证地址已失效，请通过龙门作业宝微信公众号重新获取。
					request.setAttribute("promptinfo", "认证地址失效，请扫码登录申请认证!");
					return gotourl;
				}
			}else {
				//当前教师资格认证地址已失效，请通过龙门作业宝微信公众号重新获取。
				request.setAttribute("promptinfo", "认证地址失效，请扫码登录申请认证!");
				return gotourl;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("promptinfo", "认证失效!");
		return gotourl;
	}
	
	/**
	 * 检查用户是否登录
	 */
	public ActionForward checkLogin(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
		String str = "0";//0未登录，1已登录
		if(sysUserInfo != null && sysUserInfo.getUserid() != null){
			str = "1";
		}
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(str);
		} catch (IOException ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}
}
