package com.wkmk.vwh.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhFilmWatch;

/**
 *<p>Description: 微课观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmWatchActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private VwhFilmWatch vwhFilmWatch = new VwhFilmWatch();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public VwhFilmWatch getVwhFilmWatch(){
		return this.vwhFilmWatch;
	}

	public void setVwhFilmWatch(VwhFilmWatch vwhFilmWatch){
		this.vwhFilmWatch=vwhFilmWatch;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}