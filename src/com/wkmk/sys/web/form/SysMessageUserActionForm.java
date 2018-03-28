package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysMessageUser;

/**
 *<p>Description: 消息用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageUserActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysMessageUser sysMessageUser = new SysMessageUser();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysMessageUser getSysMessageUser(){
		return this.sysMessageUser;
	}

	public void setSysMessageUser(SysMessageUser sysMessageUser){
		this.sysMessageUser=sysMessageUser;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}