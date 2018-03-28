package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkWrongReason;

/**
 *<p>Description: 在线作业常见错误原因</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkWrongReasonActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkWrongReason zxHomeworkWrongReason = new ZxHomeworkWrongReason();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkWrongReason getZxHomeworkWrongReason(){
		return this.zxHomeworkWrongReason;
	}

	public void setZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason){
		this.zxHomeworkWrongReason=zxHomeworkWrongReason;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}