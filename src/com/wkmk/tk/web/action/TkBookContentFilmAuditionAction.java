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
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilmAudition;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentFilmAuditionManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.web.form.TkBookContentFilmAuditionActionForm;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.vwh.service.VwhFilmPixManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 试听解题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid=Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = manager.getPageTkBookContentFilmAuditionsByFilm(bookcontentid,title, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("title", title);
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
		String bookcontentid=Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = manager.getContentFilm(bookcontentid,title,pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		return actionMapping.findForward("bookcontentfilmlist");
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
		String[] checkids=httpServletRequest.getParameterValues("checkid");
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		for (int i = 0; i < checkids.length; i++) {
			TkBookContentFilmAudition bcfa=new TkBookContentFilmAudition();
			bcfa.setContentfilmid(Integer.parseInt(checkids[i]));
			bcfa.setHits(0);
			bcfa.setOrderindex(manager.getMaxOrderIndex()+1);
			bcfa.setStatus("1");
			manager.addTkBookContentFilmAudition(bcfa);
		}
		
		//删除微信端缓存，重新获取最新数据
		CacheUtil.deleteObject("TkBookContentFilmAudition_List_page_0");
		
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
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		String bookcontentid=Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkBookContentFilmAudition model = manager.getTkBookContentFilmAudition(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("bookcontentid", "bookcontentid");
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
		TkBookContentFilmAuditionActionForm form = (TkBookContentFilmAuditionActionForm)actionForm;
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilmAudition model = form.getTkBookContentFilmAudition();
				manager.updateTkBookContentFilmAudition(model);
				addLog(httpServletRequest,"修改了一个试听解题微课");
			}catch (Exception e){
			}
		}
		
		//删除微信端缓存，重新获取最新数据
		CacheUtil.deleteObject("TkBookContentFilmAudition_List_page_0");
		
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
		TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentFilmAudition(checkids[i]);
			addLog(httpServletRequest,"删除了一个试听解题微课");
		}
		
		//删除微信端缓存，重新获取最新数据
		CacheUtil.deleteObject("TkBookContentFilmAudition_List_page_0");
		
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
			url="javascript:;";
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
			url = "/tkBookContentFilmAuditionAction.do?method=list&bookcontentid=" + bookcontent.getBookcontentid();
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
	
	//微课视频列表
	public ActionForward filmlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
		
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "filmid", "=", filmid);
		if(!"".equals(name)){
			SearchCondition.addCondition(condition, "name", "like", "%" + name + "%");	
		}
		if(!"".equals(convertstatus)){
			SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);	
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageVwhFilmPixs(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("filmid", filmid);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("convertstatus", convertstatus);
		return actionMapping.findForward("filmlist");
	}
}