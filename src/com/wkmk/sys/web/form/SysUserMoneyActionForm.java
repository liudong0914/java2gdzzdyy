package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserMoney;

/**
 *<p>Description: 用户交易记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserMoneyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserMoney sysUserMoney = new SysUserMoney();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserMoney getSysUserMoney(){
		return this.sysUserMoney;
	}

	public void setSysUserMoney(SysUserMoney sysUserMoney){
		this.sysUserMoney=sysUserMoney;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}