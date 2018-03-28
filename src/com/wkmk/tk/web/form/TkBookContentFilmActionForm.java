package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentFilm;

/**
 *<p>Description: 作业本微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentFilm tkBookContentFilm = new TkBookContentFilm();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentFilm getTkBookContentFilm(){
		return this.tkBookContentFilm;
	}

	public void setTkBookContentFilm(TkBookContentFilm tkBookContentFilm){
		this.tkBookContentFilm=tkBookContentFilm;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}