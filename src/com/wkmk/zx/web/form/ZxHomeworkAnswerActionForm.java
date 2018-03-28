package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkAnswer;

/**
 *<p>Description: 在线作业回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkAnswerActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkAnswer zxHomeworkAnswer = new ZxHomeworkAnswer();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkAnswer getZxHomeworkAnswer(){
		return this.zxHomeworkAnswer;
	}

	public void setZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer){
		this.zxHomeworkAnswer=zxHomeworkAnswer;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}