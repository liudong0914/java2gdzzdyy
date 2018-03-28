package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.sys.bo.SysTeacherQualification;

/**
 *<p>Description: 教师资格认证</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysTeacherQualificationActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysTeacherQualification sysTeacherQualification = new SysTeacherQualification();
	 private FormFile file;//上传文件 
	 private FormFile twofile;//上传文件
	 
	 
	 public FormFile getTwofile()
    {
        return twofile;
    }
    public void setTwofile(FormFile twofile)
    {
        this.twofile = twofile;
    }
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

	public SysTeacherQualification getSysTeacherQualification(){
		return this.sysTeacherQualification;
	}

	public void setSysTeacherQualification(SysTeacherQualification sysTeacherQualification){
		this.sysTeacherQualification=sysTeacherQualification;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}