package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysModuleInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统模块信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysModuleInfoManager {
	/**
	 *根据id获取系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo getSysModuleInfo(String moduleid);

	/**
	 *根据id获取系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo getSysModuleInfo(Integer moduleid);

	/**
	 *增加系统模块信息
	 *@param sysModuleInfo SysModuleInfo
	 *@return SysModuleInfo
	 */
	public SysModuleInfo addSysModuleInfo(SysModuleInfo sysModuleInfo);

	/**
	 *删除系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo delSysModuleInfo(String moduleid);
	
	/**
	 *删除系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo delSysModuleInfo(SysModuleInfo sysModuleInfo);

	/**
	 *修改系统模块信息
	 *@param sysModuleInfo SysModuleInfo
	 *@return SysModuleInfo
	 */
	public SysModuleInfo updateSysModuleInfo(SysModuleInfo sysModuleInfo);

	/**
	 *获取系统模块信息集合
	 *@return List
	 */
	public List getSysModuleInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统模块信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysModuleInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 获取系统当前用户在当前单位当前产品所有的模块集合
	 * @return List
	 */
	public List getPermissionModuleList(Integer userid, Integer unitid, Integer productid);
	
	/**
	 * 获取一级菜单模块集合
	 * @return List
	 */
	public List getAllParentModuleList(Integer productid);
	
	/**
	 * 获取当前单位所有父模块编号
	 * @return List
	 */
	public List getAllParentnoByProduct(Integer productid);
	
	/**
	 * 获取没有子模块的模块id
	 * @return List
	 */
	public List getNoSubModuleids(Integer productid);
	
	/**
	 * 获取所有菜单模块集合
	 * @return List
	 */
	public List getAllModuleList(Integer productid);
}