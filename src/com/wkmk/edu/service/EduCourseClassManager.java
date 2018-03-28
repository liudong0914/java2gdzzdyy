package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseClass;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程班</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseClassManager {
	/**
	 *根据id获取课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass getEduCourseClass(String courseclassid);

	/**
	 *根据id获取课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass getEduCourseClass(Integer courseclassid);

	/**
	 *增加课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass addEduCourseClass(EduCourseClass eduCourseClass);

	/**
	 *删除课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass delEduCourseClass(String courseclassid);

	/**
	 *删除课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass delEduCourseClass(EduCourseClass eduCourseClass);

	/**
	 *修改课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass updateEduCourseClass(EduCourseClass eduCourseClass);

	/**
	 *获取课程班集合
	 *@return List
	 */
	public List getEduCourseClasss (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程班集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseClasss (List<SearchModel> condition, String orderby, int start, int pagesize);

}