package com.wkmk.vwh.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhFilmInfo;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private VwhFilmInfo vwhFilmInfo = new VwhFilmInfo();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public VwhFilmInfo getVwhFilmInfo(){
		return this.vwhFilmInfo;
	}

	public void setVwhFilmInfo(VwhFilmInfo vwhFilmInfo){
		this.vwhFilmInfo=vwhFilmInfo;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}