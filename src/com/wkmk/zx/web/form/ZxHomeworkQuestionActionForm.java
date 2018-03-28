package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkQuestion;

/**
 *<p>Description: 在线作业提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkQuestion zxHomeworkQuestion = new ZxHomeworkQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkQuestion getZxHomeworkQuestion(){
		return this.zxHomeworkQuestion;
	}

	public void setZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion){
		this.zxHomeworkQuestion=zxHomeworkQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}