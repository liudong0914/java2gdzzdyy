package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;
import com.wkmk.sys.service.SysUserGiveMoneyActivityManager;
import com.wkmk.sys.web.form.SysUserGiveMoneyActivityActionForm;

/**
 *<p>Description: 用户赠送学币活动</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyActivityAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserGiveMoneyActivityManager manager = (SysUserGiveMoneyActivityManager)getBean("sysUserGiveMoneyActivityManager");
		SysUserGiveMoneyActivity model = new SysUserGiveMoneyActivity();
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		httpServletRequest.setAttribute("name", name);
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		httpServletRequest.setAttribute("type", type);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "name", "like", "%"+name+"%");
		SearchCondition.addCondition(condition, "type", "=", type);
		String sorderindex = "activityid desc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserGiveMoneyActivitys(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("pagelist", page);
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
		SysUserGiveMoneyActivity model = new SysUserGiveMoneyActivity();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		model.setType("1");
		model.setStatus("1");
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
		SysUserGiveMoneyActivityActionForm form = (SysUserGiveMoneyActivityActionForm)actionForm;
		SysUserGiveMoneyActivityManager manager = (SysUserGiveMoneyActivityManager)getBean("sysUserGiveMoneyActivityManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserGiveMoneyActivity model = form.getSysUserGiveMoneyActivity();
				manager.addSysUserGiveMoneyActivity(model);
				addLog(httpServletRequest,"增加了一个用户赠送学币活动");
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
		SysUserGiveMoneyActivityManager manager = (SysUserGiveMoneyActivityManager)getBean("sysUserGiveMoneyActivityManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		try {
			SysUserGiveMoneyActivity model = manager.getSysUserGiveMoneyActivity(objid);
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
		SysUserGiveMoneyActivityActionForm form = (SysUserGiveMoneyActivityActionForm)actionForm;
		SysUserGiveMoneyActivityManager manager = (SysUserGiveMoneyActivityManager)getBean("sysUserGiveMoneyActivityManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserGiveMoneyActivity model = form.getSysUserGiveMoneyActivity();
				manager.updateSysUserGiveMoneyActivity(model);
				addLog(httpServletRequest,"修改了一个用户赠送学币活动");
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
		SysUserGiveMoneyActivityManager manager = (SysUserGiveMoneyActivityManager)getBean("sysUserGiveMoneyActivityManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysUserGiveMoneyActivity(checkids[i]);
			addLog(httpServletRequest, "删除了一个用户赠送学币活动");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}