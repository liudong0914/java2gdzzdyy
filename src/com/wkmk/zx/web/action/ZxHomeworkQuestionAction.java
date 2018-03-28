package com.wkmk.zx.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkQuestion;
import com.wkmk.zx.service.ZxHomeworkQuestionManager;
import com.wkmk.zx.web.form.ZxHomeworkQuestionActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 在线作业提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkQuestionAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHomeworkQuestionManager manager = (ZxHomeworkQuestionManager)getBean("zxHomeworkQuestionManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageZxHomeworkQuestions(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		ZxHomeworkQuestion model = new ZxHomeworkQuestion();
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
		ZxHomeworkQuestionActionForm form = (ZxHomeworkQuestionActionForm)actionForm;
		ZxHomeworkQuestionManager manager = (ZxHomeworkQuestionManager)getBean("zxHomeworkQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHomeworkQuestion model = form.getZxHomeworkQuestion();
				manager.addZxHomeworkQuestion(model);
				addLog(httpServletRequest,"增加了一个在线作业提问");
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
		ZxHomeworkQuestionManager manager = (ZxHomeworkQuestionManager)getBean("zxHomeworkQuestionManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			ZxHomeworkQuestion model = manager.getZxHomeworkQuestion(objid);
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
		ZxHomeworkQuestionActionForm form = (ZxHomeworkQuestionActionForm)actionForm;
		ZxHomeworkQuestionManager manager = (ZxHomeworkQuestionManager)getBean("zxHomeworkQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHomeworkQuestion model = form.getZxHomeworkQuestion();
				manager.updateZxHomeworkQuestion(model);
				addLog(httpServletRequest,"修改了一个在线作业提问");
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
		ZxHomeworkQuestionManager manager = (ZxHomeworkQuestionManager)getBean("zxHomeworkQuestionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delZxHomeworkQuestion(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}