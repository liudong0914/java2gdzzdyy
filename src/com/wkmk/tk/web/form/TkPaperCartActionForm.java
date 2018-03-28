package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperCart;

/**
 *<p>Description: 题库组卷试题蓝</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperCartActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkPaperCart tkPaperCart = new TkPaperCart();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkPaperCart getTkPaperCart(){
		return this.tkPaperCart;
	}

	public void setTkPaperCart(TkPaperCart tkPaperCart){
		this.tkPaperCart=tkPaperCart;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}