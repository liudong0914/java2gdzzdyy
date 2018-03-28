package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserInfoDetail;

/**
 *<p>Description: 系统用户扩展信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoDetailActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserInfoDetail sysUserInfoDetail = new SysUserInfoDetail();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserInfoDetail getSysUserInfoDetail(){
		return this.sysUserInfoDetail;
	}

	public void setSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail){
		this.sysUserInfoDetail=sysUserInfoDetail;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

