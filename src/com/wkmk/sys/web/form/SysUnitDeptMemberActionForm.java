package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUnitDeptMember;

/**
 *<p>Description: 系统单位机构成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptMemberActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUnitDeptMember sysUnitDeptMember = new SysUnitDeptMember();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUnitDeptMember getSysUnitDeptMember(){
		return this.sysUnitDeptMember;
	}

	public void setSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember){
		this.sysUnitDeptMember=sysUnitDeptMember;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

