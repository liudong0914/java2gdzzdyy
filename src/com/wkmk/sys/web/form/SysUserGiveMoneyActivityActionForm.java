package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserGiveMoneyActivity;

/**
 *<p>Description: 用户赠送龙币活动</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyActivityActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserGiveMoneyActivity sysUserGiveMoneyActivity = new SysUserGiveMoneyActivity();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserGiveMoneyActivity getSysUserGiveMoneyActivity(){
		return this.sysUserGiveMoneyActivity;
	}

	public void setSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity){
		this.sysUserGiveMoneyActivity=sysUserGiveMoneyActivity;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}