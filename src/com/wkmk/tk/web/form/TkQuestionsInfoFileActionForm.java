package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkQuestionsInfoFile;

/**
 *<p>Description: 题库碎片化附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsInfoFileActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkQuestionsInfoFile tkQuestionsInfoFile = new TkQuestionsInfoFile();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkQuestionsInfoFile getTkQuestionsInfoFile(){
		return this.tkQuestionsInfoFile;
	}

	public void setTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile){
		this.tkQuestionsInfoFile=tkQuestionsInfoFile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}