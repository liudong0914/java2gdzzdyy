package com.wkmk.vwh.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhFilmPix;

/**
 *<p>Description: 视频库视频影片</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmPixActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private VwhFilmPix vwhFilmPix = new VwhFilmPix();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public VwhFilmPix getVwhFilmPix(){
		return this.vwhFilmPix;
	}

	public void setVwhFilmPix(VwhFilmPix vwhFilmPix){
		this.vwhFilmPix=vwhFilmPix;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}