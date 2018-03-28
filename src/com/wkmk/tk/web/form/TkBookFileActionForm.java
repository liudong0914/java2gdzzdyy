package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookFile;

/**
 *<p>Description: 作业本附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookFileActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookFile tkBookFile = new TkBookFile();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookFile getTkBookFile(){
		return this.tkBookFile;
	}

	public void setTkBookFile(TkBookFile tkBookFile){
		this.tkBookFile=tkBookFile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}