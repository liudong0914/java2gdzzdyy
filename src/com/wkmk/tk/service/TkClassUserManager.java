package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassUser;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassUserManager {
	/**
	 *根据id获取班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser getTkClassUser(String classuserid);

	/**
	 *根据id获取班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser getTkClassUser(Integer classuserid);

	/**
	 *增加班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser addTkClassUser(TkClassUser tkClassUser);

	/**
	 *删除班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser delTkClassUser(String classuserid);

	/**
	 *删除班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser delTkClassUser(TkClassUser tkClassUser);

	/**
	 *修改班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser updateTkClassUser(TkClassUser tkClassUser);

	/**
	 *获取班级用户信息集合
	 *@return List
	 */
	public List getTkClassUsers (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取班级用户信息集合
 	 *@return List
	 */
	public List getBookAndClassByUserid(String userid);
	
	/**
	 *获取班级用户关联作业本信息集合
 	 *@return List
	 */
	public List getAllBookidByUserid(String userid);
	
	public int getStudentsByClassid(String classid);
	
	public List getSysUserInfosByClassid(String classid);
	
	/**
	 * 未提交作业学生
	 */
	public List getUncommitStudentsByClassid(String classid, List answerUseridList);

	/**
	 *获取一页班级用户信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassUsers (List<SearchModel> condition, String orderby, int start, int pagesize);

}