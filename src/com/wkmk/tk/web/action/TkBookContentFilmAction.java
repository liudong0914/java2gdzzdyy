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
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.web.form.TkBookContentFilmActionForm;
import com.wkmk.util.common.Constants;

/**
 *<p>Description: 作业本微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAction extends BaseAction {

	/**
	 *审核，列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookcontentid", "=", bookcontentid);
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
			SearchCondition.addCondition(condition, "status", "=", "0");
		PageList page = manager.getPageTkBookContentFilms(condition, "a.orderindex,a.fid", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("title", title);
		return actionMapping.findForward("list");
	}
	
	/**
	 *管理，列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward listguanli(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(bookid)){
			SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
		}
		if(!"".equals(bookcontentid)){
			SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
		}
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		if(!"".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		PageList page = manager.getPageTkBookContentFilms(condition, "a.orderindex,a.fid", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("title", title);
		return actionMapping.findForward("listguanli");
	}

	
	/**
	 *我的微课，列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward mycontentfilmlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		HttpSession session = httpServletRequest.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(bookcontentid)){
            SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
        }
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		if(!"".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", userid);
		PageList page = manager.getPageTkBookContentFilms(condition, "a.orderindex,a.fid", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("status", status);
		return actionMapping.findForward("mycontentfilmlist");
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
		TkBookContentFilm model = new TkBookContentFilm();
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
		TkBookContentFilmActionForm form = (TkBookContentFilmActionForm)actionForm;
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilm model = form.getTkBookContentFilm();
				manager.addTkBookContentFilm(model);
				addLog(httpServletRequest,"增加了一个作业本微课");
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
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		try {
			TkBookContentFilm model = manager.getTkBookContentFilm(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
			e.printStackTrace();
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
		TkBookContentFilmActionForm form = (TkBookContentFilmActionForm)actionForm;
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		SysUserInfoManager userinfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUserInfo userInfo = userinfoManager.getSysUserInfo(userid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilm model = form.getTkBookContentFilm();
				model.setSysUserInfo(userInfo);
				manager.updateTkBookContentFilm(model);
				addLog(httpServletRequest,"修改了一个作业本微课");
			}catch (Exception e){
				e.printStackTrace();
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
	public ActionForward beforeUpdate1(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("bookid", bookid);
		try {
			TkBookContentFilm model = manager.getTkBookContentFilm(objid);
			httpServletRequest.setAttribute("act", "updateSave1");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
			e.printStackTrace();
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit1");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSave1(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmActionForm form = (TkBookContentFilmActionForm)actionForm;
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		SysUserInfoManager userinfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUserInfo userInfo = userinfoManager.getSysUserInfo(userid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilm model = form.getTkBookContentFilm();
				model.setSysUserInfo(userInfo);
				manager.updateTkBookContentFilm(model);
				addLog(httpServletRequest,"修改了一个作业本微课");
			}catch (Exception e){
			}
		}

		return listguanli(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdatemy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		try {
			TkBookContentFilm model = manager.getTkBookContentFilm(objid);
			httpServletRequest.setAttribute("act", "updateSavemy");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
			e.printStackTrace();
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("editmy");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSavemy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmActionForm form = (TkBookContentFilmActionForm)actionForm;
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		SysUserInfoManager userinfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUserInfo userInfo = userinfoManager.getSysUserInfo(userid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilm model = form.getTkBookContentFilm();
				model.setSysUserInfo(userInfo);
				manager.updateTkBookContentFilm(model);
				addLog(httpServletRequest,"修改了一个作业本微课");
			}catch (Exception e){
			}
		}

		return mycontentfilmlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentFilm model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			manager.delTkBookContentFilm(checkids[i]);
			addLog(httpServletRequest,"删除了一个微课程【" + model.getTitle() + "】信息");
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
	public ActionForward delBatchRecord1(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		TkBookContentFilm model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			manager.delTkBookContentFilm(checkids[i]);
			addLog(httpServletRequest,"删除了一个微课程【" + model.getTitle() + "】信息");
		}
		return listguanli(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *批量删除
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchRecordmy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentFilm model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			manager.delTkBookContentFilm(checkids[i]);
			addLog(httpServletRequest,"删除了一个微课程【" + model.getTitle() + "】信息");
		}
		return mycontentfilmlist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 *批量审核
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward checkBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentFilm model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			model.setStatus("1");
			manager.updateTkBookContentFilm(model);
			addLog(httpServletRequest,"审核了一个微课程【" + model.getTitle() + "】信息");
		}
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
		HttpSession session = httpServletRequest.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		// 查询学科
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
		// 查询相关作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		//SearchCondition.addCondition(condition, "status", "=", "1");//管理员功能不控制
		List<TkBookInfo> bookinfoList = bookinfoManager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
//			url = "/tkBookContentAction.do?method=list&bookid=" + bookinfo.getBookid() + "&parentno=0000";
			url="/tkBookContentFilmAction.do?method=listguanli&bookid=" + bookinfo.getBookid()+"&status=1";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		List<SearchModel> condition2 = new ArrayList<SearchModel>();
		List<TkBookContent> contentList = bookcontentManager.getTkBookContents(condition2, "contentno", 0);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			if(bookcontent.getParentno().length()>4){
				url = "/tkBookContentFilmAction.do?method=listguanli&bookcontentid=" + bookcontent.getBookcontentid()+"&status=1";	
			}else{
				url = "javascript:;";
			}
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno())
					.append("\"]=\"");
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
	 * 跳转到主工作区
	 */
	public ActionForward mycontentfilmmain(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("mycontentfilmmain");
	}

	/**
	 * 树型选择器
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward mycontentfilmtree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		Integer userid = (Integer) session.getAttribute("s_userid");
		
		// 查询学科
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getSubjectsByuserid(userid);
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
		// 查询相关作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<TkBookInfo> bookinfoList = bookinfoManager.getBookInfosByuserid(userid);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url="javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
//		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
//		List<TkBookContent> contentList2 = bookcontentManager.getBookContentByuserid2(userid);
//		TkBookContent bookcontent2 = null;
//		for (int i = 0; i < contentList2.size(); i++) {
//			bookcontent2 = (TkBookContent) contentList2.get(i);
//			text = bookcontent2.getTitle();
//			url = "javascript:;";
//			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent2.getBookid() + "0000" + "_" + bookcontent2.getBookid() + bookcontent2.getContentno())
//					.append("\"]=\"");
//			if (text != null && !"".equals(text.trim())) {
//				tree.append("text:").append(text).append(";");
//			}
//			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
//		}
		
		//查询相关作业本内容
		List<TkBookContent> contentList = bookcontentManager.getBookContentByuserid(userid);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentFilmAction.do?method=mycontentfilmlist&bookcontentid=" + bookcontent.getBookcontentid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno())
					.append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("mycontentfilmtree");
	}
}