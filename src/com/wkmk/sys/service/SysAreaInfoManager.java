package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysAreaInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 地区信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysAreaInfoManager {
	/**
	 *根据id获取地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfo(String areaid);

	/**
	 *根据id获取地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfo(Integer areaid);

	/**
	 *增加地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo addSysAreaInfo(SysAreaInfo sysAreaInfo);

	/**
	 *删除地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo delSysAreaInfo(String areaid);

	/**
	 *删除地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo delSysAreaInfo(SysAreaInfo sysAreaInfo);

	/**
	 *修改地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo updateSysAreaInfo(SysAreaInfo sysAreaInfo);

	/**
	 *获取地区信息集合
	 *@return List
	 */
	public List getSysAreaInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取地区信息集合
 	 *@return List
	 */
	public List getSysAreaInfosByParentno(String parentno);
	
	/**
	 *获取地区信息
 	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfosByCitycode(String citycode);
	
	/**
	 *获取一页地区信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysAreaInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}