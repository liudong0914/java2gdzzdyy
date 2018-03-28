package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysKeywordFilter;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统关键字过滤</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysKeywordFilterManager {
	/**
	 *根据id获取系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter getSysKeywordFilter(String keywordid);

	/**
	 *根据id获取系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter getSysKeywordFilter(Integer keywordid);

	/**
	 *增加系统关键字过滤
	 *@param sysKeywordFilter SysKeywordFilter
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter addSysKeywordFilter(SysKeywordFilter sysKeywordFilter);

	/**
	 *删除系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter delSysKeywordFilter(String keywordid);

	/**
	 *修改系统关键字过滤
	 *@param sysKeywordFilter SysKeywordFilter
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter updateSysKeywordFilter(SysKeywordFilter sysKeywordFilter);

	/**
	 *获取系统关键字过滤集合
	 *@return List
	 */
	public List getSysKeywordFilters (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统关键字过滤集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysKeywordFilters (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 获取某单位的关键字过滤器
	 * @param product
	 * @param unitid
	 * @return
	 */
	public SysKeywordFilter getSysKeywordFilterByUnitid(Integer unitid);
}