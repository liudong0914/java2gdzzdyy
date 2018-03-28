package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseFileColumn;

/**
 *<p>Description: 课程资源目录信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileColumnActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseFileColumn eduCourseFileColumn = new EduCourseFileColumn();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseFileColumn getEduCourseFileColumn(){
		return this.eduCourseFileColumn;
	}

	public void setEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn){
		this.eduCourseFileColumn=eduCourseFileColumn;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}