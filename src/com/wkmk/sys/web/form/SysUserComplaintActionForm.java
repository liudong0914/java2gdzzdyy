package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserComplaint;

/**
 *<p>Description: 用户被投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserComplaintActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserComplaint sysUserComplaint = new SysUserComplaint();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserComplaint getSysUserComplaint(){
		return this.sysUserComplaint;
	}

	public void setSysUserComplaint(SysUserComplaint sysUserComplaint){
		this.sysUserComplaint=sysUserComplaint;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}