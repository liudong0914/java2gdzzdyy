package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHelpQuestionBuy;

/**
 *<p>Description: 在线答疑购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionBuyActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpQuestionBuy zxHelpQuestionBuy = new ZxHelpQuestionBuy();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpQuestionBuy getZxHelpQuestionBuy(){
		return this.zxHelpQuestionBuy;
	}

	public void setZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy){
		this.zxHelpQuestionBuy=zxHelpQuestionBuy;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}