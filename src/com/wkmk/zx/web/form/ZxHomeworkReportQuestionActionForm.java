package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkReportQuestion;

/**
 *<p>Description: 在线作业批改报告错题内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReportQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkReportQuestion zxHomeworkReportQuestion = new ZxHomeworkReportQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkReportQuestion getZxHomeworkReportQuestion(){
		return this.zxHomeworkReportQuestion;
	}

	public void setZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion){
		this.zxHomeworkReportQuestion=zxHomeworkReportQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}