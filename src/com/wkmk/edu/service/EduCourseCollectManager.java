package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseCollect;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程收藏</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseCollectManager {
	/**
	 *根据id获取课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect getEduCourseCollect(String collectid);

	/**
	 *根据id获取课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect getEduCourseCollect(Integer collectid);

	/**
	 *增加课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect addEduCourseCollect(EduCourseCollect eduCourseCollect);

	/**
	 *删除课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect delEduCourseCollect(String collectid);

	/**
	 *删除课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect delEduCourseCollect(EduCourseCollect eduCourseCollect);

	/**
	 *修改课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect updateEduCourseCollect(EduCourseCollect eduCourseCollect);

	/**
	 *获取课程收藏集合
	 *@return List
	 */
	public List getEduCourseCollects (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程收藏集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseCollects (List<SearchModel> condition, String orderby, int start, int pagesize);

}