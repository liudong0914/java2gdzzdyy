package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysProductInfo;

/**
 *<p>Description: 系统产品信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysProductInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysProductInfo sysProductInfo = new SysProductInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysProductInfo getSysProductInfo(){
		return this.sysProductInfo;
	}

	public void setSysProductInfo(SysProductInfo sysProductInfo){
		this.sysProductInfo=sysProductInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

