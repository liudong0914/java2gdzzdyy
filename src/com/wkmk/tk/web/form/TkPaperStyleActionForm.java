package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperStyle;

/**
 *<p>Description: 题库试卷样式</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperStyleActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperStyle tkPaperStyle = new TkPaperStyle();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperStyle getTkPaperStyle(){
		return this.tkPaperStyle;
	}

	public void setTkPaperStyle(TkPaperStyle tkPaperStyle){
		this.tkPaperStyle=tkPaperStyle;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}