package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseAnswerQuestion;

/**
 *<p>Description: 课程答疑</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseAnswerQuestionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseAnswerQuestion eduCourseAnswerQuestion = new EduCourseAnswerQuestion();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseAnswerQuestion getEduCourseAnswerQuestion(){
		return this.eduCourseAnswerQuestion;
	}

	public void setEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion){
		this.eduCourseAnswerQuestion=eduCourseAnswerQuestion;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}