package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.edu.bo.EduCourseFile;

/**
 *<p>Description: 课程文件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseFile eduCourseFile = new EduCourseFile();
	private FormFile file;//上传文件 
	
	   public FormFile getFile() {  
	         return file;  
	        }  
    public void setFile(FormFile file) {  
     this.file = file;  
    }  

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseFile getEduCourseFile(){
		return this.eduCourseFile;
	}

	public void setEduCourseFile(EduCourseFile eduCourseFile){
		this.eduCourseFile=eduCourseFile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}