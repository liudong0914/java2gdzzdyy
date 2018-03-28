package com.wkmk.tk.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.file.zip.AntZipFile;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.tk.bo.TkQuestionsFilm;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsKnopoint;
import com.wkmk.tk.bo.TkQuestionsSimilar;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkQuestionsFilmManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsKnopointManager;
import com.wkmk.tk.service.TkQuestionsSimilarManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.tk.web.form.TkQuestionsInfoActionForm;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.util.oss.OssUtil;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.service.VwhFilmInfoManager;

/**
 * <p>
 * Description: 题库试题信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsInfoAction extends BaseAction {

	/**
	 * 跳转到工作区
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 页面右工作区
	 * **/
	public ActionForward tab(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String result = "error";
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List questiontypes = typeManager.getTkQuestionsTypes(condition, "typeno", 0);
		httpServletRequest.setAttribute("questiontypes", questiontypes);
		if (questiontypes != null && questiontypes.size() > 0) {
			httpServletRequest.setAttribute("questiontypeid", ((TkQuestionsType) questiontypes.get(0)).getTypeid());
			result = "tab";
		}
		httpServletRequest.setAttribute("subjectid", subjectid);
		return actionMapping.findForward(result);
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
			url = "/tkQuestionsInfoAction.do?method=tab&subjectid=" + esi.getSubjectid();
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
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String grade = Encode.nullToBlank(httpServletRequest.getParameter("grade"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "tkQuestionsType.typeid", "=", Integer.parseInt(questiontypeid));
		if (!"".equals(grade)) {
			SearchCondition.addCondition(condition, "gradeid", "=", Integer.parseInt(grade));
		}
		if (!"".equals(title)) {
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		if (!"".equals(tag)) {
			SearchCondition.addCondition(condition, "tag", "like", "%" + tag + "%");
		}
		SearchCondition.addCondition(condition, "parentid", "=", 0);
		if (!"".equals(difficult)) {
			SearchCondition.addCondition(condition, "difficult", "=", difficult);
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkQuestionsInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		List parentids = manager.getAllparentids();
		TkQuestionsInfo model = null;
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkQuestionsInfo) dataList.get(i);
			if (parentids.contains(model.getQuestionid())) {
				model.setFlags("disabled=\"disabled\"");
			}
		}
		condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List gradeinfoLists = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
		httpServletRequest.setAttribute("tag", tag);
		httpServletRequest.setAttribute("grade", grade);
		httpServletRequest.setAttribute("gradeLists", gradeinfoLists);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("questiontypeid", questiontypeid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
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
		String parentid = Encode.nullToBlank(httpServletRequest.getParameter("parentid"));
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager quesManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		TkQuestionsInfo model = new TkQuestionsInfo();
		String unitid = Encode.nullToBlank(httpServletRequest.getSession().getAttribute("s_unitid"));
		String result = "";
		// 单选题
		if ("A".equals(tkQuestionType.getType())) {
			// 跳转到单选题编辑页面
			httpServletRequest.setAttribute("act", "addSave_A");
			if (parentid == null || parentid.trim().length() <= 0) {
				model.setParentid(0);
			} else {
				TkQuestionsInfo parentmodel = quesManager.getTkQuestionsInfo(parentid);
				model.setParentid(Integer.parseInt(parentid));
				model.setGradeid(parentmodel.getGradeid());
				model.setArea(parentmodel.getArea());
				model.setTheyear(parentmodel.getTheyear());
			}
			model.setOptionnum(4);
			result = "a_edit";
			// 多选题
		} else if ("B".equals(tkQuestionType.getType())) {
			// 跳转到多选题编辑页面
			httpServletRequest.setAttribute("act", "addSave_B");
			if (parentid == null || parentid.trim().length() <= 0) {
				model.setParentid(0);
			} else {
				TkQuestionsInfo parentmodel = quesManager.getTkQuestionsInfo(parentid);
				model.setParentid(Integer.parseInt(parentid));
				model.setGradeid(parentmodel.getGradeid());
				model.setArea(parentmodel.getArea());
				model.setTheyear(parentmodel.getTheyear());
			}
			model.setOptionnum(4);
			result = "b_edit";
			// 判断题
		} else if ("C".equals(tkQuestionType.getType())) {
			httpServletRequest.setAttribute("act", "addSave_C");
			if (parentid == null || parentid.trim().length() <= 0) {
				model.setParentid(0);
			} else {
				TkQuestionsInfo parentmodel = quesManager.getTkQuestionsInfo(parentid);
				model.setParentid(Integer.parseInt(parentid));
				model.setGradeid(parentmodel.getGradeid());
				model.setArea(parentmodel.getArea());
				model.setTheyear(parentmodel.getTheyear());
			}
			model.setRightans("1");
			result = "c_edit";
			// 填空题
		} else if ("E".equals(tkQuestionType.getType())) {
			httpServletRequest.setAttribute("act", "addSave_E");
			model.setOptionnum(1);
			model.setIsrightans("0");
			if (parentid == null || parentid.trim().length() <= 0) {
				model.setParentid(0);
			} else {
				TkQuestionsInfo parentmodel = quesManager.getTkQuestionsInfo(parentid);
				model.setParentid(Integer.parseInt(parentid));
				model.setGradeid(parentmodel.getGradeid());
				model.setArea(parentmodel.getArea());
				model.setTheyear(parentmodel.getTheyear());
			}
			result = "e_edit";
			// 英语完型填空题
		} else if ("F".equals(tkQuestionType.getType())) {
			httpServletRequest.setAttribute("act", "addSave_F");
			model.setIsrightans("1");
			result = "f_edit";
			// 复合题
		} else if ("M".equals(tkQuestionType.getType())) {
			httpServletRequest.setAttribute("act", "addSave_M");
			model.setIsrightans("1");
			result = "m_edit";
			// 主观题
		} else if ("S".equals(tkQuestionType.getType())) {
			httpServletRequest.setAttribute("act", "addSave_S");
			if (parentid == null || parentid.trim().length() <= 0) {
				model.setParentid(0);
			} else {
				model.setParentid(Integer.parseInt(parentid));
			}
			model.setIsrightans("0");
			result = "s_edit";
		}
		model.setSubjectid(Integer.parseInt(subjectid));
		model.setUnitid(Integer.parseInt(unitid));
		model.setTkQuestionsType(tkQuestionType);
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("questiontypeid", questiontypeid);
		// 查询年级
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		List gradeinfoLists = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
		httpServletRequest.setAttribute("gradeLists", gradeinfoLists);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("questiontypeid", questiontypeid);
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		saveToken(httpServletRequest);
		return actionMapping.findForward(result);
	}

	/**
	 * 单选题增加时保存
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
	public ActionForward addSave_A(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("O");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				for (int i = 10; i > model.getOptionnum(); i--) {
					if (i == 10) {
						model.setOption10("");
					} else if (i == 9) {
						model.setOption9("");
					} else if (i == 8) {
						model.setOption8("");
					} else if (i == 7) {
						model.setOption7("");
					} else if (i == 6) {
						model.setOption6("");
					} else if (i == 5) {
						model.setOption5("");
					} else if (i == 4) {
						model.setOption4("");
					} else if (i == 3) {
						model.setOption3("");
					} else if (i == 2) {
						model.setOption2("");
					} else if (i == 1) {
						model.setOption1("");
					}
				}
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 多选题增加时保存
	 * */
	public ActionForward addSave_B(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		String rightans = Encode.nullToBlank(httpServletRequest.getParameter("rightans"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("O");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				rightans = rightans.replaceAll(",", "");
				model.setRightans(rightans);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				for (int i = 10; i > model.getOptionnum(); i--) {
					if (i == 10) {
						model.setOption10("");
					} else if (i == 9) {
						model.setOption9("");
					} else if (i == 8) {
						model.setOption8("");
					} else if (i == 7) {
						model.setOption7("");
					} else if (i == 6) {
						model.setOption6("");
					} else if (i == 5) {
						model.setOption5("");
					} else if (i == 4) {
						model.setOption4("");
					} else if (i == 3) {
						model.setOption3("");
					} else if (i == 2) {
						model.setOption2("");
					} else if (i == 1) {
						model.setOption1("");
					}
				}
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 判断新增时候保存
	 * */
	public ActionForward addSave_C(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("O");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 填空题新增时保存
	 * */
	public ActionForward addSave_E(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("S");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				String socre = "";
				for (int i = 1; i <= 10; i++) {
					String tempscore = httpServletRequest.getParameter("cankao" + i);
					if (tempscore != null && !"".equals(tempscore)) {
						if (i == 1) {
							socre = tempscore;
						} else {
							socre += "【】" + tempscore;
						}
					}
				}
				String option1 = "";
				String option2 = "";
				String option3 = "";
				String option4 = "";
				String option5 = "";
				String option6 = "";
				String option7 = "";
				String option8 = "";
				String option9 = "";
				String option10 = "";
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("A" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option1 = name;
						} else {
							option1 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("B" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option2 = name;
						} else {
							option2 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("C" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option3 = name;
						} else {
							option3 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("D" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option4 = name;
						} else {
							option4 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("E" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option5 = name;
						} else {
							option5 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("F" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option6 = name;
						} else {
							option6 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("G" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option7 = name;
						} else {
							option7 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("H" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option8 = name;
						} else {
							option8 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("I" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option9 = name;
						} else {
							option9 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("J" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option10 = name;
						} else {
							option10 += "【或】" + name;
						}
					}
				}
				if (!"".equals(option1)) {
					model.setOption1(option1);
				}
				if (!"".equals(option2)) {
					model.setOption2(option2);
				}
				if (!"".equals(option3)) {
					model.setOption3(option3);
				}
				if (!"".equals(option4)) {
					model.setOption4(option4);
				}
				if (!"".equals(option5)) {
					model.setOption5(option5);
				}
				if (!"".equals(option6)) {
					model.setOption6(option6);
				}
				if (!"".equals(option7)) {
					model.setOption7(option7);
				}
				if (!"".equals(option8)) {
					model.setOption8(option8);
				}
				if (!"".equals(option9)) {
					model.setOption9(option9);
				}
				if (!"".equals(option10)) {
					model.setOption10(option10);
				}
				String rights = model.getOption1();
				if (model.getOption2() != null && !"".equals(model.getOption2())) {
					rights += "【】" + model.getOption2();
				}
				if (model.getOption3() != null && !"".equals(model.getOption3())) {
					rights += "【】" + model.getOption3();
				}
				if (model.getOption4() != null && !"".equals(model.getOption4())) {
					rights += "【】" + model.getOption4();
				}
				if (model.getOption5() != null && !"".equals(model.getOption5())) {
					rights += "【】" + model.getOption5();
				}
				if (model.getOption6() != null && !"".equals(model.getOption6())) {
					rights += "【】" + model.getOption6();
				}
				if (model.getOption7() != null && !"".equals(model.getOption7())) {
					rights += "【】" + model.getOption7();
				}
				if (model.getOption8() != null && !"".equals(model.getOption8())) {
					rights += "【】" + model.getOption8();
				}
				if (model.getOption9() != null && !"".equals(model.getOption9())) {
					rights += "【】" + model.getOption9();
				}
				if (model.getOption10() != null && !"".equals(model.getOption10())) {
					rights += "【】" + model.getOption10();
				}
				model.setRightans(rights);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setScore(socre);
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					isUpdateisrightans(model.getParentid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 英语完型填空新增时保存
	 * */
	public ActionForward addSave_F(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsInfo model = form.getTkQuestionsInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("S");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	// 主观题新增时保存
	public ActionForward addSave_S(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("S");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 复合题新增时保存
	 * */
	public ActionForward addSave_M(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(questiontypeid);
		SysUserInfo userinfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsInfo model = form.getTkQuestionsInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCretatdate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				model.setQuestiontype("S");
				model.setDoctype("0");
				model.setTkQuestionsType(tkQuestionType);
				manager.addTkQuestionsInfo(model);
				String questionno = "";
				for (int i = model.getQuestionid().toString().length(); i < 10; i++) {
					questionno += "0";
				}
				questionno += model.getQuestionid();
				model.setQuestionno(questionno);
				model.setAuthorid(userinfo.getUserid());
				model.setAuthorname(userinfo.getUsername());
				model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, httpServletRequest));
				model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, httpServletRequest));
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		String result = "";
		try {
			TkQuestionsInfo model = manager.getTkQuestionsInfo(objid);
			// 单选题
			if ("A".equals(model.getTkQuestionsType().getType())) {
				result = "a_edit";
				httpServletRequest.setAttribute("act", "updateSave_A");
				// 多选题
			} else if ("B".equals(model.getTkQuestionsType().getType())) {
				result = "b_edit";
				httpServletRequest.setAttribute("act", "updateSave_B");
				// 判断题
			} else if ("C".equals(model.getTkQuestionsType().getType())) {
				result = "c_edit";
				httpServletRequest.setAttribute("act", "updateSave_C");
				// 填空题
			} else if ("E".equals(model.getTkQuestionsType().getType())) {
				if ("1".equals(model.getIsrightans())) {
					result = "e_edit3";
				} else {
					result = "e_edit2";
				}
				httpServletRequest.setAttribute("act", "updateSave_E");
				// 英语完型填空题
			} else if ("F".equals(model.getTkQuestionsType().getType())) {
				result = "f_edit";
				httpServletRequest.setAttribute("act", "updateSave_F");
				// 复合题
			} else if ("M".equals(model.getTkQuestionsType().getType())) {
				result = "m_edit";
				httpServletRequest.setAttribute("act", "updateSave_M");
				// 复合题
			} else if ("S".equals(model.getTkQuestionsType().getType())) {
				result = "s_edit";
				httpServletRequest.setAttribute("act", "updateSave_S");
			}
			httpServletRequest.setAttribute("model", model);
			// 查询年级
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", model.getSubjectid());
			List gradeinfoLists = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
			httpServletRequest.setAttribute("gradeLists", gradeinfoLists);
			httpServletRequest.setAttribute("subjectid", model.getSubjectid());
			httpServletRequest.setAttribute("questiontypeid", model.getTkQuestionsType().getTypeid());
			String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
			String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
			String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
			httpServletRequest.setAttribute("questionno", questionno);
			httpServletRequest.setAttribute("title", title);
			httpServletRequest.setAttribute("difficult", difficult);
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			// 查询知识点
			TkQuestionsKnopointManager knopointManager = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
			List knopointlist = knopointManager.getEduKnopointInfoByQuestionid(model.getQuestionid());
			StringBuffer knopointids = new StringBuffer();
			StringBuffer knopointnames = new StringBuffer();
			if (knopointlist != null && knopointlist.size() > 0) {
				EduKnopointInfo eki = null;
				for (int i = 0, size = knopointlist.size(); i < size; i++) {
					eki = (EduKnopointInfo) knopointlist.get(i);
					knopointids.append(";").append(eki.getKnopointid());
					knopointnames.append(";").append(eki.getTitle());
				}
				httpServletRequest.setAttribute("knopointids", knopointids.substring(1));
				httpServletRequest.setAttribute("knopointnames", knopointnames.substring(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward(result);
	}

	/**
	 * 单选题修改时保存
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
	public ActionForward updateSave_A(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				for (int i = 10; i > model.getOptionnum(); i--) {
					if (i == 10) {
						model.setOption10("");
					} else if (i == 9) {
						model.setOption9("");
					} else if (i == 8) {
						model.setOption8("");
					} else if (i == 7) {
						model.setOption7("");
					} else if (i == 6) {
						model.setOption6("");
					} else if (i == 5) {
						model.setOption5("");
					} else if (i == 4) {
						model.setOption4("");
					} else if (i == 3) {
						model.setOption3("");
					} else if (i == 2) {
						model.setOption2("");
					} else if (i == 1) {
						model.setOption1("");
					}
				}
				manager.updateTkQuestionsInfo(model);
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					// 清空缓存
					CacheUtil.deleteObject("TkQuestionsInfo_" + model.getParentid());
					CacheUtil.deleteList("TkQuestionsInfoChildList_" + model.getParentid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 多选题修改时保存
	 * */
	public ActionForward updateSave_B(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		String rightans = Encode.nullToBlank(httpServletRequest.getParameter("rightans"));
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				model.setRightans(rightans);
				for (int i = 10; i > model.getOptionnum(); i--) {
					if (i == 10) {
						model.setOption10("");
					} else if (i == 9) {
						model.setOption9("");
					} else if (i == 8) {
						model.setOption8("");
					} else if (i == 7) {
						model.setOption7("");
					} else if (i == 6) {
						model.setOption6("");
					} else if (i == 5) {
						model.setOption5("");
					} else if (i == 4) {
						model.setOption4("");
					} else if (i == 3) {
						model.setOption3("");
					} else if (i == 2) {
						model.setOption2("");
					} else if (i == 1) {
						model.setOption1("");
					}
				}
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					// 清空缓存
					CacheUtil.deleteObject("TkQuestionsInfo_" + model.getParentid());
					CacheUtil.deleteList("TkQuestionsInfoChildList_" + model.getParentid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 判断题修改时保存
	 * */
	public ActionForward updateSave_C(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				manager.updateTkQuestionsInfo(model);
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "O");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					// 清空缓存
					CacheUtil.deleteObject("TkQuestionsInfo_" + model.getParentid());
					CacheUtil.deleteList("TkQuestionsInfoChildList_" + model.getParentid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 填空题修改时保存
	 * */
	public ActionForward updateSave_E(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				model.setOptionnum(Integer.parseInt(httpServletRequest.getParameter("optionnum")));
				String socre = "";
				for (int i = 1; i <= 10; i++) {
					String tempscore = httpServletRequest.getParameter("cankao" + i);
					if (tempscore != null && !"".equals(tempscore)) {
						if (i == 1) {
							socre = tempscore;
						} else {
							socre += "【】" + tempscore;
						}
					}
				}
				String option1 = "";
				String option2 = "";
				String option3 = "";
				String option4 = "";
				String option5 = "";
				String option6 = "";
				String option7 = "";
				String option8 = "";
				String option9 = "";
				String option10 = "";
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("A" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option1 = name;
						} else {
							option1 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("B" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option2 = name;
						} else {
							option2 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("C" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option3 = name;
						} else {
							option3 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("D" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option4 = name;
						} else {
							option4 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("E" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option5 = name;
						} else {
							option5 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("F" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option6 = name;
						} else {
							option6 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("G" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option7 = name;
						} else {
							option7 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("H" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option8 = name;
						} else {
							option8 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("I" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option9 = name;
						} else {
							option9 += "【或】" + name;
						}
					}
				}
				for (int i = 1; i <= 3; i++) {
					String name = httpServletRequest.getParameter("J" + i);
					if (name != null && !"".equals(name)) {
						if (i == 1) {
							option10 = name;
						} else {
							option10 += "【或】" + name;
						}
					}
				}
				if (!"".equals(option1)) {
					model.setOption1(option1);
				}
				if (!"".equals(option2)) {
					model.setOption2(option2);
				}
				if (!"".equals(option3)) {
					model.setOption3(option3);
				}
				if (!"".equals(option4)) {
					model.setOption4(option4);
				}
				if (!"".equals(option5)) {
					model.setOption5(option5);
				}
				if (!"".equals(option6)) {
					model.setOption6(option6);
				}
				if (!"".equals(option7)) {
					model.setOption7(option7);
				}
				if (!"".equals(option8)) {
					model.setOption8(option8);
				}
				if (!"".equals(option9)) {
					model.setOption9(option9);
				}
				if (!"".equals(option10)) {
					model.setOption10(option10);
				}
				String rights = model.getOption1();
				if (model.getOption2() != null && !"".equals(model.getOption2())) {
					rights += "【】" + model.getOption2();
				}
				if (model.getOption3() != null && !"".equals(model.getOption3())) {
					rights += "【】" + model.getOption3();
				}
				if (model.getOption4() != null && !"".equals(model.getOption4())) {
					rights += "【】" + model.getOption4();
				}
				if (model.getOption5() != null && !"".equals(model.getOption5())) {
					rights += "【】" + model.getOption5();
				}
				if (model.getOption6() != null && !"".equals(model.getOption6())) {
					rights += "【】" + model.getOption6();
				}
				if (model.getOption7() != null && !"".equals(model.getOption7())) {
					rights += "【】" + model.getOption7();
				}
				if (model.getOption8() != null && !"".equals(model.getOption8())) {
					rights += "【】" + model.getOption8();
				}
				if (model.getOption9() != null && !"".equals(model.getOption9())) {
					rights += "【】" + model.getOption9();
				}
				if (model.getOption10() != null && !"".equals(model.getOption10())) {
					rights += "【】" + model.getOption10();
				}
				model.setRightans(rights);
				model.setScore(socre);
				manager.updateTkQuestionsInfo(model);
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
				// 判断是否为子题，如果是子题修改父题分值
				if (!"0".equals(model.getParentid().toString())) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int i = 0; i < childrenQuestion.size(); i++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(i);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					isUpdateisrightans(model.getParentid());
					// 清空缓存
					CacheUtil.deleteObject("TkQuestionsInfo_" + model.getParentid());
					CacheUtil.deleteList("TkQuestionsInfoChildList_" + model.getParentid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 完型填空修改时保存
	 * */
	public ActionForward updateSave_F(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsInfo model = form.getTkQuestionsInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				manager.updateTkQuestionsInfo(model);
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 复合体修改时保存
	 * */
	public ActionForward updateSave_M(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsInfo model = form.getTkQuestionsInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				manager.updateTkQuestionsInfo(model);
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 主观题修改时保存
	 * */
	public ActionForward updateSave_S(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoActionForm form = (TkQuestionsInfoActionForm) actionForm;
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		TkQuestionsInfo model = form.getTkQuestionsInfo();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				model.setTkQuestionsType(typeManager.getTkQuestionsType(questiontypeid));
				manager.updateTkQuestionsInfo(model);
				CacheUtil.deleteObject("TkQuestionsInfo_" + model.getQuestionid());
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "题库试题信息");
				// 保存知识点
				if ("1".equals(httpServletRequest.getParameter("knopointidupdate"))) {
					saveKnopoints(model.getQuestionid(), httpServletRequest.getParameter("knopointid"), "S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 详情页面
	 * */
	public ActionForward detail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String id = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsInfo model = manager.getTkQuestionsInfo(id);
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		String result = "";
		// 单选题
		if ("A".equals(model.getTkQuestionsType().getType())) {
			result = "a_detail";
			// 多选题
		} else if ("B".equals(model.getTkQuestionsType().getType())) {
			result = "b_detail";
			// 判断题
		} else if ("C".equals(model.getTkQuestionsType().getType())) {
			result = "c_detail";
			// 填空题
		} else if ("E".equals(model.getTkQuestionsType().getType())) {
			if ("1".equals(model.getIsrightans())) {
				result = "e_detail";
			} else {
				result = "e_detail2";
			}
			// 英语完型填空
		} else if ("F".equals(model.getTkQuestionsType().getType())) {
			result = "f_detail";
			// 复合题
		} else if ("M".equals(model.getTkQuestionsType().getType())) {
			result = "m_detail";
			// 主观题
		} else if ("S".equals(model.getTkQuestionsType().getType())) {
			result = "s_detail";
		}
		httpServletRequest.setAttribute("model", model);
		String difficult[] = Constants.CODETYPE_DIFFICULT_ID;
		for (int i = 0; i < difficult.length; i++) {
			if (difficult[i].equals(model.getDifficult())) {
				httpServletRequest.setAttribute("difficultName", Constants.CODETYPE_DIFFICULT_NAME[i]);
			}
		}
		if ("1".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "正常");
		} else if ("2".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "禁用");
		}
		httpServletRequest.setAttribute("gradeName", gradeManager.getEduGradeInfo(model.getGradeid()).getGradename());
		// 查询知识点
		TkQuestionsKnopointManager knopointManager = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
		List knopointlist = knopointManager.getEduKnopointInfoByQuestionid(model.getQuestionid());
		StringBuffer knopointnames = new StringBuffer();
		if (knopointlist != null && knopointlist.size() > 0) {
			EduKnopointInfo eki = null;
			for (int i = 0, size = knopointlist.size(); i < size; i++) {
				eki = (EduKnopointInfo) knopointlist.get(i);
				knopointnames.append(";").append(eki.getTitle());
			}
			httpServletRequest.setAttribute("knopointnames", knopointnames.substring(1));
		}
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficults = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficults);
		return actionMapping.findForward(result);
	}

	/**
	 * 保存知识点
	 * */
	public void saveKnopoints(Integer questionid, String knopoint, String type) {
		TkQuestionsKnopointManager knopointManager = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
		knopointManager.deleteTkQuestionKnopoints(questionid);
		if (knopoint != null && !"".equals(knopoint)) {
			String[] knopoints = knopoint.split(";");
			for (int i = 0; i < knopoints.length; i++) {
				TkQuestionsKnopoint model = new TkQuestionsKnopoint();
				model.setQuestionid(questionid);
				model.setType(type);
				model.setKnopointid(Integer.parseInt(knopoints[i]));
				knopointManager.addTkQuestionsKnopoint(model);
			}
		}
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
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		TkQuestionsInfo model = null;
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				model = manager.getTkQuestionsInfo(checkids[i]);
				int parentid = model.getParentid();
				String type = model.getTkQuestionsType().getType();
				addLog(httpServletRequest, "删除了一个" + model.getTitle() + "题库试题信息");
				manager.delTkQuestionsInfo(checkids[i]);
				if ("E".equals(type) && parentid > 0) {
					isUpdateisrightans(parentid);
				}
				// 清空缓存
				CacheUtil.deleteObject("TkQuestionsInfo_" + checkids[i]);
				if (parentid > 0) {
					TkQuestionsInfo parentmodel = manager.getTkQuestionsInfo(model.getParentid());
					// 查询子题所有分数的总和
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "parentid", "=", parentmodel.getQuestionid());
					List childrenQuestion = manager.getTkQuestionsInfos(condition, "questionno  asc", 0);
					int score = 0;
					for (int k = 0; k < childrenQuestion.size(); k++) {
						TkQuestionsInfo cmodel = (TkQuestionsInfo) childrenQuestion.get(k);
						if ("E".equals(cmodel.getTkQuestionsType().getType())) {
							String[] scores = cmodel.getScore().split("【】");
							for (int j = 0; j < scores.length; j++) {
								score += Integer.parseInt(scores[j]);
							}
						} else {
							score += Integer.parseInt(cmodel.getScore());
						}
					}
					parentmodel.setScore(String.valueOf(score));
					manager.updateTkQuestionsInfo(parentmodel);
					// 清空缓存
					CacheUtil.deleteObject("TkQuestionsInfo_" + parentmodel.getQuestionid());
					CacheUtil.deleteList("TkQuestionsInfoChildList_" + parentmodel.getQuestionid());
				}
			}
			// 删除关联知识点
			TkQuestionsKnopointManager knopointManager = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
			knopointManager.deleteTkQuestionKnopoints(model.getQuestionid());
		}
		if (model != null && model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 子题管理
	 * */
	public ActionForward childreaQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String parentid = Encode.nullToBlank(httpServletRequest.getParameter("parentid"));
		String result = "error";
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		// SearchCondition.addCondition(condition, "type", "!=", "F");
		SearchCondition.addCondition(condition, "type", "!=", "M");
		// SearchCondition.addCondition(condition, "type", "!=", "S");
		List<TkQuestionsType> questiontypes = typeManager.getTkQuestionsTypes(condition, "typeno", 0);
		if (questiontypes != null && questiontypes.size() > 0) {
			httpServletRequest.setAttribute("questiontypeid", questiontypes.get(0).getTypeid());
			httpServletRequest.setAttribute("parentid", parentid);
			httpServletRequest.setAttribute("questiontypes", questiontypes);
			result = "childrentab";
		}
		return actionMapping.findForward(result);
	}

	/**
	 * 子题列表
	 * */
	public ActionForward childrenlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String id = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsInfo question = manager.getTkQuestionsInfo(id);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "parentid", "=", id);
		SearchCondition.addCondition(condition, "tkQuestionsType.typeid", "=", questiontypeid);
		SearchCondition.addCondition(condition, "questionno", "like", "%" + questionno + "%");
		SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		SearchCondition.addCondition(condition, "difficult", "=", difficult);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkQuestionsInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("parentid", id);
		httpServletRequest.setAttribute("questiontypeid", questiontypeid);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("subjectid", question.getSubjectid());
		return actionMapping.findForward("childerlist");
	}

	/**
	 * 禁用
	 * */
	public ActionForward updateStatus(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		TkQuestionsInfo model = null;
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				model = manager.getTkQuestionsInfo(checkids[i]);
				model.setStatus("2");
				manager.updateTkQuestionsInfo(model);
				addLog(httpServletRequest, "禁用了一个" + model.getTitle() + "题库试题信息");
			}
		}
		if (model != null && model.getParentid() != 0) {
			return childrenlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		} else {
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
	}

	/**
	 * 举一反三
	 * */
	public ActionForward similarQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		String typeid = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkQuestionsInfo questionmodel = manager.getTkQuestionsInfo(questionid);
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", questionmodel.getSubjectid());
		List typelist = typeManager.getTkQuestionsTypes(condition, "typeid", 0);
		httpServletRequest.setAttribute("typelist", typelist);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getAllsimilarQuestions(questionid, questionno, title, difficult, sorderindex, typeid, questionmodel.getSubjectid(), questionmodel.getGradeid(), pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("typeid", typeid);
		return actionMapping.findForward("similar_list");
	}

	/**
	 * 举一反三未关联列表
	 * */
	public ActionForward unsimilarQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title2"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno2"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult2"));
		String typeid = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkQuestionsInfo questionmodel = manager.getTkQuestionsInfo(questionid);
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", questionmodel.getSubjectid());
		List typelist = typeManager.getTkQuestionsTypes(condition, "typeid", 0);
		httpServletRequest.setAttribute("typelist", typelist);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getUnAllsimilarQuestions(questionid, questionno, title, difficult, sorderindex, typeid, questionmodel.getSubjectid(), questionmodel.getGradeid(), pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("typeid", typeid);
		return actionMapping.findForward("unsimilar_list");
	}

	/**
	 * 举一反三批量添加
	 * */
	public ActionForward batchaddSimilarQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsSimilarManager similarManager = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
		TkQuestionsSimilar model = new TkQuestionsSimilar();
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				model.setQuestionid(Integer.parseInt(questionid));
				model.setSimilarquestionid(Integer.parseInt(checkids[i]));
				similarManager.addTkQuestionsSimilar(model);
			}
		}
		return unsimilarQuestion(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 举一反三批量移除
	 * */
	public ActionForward bathdelsimilarQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsSimilarManager similarManager = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				similarManager.delTkQuestionsSimilars(questionid, checkids[i]);
			}
		}
		return similarQuestion(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 已关联微课列表
	 * */
	public ActionForward filmInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = " orderindex asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getAllfimInfo(questionid, title, actor, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("actor", actor);
		return actionMapping.findForward("filmInfo");
	}

	/**
	 * 未关联微课
	 * */
	public ActionForward unfilmInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));
		TkQuestionsInfo questioninfo = manager.getTkQuestionsInfo(questionid);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = " orderindex asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getUnAllfimInfo(questionid, title, actor, questioninfo.getGradeid().toString(), sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("actor", actor);
		return actionMapping.findForward("unfilmInfo");
	}

	/**
	 * 批量添加关联微课
	 * */
	public ActionForward batchaddFilminfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		TkQuestionsFilm model = new TkQuestionsFilm();
		TkQuestionsFilmManager manager = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				model.setQuestionid(Integer.parseInt(questionid));
				model.setFilmid(Integer.parseInt(checkids[i]));
				manager.addTkQuestionsFilm(model);
			}
		}
		return unfilmInfo(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 移除微课
	 * */
	public ActionForward delBatchFilms(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		TkQuestionsFilmManager manager = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkQuestionsFilms(questionid, checkids[i]);
		}
		return filmInfo(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 微课详情
	 * */
	public ActionForward filmDetail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
		VwhFilmInfoManager manager = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
		VwhFilmInfo model = manager.getVwhFilmInfo(filmid);
		if ("0".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "待审核");
		} else if ("1".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "已审核");
		} else if ("2".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "禁用");
		} else if ("9".equals(model.getStatus())) {
			httpServletRequest.setAttribute("statusName", "删除");
		}
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("userName", model.getSysUserInfo().getUsername());
		return actionMapping.findForward("filmdetail");
	}

	/**
	 * 图片详情
	 * */
	public ActionForward picdetail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String url = Encode.nullToBlank(httpServletRequest.getParameter("url"));
		httpServletRequest.setAttribute("url", url);
		return actionMapping.findForward("picdetail");
	}

	/**
	 * 判断是否修改题是否有固定标准答案
	 * */
	public void isUpdateisrightans(int questionid) {
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		boolean blean = manager.getishaveIsrights(questionid);
		if (blean == false) {
			TkQuestionsInfo model = manager.getTkQuestionsInfo(questionid);
			if ("1".equals(model.getIsrightans())) {
				model.setIsrightans("0");
				manager.updateTkQuestionsInfo(model);
			}
		} else {
			TkQuestionsInfo model = manager.getTkQuestionsInfo(questionid);
			if ("0".equals(model.getIsrightans())) {
				model.setIsrightans("1");
				manager.updateTkQuestionsInfo(model);
			}
		}
	}

	/**
	 * 导出二维码
	 * */
	public ActionForward exportTwocode(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		EduSubjectInfoManager subjectManager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		EduSubjectInfo subject = subjectManager.getEduSubjectInfo(subjectid);
		subject.setSubjectname(subject.getSubjectname().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
		if (subject.getSubjectname().endsWith(".")) {
			subject.setSubjectname(subject.getSubjectname().substring(0, subject.getSubjectname().length() - 1) + "。");
		}
		String targetfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "uploadtemp/exportQuestion/" + subject.getSubjectname() + "/";
		// String targetfile =
		// httpServletRequest.getSession().getServletContext().getRealPath("/")
		// + "upload/exportQuestion/" + subject.getSubjectname() + "/";
		File targetFile = new File(targetfile);
		if (!targetFile.exists() && !targetFile.isDirectory()) {
			targetFile.mkdirs();
		} else {
			deleteDirectory(targetfile);
			targetFile.mkdirs();
		}
		// 查询学科下的年级
		List<Integer> gradeList = manager.getGradeids(Integer.parseInt(subjectid));
		try {
			// 根据查询条件导出试题
			String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
			String grade = Encode.nullToBlank(httpServletRequest.getParameter("grade"));
			String questiontypeid = Encode.nullToBlank(httpServletRequest.getParameter("questiontypeid"));
			for (int i = 0; i < gradeList.size(); i++) {
				if (!"".equals(grade) && !grade.equals(gradeList.get(i).toString())) {
					continue;
				}
				EduGradeInfo grademodel = gradeManager.getEduGradeInfo(gradeList.get(i));
				grademodel.setGradename(grademodel.getGradename().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
				if (grademodel.getGradename().endsWith(".")) {
					grademodel.setGradename(grademodel.getGradename().substring(0, grademodel.getGradename().length() - 1) + "。");
				}
				String gradetargetfile = targetfile + grademodel.getGradename() + "/";
				File gradetargetFile = new File(gradetargetfile);
				if (!gradetargetFile.exists() && !gradetargetFile.isDirectory()) {
					gradetargetFile.mkdirs();
				}
				// 查询年级下的试题
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
				SearchCondition.addCondition(condition, "gradeid", "=", grademodel.getGradeid());
				SearchCondition.addCondition(condition, "tkQuestionsType.typeid", "=", Integer.parseInt(questiontypeid));
				if (!"".equals(title)) {
					SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
				}
				List<TkQuestionsInfo> questionList = manager.getTkQuestionsInfos(condition, "questionno", 0);
				for (TkQuestionsInfo model : questionList) {
					// System.out.println(model.getTitle()+">>>>>>>>>>>>>>>......");
					// String filmtwocodepath =
					// httpServletRequest.getSession().getServletContext().getRealPath("/")
					// + "upload/" + model.getFilmtwocodepath();
					// String similartwocodepath =
					// httpServletRequest.getSession().getServletContext().getRealPath("/")
					// + "upload/" + model.getSimilartwocodepath();
					model.setTitle(model.getTitle().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
					if (model.getTitle().endsWith(".")) {
						model.setTitle(model.getTitle().substring(0, model.getTitle().length() - 1) + "。");
					}
					String questiontargetfile = targetfile + grademodel.getGradename() + "/" + model.getTitle() + "/";
					File questiontargetFile = new File(questiontargetfile);
					if (!questiontargetFile.exists() && !questiontargetFile.isDirectory()) {
						questiontargetFile.mkdirs();
					}
					String filmtwocodepath = questiontargetfile + "微课.png";
					String similartwocodepath = questiontargetfile + "举一反三.png";
					OssUtil ot = new OssUtil();
					ot.ossFileDowmload(model.getFilmtwocodepath(), filmtwocodepath);
					ot.ossFileDowmload(model.getSimilartwocodepath(), similartwocodepath);
					// File filmFile = new File(filmtwocodepath);
					// File similarFile = new File(similartwocodepath);
					// copyFile(filmFile, new File(questiontargetfile +
					// "微课.png"));
					// copyFile(similarFile, new File(questiontargetfile +
					// "举一反三.png"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 自动下载zip文件包
		String uuid = UUID.getNewUUID();
		String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String rootpath = realpath + "/uploadtemp/exportQuestion";
		AntZipFile antZipFile = new AntZipFile();
		antZipFile.doZip(targetfile, rootpath, uuid + ".zip");
		// 下载包
		try {
			InputStream is = new FileInputStream(rootpath + "/" + uuid + ".zip");
			OutputStream os = httpServletResponse.getOutputStream();// 取得输出流
			httpServletResponse.reset();// 清空输出流
			httpServletResponse.setContentType("text/html; charset=utf-8");// text/html
			// application/octet-stream
			httpServletResponse.setContentType("bin");
			String name = subject.getSubjectname() + "试题.zip";
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file2 = new File(targetfile);
		deleteFile(file2);
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
				file.delete();
			}
			file.delete();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @throws IOException
	 * */
	public void copyFile(File sourcefile, File targetfile) throws IOException {
		if (sourcefile.isFile() && sourcefile.exists()) {
			FileInputStream input = new FileInputStream(sourcefile);
			BufferedInputStream inbuff = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(targetfile);
			BufferedOutputStream outbuff = new BufferedOutputStream(output);
			// 创建缓冲组
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = inbuff.read(b)) != -1) {
				outbuff.write(b, 0, len);
			}
			outbuff.flush();
			outbuff.close();
			output.close();
			inbuff.close();
			input.close();
		}
	}

	/**
	 * 删除文件
	 * */
	public boolean deleteFile(String path) {
		boolean falg = false;
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			file.delete();
			falg = true;
		}
		return falg;
	}

	/**
	 * 删除文件夹
	 * */
	public boolean deleteDirectory(String path) {
		File dirFile = new File(path);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean falg = false;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 是文件
				falg = deleteFile(files[i].getAbsolutePath());
				if (!falg) {
					break;
				}
			} else {
				falg = deleteDirectory(files[i].getAbsolutePath());
				if (!falg) {
					break;
				}
			}
		}
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 处理英语填空题导入bug
	 * */
	public ActionForward bug(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", 13);// 英语
			SearchCondition.addCondition(condition, "tkQuestionsType.typeid", "=", 16);// 填空题
			SearchCondition.addCondition(condition, "optionnum", "=", 0);// 有问题的填空题
			SearchCondition.addCondition(condition, "isrightans", "=", "1");// 有标准答案
			List list = manager.getTkQuestionsInfos(condition, "", 0);
			TkQuestionsInfo model = null;
			int optionnum = 0;
			String rightan = null;
			for (int i = 0, size = list.size(); i < size; i++) {
				model = (TkQuestionsInfo) list.get(i);
				rightan = model.getRightans();
				rightan = rightan.replaceAll("<p>", "").replaceAll("</p>", "");
				String[] rightans = model.getRightans().split("【】");
				if (rightans.length <= 10) {
					optionnum = rightans.length;
					model.setOptionnum(optionnum);
					model.setRightans(rightan);
					if (model.getScore().indexOf("【】") == -1 && optionnum > 1) {
						String value = model.getScore();
						double result;
						double yu;
						if (value.indexOf(".") == -1) {
							value = value + ".0";
						}
						// 如果可以除尽则以小数呈现，比如0.5
						double tempsum = Math.floor(Double.valueOf(value).doubleValue());
						result = tempsum / optionnum;
						if ((result + "").length() > 4) {
							double xiaoshu = Double.parseDouble(value.substring(value.indexOf(".")));
							int sum = Double.valueOf(value).intValue();
							result = sum / optionnum;
							yu = sum % optionnum + xiaoshu;
						} else {
							// 可以除尽就没有余数
							yu = Double.parseDouble(value.substring(value.indexOf(".")));
						}
						String sresult = "";
						for (int k = 0; k < optionnum; k++) {
							if (k == optionnum - 1) {
								sresult += (yu + result);
							} else {
								sresult += result + "【】";
							}
						}
						model.setScore(sresult);
					}
					manager.updateTkQuestionsInfo(model);
				} else {
					model.setIsrightans("0");
					manager.updateTkQuestionsInfo(model);
				}
			}
		} catch (Exception e) {
			System.out.println("试题处理失败!");
			e.printStackTrace();
			String promptinfo = "试题处理失败! 【失败原因】" + e.getMessage();
			httpServletRequest.setAttribute("promptinfo", promptinfo);
			return actionMapping.findForward("failure");
		}
		System.out.println("试题处理成功!");
		httpServletRequest.setAttribute("promptinfo", "试题处理成功!");
		return actionMapping.findForward("success");
	}
}