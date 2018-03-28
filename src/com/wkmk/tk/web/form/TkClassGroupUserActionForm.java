package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassGroupUser;

/**
 *<p>Description: 班级组成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassGroupUserActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassGroupUser tkClassGroupUser = new TkClassGroupUser();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassGroupUser getTkClassGroupUser(){
		return this.tkClassGroupUser;
	}

	public void setTkClassGroupUser(TkClassGroupUser tkClassGroupUser){
		this.tkClassGroupUser=tkClassGroupUser;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}