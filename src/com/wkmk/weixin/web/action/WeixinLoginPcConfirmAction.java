package com.wkmk.weixin.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.string.Encode;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信扫码确认登录PC客户端
 * @version 1.0
 */
public class WeixinLoginPcConfirmAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = Encode.nullToBlank(request.getParameter("userid"));
			String randomcode = Encode.nullToBlank(request.getParameter("randomcode"));
			String type = Encode.nullToBlank(request.getParameter("type"));//1确认登录，0取消登录
			
			if(!"1".equals(type)){
				CacheUtil.deleteObject("validation_success_" + randomcode);//立刻失效
				
				String url = request.getRequestURL().toString() + "?" + request.getQueryString();
				Map<String, String> jsticket = MpUtil.sign(url);
				request.setAttribute("jsticket", jsticket);
				
				return mapping.findForward("cancel");
			}
			
			if(!"".equals(randomcode)){
				String randomcodeValue = (String) CacheUtil.getObject("validation_success_" + randomcode);
				if(randomcode.equals(randomcodeValue)){
					//pc端通过js定时监控缓存中此值，有效则自动跳转登录成功后的个人中心界面
					CacheUtil.putObject("login_success_" + randomcode, userid, 60);
					
					return mapping.findForward("ok");
				}else {
					String url = request.getRequestURL().toString() + "?" + request.getQueryString();
					Map<String, String> jsticket = MpUtil.sign(url);
					request.setAttribute("jsticket", jsticket);
					
					return mapping.findForward("cancel");
				}
			}else {
				String url = request.getRequestURL().toString() + "?" + request.getQueryString();
				Map<String, String> jsticket = MpUtil.sign(url);
				request.setAttribute("jsticket", jsticket);
				
				return mapping.findForward("cancel");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}