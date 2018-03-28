package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperContent;

/**
 *<p>Description: 试卷内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperContentActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperContent tkPaperContent = new TkPaperContent();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperContent getTkPaperContent(){
		return this.tkPaperContent;
	}

	public void setTkPaperContent(TkPaperContent tkPaperContent){
		this.tkPaperContent=tkPaperContent;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}