package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysSmsInfo;

/**
 *<p>Description: 手机短信</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysSmsInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysSmsInfo sysSmsInfo = new SysSmsInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysSmsInfo getSysSmsInfo(){
		return this.sysSmsInfo;
	}

	public void setSysSmsInfo(SysSmsInfo sysSmsInfo){
		this.sysSmsInfo=sysSmsInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}