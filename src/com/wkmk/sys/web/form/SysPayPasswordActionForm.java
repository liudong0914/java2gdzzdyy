package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysPayPassword;

/**
 *<p>Description: 支付密码错误记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPayPasswordActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysPayPassword sysPayPassword = new SysPayPassword();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysPayPassword getSysPayPassword(){
		return this.sysPayPassword;
	}

	public void setSysPayPassword(SysPayPassword sysPayPassword){
		this.sysPayPassword=sysPayPassword;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}