package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassUser;

/**
 *<p>Description: 班级用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUserActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassUser tkClassUser = new TkClassUser();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassUser getTkClassUser(){
		return this.tkClassUser;
	}

	public void setTkClassUser(TkClassUser tkClassUser){
		this.tkClassUser=tkClassUser;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}