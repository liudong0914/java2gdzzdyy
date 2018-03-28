package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduBookInfo;

/**
 *<p>Description: 教材课本</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduBookInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduBookInfo eduBookInfo = new EduBookInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduBookInfo getEduBookInfo(){
		return this.eduBookInfo;
	}

	public void setEduBookInfo(EduBookInfo eduBookInfo){
		this.eduBookInfo=eduBookInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}