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
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkUserBookContent;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkUserBookContentManager;
import com.wkmk.tk.web.form.TkUserBookContentActionForm;

/**
 *<p>Description: 客户端用户作业本内容授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserBookContentAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkUserBookContentManager manager = (TkUserBookContentManager)getBean("tkUserBookContentManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkUserBookContents(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		TkUserBookContent model = new TkUserBookContent();
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
		TkUserBookContentActionForm form = (TkUserBookContentActionForm)actionForm;
		TkUserBookContentManager manager = (TkUserBookContentManager)getBean("tkUserBookContentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkUserBookContent model = form.getTkUserBookContent();
				manager.addTkUserBookContent(model);
				addLog(httpServletRequest,"增加了一个客户端用户作业本内容授权");
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
		TkUserBookContentManager manager = (TkUserBookContentManager)getBean("tkUserBookContentManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkUserBookContent model = manager.getTkUserBookContent(objid);
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
		TkUserBookContentActionForm form = (TkUserBookContentActionForm)actionForm;
		TkUserBookContentManager manager = (TkUserBookContentManager)getBean("tkUserBookContentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkUserBookContent model = form.getTkUserBookContent();
				manager.updateTkUserBookContent(model);
				addLog(httpServletRequest,"修改了一个客户端用户作业本内容授权");
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
		TkUserBookContentManager manager = (TkUserBookContentManager)getBean("tkUserBookContentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkUserBookContent(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *学员列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward userList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "1");//已审核
		SearchCondition.addCondition(condition, "loginname", "<>", "~admin~");
		if(!"".equals(loginname)){
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if(!"".equals(sex) && !"-1".equals(sex)){
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if(!"".equals(usertype) && !"-1".equals(usertype)){
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		if(!"".equals(studentno)){
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		return actionMapping.findForward("userlist");
	}
	
	/**
	 * 跳转到工作区
	 * */
	public ActionForward bookContentMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("userid", Encode.nullToBlank(httpServletRequest.getParameter("userid")));
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		String userid=Encode.nullToBlank(httpServletRequest.getParameter("userid"));
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
		SearchCondition.addCondition(condition, "status", "=", 1);
		List<TkBookInfo> bookinfoList = bookinfoManager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle();
			url = "/tkUserBookContentAction.do?method=bookContentList&bookid=" + bookinfo.getBookid()+"&userid="+userid;
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000")
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
	public ActionForward bookContentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String userid=Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		
		TkBookContentManager manager=(TkBookContentManager)getBean("tkBookContentManager");
		TkUserBookContentManager ubcManager=(TkUserBookContentManager)getBean("tkUserBookContentManager");
		List bookContent=manager.getAllBookContentByBook(bookid);
		List userBookContent=ubcManager.findBookContentsByUser(userid);
		httpServletRequest.setAttribute("bookContent", bookContent);
		httpServletRequest.setAttribute("userBookContent", userBookContent);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("userid", userid);
		return actionMapping.findForward("bookContentList");
	}
	
	
	public ActionForward userAddBookContent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		TkUserBookContentManager manager = (TkUserBookContentManager) getBean("tkUserBookContentManager");
		TkBookInfoManager bookInfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String userid=Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String []bookcontent=httpServletRequest.getParameterValues("checkid");
		
		try {
			manager.delBookContentByUserAndBook(userid, bookid);
			TkBookInfo bookInfo=bookInfoManager.getTkBookInfo(bookid);
			for (String bc : bookcontent) {
				TkUserBookContent tkUserBookContent=new TkUserBookContent();
				tkUserBookContent.setBookcontentid(Integer.parseInt(bc));
				tkUserBookContent.setBookid(Integer.parseInt(bookid));
				tkUserBookContent.setGradeid(bookInfo.getGradeid());
				tkUserBookContent.setSubjectid(bookInfo.getSubjectid());
				tkUserBookContent.setUserid(Integer.parseInt(userid));
				manager.addTkUserBookContent(tkUserBookContent);
			}
		} catch (Exception e) {
		}
		httpServletRequest.setAttribute("promptinfo", "作业内容授权成功。");
		return actionMapping.findForward("success");
	}	
}