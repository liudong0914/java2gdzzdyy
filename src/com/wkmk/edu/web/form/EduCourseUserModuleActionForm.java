package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseUserModule;

/**
 *<p>Description: 课程用户模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserModuleActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseUserModule eduCourseUserModule = new EduCourseUserModule();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseUserModule getEduCourseUserModule(){
		return this.eduCourseUserModule;
	}

	public void setEduCourseUserModule(EduCourseUserModule eduCourseUserModule){
		this.eduCourseUserModule=eduCourseUserModule;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}