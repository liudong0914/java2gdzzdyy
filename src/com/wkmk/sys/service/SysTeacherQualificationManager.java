package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysTeacherQualification;

/**
 *<p>Description: 教师资格认证</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysTeacherQualificationManager {
	/**
	 *根据id获取教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification getSysTeacherQualification(String teacherid);

	/**
	 *根据id获取教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification getSysTeacherQualification(Integer teacherid);

	/**
	 *增加教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification addSysTeacherQualification(SysTeacherQualification sysTeacherQualification);

	/**
	 *删除教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification delSysTeacherQualification(String teacherid);

	/**
	 *删除教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification delSysTeacherQualification(SysTeacherQualification sysTeacherQualification);

	/**
	 *修改教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification updateSysTeacherQualification(SysTeacherQualification sysTeacherQualification);

	/**
	 *获取教师资格认证集合
	 *@return List
	 */
	public List getSysTeacherQualifications (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页教师资格认证集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysTeacherQualifications (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
     * 根据userid查询教师申请信息
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2016-12-19 下午2:30:34 
    * @lastModified ; 2016-12-19 下午2:30:34 
    * @version ; 1.0 
    * @param userid
    * @return
     */
    public List geTeacherQualificationByUserid(Integer userid);
}