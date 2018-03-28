package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkOrder;

/**
 *<p>Description: 在线作业订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkOrderActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkOrder zxHomeworkOrder = new ZxHomeworkOrder();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkOrder getZxHomeworkOrder(){
		return this.zxHomeworkOrder;
	}

	public void setZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder){
		this.zxHomeworkOrder=zxHomeworkOrder;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}