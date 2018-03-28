package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.service.TkPaperCartManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.web.form.TkPaperInfoActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 * <p>
 * Description: 试卷信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperInfoAction extends BaseAction {

	/**
	 * 跳转到工作区
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		// 查询学科
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getAllSubjectByUnitType(sysUnitinfo.getType());
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		String hint = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			hint = "";
			url = "/tkPaperInfoAction.do?method=list&subjectid=" + esi.getSubjectid();
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			if (hint != null && !"".equals(hint.trim())) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

	/**
	 * 列表显示
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String grade = Encode.nullToBlank(httpServletRequest.getParameter("grade"));
		TkPaperInfoManager manager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(title)) {
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		if (!"".equals(grade)) {
			SearchCondition.addCondition(condition, "gradeid", "=", Integer.parseInt(grade));
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "gradeid asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkPaperInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		List paperids = manager.getAllbookcontentPaperid();
		TkPaperInfo model = null;
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkPaperInfo) dataList.get(i);
			if (paperids.contains(model.getPaperid())) {
				model.setFlags("disabled=\"disabled\"");
			}
			model.setGradeName(gradeManager.getEduGradeInfo(model.getGradeid()).getGradename());
		}
		condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List gradeinfoLists = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("gradeLists", gradeinfoLists);
		httpServletRequest.setAttribute("grade", grade);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("title", title);
		return actionMapping.findForward("list");
	}

	/**
	 * 查看试卷
	 * */
	public ActionForward detatilQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoManager questionManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkPaperInfoManager paperinfoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		String paperid = Encode.nullToBlank(httpServletRequest.getParameter("paperid"));
		List list = paperinfoManager.getPaperQuestions(Integer.parseInt(paperid));
		if (list != null && list.size() != 0) {
			TkQuestionsInfo model = null;
			for (int i = 0; i < list.size(); i++) {
				model = (TkQuestionsInfo) list.get(i);
				if ("F".equals(model.getTkQuestionsType().getType()) || "M".equals(model.getTkQuestionsType().getType())) {
					// 查询子题
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", model.getQuestionid());
					SearchCondition.addCondition(condition, "status", "=", "1");
					List childrenquestions = questionManager.getTkQuestionsInfos(condition, "questionno asc", 0);
					model.setChildrenquestions(childrenquestions);
				}
			}
		}
		httpServletRequest.setAttribute("list", list);
		return actionMapping.findForward("question_pervier");
	}

	/**
	 * 增加前
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return null;
	}

	/**
	 * 增加时保存
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperInfoActionForm form = (TkPaperInfoActionForm) actionForm;
		TkPaperInfoManager manager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperInfo model = form.getTkPaperInfo();
				manager.addTkPaperInfo(model);
				addLog(httpServletRequest, "增加了一个试卷信息");
			} catch (Exception e) {
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 修改前
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		TkPaperInfoManager manager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		try {
			TkPaperInfo model = manager.getTkPaperInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("subjectid", model.getSubjectid());
			httpServletRequest.setAttribute("title", title);
			model.setGradeName(gradeManager.getEduGradeInfo(model.getGradeid()).getGradename());
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperInfoActionForm form = (TkPaperInfoActionForm) actionForm;
		TkPaperInfoManager manager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperInfo model = form.getTkPaperInfo();
				manager.updateTkPaperInfo(model);
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "试卷信息");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperInfoManager manager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		TkPaperCartManager cartManager = (TkPaperCartManager) getBean("tkPaperCartManager");
		TkPaperContentManager contentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "paperid", "=", Integer.parseInt(checkids[i]));
				List list = contentManager.getTkPaperContents(condition, "orderindex", 0);
				cartManager.deleteList(list);
				manager.delTkPaperInfo(checkids[i]);
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}