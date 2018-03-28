package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysPaymentAccount;

/**
 *<p>Description: 系统支付账号</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPaymentAccountActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysPaymentAccount sysPaymentAccount = new SysPaymentAccount();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysPaymentAccount getSysPaymentAccount(){
		return this.sysPaymentAccount;
	}

	public void setSysPaymentAccount(SysPaymentAccount sysPaymentAccount){
		this.sysPaymentAccount=sysPaymentAccount;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}