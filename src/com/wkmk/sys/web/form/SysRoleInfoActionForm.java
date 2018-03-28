package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysRoleInfo;

/**
 *<p>Description: 系统角色信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysRoleInfo sysRoleInfo = new SysRoleInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysRoleInfo getSysRoleInfo(){
		return this.sysRoleInfo;
	}

	public void setSysRoleInfo(SysRoleInfo sysRoleInfo){
		this.sysRoleInfo=sysRoleInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

