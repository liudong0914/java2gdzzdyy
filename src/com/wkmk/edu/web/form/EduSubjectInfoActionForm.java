package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduSubjectInfo;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduSubjectInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduSubjectInfo eduSubjectInfo = new EduSubjectInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduSubjectInfo getEduSubjectInfo(){
		return this.eduSubjectInfo;
	}

	public void setEduSubjectInfo(EduSubjectInfo eduSubjectInfo){
		this.eduSubjectInfo=eduSubjectInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}