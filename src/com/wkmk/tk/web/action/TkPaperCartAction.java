package com.wkmk.tk.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduKnopointInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.tk.bo.TkPaperCart;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkPaperCartManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.tk.web.form.TkPaperCartActionForm;

/**
 * <p>
 * Description: 题库组卷试题蓝
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperCartAction extends BaseAction {

	/**
	 * 跳转到工作区
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		if ("".equals(type) || "".equals(gradetype)) {
			EduGradeInfoManager gradeInfoManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			// 查询小学学科
			List xsubjectlist = gradeInfoManager.getAllSubjectByXueduanid(2);
			// 查询初中学科
			List csubjectlist = gradeInfoManager.getAllSubjectByXueduanid(3);
			// 查询高中学科
			List gsubjectlist = gradeInfoManager.getAllSubjectByXueduanid(4);
			if (xsubjectlist != null && xsubjectlist.size() > 0) {
				gradetype = "2";
				subjectid = ((EduSubjectInfo) xsubjectlist.get(0)).getSubjectid().toString();
			} else if ((xsubjectlist == null || xsubjectlist.size() == 0) && (csubjectlist != null && csubjectlist.size() > 0)) {
				gradetype = "3";
				subjectid = ((EduSubjectInfo) csubjectlist.get(0)).getSubjectid().toString();
			} else if ((xsubjectlist == null || xsubjectlist.size() == 0) && (csubjectlist == null || csubjectlist.size() == 0) && (gsubjectlist != null && gsubjectlist.size() > 0)) {
				gradetype = "4";
				subjectid = ((EduSubjectInfo) gsubjectlist.get(0)).getSubjectid().toString();
			}
		}
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("gradetype", gradetype);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.getSession().setAttribute("subjectid", subjectid);
		httpServletRequest.getSession().setAttribute("type", type.trim().length() <= 0 ? 0 : type);
		httpServletRequest.getSession().setAttribute("gradetype", gradetype);
		return actionMapping.findForward("main");
	}

	/**
	 * 学科列表
	 * */
	public ActionForward subject(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduGradeInfoManager gradeInfoManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		// 查询小学学科
		List xsubjectlist = gradeInfoManager.getAllSubjectByXueduanid(2);
		// 查询初中学科
		List csubjectlist = gradeInfoManager.getAllSubjectByXueduanid(3);
		// 查询高中学科
		List gsubjectlist = gradeInfoManager.getAllSubjectByXueduanid(4);
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String subjectid = "";
		String gradetype = "";
		if ("".equals(type) || "0".equals(type)) {
			if (xsubjectlist != null && xsubjectlist.size() > 0) {
				httpServletRequest.setAttribute("firstname", "小学-" + ((EduSubjectInfo) xsubjectlist.get(0)).getSubjectname());
				gradetype = "2";
				subjectid = ((EduSubjectInfo) xsubjectlist.get(0)).getSubjectid().toString();
			} else if ((xsubjectlist == null || xsubjectlist.size() == 0) && (csubjectlist != null && csubjectlist.size() > 0)) {
				httpServletRequest.setAttribute("firstname", "初中-" + ((EduSubjectInfo) csubjectlist.get(0)).getSubjectname());
				gradetype = "3";
				subjectid = ((EduSubjectInfo) csubjectlist.get(0)).getSubjectid().toString();
			} else if ((xsubjectlist == null || xsubjectlist.size() == 0) && (csubjectlist == null || csubjectlist.size() == 0) && (gsubjectlist != null && gsubjectlist.size() > 0)) {
				httpServletRequest.setAttribute("firstname", "高中-" + ((EduSubjectInfo) gsubjectlist.get(0)).getSubjectname());
				gradetype = "4";
				subjectid = ((EduSubjectInfo) gsubjectlist.get(0)).getSubjectid().toString();
			}
		} else {
			EduSubjectInfoManager subjectManager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
			gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
			subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
			String gradetypename = "";
			if ("2".equals(gradetype)) {
				gradetypename = "小学-";
			} else if ("3".equals(gradetype)) {
				gradetypename = "初中-";
			} else if ("4".equals(gradetype)) {
				gradetypename = "高中-";
			}
			String subjectname = subjectManager.getEduSubjectInfo(subjectid).getSubjectname();
			httpServletRequest.setAttribute("firstname", gradetypename + subjectname);
			httpServletRequest.setAttribute("gradetype", gradetype);
		}
		// 计算各类型题目的数量、百分比、分值
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List questiontypes = typeManager.getTkQuestionsTypes(condition, "typeno", 0);
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		for (int i = 0; i < questiontypes.size(); i++) {
			TkQuestionsType model = (TkQuestionsType) questiontypes.get(i);
			int numcount = typeManager.getTkQuestionsTypesNums(subjectid, gradetype, model.getTypeid().toString(), userid);// 求出该类型题的个数
			String percent = typeManager.getTkQuestionsTypesPercent(subjectid, gradetype, model.getTypeid().toString(), userid);// 求出百分比
			double score = typeManager.getTkQuestionsTypesScore(subjectid, gradetype, model.getTypeid().toString(), userid);// 求出分数
			model.setCountnum(String.valueOf(numcount));
			model.setPercent(percent);
			model.setScore(String.valueOf(score));
		}
		httpServletRequest.setAttribute("questioncount", typeManager.getTkQuestionsSum(subjectid, gradetype, userid));
		httpServletRequest.setAttribute("questiontypes", questiontypes);
		httpServletRequest.setAttribute("xsubjectlist", xsubjectlist);
		httpServletRequest.setAttribute("csubjectlist", csubjectlist);
		httpServletRequest.setAttribute("gsubjectlist", gsubjectlist);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.getSession().setAttribute("subjectid", subjectid);
		httpServletRequest.getSession().setAttribute("type", type.trim().length() <= 0 ? 0 : type);
		httpServletRequest.getSession().setAttribute("gradetype", gradetype);
		return actionMapping.findForward("subject");
	}

	/**
	 * 左侧工作区
	 * */
	public ActionForward leftmain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		httpServletRequest.setAttribute("gradetype", gradetype);
		httpServletRequest.setAttribute("subjectid", subjectid);
		return actionMapping.findForward("leftmain");
	}

	/**
	 * 知识点树形图
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduKnopointInfoManager knopointManager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		// 查询知识点
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "gradetype", "=", gradetype);
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List<EduKnopointInfo> knopointList = knopointManager.getEduKnopointInfos(condition, "knopointno asc", 0);
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "onright";
		String url = null;
		EduKnopointInfo ekp = null;
		for (int i = 0; i < knopointList.size(); i++) {
			ekp = (EduKnopointInfo) knopointList.get(i);
			text = ekp.getTitle();
			url = "/tkPaperCartAction.do?method=questionmain&knopointid=" + ekp.getKnopointid() + "&subjectid=" + subjectid + "&gradetype=" + gradetype;
			tree.append("\n").append("tree").append(".nodes[\"" + ekp.getParentno() + "_" + ekp.getKnopointno() + "\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "/tkPaperCartAction.do?method=questionmain&subjectid=" + subjectid + "&gradetype=" + gradetype;
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

	/**
	 * 试题工作区
	 * */
	public ActionForward questionmain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String knopointid = Encode.nullToBlank(httpServletRequest.getParameter("knopointid"));
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String result = "error";
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List questiontypes = typeManager.getTkQuestionsTypes(condition, "typeno", 0);
		httpServletRequest.setAttribute("questiontypes", questiontypes);
		if (questiontypes != null && questiontypes.size() > 0) {
			httpServletRequest.setAttribute("questiontypeid", ((TkQuestionsType) questiontypes.get(0)).getTypeid());
			result = "questionmain";
		}
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("knopointid", knopointid);
		httpServletRequest.setAttribute("gradetype", gradetype);
		return actionMapping.findForward(result);
	}

	/**
	 * 试题
	 * */
	public ActionForward questions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String knopointid = Encode.nullToBlank(httpServletRequest.getParameter("knopointid"));
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		TkPaperCartManager papercartManager = (TkPaperCartManager) getBean("tkPaperCartManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typemanager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		EduKnopointInfoManager knopointManager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = null;
		List dataList = null;
		List questionids = papercartManager.getAllQuestionid(subjectid, gradetype, httpServletRequest.getSession().getAttribute("s_userid").toString());
		// 当存在知识点时
		if (!"".equals(knopointid)) {
			EduKnopointInfo eduKnopointInfo = knopointManager.getEduKnopointInfo(knopointid);
			page = manager.getQuestionsInfo(title, subjectid, knopointid, eduKnopointInfo.getKnopointno(), questiontypeid, difficult, pageUtil.getStartCount(), pageUtil.getPageSize());
			dataList = page.getDatalist();
			// 当不存在知识点只有学科时
		} else {
			page = manager.getQuestionsInfo(title, subjectid, gradetype, questiontypeid, difficult, pageUtil.getStartCount(), pageUtil.getPageSize());
			dataList = page.getDatalist();
		}
		TkQuestionsInfo model = null;
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkQuestionsInfo) dataList.get(i);
			if (questionids.contains(model.getQuestionid())) {
				model.setFlags("1");
			}
			if ("E".equals(model.getTkQuestionsType().getType())) {
				String[] scores = model.getScore().split("【】");
				int sum = 0;
				String rightan = "";
				for (int j = 0; j < scores.length; j++) {
					sum += Integer.parseInt(scores[j]);
				}
				model.setScore(String.valueOf(sum));
				if (model.getRightans() != null) {
					String[] rightans = model.getRightans().split("【】");
					for (int k = 0; k < rightans.length; k++) {
						if (k == 0) {
							rightan = rightans[k].replaceAll("【或】", "或");
						} else {
							rightan += "<br/>" + rightans[k].replaceAll("【或】", "或");
						}
					}
					model.setRightans(rightan);
				}
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("list", page.getDatalist());
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("knopointid", knopointid);
		httpServletRequest.setAttribute("questiontypeid", questiontypeid);
		httpServletRequest.setAttribute("questionsize", page.getDatalist().size());
		httpServletRequest.setAttribute("gradetype", gradetype);
		TkQuestionsType type = typemanager.getTkQuestionsType(questiontypeid);
		String result = "";
		if ("A".equals(type.getType()) || "B".equals(type.getType())) {
			// 单选题和多选题
			result = "question_a_list";
		} else if ("C".equals(type.getType()) || "E".equals(type.getType()) || "S".equals(type.getType())) {
			// 判断题和填空题和主观题
			result = "question_c_list";
		} else if ("F".equals(type.getType()) || "M".equals(type.getType())) {
			// 复合题和英语完型填空题
			result = "question_f_list";
		}
		return actionMapping.findForward(result);
	}

	/**
	 * 将试题加入到试卷中
	 * */
	public ActionForward addQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String score = Encode.nullToBlank(httpServletRequest.getParameter("score"));
		TkPaperCart model = new TkPaperCart();
		model.setUserid(Integer.parseInt(userid));
		model.setSubjectid(Integer.parseInt(subjectid));
		model.setXueduanid(Integer.parseInt(xueduanid));
		model.setQuestionsid(Integer.parseInt(questionid));
		int orderindex = manager.getMaxOrderindex(userid, subjectid, xueduanid);
		model.setOrderindex(orderindex);
		model.setScore(Float.parseFloat(score));
		try {
			manager.addTkPaperCart(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 撤销试题(删除)
	 * */
	public ActionForward deleteQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		try {
			manager.deleteQuestion(subjectid, xueduanid, questionid, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清空试卷中的全部试题
	 * */
	public ActionForward clearAllQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		try {
			manager.delAllQuestions(subjectid, gradetype, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查看子题
	 * */
	public ActionForward childrenQuestionDetail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questiontype = Encode.nullToBlank(httpServletRequest.getParameter("questiontype"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsInfo model = manager.getTkQuestionsInfo(questionid);
		// 查询拥有的题目类型
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", model.getSubjectid());
		SearchCondition.addCondition(condition, "type", "!=", "F");
		SearchCondition.addCondition(condition, "type", "!=", "M");
		SearchCondition.addCondition(condition, "type", "!=", "S");
		List questiontypes = typeManager.getTkQuestionsTypes(condition, "typeno", 0);
		httpServletRequest.setAttribute("questiontypes", questiontypes);
		if (questiontypes != null && questiontypes.size() > 0) {
			httpServletRequest.setAttribute("questiontypeid", ((TkQuestionsType) questiontypes.get(0)).getTypeid());
		}
		httpServletRequest.setAttribute("questiontype", questiontype);
		httpServletRequest.setAttribute("questionid", questionid);
		return actionMapping.findForward("children_question");
	}

	public ActionForward childrenQuestionList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String questiontype = Encode.nullToBlank(httpServletRequest.getParameter("questiontype"));
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "parentid", "=", Integer.parseInt(questionid));
		SearchCondition.addCondition(condition, "tkQuestionsType.typeid", "=", Integer.parseInt(questiontype));
		SearchCondition.addCondition(condition, "status", "=", "1");
		PageList page = manager.getPageTkQuestionsInfos(condition, "questionno", pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		String result = "";
		TkQuestionsInfo questioninfo = null;
		for (int i = 0; i < dataList.size(); i++) {
			questioninfo = (TkQuestionsInfo) dataList.get(i);
			if ("E".equals(questioninfo.getTkQuestionsType().getType())) {
				String[] scores = questioninfo.getScore().split("【】");
				int sum = 0;
				String rightan = "";
				for (int j = 0; j < scores.length; j++) {
					sum += Integer.parseInt(scores[j]);
				}
				questioninfo.setScore(String.valueOf(sum));
				if (questioninfo.getRightans() != null) {
					String[] rightans = questioninfo.getRightans().split("【】");
					for (int k = 0; k < rightans.length; k++) {
						if (k == 0) {
							rightan = rightans[k].replaceAll("【或】", "或");
						} else {
							rightan += "<br/>" + rightans[k].replaceAll("【或】", "或");
						}
					}
					questioninfo.setRightans(rightan);
				}
			}
		}
		TkQuestionsType typemodel = typeManager.getTkQuestionsType(questiontype);
		if ("A".equals(typemodel.getType()) || "B".equals(typemodel.getType())) {
			result = "question_a_children";
		} else if ("C".equals(typemodel.getType()) || "E".equals(typemodel.getType()) || "S".equals(typemodel.getType())) {
			result = "question_c_children";
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("questiontype", questiontype);
		httpServletRequest.setAttribute("list", page.getDatalist());
		httpServletRequest.setAttribute("questionsize", page.getDatalist().size());
		return actionMapping.findForward(result);
	}

	/**
	 * 试卷预览
	 * */
	public ActionForward paperPervier(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String xtype = Encode.nullToBlank(httpServletRequest.getParameter("xtype"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkQuestionsInfoManager questionManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		List list = manager.getAllPaperQuestions(subjectid, xueduanid, userid);
		String result = "";
		if (list != null && list.size() != 0) {
			TkQuestionsInfo model = null;
			for (int i = 0; i < list.size(); i++) {
				model = (TkQuestionsInfo) list.get(i);
				model.setPapercartSocre(manager.getPaperCartScore(subjectid, xueduanid, userid, model.getQuestionid().toString()));
				if ("F".equals(model.getTkQuestionsType().getType()) || "M".equals(model.getTkQuestionsType().getType())) {
					// 查询子题
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", model.getQuestionid());
					SearchCondition.addCondition(condition, "status", "=", "1");
					List childrenquestions = questionManager.getTkQuestionsInfos(condition, "questionno asc", 0);
					model.setChildrenquestions(childrenquestions);
				}
			}
			result = "pervier";
		} else {
			result = "pervier_error";
		}
		httpServletRequest.setAttribute("xtype", xtype);
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("list", list);
		httpServletRequest.setAttribute("userid", userid);
		httpServletRequest.setAttribute("xueduanid", xueduanid);
		httpServletRequest.setAttribute("subjectid", subjectid);
		return actionMapping.findForward(result);
	}

	/**
	 * 修改分数
	 * */
	public ActionForward beforeupdateScore(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		SearchCondition.addCondition(condition, "questionsid", "=", Integer.parseInt(questionid));
		TkPaperCart model = (TkPaperCart) manager.getTkPaperCarts(condition, "orderindex", 0).get(0);
		httpServletRequest.setAttribute("score", model.getScore());
		httpServletRequest.setAttribute("userid", userid);
		httpServletRequest.setAttribute("xueduanid", xueduanid);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("type", type);
		return actionMapping.findForward("before_updatescore");
	}

	/**
	 * 保存修改分数
	 * */
	public ActionForward updateScore(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String score = Encode.nullToBlank(httpServletRequest.getParameter("score"));
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		SearchCondition.addCondition(condition, "questionsid", "=", Integer.parseInt(questionid));
		TkPaperCart model = (TkPaperCart) manager.getTkPaperCarts(condition, "orderindex", 0).get(0);
		model.setScore(Float.parseFloat(score));
		manager.updateTkPaperCart(model);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("score", score);
		return actionMapping.findForward("updatescore_close");
	}

	/**
	 * 修改orderinx排序
	 * */
	public ActionForward updateOrdeinxs(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		SearchCondition.addCondition(condition, "questionsid", "=", Integer.parseInt(questionid));
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		TkPaperCart model = (TkPaperCart) manager.getTkPaperCarts(condition, "orderindex", 0).get(0);
		if ("up".equals(status)) {
			// 查询orderindex比model小的对象
			TkPaperCart beforemodel = manager.getSmallTkPaperCart(subjectid, xueduanid, userid, model.getOrderindex().toString());
			if (beforemodel != null) {
				Integer temp = beforemodel.getOrderindex();
				beforemodel.setOrderindex(model.getOrderindex());
				manager.updateTkPaperCart(beforemodel);
				model.setOrderindex(temp);
				manager.updateTkPaperCart(model);
			}
		} else if ("down".equals(status)) {
			// 查询orderindex比model大的对象
			TkPaperCart beforemodel = manager.getBigTkPaperCart(subjectid, xueduanid, userid, model.getOrderindex().toString());
			if (beforemodel != null) {
				Integer temp = beforemodel.getOrderindex();
				beforemodel.setOrderindex(model.getOrderindex());
				manager.updateTkPaperCart(beforemodel);
				model.setOrderindex(temp);
				manager.updateTkPaperCart(model);
			}
		}
		return null;
	}

	/**
	 * 新增试卷
	 * */
	public ActionForward addPaperInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String userid = httpServletRequest.getSession().getAttribute("s_userid").toString();
		String unitid = httpServletRequest.getSession().getAttribute("s_unitid").toString();
		String result = "";
		TkPaperCartManager cartManager = (TkPaperCartManager) getBean("tkPaperCartManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		List papercartlist = cartManager.getTkPaperCarts(condition, "orderindex", 0);
		if (papercartlist != null && papercartlist.size() != 0) {
			TkPaperInfo model = new TkPaperInfo();
			model.setUserid(Integer.parseInt(userid));
			model.setUnitid(Integer.parseInt(unitid));
			model.setSubjectid(Integer.parseInt(subjectid));
			model.setPapertype("1");
			model.setStatus("1");
			httpServletRequest.setAttribute("model", model);
			// 查询年级
			EduGradeInfoManager gradeinfoManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
			SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
			List gradeinfos = gradeinfoManager.getEduGradeInfos(condition, "orderindex", 0);
			httpServletRequest.setAttribute("gradeinfos", gradeinfos);
			httpServletRequest.setAttribute("xueduanid", xueduanid);
			result = "addpaperinfo";
		} else {
			result = "info_error";
		}
		return actionMapping.findForward(result);
	}

	/**
	 * 新建试卷转移题库
	 * **/
	public ActionForward addSavePaperInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperInfoManager infoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.title"));
		String descript = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.descript"));
		String grade = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.gradeid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.status"));
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.userid"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.unitid"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.subjectid"));
		String papertype = Encode.nullToBlank(httpServletRequest.getParameter("tkPaperInfo.papertype"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		TkPaperInfo model = new TkPaperInfo();
		model.setCreatedate(sdf.format(new Date()));
		model.setTitle(title);
		model.setDescript(descript);
		model.setGradeid(Integer.parseInt(grade));
		model.setStatus(status);
		model.setUserid(Integer.parseInt(userid));
		model.setUnitid(Integer.parseInt(unitid));
		model.setSubjectid(Integer.parseInt(subjectid));
		model.setPapertype(papertype);
		infoManager.addTkPaperInfo(model);
		// cart数据转移到conent中
		TkPaperCartManager cartManager = (TkPaperCartManager) getBean("tkPaperCartManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List papercarts = cartManager.getTkPaperCarts(condition, "orderindex asc", 0);
		TkPaperCart cartmodel = null;
		TkPaperContentManager contentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		for (int i = 0; i < papercarts.size(); i++) {
			TkPaperContent contentmodel = new TkPaperContent();
			cartmodel = (TkPaperCart) papercarts.get(i);
			contentmodel.setPaperid(model.getPaperid());
			contentmodel.setQuestionid(cartmodel.getQuestionsid());
			contentmodel.setOrderindex(cartmodel.getOrderindex());
			contentmodel.setScore(cartmodel.getScore());
			contentManager.addTkPaperContent(contentmodel);
		}
		// 删除原有数据
		cartManager.deleteList(papercarts);
		return actionMapping.findForward("info_success");
	}

	/**
	 * 智能组卷页面
	 * */
	public ActionForward beforeInteligencePaper(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String gradetype = Encode.nullToBlank(httpServletRequest.getParameter("gradetype"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		// 查询该学科中含有的题型
		String result = "";
		TkQuestionsTypeManager questiontypeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List questionTypes = questiontypeManager.getTkQuestionsTypes(condition, "typeno asc", 0);
		if (questionTypes == null || questionTypes.size() == 0) {
			result = "Inteligenc_questiontype_error";
		} else {
			httpServletRequest.setAttribute("questionTypes", questionTypes);
			// 查询知识点
			EduKnopointInfoManager knopointInfoManager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
			condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
			SearchCondition.addCondition(condition, "gradetype", "=", gradetype);
			SearchCondition.addCondition(condition, "parentno", "=", "0000");
			List knopointInfos = knopointInfoManager.getEduKnopointInfos(condition, "knopointno asc", 0);
			httpServletRequest.setAttribute("num", knopointInfos.size());
			for (int i = 0; i < knopointInfos.size(); i++) {
				EduKnopointInfo model = (EduKnopointInfo) knopointInfos.get(i);
				condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
				SearchCondition.addCondition(condition, "gradetype", "=", gradetype);
				SearchCondition.addCondition(condition, "parentno", "like ", model.getKnopointno() + "%");
				List knopointlist = knopointInfoManager.getEduKnopointInfos(condition, "knopointno asc", 0);
				knopointlist.add(0, model);
				for (int j = 0; j < knopointlist.size(); j++) {
					EduKnopointInfo tempmodel = (EduKnopointInfo) knopointlist.get(j);
					int temp = tempmodel.getKnopointno().length() - 8;
					String nbsp = "";
					for (int m = 0; m < temp; m++) {
						nbsp += "&nbsp;";
					}
					tempmodel.setTitle(nbsp + tempmodel.getTitle());
				}
				httpServletRequest.setAttribute("knopoinlist" + i, knopointlist);
			}
			httpServletRequest.setAttribute("type", type);
			httpServletRequest.setAttribute("subjectid", subjectid);
			httpServletRequest.setAttribute("xueduanid", gradetype);
			result = "Inteligenc_paper";
		}
		return actionMapping.findForward(result);
	}

	/**
	 * 智能组卷
	 * */
	public ActionForward InteligencePaper(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperCartManager cartManager = (TkPaperCartManager) getBean("tkPaperCartManager");
		TkQuestionsInfoManager questionManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		String userid = Encode.nullToBlank(httpServletRequest.getSession().getAttribute("s_userid").toString());
		// 删除试卷原有题
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", Integer.parseInt(userid));
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "xueduanid", "=", Integer.parseInt(xueduanid));
		List list = cartManager.getTkPaperCarts(condition, "orderindex", 0);
		cartManager.deleteList(list);
		// 获取各题型个数
		String Anum = Encode.nullToBlank(httpServletRequest.getParameter("A"));// 单选题个数
		String Bnum = Encode.nullToBlank(httpServletRequest.getParameter("B"));// 多选题个数
		String Cnum = Encode.nullToBlank(httpServletRequest.getParameter("C"));// 判断题个数
		String Dnum = Encode.nullToBlank(httpServletRequest.getParameter("D"));// 连线题个数
		String Enum = Encode.nullToBlank(httpServletRequest.getParameter("E"));// 填空题个数
		String Fnum = Encode.nullToBlank(httpServletRequest.getParameter("F"));// 英语完型填空题个数
		String Mnum = Encode.nullToBlank(httpServletRequest.getParameter("M"));// 复合题个数
		String Snum = Encode.nullToBlank(httpServletRequest.getParameter("S"));// 主观题个数
		if ("".equals(Anum)) {
			Anum = "0";
		}
		if ("".equals(Bnum)) {
			Bnum = "0";
		}
		if ("".equals(Cnum)) {
			Cnum = "0";
		}
		if ("".equals(Dnum)) {
			Dnum = "0";
		}
		if ("".equals(Enum)) {
			Enum = "0";
		}
		if ("".equals(Fnum)) {
			Fnum = "0";
		}
		if ("".equals(Mnum)) {
			Mnum = "0";
		}
		if ("".equals(Snum)) {
			Snum = "0";
		}
		// 获取各题总分
		String Asumscore = Encode.nullToBlank(httpServletRequest.getParameter("A_score"));
		String Bsumscore = Encode.nullToBlank(httpServletRequest.getParameter("B_score"));
		String Csumscore = Encode.nullToBlank(httpServletRequest.getParameter("C_score"));
		String Dsumscore = Encode.nullToBlank(httpServletRequest.getParameter("D_score"));
		String Esumscore = Encode.nullToBlank(httpServletRequest.getParameter("E_score"));
		String Fsumscore = Encode.nullToBlank(httpServletRequest.getParameter("F_score"));
		String Msumscore = Encode.nullToBlank(httpServletRequest.getParameter("M_score"));
		String Ssumscore = Encode.nullToBlank(httpServletRequest.getParameter("S_score"));
		// 获取难易度
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		// 获取知识点
		String[] knopoints = httpServletRequest.getParameterValues("knopoint");
		List AList = new ArrayList();
		List BList = new ArrayList();
		List CList = new ArrayList();
		List DList = new ArrayList();
		List EList = new ArrayList();
		List FList = new ArrayList();
		List MList = new ArrayList();
		List SList = new ArrayList();
		if (knopoints != null) {
			String temp = "";
			for (int i = 0; i < knopoints.length; i++) {
				if (i == 0) {
					temp = "t.knopointid=" + knopoints[i];
				} else {
					temp += " or t.knopointid=" + knopoints[i];
				}
			}
			AList = questionManager.getQuestionsInfo("A", difficult, Integer.parseInt(Anum), temp);
			BList = questionManager.getQuestionsInfo("B", difficult, Integer.parseInt(Bnum), temp);
			CList = questionManager.getQuestionsInfo("C", difficult, Integer.parseInt(Cnum), temp);
			DList = questionManager.getQuestionsInfo("D", difficult, Integer.parseInt(Dnum), temp);
			EList = questionManager.getQuestionsInfo("E", difficult, Integer.parseInt(Enum), temp);
			FList = questionManager.getQuestionsInfo("F", difficult, Integer.parseInt(Fnum), temp);
			MList = questionManager.getQuestionsInfo("M", difficult, Integer.parseInt(Mnum), temp);
			SList = questionManager.getQuestionsInfo("S", difficult, Integer.parseInt(Snum), temp);
		} else {
			AList = questionManager.getQuestionsInfo("A", difficult, Integer.parseInt(Anum));
			BList = questionManager.getQuestionsInfo("B", difficult, Integer.parseInt(Bnum));
			CList = questionManager.getQuestionsInfo("C", difficult, Integer.parseInt(Cnum));
			DList = questionManager.getQuestionsInfo("D", difficult, Integer.parseInt(Dnum));
			EList = questionManager.getQuestionsInfo("E", difficult, Integer.parseInt(Enum));
			FList = questionManager.getQuestionsInfo("F", difficult, Integer.parseInt(Fnum));
			MList = questionManager.getQuestionsInfo("M", difficult, Integer.parseInt(Mnum));
			SList = questionManager.getQuestionsInfo("S", difficult, Integer.parseInt(Snum));
		}
		int orderindex = 0;
		if (AList != null) {
			for (int i = 0; i < AList.size(); i++) {
				Float sumscore = Float.parseFloat(Asumscore);
				int num = Integer.parseInt(Anum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) AList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (BList != null) {
			for (int i = 0; i < BList.size(); i++) {
				Float sumscore = Float.parseFloat(Bsumscore);
				int num = Integer.parseInt(Bnum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) BList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (CList != null) {
			for (int i = 0; i < CList.size(); i++) {
				Float sumscore = Float.parseFloat(Csumscore);
				int num = Integer.parseInt(Cnum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) CList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (DList != null) {
			for (int i = 0; i < DList.size(); i++) {
				Float sumscore = Float.parseFloat(Dsumscore);
				int num = Integer.parseInt(Dnum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) DList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (EList != null) {
			for (int i = 0; i < EList.size(); i++) {
				Float sumscore = Float.parseFloat(Esumscore);
				int num = Integer.parseInt(Enum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) EList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (FList != null) {
			for (int i = 0; i < FList.size(); i++) {
				Float sumscore = Float.parseFloat(Fsumscore);
				int num = Integer.parseInt(Fnum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) FList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (MList != null) {
			for (int i = 0; i < MList.size(); i++) {
				Float sumscore = Float.parseFloat(Msumscore);
				int num = Integer.parseInt(Mnum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) MList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		if (SList != null) {
			for (int i = 0; i < SList.size(); i++) {
				Float sumscore = Float.parseFloat(Ssumscore);
				int num = Integer.parseInt(Snum);
				Float score = sumscore / num;
				TkQuestionsInfo model = (TkQuestionsInfo) SList.get(i);
				TkPaperCart cartmodel = new TkPaperCart();
				cartmodel.setUserid(Integer.parseInt(userid));
				cartmodel.setSubjectid(Integer.parseInt(subjectid));
				cartmodel.setXueduanid(Integer.parseInt(xueduanid));
				cartmodel.setOrderindex(orderindex);
				cartmodel.setQuestionsid(model.getQuestionid());
				cartmodel.setScore(score);
				cartManager.addTkPaperCart(cartmodel);
				orderindex += 1;
			}
		}
		String content = "";
		if (Integer.parseInt(Anum) != AList.size()) {
			content += "单选题差" + (Integer.parseInt(Anum) - AList.size()) + "个";
		}
		if (Integer.parseInt(Bnum) != BList.size()) {
			content += "  多选题差" + (Integer.parseInt(Bnum) - BList.size()) + "个";
		}
		if (Integer.parseInt(Cnum) != CList.size()) {
			content += "  判断题差" + (Integer.parseInt(Cnum) - CList.size()) + "个";
		}
		if (Integer.parseInt(Dnum) != DList.size()) {
			content += "  连线题差" + (Integer.parseInt(Dnum) - DList.size()) + "个";
		}
		if (Integer.parseInt(Enum) != EList.size()) {
			content += "  填空题差" + (Integer.parseInt(Enum) - EList.size()) + "个";
		}
		if (Integer.parseInt(Fnum) != FList.size()) {
			content += "  英语完型填空题差" + (Integer.parseInt(Fnum) - FList.size()) + "个";
		}
		if (Integer.parseInt(Mnum) != MList.size()) {
			content += "  复合题差" + (Integer.parseInt(Mnum) - MList.size()) + "个";
		}
		if (Integer.parseInt(Snum) != SList.size()) {
			content += "  主观题差" + (Integer.parseInt(Snum) - SList.size()) + "个";
		}
		if (content.trim().length() > 0) {
			httpServletRequest.setAttribute("content", content);
			return actionMapping.findForward("inteligence_error");
		} else {
			return paperPervier(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	public ActionForward infoSuccess(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("paperinfo_success");
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
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkPaperCarts(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
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
		TkPaperCart model = new TkPaperCart();
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
		TkPaperCartActionForm form = (TkPaperCartActionForm) actionForm;
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperCart model = form.getTkPaperCart();
				manager.addTkPaperCart(model);
				addLog(httpServletRequest, "增加了一个题库组卷试题蓝");
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
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkPaperCart model = manager.getTkPaperCart(objid);
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
		TkPaperCartActionForm form = (TkPaperCartActionForm) actionForm;
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperCart model = form.getTkPaperCart();
				manager.updateTkPaperCart(model);
				addLog(httpServletRequest, "修改了一个题库组卷试题蓝");
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
		TkPaperCartManager manager = (TkPaperCartManager) getBean("tkPaperCartManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkPaperCart(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}