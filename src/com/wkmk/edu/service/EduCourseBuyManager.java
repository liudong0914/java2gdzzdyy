package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseBuy;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程购买信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseBuyManager {
	/**
	 *根据id获取课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy getEduCourseBuy(String buyid);

	/**
	 *根据id获取课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy getEduCourseBuy(Integer buyid);

	/**
	 *增加课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy addEduCourseBuy(EduCourseBuy eduCourseBuy);

	/**
	 *删除课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy delEduCourseBuy(String buyid);

	/**
	 *删除课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy delEduCourseBuy(EduCourseBuy eduCourseBuy);

	/**
	 *修改课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy updateEduCourseBuy(EduCourseBuy eduCourseBuy);

	/**
	 *获取课程购买信息集合
	 *@return List
	 */
	public List getEduCourseBuys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程购买信息集合
 	 *@return List
	 */
	public List getAllCoursefilmidByUserid(String userid, String courseid);
	
	/**
	 *获取一页课程购买信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseBuys (List<SearchModel> condition, String orderby, int start, int pagesize);

}