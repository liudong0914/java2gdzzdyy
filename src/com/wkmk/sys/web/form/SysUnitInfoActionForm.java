package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUnitInfo;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUnitInfo sysUnitInfo = new SysUnitInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUnitInfo getSysUnitInfo(){
		return this.sysUnitInfo;
	}

	public void setSysUnitInfo(SysUnitInfo sysUnitInfo){
		this.sysUnitInfo=sysUnitInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

