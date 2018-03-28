package com.wkmk.weixin.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.date.DateTime;
import com.util.encrypt.Base64Utils;
import com.util.encrypt.DES;
import com.util.string.Encode;
import com.util.action.BaseAction;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.util.common.EmojiFilter;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 调用微信自身扫一扫功能，扫二维码跳转至此
 * @version 1.0
 */
public class WeixinScanTwoCodeAction extends BaseAction {

	/**
	 * 微信菜单跳转
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			String accessjson = MpUtil.getAccessTokenByCode(MpUtil.APPID, MpUtil.APPSECRET, code);
			// 根据菜单模式决定跳转页面
			//String keycode = request.getParameter("state");
			// 获取当前用户微信号
			String openid = MpUtil.getAccessTokenValue(accessjson, "openid");
			//System.out.println("WeixinScanTwoCodeActionOpenid01:"+openid);
			if (openid != null && !"".equals(openid)) {
				//根据openid去取是否绑定用户
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention userAttention = suam.getSysUserAttentionByOpenid(openid);
				//System.out.println("WeixinScanTwoCodeActionuserAttention:"+userAttention);
				String userid = null;
				if(userAttention != null){
					userid = userAttention.getUserid().toString();
				}else {
					//获取关注公众号客户端用户信息
					String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
					String userjson = MpUtil.getUserInfo0(access_token, openid);
					//System.out.println("获取微信信息WeixinScanTwoCodeActionuserjson:"+userjson);
					userAttention = new SysUserAttention();
					String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
					//System.out.println("去表情前："+nickname+"-------");
					userAttention.setNickname(EmojiFilter.filterEmoji(nickname.trim()));
					//System.out.println("去表情后："+EmojiFilter.filterEmoji(nickname.trim())+"-------");
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
				
				if(!"1".equals(userid)){
					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
					SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
					//记录日志内容
					String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")调用了非公众号的扫一扫功能查看了二维码内容";
				    addLog(request,  descript, sysUserInfo);
				}else {
					SysUserInfo sysUserInfo = new SysUserInfo();
					sysUserInfo.setUserid(1);
					sysUserInfo.setUnitid(1);
					//记录日志内容
					String descript = "用户调用了非公众号的扫一扫功能查看了二维码内容";
				    addLog(request,  descript, sysUserInfo);
				}
				
				//模拟WeixinServiceAction.java扫一扫跳转
				//获取关注公众号客户端用户信息
				String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
				String userjson = MpUtil.getUserInfo0(access_token, openid);
				//System.out.println("json:"+userjson);
				if(userjson == null || "".equals(userjson)){
					return mapping.findForward("tips");
				}
				//通过url携带的用户昵称参数要做base64处理
				StringBuffer userUrlBuffer = new StringBuffer();
				String sNickNameEncoded = Base64Utils.base64Encoder(MpUtil.getUserInfoValue(userjson, "nickname").trim());
				userUrlBuffer.append("&nickname=").append(sNickNameEncoded);
				//System.out.println("原昵称Orign="+MpUtil.getUserInfoValue(userjson, "nickname"));
				//System.out.println("原昵称Encoded="+sNickNameEncoded);
				//System.out.println("原昵称Decoded="+Base64Utils.base64Decoder(sNickNameEncoded));

//				String a="test";
//				System.out.println("加密"+Base64Utils.base64Encoder(a));
//				String b=Base64Utils.base64Encoder(a);
//				System.out.println("解密"+b);
				userUrlBuffer.append("&headimgurl=").append(MpUtil.getUserInfoValue(userjson, "headimgurl"));
				userUrlBuffer.append("&sex=").append(MpUtil.getUserInfoValue(userjson, "sex"));
				userUrlBuffer.append("&openid=").append(openid);
				String userUrlStr = userUrlBuffer.toString();
				
				String requestQueryString = Encode.nullToBlank(request.getParameter("requestQueryString"));
				String url = "/weixinScanQRCode.app?method=scan&userid=" + DES.getEncryptPwd(userid) + "&scanResult=" + DES.getDecryptPwd(requestQueryString) + userUrlStr;
				
				response.sendRedirect(url);
				return null;
			}else {
				//如果用户没有关注公众号，而直接用微信自身扫一扫扫描二维码时，无法获取到openid
				return mapping.findForward("tips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("tips");
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
}