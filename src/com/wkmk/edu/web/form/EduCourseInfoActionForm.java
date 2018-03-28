package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseInfo;

/**
 *<p>Description: 课程信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseInfo eduCourseInfo = new EduCourseInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseInfo getEduCourseInfo(){
		return this.eduCourseInfo;
	}

	public void setEduCourseInfo(EduCourseInfo eduCourseInfo){
		this.eduCourseInfo=eduCourseInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}