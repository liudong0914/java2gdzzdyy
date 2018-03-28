package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserDisable;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户禁用记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserDisableManager {
	/**
	 *根据id获取用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable getSysUserDisable(String disableid);

	/**
	 *根据id获取用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable getSysUserDisable(Integer disableid);

	/**
	 *增加用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable addSysUserDisable(SysUserDisable sysUserDisable);

	/**
	 *删除用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable delSysUserDisable(String disableid);

	/**
	 *删除用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable delSysUserDisable(SysUserDisable sysUserDisable);

	/**
	 *修改用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable updateSysUserDisable(SysUserDisable sysUserDisable);

	/**
	 *获取用户禁用记录集合
	 *@return List
	 */
	public List getSysUserDisables (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页用户禁用记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserDisables (List<SearchModel> condition, String orderby, int start, int pagesize);

}