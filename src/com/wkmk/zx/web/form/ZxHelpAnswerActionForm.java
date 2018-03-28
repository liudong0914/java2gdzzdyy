package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHelpAnswer;

/**
 *<p>Description: 在线答疑回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpAnswerActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpAnswer zxHelpAnswer = new ZxHelpAnswer();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpAnswer getZxHelpAnswer(){
		return this.zxHelpAnswer;
	}

	public void setZxHelpAnswer(ZxHelpAnswer zxHelpAnswer){
		this.zxHelpAnswer=zxHelpAnswer;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}