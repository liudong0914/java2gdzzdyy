package com.wkmk.vwh.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhFilmKnopoint;

/**
 *<p>Description: 微课知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmKnopointActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private VwhFilmKnopoint vwhFilmKnopoint = new VwhFilmKnopoint();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public VwhFilmKnopoint getVwhFilmKnopoint(){
		return this.vwhFilmKnopoint;
	}

	public void setVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint){
		this.vwhFilmKnopoint=vwhFilmKnopoint;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}