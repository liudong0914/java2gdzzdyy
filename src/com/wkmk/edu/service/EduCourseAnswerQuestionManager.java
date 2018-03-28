package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseAnswerQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程答疑</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseAnswerQuestionManager {
	/**
	 *根据id获取课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion getEduCourseAnswerQuestion(String questionid);

	/**
	 *根据id获取课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion getEduCourseAnswerQuestion(Integer questionid);

	/**
	 *增加课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion addEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion);

	/**
	 *删除课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion delEduCourseAnswerQuestion(String questionid);

	/**
	 *删除课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion delEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion);

	/**
	 *修改课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion updateEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion);

	/**
	 *获取课程答疑集合
	 *@return List
	 */
	public List getEduCourseAnswerQuestions (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程答疑集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseAnswerQuestions (List<SearchModel> condition, String orderby, int start, int pagesize);

}