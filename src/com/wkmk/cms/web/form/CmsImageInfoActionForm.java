package com.wkmk.cms.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.cms.bo.CmsImageInfo;

/**
 *<p>Description: 图片广告信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsImageInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private CmsImageInfo cmsImageInfo = new CmsImageInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public CmsImageInfo getCmsImageInfo(){
		return this.cmsImageInfo;
	}

	public void setCmsImageInfo(CmsImageInfo cmsImageInfo){
		this.cmsImageInfo=cmsImageInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}