package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkUserErrorQuestion;

/**
 *<p>Description: 个人错题集</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserErrorQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkUserErrorQuestion tkUserErrorQuestion = new TkUserErrorQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkUserErrorQuestion getTkUserErrorQuestion(){
		return this.tkUserErrorQuestion;
	}

	public void setTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion){
		this.tkUserErrorQuestion=tkUserErrorQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}