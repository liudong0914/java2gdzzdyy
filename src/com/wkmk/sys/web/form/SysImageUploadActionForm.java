package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class SysImageUploadActionForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private FormFile thefile;
	private String act;

	public String getAct(){
		return this.act;
	}

	public void setAct(String act){
		this.act=act;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}
