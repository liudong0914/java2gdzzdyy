package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentFilmWatch;

/**
 *<p>Description: 解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmWatchActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentFilmWatch tkBookContentFilmWatch = new TkBookContentFilmWatch();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentFilmWatch getTkBookContentFilmWatch(){
		return this.tkBookContentFilmWatch;
	}

	public void setTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch){
		this.tkBookContentFilmWatch=tkBookContentFilmWatch;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}