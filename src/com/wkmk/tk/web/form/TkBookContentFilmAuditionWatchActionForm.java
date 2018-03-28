package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentFilmAuditionWatch;

/**
 *<p>Description: 试听解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionWatchActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch = new TkBookContentFilmAuditionWatch();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentFilmAuditionWatch getTkBookContentFilmAuditionWatch(){
		return this.tkBookContentFilmAuditionWatch;
	}

	public void setTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch){
		this.tkBookContentFilmAuditionWatch=tkBookContentFilmAuditionWatch;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}