package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserTeaching;

/**
 *<p>Description: 系统用户教学设置</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserTeachingActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserTeaching sysUserTeaching = new SysUserTeaching();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserTeaching getSysUserTeaching(){
		return this.sysUserTeaching;
	}

	public void setSysUserTeaching(SysUserTeaching sysUserTeaching){
		this.sysUserTeaching=sysUserTeaching;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}