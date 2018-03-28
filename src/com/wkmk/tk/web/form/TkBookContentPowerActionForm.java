package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentPower;

/**
 *<p>Description: 作业本内容校验授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentPowerActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentPower tkBookContentPower = new TkBookContentPower();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentPower getTkBookContentPower(){
		return this.tkBookContentPower;
	}

	public void setTkBookContentPower(TkBookContentPower tkBookContentPower){
		this.tkBookContentPower=tkBookContentPower;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}