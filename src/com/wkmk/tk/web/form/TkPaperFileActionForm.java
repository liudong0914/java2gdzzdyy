package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.tk.bo.TkPaperFile;

/**
 *<p>Description: 试卷附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperFileActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private FormFile thefile;
	private TkPaperFile tkPaperFile = new TkPaperFile();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	
	}

	public TkPaperFile getTkPaperFile(){
		return this.tkPaperFile;
	}

	public void setTkPaperFile(TkPaperFile tkPaperFile){
		this.tkPaperFile=tkPaperFile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}	
}