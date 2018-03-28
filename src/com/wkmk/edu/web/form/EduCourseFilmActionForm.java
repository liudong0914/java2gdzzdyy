package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;

/**
 *<p>Description: 课程视频</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFilmActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseFilm eduCourseFilm = new EduCourseFilm();
	private VwhFilmInfo vwhFilmInfo = new VwhFilmInfo();
	private VwhFilmPix vwhFilmPix = new VwhFilmPix();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseFilm getEduCourseFilm(){
		return this.eduCourseFilm;
	}

	public void setEduCourseFilm(EduCourseFilm eduCourseFilm){
		this.eduCourseFilm=eduCourseFilm;
	}

	public VwhFilmInfo getVwhFilmInfo()
    {
        return vwhFilmInfo;
    }

    public void setVwhFilmInfo(VwhFilmInfo vwhFilmInfo)
    {
        this.vwhFilmInfo = vwhFilmInfo;
    }
    
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