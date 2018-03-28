package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentFilmAudition;

/**
 *<p>Description: 试听解题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentFilmAudition tkBookContentFilmAudition = new TkBookContentFilmAudition();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentFilmAudition getTkBookContentFilmAudition(){
		return this.tkBookContentFilmAudition;
	}

	public void setTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition){
		this.tkBookContentFilmAudition=tkBookContentFilmAudition;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}