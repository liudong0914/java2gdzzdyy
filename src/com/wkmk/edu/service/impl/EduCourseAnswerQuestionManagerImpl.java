package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseAnswerQuestion;
import com.wkmk.edu.service.EduCourseAnswerQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程答疑</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseAnswerQuestionManagerImpl implements EduCourseAnswerQuestionManager{

	private BaseDAO baseDAO;
	private String modelname = "课程答疑";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion getEduCourseAnswerQuestion(String questionid){
		Integer iid = Integer.valueOf(questionid);
		return  (EduCourseAnswerQuestion)baseDAO.getObject(modelname,EduCourseAnswerQuestion.class,iid);
	}

	/**
	 *根据id获取课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion getEduCourseAnswerQuestion(Integer questionid){
		return  (EduCourseAnswerQuestion)baseDAO.getObject(modelname,EduCourseAnswerQuestion.class,questionid);
	}

	/**
	 *增加课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion addEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion){
		return (EduCourseAnswerQuestion)baseDAO.addObject(modelname,eduCourseAnswerQuestion);
	}

	/**
	 *删除课程答疑
	 *@param questionid Integer
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion delEduCourseAnswerQuestion(String questionid){
		EduCourseAnswerQuestion model = getEduCourseAnswerQuestion(questionid);
		return (EduCourseAnswerQuestion)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion delEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion){
		return (EduCourseAnswerQuestion)baseDAO.delObject(modelname,eduCourseAnswerQuestion);
	}

	/**
	 *修改课程答疑
	 *@param eduCourseAnswerQuestion EduCourseAnswerQuestion
	 *@return EduCourseAnswerQuestion
	 */
	public EduCourseAnswerQuestion updateEduCourseAnswerQuestion(EduCourseAnswerQuestion eduCourseAnswerQuestion){
		return (EduCourseAnswerQuestion)baseDAO.updateObject(modelname,eduCourseAnswerQuestion);
	}

	/**
	 *获取课程答疑集合
 	 *@return List
	 */
	public List getEduCourseAnswerQuestions(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseAnswerQuestion",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程答疑集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseAnswerQuestions (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseAnswerQuestion","questionid",condition, orderby, start,pagesize);
	}

}