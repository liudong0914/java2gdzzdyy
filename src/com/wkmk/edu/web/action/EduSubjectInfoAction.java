package com.wkmk.edu.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.edu.web.form.EduSubjectInfoActionForm;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduSubjectInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectname = Encode.nullToBlank(httpServletRequest.getParameter("subjectname"));
		
		EduSubjectInfoManager manager = (EduSubjectInfoManager)getBean("eduSubjectInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(subjectname)){
			SearchCondition.addCondition(condition, "subjectname", "like", "%" + subjectname + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageEduSubjectInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List allsubjectids = egim.getAllSubjectids();
		List dataList = page.getDatalist();
		EduSubjectInfo model = null;
		for(int i=0, size=dataList.size(); i<size; i++){
			model = (EduSubjectInfo) dataList.get(i);
			if(allsubjectids.contains(model.getSubjectid())){
				model.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectname", subjectname);
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
		String subjectname = Encode.nullToBlank(httpServletRequest.getParameter("subjectname"));
		httpServletRequest.setAttribute("subjectname", subjectname);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduSubjectInfo model = new EduSubjectInfo();
		model.setOrderindex(1);
		model.setStatus("1");
		model.setBanner("default.gif");
		model.setUpdatetime(DateTime.getDate());
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
		EduSubjectInfoActionForm form = (EduSubjectInfoActionForm)actionForm;
		EduSubjectInfoManager manager = (EduSubjectInfoManager)getBean("eduSubjectInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduSubjectInfo model = form.getEduSubjectInfo();
				manager.addEduSubjectInfo(model);
				addLog(httpServletRequest,"增加了一个教育学科【" + model.getSubjectname() + "】信息");
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
		String subjectname = Encode.nullToBlank(httpServletRequest.getParameter("subjectname"));
		httpServletRequest.setAttribute("subjectname", subjectname);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduSubjectInfoManager manager = (EduSubjectInfoManager)getBean("eduSubjectInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduSubjectInfo model = manager.getEduSubjectInfo(objid);
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
		EduSubjectInfoActionForm form = (EduSubjectInfoActionForm)actionForm;
		EduSubjectInfoManager manager = (EduSubjectInfoManager)getBean("eduSubjectInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduSubjectInfo model = form.getEduSubjectInfo();
				EduSubjectInfo esi = manager.getEduSubjectInfo(model.getSubjectid());
				model.setUpdatetime(DateTime.getDate());
				manager.updateEduSubjectInfo(model);
				addLog(httpServletRequest,"修改了一个教育学科【" + model.getSubjectname() + "】信息");
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
		EduSubjectInfoManager manager = (EduSubjectInfoManager)getBean("eduSubjectInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduSubjectInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getEduSubjectInfo(checkids[i]);
			manager.delEduSubjectInfo(model);
			addLog(httpServletRequest,"删除了一个教育学科【" + model.getSubjectname() + "】信息");
		}
		
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}