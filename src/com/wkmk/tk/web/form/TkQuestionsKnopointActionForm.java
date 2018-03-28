package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsKnopoint;

/**
 *<p>Description: 题库知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsKnopointActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkQuestionsKnopoint tkQuestionsKnopoint = new TkQuestionsKnopoint();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkQuestionsKnopoint getTkQuestionsKnopoint(){
		return this.tkQuestionsKnopoint;
	}

	public void setTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint){
		this.tkQuestionsKnopoint=tkQuestionsKnopoint;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}