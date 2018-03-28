package com.wkmk.tk.web.action;

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
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentQuestion;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentQuestionManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.tk.web.form.TkBookContentQuestionActionForm;

/**
 * <p>
 * Description: 作业本内容关联试题
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentQuestionAction extends BaseAction {

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
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String typeid = Encode.nullToBlank(httpServletRequest.getParameter("typeid"));
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		TkBookInfoManager bookManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookInfo bookmodel = bookManager.getTkBookInfo(bookid);
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", bookmodel.getSubjectid());
		List typelist = typeManager.getTkQuestionsTypes(condition, "typeid", 0);
		httpServletRequest.setAttribute("typelist", typelist);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = " questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageQuestions(bookcontenid, questionno, title, difficult, type, sorderindex, typeid, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontenid", bookcontenid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("typeid", typeid);
		httpServletRequest.setAttribute("bookid", bookid);
		if ("1".equals(type)) {
			httpServletRequest.setAttribute("typetitle", "课前预习题");
		} else if ("2".equals(type)) {
			httpServletRequest.setAttribute("typetitle", "教学案题");
		}
		return actionMapping.findForward("list");
	}

	/**
	 * 未关联list列表
	 * */
	public ActionForward unlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String typeid = Encode.nullToBlank(httpServletRequest.getParameter("typeid"));
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		TkBookInfoManager infoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookInfo bookmodel = infoManager.getTkBookInfo(bookid);
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", bookmodel.getSubjectid());
		List typelist = typeManager.getTkQuestionsTypes(condition, "typeid", 0);
		httpServletRequest.setAttribute("typelist", typelist);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = " questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageUnQuestions(bookcontenid, questionno, title, difficult, type, sorderindex, bookmodel.getSubjectid(), bookmodel.getGradeid(), typeid,
				pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontenid", bookcontenid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("typeid", typeid);
		return actionMapping.findForward("unlist");
	}

	/**
	 * 批量添加
	 * */
	public ActionForward bathaddQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				TkBookContentQuestion model = new TkBookContentQuestion();
				model.setType(type);
				model.setBookcontentid(Integer.parseInt(bookcontenid));
				model.setQuestionid(Integer.parseInt(checkids[i]));
				manager.addTkBookContentQuestion(model);
			}
		}
		return unlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量移除
	 * */
	public ActionForward bathdelQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				manager.delQuestions(type, bookcontenid, checkids[i]);
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);

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
		TkBookContentQuestion model = new TkBookContentQuestion();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
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
		TkBookContentQuestionActionForm form = (TkBookContentQuestionActionForm) actionForm;
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentQuestion model = form.getTkBookContentQuestion();
				manager.addTkBookContentQuestion(model);
				addLog(httpServletRequest, "增加了一个作业本内容关联试题");
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
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkBookContentQuestion model = manager.getTkBookContentQuestion(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
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
		TkBookContentQuestionActionForm form = (TkBookContentQuestionActionForm) actionForm;
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentQuestion model = form.getTkBookContentQuestion();
				manager.updateTkBookContentQuestion(model);
				addLog(httpServletRequest, "修改了一个作业本内容关联试题");
			} catch (Exception e) {
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
		TkBookContentQuestionManager manager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentQuestion(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 详情
	 * */
	public ActionForward detail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		TkQuestionsInfoManager questionManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsInfo questionmodel = questionManager.getTkQuestionsInfo(questionid);
		List<TkQuestionsInfo> list = new ArrayList<TkQuestionsInfo>();
		if ("F".equals(questionmodel.getTkQuestionsType().getType()) || "M".equals(questionmodel.getTkQuestionsType().getType())) {
			// 查询子题
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "parentid", "=", questionmodel.getQuestionid());
			SearchCondition.addCondition(condition, "status", "=", "1");
			List childrenquestions = questionManager.getTkQuestionsInfos(condition, "questionno asc", 0);
			questionmodel.setChildrenquestions(childrenquestions);
		}
		list.add(questionmodel);
		httpServletRequest.setAttribute("list", list);
		return actionMapping.findForward("detail");
	}
}