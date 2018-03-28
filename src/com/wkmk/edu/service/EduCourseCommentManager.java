package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseComment;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseCommentManager {
	/**
	 *根据id获取课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment getEduCourseComment(String commentid);

	/**
	 *根据id获取课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment getEduCourseComment(Integer commentid);

	/**
	 *增加课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment addEduCourseComment(EduCourseComment eduCourseComment);

	/**
	 *删除课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment delEduCourseComment(String commentid);

	/**
	 *删除课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment delEduCourseComment(EduCourseComment eduCourseComment);

	/**
	 *修改课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment updateEduCourseComment(EduCourseComment eduCourseComment);

	/**
	 *获取课程评价集合
	 *@return List
	 */
	public List getEduCourseComments (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程评价分数集合
 	 *@return List
	 */
	public List getEduCourseCommentScores(String courseid);
	
	/**
	 *获取一页课程评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseComments (List<SearchModel> condition, String orderby, int start, int pagesize);

}