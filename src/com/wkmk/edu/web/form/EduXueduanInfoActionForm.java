package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduXueduanInfo;

/**
 *<p>Description: 教育学段信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduXueduanInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduXueduanInfo eduXueduanInfo = new EduXueduanInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduXueduanInfo getEduXueduanInfo(){
		return this.eduXueduanInfo;
	}

	public void setEduXueduanInfo(EduXueduanInfo eduXueduanInfo){
		this.eduXueduanInfo=eduXueduanInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}