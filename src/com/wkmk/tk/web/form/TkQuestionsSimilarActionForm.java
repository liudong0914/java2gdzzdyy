package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsSimilar;

/**
 *<p>Description: 习题举一反三</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilarActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkQuestionsSimilar tkQuestionsSimilar = new TkQuestionsSimilar();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkQuestionsSimilar getTkQuestionsSimilar(){
		return this.tkQuestionsSimilar;
	}

	public void setTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar){
		this.tkQuestionsSimilar=tkQuestionsSimilar;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}