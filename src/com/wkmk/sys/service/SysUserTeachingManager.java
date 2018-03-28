package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserTeaching;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户教学设置</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserTeachingManager {
	/**
	 *根据id获取系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching getSysUserTeaching(String teachingid);

	/**
	 *根据id获取系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching getSysUserTeaching(Integer teachingid);

	/**
	 *增加系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching addSysUserTeaching(SysUserTeaching sysUserTeaching);

	/**
	 *删除系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching delSysUserTeaching(String teachingid);

	/**
	 *删除系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching delSysUserTeaching(SysUserTeaching sysUserTeaching);
	
	/**
	 *删除用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public void delSysUserTeachingByUserid(Integer userid);

	/**
	 *修改系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching updateSysUserTeaching(SysUserTeaching sysUserTeaching);

	/**
	 *获取系统用户教学设置集合
	 *@return List
	 */
	public List getSysUserTeachings (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取系统用户教学设置集合
 	 *@return List
	 */
	public List getSysUserTeachings(Integer userid);

	/**
	 *获取一页系统用户教学设置集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserTeachings (List<SearchModel> condition, String orderby, int start, int pagesize);
	/**
	 * 获取教师前五天教学信息设置
	 * @param userid
	 * @return
	 */
	public List getFiveTeaching(String userid);
	/**
	 *学科id,年级id判断用户是否已经设置过年级
	 * @return
	 */
	public boolean gradeExists(String userid,String subjectid,String gradeid);
}