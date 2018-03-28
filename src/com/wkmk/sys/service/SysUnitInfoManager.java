package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUnitInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUnitInfoManager {
	/**
	 *根据id获取系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo getSysUnitInfo(String unitid);

	/**
	 *根据id获取系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo getSysUnitInfo(Integer unitid);

	/**
	 *增加系统单位信息
	 *@param sysUnitInfo SysUnitInfo
	 *@return SysUnitInfo
	 */
	public SysUnitInfo addSysUnitInfo(SysUnitInfo sysUnitInfo);

	/**
	 *删除系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo delSysUnitInfo(String unitid);

	/**
	 *修改系统单位信息
	 *@param sysUnitInfo SysUnitInfo
	 *@return SysUnitInfo
	 */
	public SysUnitInfo updateSysUnitInfo(SysUnitInfo sysUnitInfo);

	/**
	 *获取系统单位信息集合
	 *@return List
	 */
	public List getSysUnitInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统单位信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}