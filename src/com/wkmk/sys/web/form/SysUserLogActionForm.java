package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserLog;

/**
 *<p>Description: 系统用户日志信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserLogActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserLog sysUserLog = new SysUserLog();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserLog getSysUserLog(){
		return this.sysUserLog;
	}

	public void setSysUserLog(SysUserLog sysUserLog){
		this.sysUserLog=sysUserLog;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

