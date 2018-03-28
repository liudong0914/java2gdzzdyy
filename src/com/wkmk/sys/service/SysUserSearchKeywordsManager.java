package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserSearchKeywords;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户历史搜索关键词</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserSearchKeywordsManager {
	/**
	 *根据id获取用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords getSysUserSearchKeywords(String searchid);

	/**
	 *根据id获取用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords getSysUserSearchKeywords(Integer searchid);

	/**
	 *增加用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords addSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords);

	/**
	 *删除用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords delSysUserSearchKeywords(String searchid);

	/**
	 *删除用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords delSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords);

	/**
	 *修改用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords updateSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords);

	/**
	 *获取用户历史搜索关键词集合
	 *@return List
	 */
	public List getSysUserSearchKeywordss (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取用户历史搜索关键词集合
 	 *@return List
	 */
	public List getSysUserSearchKeywordssByHistory(String userid, String searchtyp, String orderby,int pagesize);
	
	/**
	 *获取一页用户历史搜索关键词集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserSearchKeywordss (List<SearchModel> condition, String orderby, int start, int pagesize);

}