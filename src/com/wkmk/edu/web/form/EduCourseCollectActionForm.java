package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseCollect;

/**
 *<p>Description: 课程收藏</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCollectActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseCollect eduCourseCollect = new EduCourseCollect();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseCollect getEduCourseCollect(){
		return this.eduCourseCollect;
	}

	public void setEduCourseCollect(EduCourseCollect eduCourseCollect){
		this.eduCourseCollect=eduCourseCollect;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}