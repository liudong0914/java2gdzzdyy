package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsType;

/**
 *<p>Description: 题库试题类型</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsTypeActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkQuestionsType tkQuestionsType = new TkQuestionsType();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkQuestionsType getTkQuestionsType(){
		return this.tkQuestionsType;
	}

	public void setTkQuestionsType(TkQuestionsType tkQuestionsType){
		this.tkQuestionsType=tkQuestionsType;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}