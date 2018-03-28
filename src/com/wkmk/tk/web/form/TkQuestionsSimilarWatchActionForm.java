package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsSimilarWatch;

/**
 *<p>Description: 举一反三观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilarWatchActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkQuestionsSimilarWatch tkQuestionsSimilarWatch = new TkQuestionsSimilarWatch();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkQuestionsSimilarWatch getTkQuestionsSimilarWatch(){
		return this.tkQuestionsSimilarWatch;
	}

	public void setTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch){
		this.tkQuestionsSimilarWatch=tkQuestionsSimilarWatch;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}