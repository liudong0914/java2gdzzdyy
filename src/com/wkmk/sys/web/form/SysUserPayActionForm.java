package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserPay;

/**
 *<p>Description: 在线交易明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPayActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserPay sysUserPay = new SysUserPay();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserPay getSysUserPay(){
		return this.sysUserPay;
	}

	public void setSysUserPay(SysUserPay sysUserPay){
		this.sysUserPay=sysUserPay;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}