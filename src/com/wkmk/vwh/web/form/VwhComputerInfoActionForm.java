package com.wkmk.vwh.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhComputerInfo;

/**
 *<p>Description: 视频库服务器信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhComputerInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private VwhComputerInfo vwhComputerInfo = new VwhComputerInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public VwhComputerInfo getVwhComputerInfo(){
		return this.vwhComputerInfo;
	}

	public void setVwhComputerInfo(VwhComputerInfo vwhComputerInfo){
		this.vwhComputerInfo=vwhComputerInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}