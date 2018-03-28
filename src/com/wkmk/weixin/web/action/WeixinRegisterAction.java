package com.wkmk.weixin.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.date.DateTime;
import com.util.action.BaseAction;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.util.common.EmojiFilter;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信服务账户绑定接口
 * @version 1.0
 */
public class WeixinRegisterAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			String accessjson = MpUtil.getAccessTokenByCode(MpUtil.APPID, MpUtil.APPSECRET, code);
			String access_token = MpUtil.getAccessTokenValue(accessjson, "access_token");
			//String access1=MpUtil.getRefreshAccessToken(MpUtil.APPID,MpUtil.getAccessTokenValue(access_token, "refresh_token"));
			// 根据菜单模式决定跳转页面
			//String keycode = request.getParameter("state");
			// 获取当前用户微信号
			String openid = MpUtil.getAccessTokenValue(accessjson, "openid");
			if (openid != null && !"".equals(openid)) {
				String userjson = MpUtil.getUserInfo(access_token, openid);
				String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
				String headimgurl = MpUtil.getUserInfoValue(userjson, "headimgurl");
				String sex = MpUtil.getUserInfoValue(userjson, "sex");
				request.setAttribute("nickname", nickname);
				request.setAttribute("headimgurl", headimgurl);
				request.setAttribute("sex", sex);
				request.setAttribute("openid", openid);
				
				//根据openid去取是否绑定用户，需要存储用户昵称和头像
				SysUserAttentionManager manager = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention userAttention = manager.getSysUserAttentionByOpenid(openid);
				if(userAttention == null){
					userAttention = new SysUserAttention();
					userAttention.setNickname(EmojiFilter.filterEmoji(nickname));
					userAttention.setOpenid(openid);
					userAttention.setSex(sex);
					userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
					userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
					userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
					userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
					userAttention.setHeadimgurl(headimgurl);
					userAttention.setCreatedate(DateTime.getDate());
					userAttention.setDeletedate("");
					userAttention.setRegistertime("");
					userAttention.setUnregistertime("");
					userAttention.setUserid(1);//默认游客用户
					userAttention.setUnitid(1);
					manager.addSysUserAttention(userAttention);
				}else {
					userAttention.setNickname(EmojiFilter.filterEmoji(nickname));
					userAttention.setSex(sex);
					userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
					userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
					userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
					userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
					userAttention.setHeadimgurl(headimgurl);
					manager.updateSysUserAttention(userAttention);
				}
				
				return mapping.findForward("login");
			}else {
				return mapping.findForward("subscribetips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}