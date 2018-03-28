package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassPassword;

/**
 *<p>Description: 班级密码</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassPasswordActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassPassword tkClassPassword = new TkClassPassword();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassPassword getTkClassPassword(){
		return this.tkClassPassword;
	}

	public void setTkClassPassword(TkClassPassword tkClassPassword){
		this.tkClassPassword=tkClassPassword;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}