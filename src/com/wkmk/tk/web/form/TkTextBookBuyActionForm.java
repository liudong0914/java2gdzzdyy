package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkTextBookBuy;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookBuyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkTextBookBuy tkTextBookBuy = new TkTextBookBuy();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkTextBookBuy getTkTextBookBuy(){
		return this.tkTextBookBuy;
	}

	public void setTkTextBookBuy(TkTextBookBuy tkTextBookBuy){
		this.tkTextBookBuy=tkTextBookBuy;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}