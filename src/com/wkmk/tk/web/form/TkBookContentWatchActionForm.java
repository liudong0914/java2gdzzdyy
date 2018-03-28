package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentWatch;

/**
 *<p>Description: 音频文件观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentWatchActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentWatch tkBookContentWatch = new TkBookContentWatch();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentWatch getTkBookContentWatch(){
		return this.tkBookContentWatch;
	}

	public void setTkBookContentWatch(TkBookContentWatch tkBookContentWatch){
		this.tkBookContentWatch=tkBookContentWatch;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}