package com.wkmk.sys.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;

/**
 * <p>
 * Description: 系统用户信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class SysUserInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private SysUserInfo sysUserInfo = new SysUserInfo();
	private SysUserInfoDetail sysUserInfoDetail = new SysUserInfoDetail();
	private FormFile thefile;
    private FormFile file;//上传文件 
    private FormFile twofile;//上传文件
    
    public FormFile getTwofile()
    {
        return twofile;
    }
    public void setTwofile(FormFile twofile)
    {
        this.twofile = twofile;
    }
    public FormFile getFile() {  
         return file;  
        }  
        public void setFile(FormFile file) {  
         this.file = file;  
        }  

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public SysUserInfo getSysUserInfo() {
		return this.sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}

	public SysUserInfoDetail getSysUserInfoDetail() {
		return sysUserInfoDetail;
	}

	public void setSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail) {
		this.sysUserInfoDetail = sysUserInfoDetail;
	}
}
