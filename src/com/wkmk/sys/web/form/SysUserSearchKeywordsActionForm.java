package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserSearchKeywords;

/**
 *<p>Description: 用户历史搜索关键词</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserSearchKeywordsActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserSearchKeywords sysUserSearchKeywords = new SysUserSearchKeywords();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public SysUserSearchKeywords getSysUserSearchKeywords(){
		return this.sysUserSearchKeywords;
	}

	public void setSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords){
		this.sysUserSearchKeywords=sysUserSearchKeywords;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}