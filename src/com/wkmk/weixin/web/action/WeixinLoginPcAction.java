package com.wkmk.weixin.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信扫码登录PC客户端
 * @version 1.0
 */
public class WeixinLoginPcAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			String accessjson = MpUtil.getAccessTokenByCode(MpUtil.APPID, MpUtil.APPSECRET, code);
			// 获取当前用户微信号
			String openid = MpUtil.getAccessTokenValue(accessjson, "openid");
			if (openid != null && !"".equals(openid)) {
				String randomcode = request.getParameter("randomcode");//登录二维码随机数，验证有效性
				String randomcodeValue = (String) CacheUtil.getObject("randomcode_" + randomcode);
				if(randomcodeValue != null && !"".equals(randomcodeValue) && randomcodeValue.equals(randomcode)){
					//根据openid去取是否绑定用户
					SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
					SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
					if(attention != null && attention.getUserid().intValue() > 1){
						//验证成功，需要点击页面上的确定授权登录按钮，有效期一分钟，如果一分钟不点击确定按钮，过后再点击确定按钮也会提示登录失败
						CacheUtil.putObject("validation_success_" + randomcode, randomcode, 60);
						
						request.setAttribute("userid", attention.getUserid());
						request.setAttribute("randomcode", randomcode);
						return mapping.findForward("success");
					}else {
						//关注了公众号，但没有绑定账号
						return mapping.findForward("register");
					}
				}else {
					String url = request.getRequestURL().toString() + "?" + request.getQueryString();
					Map<String, String> jsticket = MpUtil.sign(url);
					request.setAttribute("jsticket", jsticket);
					
					//验证码已失效，请重新扫描（需要pc端页面刷新二维码）
					return mapping.findForward("failure");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("null");
	}
}