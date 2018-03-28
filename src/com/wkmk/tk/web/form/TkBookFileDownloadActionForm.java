package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookFileDownload;

/**
 *<p>Description: 作业本附件下载详情</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookFileDownloadActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookFileDownload tkBookFileDownload = new TkBookFileDownload();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookFileDownload getTkBookFileDownload(){
		return this.tkBookFileDownload;
	}

	public void setTkBookFileDownload(TkBookFileDownload tkBookFileDownload){
		this.tkBookFileDownload=tkBookFileDownload;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}