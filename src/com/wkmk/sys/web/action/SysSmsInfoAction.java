package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysSmsInfo;
import com.wkmk.sys.service.SysSmsInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysSmsInfoActionForm;
import com.wkmk.util.sms.SmsSend;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 手机短信</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysSmsInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String mobile=Encode.nullToBlank(httpServletRequest.getParameter("mobile"));
		String username=Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String state=Encode.nullToBlank(httpServletRequest.getParameter("state"));
		String type=Encode.nullToBlank(httpServletRequest.getParameter("type"));
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = manager.getPageSysSmsInfos(mobile,username,state,type,"", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("mobile", mobile);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("state", state);
		httpServletRequest.setAttribute("type", type);
		return actionMapping.findForward("list");
	}

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysSmsInfo model = new SysSmsInfo();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysSmsInfoActionForm form = (SysSmsInfoActionForm)actionForm;
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysSmsInfo model = form.getSysSmsInfo();
				manager.addSysSmsInfo(model);
				addLog(httpServletRequest,"增加了一个手机短信");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysSmsInfo model = manager.getSysSmsInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysSmsInfoActionForm form = (SysSmsInfoActionForm)actionForm;
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysSmsInfo model = form.getSysSmsInfo();
				manager.updateSysSmsInfo(model);
				addLog(httpServletRequest,"修改了一个手机短信");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *批量删除
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysSmsInfo(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	public ActionForward smsRetransmission (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		try {
			String smsid=Encode.nullToBlank(httpServletRequest.getParameter("smsid"));
			SysSmsInfo model=manager.getSysSmsInfo(smsid);
			if("2".equals(model.getState())){
				String state=SmsSend.send(model.getMobile(), model.getContent())?"1":"2";
				model.setState(state);
				manager.updateSysSmsInfo(model);
			}
		}catch (Exception e){
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	public ActionForward smsDetail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysSmsInfoManager manager = (SysSmsInfoManager)getBean("sysSmsInfoManager");
		SysUserInfoManager usermanager = (SysUserInfoManager)getBean("sysUserInfoManager");
		try {
			String smsid=Encode.nullToBlank(httpServletRequest.getParameter("smsid"));
			SysSmsInfo model=manager.getSysSmsInfo(smsid);
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("username", usermanager.getSysUserInfo(model.getUserid()).getUsername());
		}catch (Exception e){
		}
		return actionMapping.findForward("detail");
	}
	
}