package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseStudy;

/**
 *<p>Description: 课程学习</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseStudyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseStudy eduCourseStudy = new EduCourseStudy();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseStudy getEduCourseStudy(){
		return this.eduCourseStudy;
	}

	public void setEduCourseStudy(EduCourseStudy eduCourseStudy){
		this.eduCourseStudy=eduCourseStudy;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}