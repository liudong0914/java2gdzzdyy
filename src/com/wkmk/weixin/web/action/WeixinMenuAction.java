package com.wkmk.weixin.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.wkmk.util.common.EmojiFilter;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;

/**
 * 微信服务菜单接口
 * @version 1.0
 */
public class WeixinMenuAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			System.out.println("code="+code);
			String accessjson = MpUtil.getAccessTokenByCode(MpUtil.APPID, MpUtil.APPSECRET, code);
			System.out.println("APPID="+MpUtil.APPID);
			System.out.println("APPSECRET="+MpUtil.APPSECRET);
			System.out.println("code="+code);
			// 根据菜单模式决定跳转页面
			String keycode = request.getParameter("state");
			System.out.println("keycode="+keycode);
			// 获取当前用户微信号
			String openid = MpUtil.getAccessTokenValue(accessjson, "openid");
			System.out.println("openid="+openid);
			if (openid != null && !"".equals(openid)) {
				//根据openid去取是否绑定用户
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention userAttention = suam.getSysUserAttentionByOpenid(openid);
				String userid = null;
				if(userAttention != null){
					userid = userAttention.getUserid().toString();
					if(userAttention.getHeadimgurl() == null || "".equals(userAttention.getHeadimgurl())){
						String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
						String userjson = MpUtil.getUserInfo0(access_token, openid);
						userAttention.setSex(MpUtil.getUserInfoValue(userjson, "sex"));
						userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
						userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
						userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
						userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
						userAttention.setHeadimgurl(MpUtil.getUserInfoValue(userjson, "headimgurl"));
						suam.updateSysUserAttention(userAttention);
					}
				}else {
					//获取关注公众号客户端用户信息
					String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
					String userjson = MpUtil.getUserInfo0(access_token, openid);
					userAttention = new SysUserAttention();
					String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
					userAttention.setNickname(EmojiFilter.filterEmoji(nickname));
					userAttention.setOpenid(openid);
					userAttention.setSex(MpUtil.getUserInfoValue(userjson, "sex"));
					userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
					userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
					userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
					userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
					userAttention.setHeadimgurl(MpUtil.getUserInfoValue(userjson, "headimgurl"));
					userAttention.setCreatedate(DateTime.getDate());
					userAttention.setDeletedate("");
					userAttention.setRegistertime("");
					userAttention.setUnregistertime("");
					userAttention.setUserid(1);//默认游客用户
					userAttention.setUnitid(1);
					suam.addSysUserAttention(userAttention);
				}
				if(userid == null){
					userid = "1";
				}
				
				if(keycode != null && !"".equals(keycode)){
					MpUtil.setUserid(request, userid);
					if("1".equals(userid)){
						String access_token = MpUtil.getAccessTokenValue(accessjson, "access_token");
						String userjson = MpUtil.getUserInfo(access_token, openid);
						String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
						String headimgurl = MpUtil.getUserInfoValue(userjson, "headimgurl");
						String sex = MpUtil.getUserInfoValue(userjson, "sex");
						request.setAttribute("nickname", nickname);
						request.setAttribute("headimgurl", headimgurl);
						request.setAttribute("sex", sex);
						request.setAttribute("openid", openid);
						
						return mapping.findForward("login");
					}else {
						//通过日志记录用户菜单行为点击事件
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
								if("K1001_1000".equals(keycode)){//首页
							    	String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")通过微信菜单《首页》进入了平台";
								    addLog(request,  descript, sysUserInfo);
								    
								    String redirecturl = "/weixinAccountIndex.app?method=index&userid=" + DES.getEncryptPwd(userid);
									response.sendRedirect(redirecturl);
									return null;
							    }else if("K1002_1000".equals(keycode)){
							    	String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")通过微信菜单《课程》进入了平台";
								    addLog(request,  descript, sysUserInfo);
								    
								    String redirecturl = "/weixinCourse.app?method=getCourseList&userid=" + DES.getEncryptPwd(userid);
									response.sendRedirect(redirecturl);
									return null;
							    }else if("K1003_1000".equals(keycode)){
							    	String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")通过微信菜单《个人中心》进入了平台";
								    addLog(request,  descript, sysUserInfo);
								    
								    String redirecturl = "/weixinAccountIndex.app?method=getPersonalCenter&userid=" + DES.getEncryptPwd(userid);
									response.sendRedirect(redirecturl);
									return null;
							    }else {
							    	String redirecturl = "/weixinAccountIndex.app?method=index&userid=" + DES.getEncryptPwd(userid);
									response.sendRedirect(redirecturl);
									return null;
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
					    	String access_token = MpUtil.getAccessTokenValue(accessjson, "access_token");
							String userjson = MpUtil.getUserInfo(access_token, openid);
							String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
							String headimgurl = MpUtil.getUserInfoValue(userjson, "headimgurl");
							String sex = MpUtil.getUserInfoValue(userjson, "sex");
							request.setAttribute("nickname", nickname);
							request.setAttribute("headimgurl", headimgurl);
							request.setAttribute("sex", sex);
							request.setAttribute("openid", openid);
							
							return mapping.findForward("login");
						}
					}
				}else {
					return mapping.findForward("subscribetips");
				}
			}else {
				return mapping.findForward("subscribetips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/*
	 * 系统日志记录
	 */
	private void addLog(HttpServletRequest request, String descript, SysUserInfo sysUserInfo) {
  		SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
	    Integer unitid = sysUserInfo.getUnitid();
	
	    SysUserLog model = new SysUserLog();
	    model.setCreatedate(DateTime.getDate());
	    model.setDescript(descript);
	    model.setUserip(IpUtil.getIpAddr(request));
	    model.setSysUserInfo(sysUserInfo);
	    model.setUnitid(unitid);
	    model.setLogtype("0");
	    
	    manager.addSysUserLog(model);
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
}