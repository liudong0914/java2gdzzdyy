package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassGroup;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级分组信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassGroupManager {
	/**
	 *根据id获取班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup getTkClassGroup(String groupid);

	/**
	 *根据id获取班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup getTkClassGroup(Integer groupid);

	/**
	 *增加班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup addTkClassGroup(TkClassGroup tkClassGroup);

	/**
	 *删除班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup delTkClassGroup(String groupid);

	/**
	 *删除班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup delTkClassGroup(TkClassGroup tkClassGroup);

	/**
	 *修改班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup updateTkClassGroup(TkClassGroup tkClassGroup);

	/**
	 *获取班级分组信息集合
	 *@return List
	 */
	public List getTkClassGroups (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页班级分组信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassGroups (List<SearchModel> condition, String orderby, int start, int pagesize);

}