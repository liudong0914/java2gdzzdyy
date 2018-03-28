package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperType;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkPaperTypeManager;
import com.wkmk.tk.web.form.TkPaperTypeActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 试卷附件分类</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperTypeAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	@SuppressWarnings("rawtypes")
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperTypeManager manager = (TkPaperTypeManager)getBean("tkPaperTypeManager");
		TkPaperFileManager filemanager = (TkPaperFileManager)getBean("tkPaperFileManager");
		String typename=Encode.nullToBlank(request.getParameter("typename"));
		String typeno=Encode.nullToBlank(request.getParameter("typeno"));
		String status=Encode.nullToBlank(request.getParameter("status"));
		PageUtil pageUtil = new PageUtil(request);
		String sorderindex = "typeno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(typename)){
			SearchCondition.addCondition(condition, "typename", "like", "%"+typename+"%");
		}
		if(!"".equals(typeno)){
			SearchCondition.addCondition(condition, "typeno", "like", "%"+typeno+"%");
		}
		SearchCondition.addCondition(condition, "status", "=", status);
		PageList page = manager.getPageTkPaperTypes(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List datalist=page.getDatalist();
		List typeids=filemanager.getPaperTypeids();
		TkPaperType model = null;
		for (int i = 0; i < datalist.size(); i++) {
			model=(TkPaperType)datalist.get(i);
			if(typeids.contains(model)){
				model.setFlags("disabled=\"disabled\"");
			}
		}
		request.setAttribute("pagelist", page);
		request.setAttribute("typename", typename);
		request.setAttribute("typeno", typeno);
		request.setAttribute("status", status);
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
		TkPaperType model = new TkPaperType();
		model.setStatus("1");
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
		TkPaperTypeActionForm form = (TkPaperTypeActionForm)actionForm;
		TkPaperTypeManager manager = (TkPaperTypeManager)getBean("tkPaperTypeManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperType model = form.getTkPaperType();
				manager.addTkPaperType(model);
				addLog(httpServletRequest,"增加了一个试卷附件分类【" + model.getTypename() + "】");
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
		TkPaperTypeManager manager = (TkPaperTypeManager)getBean("tkPaperTypeManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkPaperType model = manager.getTkPaperType(objid);
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
		TkPaperTypeActionForm form = (TkPaperTypeActionForm)actionForm;
		TkPaperTypeManager manager = (TkPaperTypeManager)getBean("tkPaperTypeManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperType model = form.getTkPaperType();
				manager.updateTkPaperType(model);
				addLog(httpServletRequest,"修改了一个试卷附件分类【" + model.getTypename() + "】");
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
		TkPaperTypeManager manager = (TkPaperTypeManager)getBean("tkPaperTypeManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		TkPaperType model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkPaperType(checkids[i]);
			manager.delTkPaperType(model);
			addLog(httpServletRequest,"删除了一个试卷附件分类【" + model.getTypename() + "】");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}