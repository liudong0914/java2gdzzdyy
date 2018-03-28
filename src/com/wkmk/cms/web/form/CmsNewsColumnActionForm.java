package com.wkmk.cms.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.cms.bo.CmsNewsColumn;

/**
 *<p>Description: 资讯栏目</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsColumnActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private CmsNewsColumn cmsNewsColumn = new CmsNewsColumn();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public CmsNewsColumn getCmsNewsColumn(){
		return this.cmsNewsColumn;
	}

	public void setCmsNewsColumn(CmsNewsColumn cmsNewsColumn){
		this.cmsNewsColumn=cmsNewsColumn;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}