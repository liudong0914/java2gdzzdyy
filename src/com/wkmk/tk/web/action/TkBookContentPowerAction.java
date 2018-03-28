package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentPower;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentPowerManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.web.form.TkBookContentPowerActionForm;
import com.wkmk.util.common.Constants;

/**
 * <p>
 * Description: 作业本内容校验授权
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentPowerAction extends BaseAction {

	/**
	 * 显示用户列表
	 * */
	public ActionForward userlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "usertype", "=", 0);
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		SearchCondition.addCondition(condition, "sex", "=", sex);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("type", type);
		return actionMapping.findForward("userlist");
	}

	/**
	 * 微课审核授权，显示用户列表
	 * */
	public ActionForward vwhuserlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "usertype", "=", 0);
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		SearchCondition.addCondition(condition, "sex", "=", sex);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("type", type);
		return actionMapping.findForward("vwhuserlist");
	}
	/**
	 * 工作区
	 * */
	public ActionForward contentMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("userid", Encode.nullToBlank(httpServletRequest.getParameter("userid")));
		httpServletRequest.setAttribute("type", Encode.nullToBlank(httpServletRequest.getParameter("type")));
		return actionMapping.findForward("main");
	}

	/**
	 * 树结构
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getAllSubjectByUnitType(sysUnitinfo.getType());
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		//SearchCondition.addCondition(condition, "status", "=", 1);//管理员功能不控制
		List<TkBookInfo> bookinfoList = bookinfoManager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url = "/tkBookContentPowerAction.do?method=list&userid=" + userid + "&type=" + type + "&bookid=" + bookinfo.getBookid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
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
		TkBookContentPowerManager manager = (TkBookContentPowerManager) getBean("tkBookContentPowerManager");
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "bookcontentid asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		List bookcontent = bookcontentManager.getAllBookContentByBook2(bookid, sorderindex);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "userid", "=", userid);
		SearchCondition.addCondition(condition, "type", "=", type);
		List userbookcontent = manager.getTkBookContentPowers(condition, "powerid asc", 0);
		for (int i = 0; i < bookcontent.size(); i++) {
			TkBookContent tkbookcontent = (TkBookContent) bookcontent.get(i);
			for (int j = 0; j < userbookcontent.size(); j++) {
				if (tkbookcontent.getBookcontentid().equals(((TkBookContentPower) userbookcontent.get(j)).getTkBookContent().getBookcontentid())) {
					tkbookcontent.setFlags("true");
				}
			}
		}
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("bookContent", bookcontent);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("userid", userid);
		saveToken(httpServletRequest);
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
		TkBookContentPower model = new TkBookContentPower();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

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
		TkBookContentPowerManager manager = (TkBookContentPowerManager) getBean("tkBookContentPowerManager");
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		TkBookInfoManager bookManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String[] bookcontent = httpServletRequest.getParameterValues("checkid");
		if (isTokenValid(httpServletRequest, true)) {
			TkBookInfo book = bookManager.getTkBookInfo(bookid);
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookid", "=", Integer.parseInt(bookid));
			List<TkBookContent> bookcontentlist = bookcontentManager.getTkBookContents(condition, "bookcontentid", 0);
			if (bookcontent != null) {
				for (TkBookContent tb : bookcontentlist) {
					for (int i = 0; i < bookcontent.length; i++) {
						if (tb.getBookcontentid().toString().equals(bookcontent[i])) {
							bookcontentlist.remove(tb);
							break;
						}
					}
				}
			}
			for (TkBookContent tb : bookcontentlist) {
				manager.delTkBookContentPower(Integer.parseInt(userid), tb.getBookcontentid(), type);
			}
			if (bookcontent != null) {
				for (String bookcontentid : bookcontent) {
					TkBookContent bookcontentModel = bookcontentManager.getTkBookContent(bookcontentid);
					TkBookContentPower model = new TkBookContentPower();
					model.setTkBookContent(bookcontentModel);
					model.setSubjectid(book.getSubjectid());
					model.setUserid(Integer.parseInt(userid));
					model.setType(type);
					manager.addTkBookContentPower(model);
				}
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 跳转到作业本本内容
	 * */
	public ActionForward bookcontentmain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("type", Encode.nullToBlank(httpServletRequest.getParameter("type")));
		return actionMapping.findForward("bookcontent_main");
	}

	/**
	 * 树列表
	 * */
	public ActionForward booktree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		HttpSession session = httpServletRequest.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getSubjectsByPower(userid, type);
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<TkBookInfo> bookinfoList = bookinfoManager.getBookInfosByPower(userid, type);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		List<TkBookContent> contentList = bookcontentManager.getBookContentByPower(userid, type);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentPowerAction.do?method=questionlilst&paperid=" + bookcontent.getPaperid() + "&type=" + type + "&bookcontentid=" + bookcontent.getBookcontentid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + "0000" + "_" + bookcontent.getBookid() + bookcontent.getContentno())
					.append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("booktree");
	}

	/**
	 * 查询试题列表
	 * */
	public ActionForward questionlilst(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String paperid = Encode.nullToBlank(httpServletRequest.getParameter("paperid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList questionlist = papercontentManager.getQuestions(Integer.parseInt(paperid), questionno, title, difficult, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", questionlist);
		httpServletRequest.setAttribute("quesitonno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("paperid", paperid);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		return actionMapping.findForward("question_list");
	}
	/**
	 * 微课审核，跳转到作业本本内容
	 * */
	public ActionForward vwhbookcontentmain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("type", Encode.nullToBlank(httpServletRequest.getParameter("type")));
		return actionMapping.findForward("vwhbookcontent_main");
	}

	/**
	 * 微课审核，树列表
	 * */
	public ActionForward vwhbooktree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		HttpSession session = httpServletRequest.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getSubjectsByPower(userid, type);
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<TkBookInfo> bookinfoList = bookinfoManager.getBookInfosByPower(userid, type);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		List<TkBookContent> contentList = bookcontentManager.getBookContentByPower(userid, type);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentFilmAction.do?method=list&bookcontentid=" + bookcontent.getBookcontentid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + "0000" + "_" + bookcontent.getBookid() + bookcontent.getContentno())
					.append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("vwhbooktree");
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
		TkBookContentPowerManager manager = (TkBookContentPowerManager) getBean("tkBookContentPowerManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkBookContentPower model = manager.getTkBookContentPower(objid);
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
		TkBookContentPowerActionForm form = (TkBookContentPowerActionForm) actionForm;
		TkBookContentPowerManager manager = (TkBookContentPowerManager) getBean("tkBookContentPowerManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentPower model = form.getTkBookContentPower();
				manager.updateTkBookContentPower(model);
				addLog(httpServletRequest, "修改了一个作业本内容校验授权");
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
		TkBookContentPowerManager manager = (TkBookContentPowerManager) getBean("tkBookContentPowerManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentPower(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	

}