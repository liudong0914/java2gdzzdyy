package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.tk.bo.TkBookContent;

/**
 *<p>Description: 作业本内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private FormFile thefile;
	private String act;
	private TkBookContent tkBookContent = new TkBookContent();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContent getTkBookContent(){
		return this.tkBookContent;
	}

	public void setTkBookContent(TkBookContent tkBookContent){
		this.tkBookContent=tkBookContent;
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