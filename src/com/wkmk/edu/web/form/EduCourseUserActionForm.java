package com.wkmk.edu.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.bo.EduCourseUserModule;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;

/**
 *<p>Description: 课程用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private EduCourseUser eduCourseUser = new EduCourseUser();
	private EduCourseUserModule eduCourseUserModule = new EduCourseUserModule();
	private SysUserInfo sysUserInfo = new SysUserInfo();
    private SysUserInfoDetail sysUserInfoDetail = new SysUserInfoDetail();

    
    
	public EduCourseUserModule getEduCourseUserModule()
    {
        return eduCourseUserModule;
    }

    public void setEduCourseUserModule(EduCourseUserModule eduCourseUserModule)
    {
        this.eduCourseUserModule = eduCourseUserModule;
    }

    public SysUserInfo getSysUserInfo()
    {
        return sysUserInfo;
    }

    public void setSysUserInfo(SysUserInfo sysUserInfo)
    {
        this.sysUserInfo = sysUserInfo;
    }

    public SysUserInfoDetail getSysUserInfoDetail()
    {
        return sysUserInfoDetail;
    }

    public void setSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail)
    {
        this.sysUserInfoDetail = sysUserInfoDetail;
    }

    public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public EduCourseUser getEduCourseUser(){
		return this.eduCourseUser;
	}

	public void setEduCourseUser(EduCourseUser eduCourseUser){
		this.eduCourseUser=eduCourseUser;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}