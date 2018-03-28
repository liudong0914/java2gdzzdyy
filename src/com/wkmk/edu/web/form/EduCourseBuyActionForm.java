package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseBuy;

/**
 *<p>Description: 课程购买信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseBuyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseBuy eduCourseBuy = new EduCourseBuy();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseBuy getEduCourseBuy(){
		return this.eduCourseBuy;
	}

	public void setEduCourseBuy(EduCourseBuy eduCourseBuy){
		this.eduCourseBuy=eduCourseBuy;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}