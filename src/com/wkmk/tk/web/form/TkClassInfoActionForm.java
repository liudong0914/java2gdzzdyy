package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkClassInfo;

/**
 *<p>Description: 班级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkClassInfo tkClassInfo = new TkClassInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkClassInfo getTkClassInfo(){
		return this.tkClassInfo;
	}

	public void setTkClassInfo(TkClassInfo tkClassInfo){
		this.tkClassInfo=tkClassInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}