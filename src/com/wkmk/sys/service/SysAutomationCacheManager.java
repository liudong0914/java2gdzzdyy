package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysAutomationCache;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统自动化高速缓存</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysAutomationCacheManager {
	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCache(String cacheid);

	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCache(Integer cacheid);
	
	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCacheByKeyid(String keyid);

	/**
	 *增加系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache addSysAutomationCache(SysAutomationCache sysAutomationCache);

	/**
	 *删除系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache delSysAutomationCache(String cacheid);

	/**
	 *删除系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache delSysAutomationCache(SysAutomationCache sysAutomationCache);

	/**
	 *删除系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public void delSysAutomationCacheByKeyid(String keyid);
	
	/**
	 *修改系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache updateSysAutomationCache(SysAutomationCache sysAutomationCache);

	/**
	 *获取系统自动化高速缓存集合
	 *@return List
	 */
	public List getSysAutomationCaches (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取系统自动化高速缓存集合
 	 *@return List
	 */
	public List getSysAutomationCaches();
	
	/**
	 *获取一页系统自动化高速缓存集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysAutomationCaches (List<SearchModel> condition, String orderby, int start, int pagesize);

}