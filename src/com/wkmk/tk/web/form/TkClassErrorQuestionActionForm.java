package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassErrorQuestion;

/**
 *<p>Description: 班级错题集</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassErrorQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassErrorQuestion tkClassErrorQuestion = new TkClassErrorQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassErrorQuestion getTkClassErrorQuestion(){
		return this.tkClassErrorQuestion;
	}

	public void setTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion){
		this.tkClassErrorQuestion=tkClassErrorQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}