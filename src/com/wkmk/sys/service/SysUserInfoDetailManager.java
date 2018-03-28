package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserInfoDetail;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户扩展信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserInfoDetailManager {
	/**
	 *根据id获取系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail getSysUserInfoDetail(String userid);

	/**
	 *根据id获取系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail getSysUserInfoDetail(Integer userid);

	/**
	 *增加系统用户扩展信息
	 *@param sysUserInfoDetail SysUserInfoDetail
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail addSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail);

	/**
	 *删除系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail delSysUserInfoDetail(String userid);

	/**
	 *修改系统用户扩展信息
	 *@param sysUserInfoDetail SysUserInfoDetail
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail updateSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail);

	/**
	 *获取系统用户扩展信息集合
	 *@return List
	 */
	public List getSysUserInfoDetails (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统用户扩展信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserInfoDetails (List<SearchModel> condition, String orderby, int start, int pagesize);

}