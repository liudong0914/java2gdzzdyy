package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperInfo;

/**
 *<p>Description: 试卷信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperInfo tkPaperInfo = new TkPaperInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperInfo getTkPaperInfo(){
		return this.tkPaperInfo;
	}

	public void setTkPaperInfo(TkPaperInfo tkPaperInfo){
		this.tkPaperInfo=tkPaperInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}