package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseComment;
import com.wkmk.edu.service.EduCourseCommentManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCommentManagerImpl implements EduCourseCommentManager{

	private BaseDAO baseDAO;
	private String modelname = "课程评价";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment getEduCourseComment(String commentid){
		Integer iid = Integer.valueOf(commentid);
		return  (EduCourseComment)baseDAO.getObject(modelname,EduCourseComment.class,iid);
	}

	/**
	 *根据id获取课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment getEduCourseComment(Integer commentid){
		return  (EduCourseComment)baseDAO.getObject(modelname,EduCourseComment.class,commentid);
	}

	/**
	 *增加课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment addEduCourseComment(EduCourseComment eduCourseComment){
		return (EduCourseComment)baseDAO.addObject(modelname,eduCourseComment);
	}

	/**
	 *删除课程评价
	 *@param commentid Integer
	 *@return EduCourseComment
	 */
	public EduCourseComment delEduCourseComment(String commentid){
		EduCourseComment model = getEduCourseComment(commentid);
		return (EduCourseComment)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment delEduCourseComment(EduCourseComment eduCourseComment){
		return (EduCourseComment)baseDAO.delObject(modelname,eduCourseComment);
	}

	/**
	 *修改课程评价
	 *@param eduCourseComment EduCourseComment
	 *@return EduCourseComment
	 */
	public EduCourseComment updateEduCourseComment(EduCourseComment eduCourseComment){
		return (EduCourseComment)baseDAO.updateObject(modelname,eduCourseComment);
	}

	/**
	 *获取课程评价集合
 	 *@return List
	 */
	public List getEduCourseComments(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseComment",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程评价分数集合
 	 *@return List
	 */
	public List getEduCourseCommentScores(String courseid){
		String sql = "select a.score from EduCourseComment as a where a.courseid=" + courseid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页课程评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseComments (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseComment","commentid",condition, orderby, start,pagesize);
	}

}