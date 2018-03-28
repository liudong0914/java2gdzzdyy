package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHelpQuestion;

/**
 *<p>Description: 在线答疑提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpQuestion zxHelpQuestion = new ZxHelpQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpQuestion getZxHelpQuestion(){
		return this.zxHelpQuestion;
	}

	public void setZxHelpQuestion(ZxHelpQuestion zxHelpQuestion){
		this.zxHelpQuestion=zxHelpQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}