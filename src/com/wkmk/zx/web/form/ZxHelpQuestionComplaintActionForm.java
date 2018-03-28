package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHelpQuestionComplaint;

/**
 *<p>Description: 在线答疑投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionComplaintActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpQuestionComplaint zxHelpQuestionComplaint = new ZxHelpQuestionComplaint();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpQuestionComplaint getZxHelpQuestionComplaint(){
		return this.zxHelpQuestionComplaint;
	}

	public void setZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint){
		this.zxHelpQuestionComplaint=zxHelpQuestionComplaint;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}