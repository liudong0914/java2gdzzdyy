package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysModuleInfo;

/**
 *<p>Description: 系统模块信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysModuleInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysModuleInfo sysModuleInfo = new SysModuleInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysModuleInfo getSysModuleInfo(){
		return this.sysModuleInfo;
	}

	public void setSysModuleInfo(SysModuleInfo sysModuleInfo){
		this.sysModuleInfo=sysModuleInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

