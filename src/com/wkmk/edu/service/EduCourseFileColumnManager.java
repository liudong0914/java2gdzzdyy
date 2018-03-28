package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFileColumn;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程资源目录信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseFileColumnManager {
	/**
	 *根据id获取课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn getEduCourseFileColumn(String filecolumnid);

	/**
	 *根据id获取课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn getEduCourseFileColumn(Integer filecolumnid);

	/**
	 *增加课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn addEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn);

	/**
	 *删除课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn delEduCourseFileColumn(String filecolumnid);

	/**
	 *删除课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn delEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn);

	/**
	 *修改课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn updateEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn);

	/**
	 *获取课程资源目录信息表集合
	 *@return List
	 */
	public List getEduCourseFileColumns (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程资源目录信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFileColumns (List<SearchModel> condition, String orderby, int start, int pagesize);

}