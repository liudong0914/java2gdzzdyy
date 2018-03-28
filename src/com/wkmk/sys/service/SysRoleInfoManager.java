package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysRoleInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统角色信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysRoleInfoManager {
	/**
	 *根据id获取系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo getSysRoleInfo(String roleid);

	/**
	 *根据id获取系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo getSysRoleInfo(Integer roleid);

	/**
	 *增加系统角色信息
	 *@param sysRoleInfo SysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo addSysRoleInfo(SysRoleInfo sysRoleInfo);

	/**
	 *删除系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo delSysRoleInfo(String roleid);
	
	/**
	 *删除系统角色信息
	 *@param SysRoleInfo sysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo delSysRoleInfo(SysRoleInfo sysRoleInfo);

	/**
	 *修改系统角色信息
	 *@param sysRoleInfo SysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo updateSysRoleInfo(SysRoleInfo sysRoleInfo);

	/**
	 *获取系统角色信息集合
	 *@return List
	 */
	public List getSysRoleInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统角色信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysRoleInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取系统角色信息集合
 	 *@return List
	 */
	public List getSysRoleInfos(Integer unitid);
}