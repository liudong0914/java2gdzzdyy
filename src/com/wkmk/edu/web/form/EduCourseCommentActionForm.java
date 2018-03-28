package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseComment;

/**
 *<p>Description: 课程评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCommentActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseComment eduCourseComment = new EduCourseComment();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseComment getEduCourseComment(){
		return this.eduCourseComment;
	}

	public void setEduCourseComment(EduCourseComment eduCourseComment){
		this.eduCourseComment=eduCourseComment;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}