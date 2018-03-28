package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsSimilarWatch;
import com.wkmk.tk.service.TkQuestionsSimilarWatchManager;
import com.wkmk.tk.web.form.TkQuestionsSimilarWatchActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 举一反三观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilarWatchAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsSimilarWatchManager manager = (TkQuestionsSimilarWatchManager)getBean("tkQuestionsSimilarWatchManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkQuestionsSimilarWatchs(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		TkQuestionsSimilarWatch model = new TkQuestionsSimilarWatch();
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
		TkQuestionsSimilarWatchActionForm form = (TkQuestionsSimilarWatchActionForm)actionForm;
		TkQuestionsSimilarWatchManager manager = (TkQuestionsSimilarWatchManager)getBean("tkQuestionsSimilarWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsSimilarWatch model = form.getTkQuestionsSimilarWatch();
				manager.addTkQuestionsSimilarWatch(model);
				addLog(httpServletRequest,"增加了一个举一反三观看记录");
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
		TkQuestionsSimilarWatchManager manager = (TkQuestionsSimilarWatchManager)getBean("tkQuestionsSimilarWatchManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkQuestionsSimilarWatch model = manager.getTkQuestionsSimilarWatch(objid);
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
		TkQuestionsSimilarWatchActionForm form = (TkQuestionsSimilarWatchActionForm)actionForm;
		TkQuestionsSimilarWatchManager manager = (TkQuestionsSimilarWatchManager)getBean("tkQuestionsSimilarWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsSimilarWatch model = form.getTkQuestionsSimilarWatch();
				manager.updateTkQuestionsSimilarWatch(model);
				addLog(httpServletRequest,"修改了一个举一反三观看记录");
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
		TkQuestionsSimilarWatchManager manager = (TkQuestionsSimilarWatchManager)getBean("tkQuestionsSimilarWatchManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkQuestionsSimilarWatch(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}