package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperFileDownload;

/**
 *<p>Description: 试卷附件下载</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperFileDownloadActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperFileDownload tkPaperFileDownload = new TkPaperFileDownload();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperFileDownload getTkPaperFileDownload(){
		return this.tkPaperFileDownload;
	}

	public void setTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload){
		this.tkPaperFileDownload=tkPaperFileDownload;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}