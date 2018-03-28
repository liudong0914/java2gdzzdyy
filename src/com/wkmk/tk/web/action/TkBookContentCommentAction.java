package com.wkmk.tk.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentComment;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentCommentManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.web.form.TkBookContentCommentActionForm;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 作业解题微课评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
@SuppressWarnings("unchecked")
public class TkBookContentCommentAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookid=Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String bookcontentid=Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String username=Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String type=Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String status=Encode.nullToBlank(httpServletRequest.getParameter("status"));
		TkBookContentCommentManager manager = (TkBookContentCommentManager)getBean("tkBookContentCommentManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(bookid)){
			SearchCondition.addCondition(condition, "b.bookid", "=", Integer.valueOf(bookid));
		}
		if(!"".equals(bookcontentid)){
			SearchCondition.addCondition(condition, "a.bookcontentid", "=", Integer.valueOf(bookcontentid));
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "a.sysUserInfo.username", "like", "%"+username+"%");
		}
		SearchCondition.addCondition(condition, "a.type", "=", type);
		SearchCondition.addCondition(condition, "a.status", "=", status);
		PageList page = manager.getPageTkBookContentComments1(condition, " a.createdate desc ", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("type", type);
		httpServletRequest.setAttribute("status", status);
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
		TkBookContentComment model = new TkBookContentComment();
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
		TkBookContentCommentActionForm form = (TkBookContentCommentActionForm)actionForm;
		TkBookContentCommentManager manager = (TkBookContentCommentManager)getBean("tkBookContentCommentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentComment model = form.getTkBookContentComment();
				manager.addTkBookContentComment(model);
				addLog(httpServletRequest,"增加了一个作业解题微课评价");
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
		TkBookContentCommentManager manager = (TkBookContentCommentManager)getBean("tkBookContentCommentManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		try {
			TkBookContentComment model = manager.getTkBookContentComment(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("bookid", bookid);
			httpServletRequest.setAttribute("bookcontentid", bookcontentid);
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
		TkBookContentCommentActionForm form = (TkBookContentCommentActionForm)actionForm;
		TkBookContentCommentManager manager = (TkBookContentCommentManager)getBean("tkBookContentCommentManager");
		SysUserInfoManager usermanager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userid=Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentComment model = form.getTkBookContentComment();
				model.setSysUserInfo(usermanager.getSysUserInfo(userid));
				model.setReplycreatedate(format.format(new Date()));
				model.setReplyuserip(IpUtil.getIpAddr(httpServletRequest));
				model.setReplyuserid((Integer)httpServletRequest.getSession().getAttribute("s_userid"));
				manager.updateTkBookContentComment(model);
				addLog(httpServletRequest,"修改了一个作业解题微课评价");
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
	public ActionForward checkBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkBookContentCommentManager manager = (TkBookContentCommentManager)getBean("tkBookContentCommentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");		for (int i = 0; i < checkids.length; i++) {
			TkBookContentComment comment=manager.getTkBookContentComment(checkids[i]);
			comment.setStatus("2");
			manager.updateTkBookContentComment(comment);
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
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		// 查询相关作业本
		TkBookContentCommentManager commentManager = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
		List<TkBookInfo> bookinfoList = commentManager.getBookListByContentComments();
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url="/tkBookContentCommentAction.do?method=list&bookid=" + bookinfo.getBookid();
			tree.append("\n").append("tree").append(".nodes[\"").append("0000_b").append(bookinfo.getBookid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		List<TkBookContent> contentList = commentManager.getBookContentListByContentComments();
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentCommentAction.do?method=list&bookid="+bookcontent.getBookid()+"&bookcontentid=" + bookcontent.getBookcontentid();	
			tree.append("\n").append("tree").append(".nodes[\"b").append(bookcontent.getBookid() + "_c" + bookcontent.getBookcontentid())
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
}