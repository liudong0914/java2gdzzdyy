package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookInfo;

/**
 *<p>Description: 作业本信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookInfo tkBookInfo = new TkBookInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookInfo getTkBookInfo(){
		return this.tkBookInfo;
	}

	public void setTkBookInfo(TkBookInfo tkBookInfo){
		this.tkBookInfo=tkBookInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}