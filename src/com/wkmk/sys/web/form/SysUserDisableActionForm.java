package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserDisable;

/**
 *<p>Description: 用户禁用记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserDisableActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserDisable sysUserDisable = new SysUserDisable();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserDisable getSysUserDisable(){
		return this.sysUserDisable;
	}

	public void setSysUserDisable(SysUserDisable sysUserDisable){
		this.sysUserDisable=sysUserDisable;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}