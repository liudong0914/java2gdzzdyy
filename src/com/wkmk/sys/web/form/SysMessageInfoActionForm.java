package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysMessageInfo;

/**
 *<p>Description: 系统消息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysMessageInfo sysMessageInfo = new SysMessageInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysMessageInfo getSysMessageInfo(){
		return this.sysMessageInfo;
	}

	public void setSysMessageInfo(SysMessageInfo sysMessageInfo){
		this.sysMessageInfo=sysMessageInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}