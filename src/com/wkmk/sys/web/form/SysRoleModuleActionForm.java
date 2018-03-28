package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysRoleModule;

/**
 *<p>Description: 系统角色模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleModuleActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysRoleModule sysRoleModule = new SysRoleModule();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysRoleModule getSysRoleModule(){
		return this.sysRoleModule;
	}

	public void setSysRoleModule(SysRoleModule sysRoleModule){
		this.sysRoleModule=sysRoleModule;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

