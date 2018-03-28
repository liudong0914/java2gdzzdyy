package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserRole;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserRoleManager {
	/**
	 *根据id获取系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole getSysUserRole(String userroleid);

	/**
	 *根据id获取系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole getSysUserRole(Integer userroleid);

	/**
	 *增加系统用户角色
	 *@param sysUserRole SysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole addSysUserRole(SysUserRole sysUserRole);

	/**
	 *删除系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole delSysUserRole(String userroleid);
	
	/**
	 *删除系统用户角色
	 *@param SysUserRole sysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole delSysUserRole(SysUserRole sysUserRole);

	/**
	 *修改系统用户角色
	 *@param sysUserRole SysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole updateSysUserRole(SysUserRole sysUserRole);

	/**
	 *获取系统用户角色集合
	 *@return List
	 */
	public List getSysUserRoles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统用户角色集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserRoles (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 获取所有用户所关联的角色id
	 * @param userid
	 * @return List
	 */
	public List getAllRoleids(Integer userid);
	
	/**
	 * 获取某角色下所有的用户
	 * @param roleid
	 * @return
	 */
	public List getAllUserInfosByRoleid(Integer roleid);
	
	/**
	 * 获取系统当前用户已有的角色
	 * @return List
	 */
	public List getSysUserRoles(Integer userid, Integer unitid);
	
	/**
	 * 获取所有用户所关联的模块id
	 * @param userid
	 * @return List
	 */
	public List getAllModuleids(Integer userid);
}