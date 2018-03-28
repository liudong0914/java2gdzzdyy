package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperType;

/**
 *<p>Description: 试卷附件分类</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperTypeActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperType tkPaperType = new TkPaperType();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperType getTkPaperType(){
		return this.tkPaperType;
	}

	public void setTkPaperType(TkPaperType tkPaperType){
		this.tkPaperType=tkPaperType;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}