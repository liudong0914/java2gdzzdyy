package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperAnswer;

/**
 *<p>Description: 试卷作答信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperAnswerActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperAnswer tkPaperAnswer = new TkPaperAnswer();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperAnswer getTkPaperAnswer(){
		return this.tkPaperAnswer;
	}

	public void setTkPaperAnswer(TkPaperAnswer tkPaperAnswer){
		this.tkPaperAnswer=tkPaperAnswer;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}