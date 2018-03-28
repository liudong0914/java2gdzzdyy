package com.wkmk.sys.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.string.Encode;

public class SysAdminLoginAction extends BaseAction {

	/**
	 * 后台登录
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String login = Encode.nullToBlank(httpServletRequest.getParameter("login"));
		if("1".equals(login)){
			return actionMapping.findForward("index1");
		}else {
			return actionMapping.findForward("index");
		}
	}
}
