package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseFileDownload;

/**
 *<p>Description: 课程资源下载记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileDownloadActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseFileDownload eduCourseFileDownload = new EduCourseFileDownload();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseFileDownload getEduCourseFileDownload(){
		return this.eduCourseFileDownload;
	}

	public void setEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload){
		this.eduCourseFileDownload=eduCourseFileDownload;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}