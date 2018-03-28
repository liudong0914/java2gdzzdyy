package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkUserBookContent;

/**
 *<p>Description: 客户端用户作业本内容授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserBookContentActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkUserBookContent tkUserBookContent = new TkUserBookContent();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkUserBookContent getTkUserBookContent(){
		return this.tkUserBookContent;
	}

	public void setTkUserBookContent(TkUserBookContent tkUserBookContent){
		this.tkUserBookContent=tkUserBookContent;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}