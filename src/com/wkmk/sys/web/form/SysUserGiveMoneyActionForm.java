package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserGiveMoney;

/**
 *<p>Description: 用户赠送龙币</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserGiveMoney sysUserGiveMoney = new SysUserGiveMoney();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserGiveMoney getSysUserGiveMoney(){
		return this.sysUserGiveMoney;
	}

	public void setSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney){
		this.sysUserGiveMoney=sysUserGiveMoney;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}