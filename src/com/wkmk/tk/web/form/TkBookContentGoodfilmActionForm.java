package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentGoodfilm;

/**
 *<p>Description: 精品微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentGoodfilmActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentGoodfilm tkBookContentGoodfilm = new TkBookContentGoodfilm();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentGoodfilm getTkBookContentGoodfilm(){
		return this.tkBookContentGoodfilm;
	}

	public void setTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm){
		this.tkBookContentGoodfilm=tkBookContentGoodfilm;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}