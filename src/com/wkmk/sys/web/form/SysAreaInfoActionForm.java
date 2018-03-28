package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.sys.bo.SysAreaInfo;

/**
 *<p>Description: 地区信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAreaInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysAreaInfo sysAreaInfo = new SysAreaInfo();
	private FormFile thefile;
	
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysAreaInfo getSysAreaInfo(){
		return this.sysAreaInfo;
	}

	public void setSysAreaInfo(SysAreaInfo sysAreaInfo){
		this.sysAreaInfo=sysAreaInfo;
	}
	
	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}