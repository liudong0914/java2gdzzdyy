package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseColumn;

/**
 *<p>Description: 课程目录信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseColumnActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseColumn eduCourseColumn = new EduCourseColumn();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseColumn getEduCourseColumn(){
		return this.eduCourseColumn;
	}

	public void setEduCourseColumn(EduCourseColumn eduCourseColumn){
		this.eduCourseColumn=eduCourseColumn;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}