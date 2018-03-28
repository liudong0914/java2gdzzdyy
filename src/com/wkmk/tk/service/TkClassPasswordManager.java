package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassPassword;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级密码</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassPasswordManager {
	/**
	 *根据id获取班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword getTkClassPassword(String passwordid);

	/**
	 *根据id获取班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword getTkClassPassword(Integer passwordid);

	/**
	 *增加班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword addTkClassPassword(TkClassPassword tkClassPassword);

	/**
	 *删除班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword delTkClassPassword(String passwordid);

	/**
	 *删除班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword delTkClassPassword(TkClassPassword tkClassPassword);

	/**
	 *修改班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword updateTkClassPassword(TkClassPassword tkClassPassword);

	/**
	 *获取班级密码集合
	 *@return List
	 */
	public List getTkClassPasswords (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *删除班级密码
 	 *@return List
	 */
	public void delTkClassPasswordByClassid(String classid);
	
	/**
	 *获取班级密码
 	 *@return List
	 */
	public TkClassPassword getTkClassPassword(String password, String classid);

	/**
	 *获取一页班级密码集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassPasswords (List<SearchModel> condition, String orderby, int start, int pagesize);

}