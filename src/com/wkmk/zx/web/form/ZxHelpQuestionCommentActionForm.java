package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHelpQuestionComment;

/**
 *<p>Description: 在线答疑评论</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionCommentActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpQuestionComment zxHelpQuestionComment = new ZxHelpQuestionComment();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpQuestionComment getZxHelpQuestionComment(){
		return this.zxHelpQuestionComment;
	}

	public void setZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment){
		this.zxHelpQuestionComment=zxHelpQuestionComment;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}