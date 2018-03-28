package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserRole;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserRoleActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserRole sysUserRole = new SysUserRole();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserRole getSysUserRole(){
		return this.sysUserRole;
	}

	public void setSysUserRole(SysUserRole sysUserRole){
		this.sysUserRole=sysUserRole;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

