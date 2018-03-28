package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserAttention;

/**
 *<p>Description: 微信用户关注</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserAttentionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserAttention sysUserAttention = new SysUserAttention();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserAttention getSysUserAttention(){
		return this.sysUserAttention;
	}

	public void setSysUserAttention(SysUserAttention sysUserAttention){
		this.sysUserAttention=sysUserAttention;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}