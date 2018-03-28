package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseStudy;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程学习</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseStudyManager {
	/**
	 *根据id获取课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy getEduCourseStudy(String studyid);

	/**
	 *根据id获取课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy getEduCourseStudy(Integer studyid);

	/**
	 *增加课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy addEduCourseStudy(EduCourseStudy eduCourseStudy);

	/**
	 *删除课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy delEduCourseStudy(String studyid);

	/**
	 *删除课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy delEduCourseStudy(EduCourseStudy eduCourseStudy);

	/**
	 *修改课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy updateEduCourseStudy(EduCourseStudy eduCourseStudy);

	/**
	 *获取课程学习集合
	 *@return List
	 */
	public List getEduCourseStudys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程学习集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseStudys (List<SearchModel> condition, String orderby, int start, int pagesize);

}