package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseClass;

/**
 *<p>Description: 课程班</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseClassActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseClass eduCourseClass = new EduCourseClass();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseClass getEduCourseClass(){
		return this.eduCourseClass;
	}

	public void setEduCourseClass(EduCourseClass eduCourseClass){
		this.eduCourseClass=eduCourseClass;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}