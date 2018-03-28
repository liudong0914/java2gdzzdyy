package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentQuestion;

/**
 *<p>Description: 作业本内容关联试题</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentQuestion tkBookContentQuestion = new TkBookContentQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentQuestion getTkBookContentQuestion(){
		return this.tkBookContentQuestion;
	}

	public void setTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion){
		this.tkBookContentQuestion=tkBookContentQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}