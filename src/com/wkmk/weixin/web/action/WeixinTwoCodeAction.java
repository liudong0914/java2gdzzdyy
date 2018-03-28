package com.wkmk.weixin.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.encrypt.DES;
import com.util.action.BaseAction;

/**
 * 微信自带扫一扫功能扫描龙门作业宝二维码图片跳转提示页面
 * @version 1.0
 */
public class WeixinTwoCodeAction extends BaseAction {

	/**
	 * 微信菜单扫一扫
	 * 所有需要使用平台的用户必须是注册用户
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//20161201改：调用微信原始扫一扫功能，跳转公众号里面，把二维码里面的参数携带即可，如果已注册，直接访问，未注册，跳转到登录界面供注册
			String requestQueryString = request.getQueryString();
			System.out.println("WeixinTwoCodeAction:"+requestQueryString);
			//参数加密处理，便于微信菜单携带数据
			request.setAttribute("requestQueryString", DES.getEncryptPwd(requestQueryString));
			
			return mapping.findForward("redirecttips");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("tips");
	}
}