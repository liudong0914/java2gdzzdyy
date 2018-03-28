package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassGroupUser;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级组成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassGroupUserManager {
	/**
	 *根据id获取班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser getTkClassGroupUser(String groupuserid);

	/**
	 *根据id获取班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser getTkClassGroupUser(Integer groupuserid);

	/**
	 *增加班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser addTkClassGroupUser(TkClassGroupUser tkClassGroupUser);

	/**
	 *删除班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser delTkClassGroupUser(String groupuserid);

	/**
	 *删除班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser delTkClassGroupUser(TkClassGroupUser tkClassGroupUser);

	/**
	 *修改班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser updateTkClassGroupUser(TkClassGroupUser tkClassGroupUser);

	/**
	 *获取班级组成员集合
	 *@return List
	 */
	public List getTkClassGroupUsers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页班级组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassGroupUsers (List<SearchModel> condition, String orderby, int start, int pagesize);

}