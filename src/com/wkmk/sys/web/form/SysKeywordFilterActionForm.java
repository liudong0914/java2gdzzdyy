package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysKeywordFilter;

/**
 *<p>Description: 系统关键字过滤</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysKeywordFilterActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysKeywordFilter sysKeywordFilter = new SysKeywordFilter();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysKeywordFilter getSysKeywordFilter(){
		return this.sysKeywordFilter;
	}

	public void setSysKeywordFilter(SysKeywordFilter sysKeywordFilter){
		this.sysKeywordFilter=sysKeywordFilter;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}

