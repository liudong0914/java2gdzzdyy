package com.wkmk.edu.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseInfo;

/**
 *<p>Description: 课程信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseInfoManager {
	/**
	 *根据id获取课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo getEduCourseInfo(String courseid);

	/**
	 *根据id获取课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo getEduCourseInfo(Integer courseid);

	/**
	 *增加课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo addEduCourseInfo(EduCourseInfo eduCourseInfo);

	/**
	 *删除课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo delEduCourseInfo(String courseid);

	/**
	 *删除课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo delEduCourseInfo(EduCourseInfo eduCourseInfo);

	/**
	 *修改课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo updateEduCourseInfo(EduCourseInfo eduCourseInfo);

	/**
	 *获取课程信息集合
	 *@return List
	 */
	public List getEduCourseInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseInfos (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList getEduCourseInfosOfPage(String title, String coursetypeid, String createdate,String sorderindex,int start, int size);

}