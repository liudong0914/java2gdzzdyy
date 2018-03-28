package com.wkmk.edu.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduKnopointInfoManager;
import com.wkmk.edu.web.form.EduKnopointInfoActionForm;
import com.wkmk.tk.service.TkQuestionsKnopointManager;
import com.wkmk.vwh.service.VwhFilmKnopointManager;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 知识点信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduKnopointInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		
		EduKnopointInfoManager manager = (EduKnopointInfoManager)getBean("eduKnopointInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "gradetype", "=", gradetype);
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "knopointno asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageEduKnopointInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		VwhFilmKnopointManager vfkm = (VwhFilmKnopointManager) getBean("vwhFilmKnopointManager");
		TkQuestionsKnopointManager tqkm = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
		List knopointids1 = vfkm.getAllKnopointids();
		List knopointids2 = tqkm.getAllKnopointids();
		List allparentnos = manager.getAllParentnos(gradetype, subjectid);
		List list = page.getDatalist();
		EduKnopointInfo eki = null;
		for(int i=0, size=list.size(); i<size; i++){
			eki = (EduKnopointInfo) list.get(i);
			if(allparentnos.contains(eki.getKnopointno()) || knopointids1.contains(eki.getKnopointid()) || knopointids2.contains(eki.getKnopointid())){
				eki.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("gradetype", gradetype);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		
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
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		httpServletRequest.setAttribute("gradetype", gradetype);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduKnopointInfo model = new EduKnopointInfo();
		model.setParentno(parentno);
		model.setType("0");
		model.setStatus("1");
		model.setGradetype(gradetype);
		model.setSubjectid(Integer.valueOf(subjectid));
		model.setUpdatetime(DateTime.getDate());
		model.setKno("");
		model.setDescript("");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("num", "");
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
		EduKnopointInfoActionForm form = (EduKnopointInfoActionForm)actionForm;
		EduKnopointInfoManager manager = (EduKnopointInfoManager)getBean("eduKnopointInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduKnopointInfo model = form.getEduKnopointInfo();
				manager.addEduKnopointInfo(model);
				addLog(httpServletRequest,"增加了一个知识点【" + model.getTitle() + "】信息");
			}catch (Exception e){
			}
		}

		httpServletRequest.setAttribute("reloadtree", "1");
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
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		httpServletRequest.setAttribute("gradetype", gradetype);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduKnopointInfoManager manager = (EduKnopointInfoManager)getBean("eduKnopointInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduKnopointInfo model = manager.getEduKnopointInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("num", model.getKnopointno().substring(model.getKnopointno().length() - 4));
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
		EduKnopointInfoActionForm form = (EduKnopointInfoActionForm)actionForm;
		EduKnopointInfoManager manager = (EduKnopointInfoManager)getBean("eduKnopointInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduKnopointInfo model = form.getEduKnopointInfo();
				manager.updateEduKnopointInfo(model);
				addLog(httpServletRequest,"修改了一个知识点【" + model.getTitle() + "】信息");
			}catch (Exception e){
			}
		}

		httpServletRequest.setAttribute("reloadtree", "1");
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
		EduKnopointInfoManager manager = (EduKnopointInfoManager)getBean("eduKnopointInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduKnopointInfo eki = null;		for (int i = 0; i < checkids.length; i++) {
			eki = manager.getEduKnopointInfo(checkids[i]);
			manager.delEduKnopointInfo(eki);
			addLog(httpServletRequest,"删除了一个知识点【" + eki.getTitle() + "】信息");
		}
		
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 跳转到主工作区
	 */
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树型选择器
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List xxList = egim.getAllSubjectByXueduanid(2);
		List czList = egim.getAllSubjectByXueduanid(3);
		List gzList = egim.getAllSubjectByXueduanid(4);
		
		EduKnopointInfoManager manager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
		List list = manager.getEduKnopointInfos(new ArrayList<SearchModel>(), "gradetype asc, subjectid asc, knopointno asc", 0);
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		//---------加入小学、初中、高中
		tree.append("\n").append("tree").append(".nodes[\"0_2\"]=\"");
		tree.append("text:小学;");
		tree.append("url:javascript:;").append("target:").append(target).append(";").append("\";");
		
		tree.append("\n").append("tree").append(".nodes[\"0_3\"]=\"");
		tree.append("text:初中;");
		tree.append("url:javascript:;").append("target:").append(target).append(";").append("\";");
		
		tree.append("\n").append("tree").append(".nodes[\"0_4\"]=\"");
		tree.append("text:高中;");
		tree.append("url:javascript:;").append("target:").append(target).append(";").append("\";");
		
		String subjectid = null;
		EduSubjectInfo subject = null;
		for(int k=0;k<xxList.size();k++){
			subject = (EduSubjectInfo) xxList.get(k);
			subjectid = subject.getSubjectid().toString();
			text = subject.getSubjectname().trim();
			hint = "";
			url="/eduKnopointInfoAction.do?method=list&gradetype=2&parentno=0000&subjectid=" + subjectid;
			tree.append("\n").append("tree").append(".nodes[\"2_2").append(subjectid).append("0000\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		for(int k=0;k<czList.size();k++){
			subject = (EduSubjectInfo) czList.get(k);
			subjectid = subject.getSubjectid().toString();
			text = subject.getSubjectname().trim();
			hint = "";
			url="/eduKnopointInfoAction.do?method=list&gradetype=3&parentno=0000&subjectid=" + subjectid;
			tree.append("\n").append("tree").append(".nodes[\"3_3").append(subjectid).append("0000\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		for(int k=0;k<gzList.size();k++){
			subject = (EduSubjectInfo) gzList.get(k);
			subjectid = subject.getSubjectid().toString();
			text = subject.getSubjectname().trim();
			hint = "";
			url="/eduKnopointInfoAction.do?method=list&gradetype=4&parentno=0000&subjectid=" + subjectid;
			tree.append("\n").append("tree").append(".nodes[\"4_4").append(subjectid).append("0000\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String parentid = null;
		EduKnopointInfo eki = null;
		for(int k=0;k<list.size();k++){
			eki = (EduKnopointInfo) list.get(k);
			parentid = eki.getGradetype() + eki.getSubjectid();
			text = eki.getTitle().trim();
			hint = "";
			url="/eduKnopointInfoAction.do?method=list&gradetype=" + eki.getGradetype() + "&parentno=" + eki.getKnopointno() + "&subjectid=" + eki.getSubjectid();
			if("0000".equals(eki.getParentno())){
				tree.append("\n").append("tree").append(".nodes[\"").append(parentid).append("0000_").append(parentid).append(eki.getKnopointno()).append("\"]=\"");
			}else {
				tree.append("\n").append("tree").append(".nodes[\"").append(parentid).append(eki.getParentno()).append("_").append(parentid).append(eki.getKnopointno()).append("\"]=\"");
			}
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
	
	/**
	 * 检查栏目是否存在
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward checkKnopointno(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String knopointno = Encode.nullToBlank(httpServletRequest.getParameter("knopointno"));
		
		EduKnopointInfoManager manager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "gradetype", "=", gradetype);
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		SearchCondition.addCondition(condition, "knopointno", "=", knopointno);
		
		List lst = manager.getEduKnopointInfos(condition, "", 0);

		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			if(lst != null && lst.size() > 0){
				pw.write("1");
			}
		} catch (IOException ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}
}