package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassUpload;

/**
 *<p>Description: 班级学生拍照上传</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUploadActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassUpload tkClassUpload = new TkClassUpload();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassUpload getTkClassUpload(){
		return this.tkClassUpload;
	}

	public void setTkClassUpload(TkClassUpload tkClassUpload){
		this.tkClassUpload=tkClassUpload;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}