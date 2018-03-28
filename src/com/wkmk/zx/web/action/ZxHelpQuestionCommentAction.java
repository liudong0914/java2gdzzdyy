package com.wkmk.zx.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.bo.ZxHelpQuestionComment;
import com.wkmk.zx.service.ZxHelpFileManager;
import com.wkmk.zx.service.ZxHelpQuestionCommentManager;
import com.wkmk.zx.service.ZxHelpQuestionManager;
import com.wkmk.zx.web.form.ZxHelpQuestionCommentActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 在线答疑评论</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionCommentAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		String username = Encode.nullToBlank(request.getParameter("username"));
		String createdate= Encode.nullToBlank(request.getParameter("createdate"));
		String status= Encode.nullToBlank(request.getParameter("status"));
		PageUtil pageUtil = new PageUtil(request);
		String orderby=pageUtil.getOrderindex();
		if("".equals(orderby)){
			orderby="createdate desc";
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%"+username+"%");
		}
		if (!"".equals(createdate)) {
	        SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
	    }
		SearchCondition.addCondition(condition, "status", "=", status);
		PageList page = manager.getPageZxHelpQuestionComments(condition, orderby, pageUtil.getStartCount(), pageUtil.getPageSize());
		request.setAttribute("pagelist", page);
		request.setAttribute("username", username);
		request.setAttribute("createdate", createdate);
		request.setAttribute("status", status);
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
		ZxHelpQuestionComment model = new ZxHelpQuestionComment();
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
		ZxHelpQuestionCommentActionForm form = (ZxHelpQuestionCommentActionForm)actionForm;
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpQuestionComment model = form.getZxHelpQuestionComment();
				manager.addZxHelpQuestionComment(model);
				addLog(httpServletRequest,"增加了一个在线答疑评论");
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
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		ZxHelpQuestionManager qmanager=(ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
		ZxHelpFileManager filemanager=(ZxHelpFileManager)getBean("zxHelpFileManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			ZxHelpQuestionComment model = manager.getZxHelpQuestionComment(objid);
			//评论关联答疑
			ZxHelpQuestion zxHelpQuestion=qmanager.getZxHelpQuestion(model.getQuestionid());
			
			//答疑关联图片集合
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "filetype", "=", "1");
			SearchCondition.addCondition(condition, "questionid", "=", model.getQuestionid());
			List imglist=filemanager.getZxHelpFiles(condition, "", 0); 
			
			//答疑关联语音集合
			condition.clear();
			SearchCondition.addCondition(condition, "filetype", "=", "2");
			SearchCondition.addCondition(condition, "questionid", "=", model.getQuestionid());
			List audiolist=filemanager.getZxHelpFiles(condition, "", 0);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("imglist", imglist);
			httpServletRequest.setAttribute("audiolist", audiolist);
			httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
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
		ZxHelpQuestionCommentActionForm form = (ZxHelpQuestionCommentActionForm)actionForm;
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		SysUserInfoManager usermanager = (SysUserInfoManager)getBean("sysUserInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String userid=httpServletRequest.getParameter("userid");
				SysUserInfo user=usermanager.getSysUserInfo(userid);
				ZxHelpQuestionComment model = form.getZxHelpQuestionComment();
				model.setSysUserInfo(user);
				manager.updateZxHelpQuestionComment(model);
				addLog(httpServletRequest,"修改了一个在线答疑评论");
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
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delZxHelpQuestionComment(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	public ActionForward batchDisable(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager)getBean("zxHelpQuestionCommentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			ZxHelpQuestionComment comment=manager.getZxHelpQuestionComment(checkids[i]);
			comment.setStatus("2");
			manager.updateZxHelpQuestionComment(comment);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}