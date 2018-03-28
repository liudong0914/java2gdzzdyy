package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseCollect;
import com.wkmk.edu.service.EduCourseCollectManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程收藏</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCollectManagerImpl implements EduCourseCollectManager{

	private BaseDAO baseDAO;
	private String modelname = "课程收藏";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect getEduCourseCollect(String collectid){
		Integer iid = Integer.valueOf(collectid);
		return  (EduCourseCollect)baseDAO.getObject(modelname,EduCourseCollect.class,iid);
	}

	/**
	 *根据id获取课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect getEduCourseCollect(Integer collectid){
		return  (EduCourseCollect)baseDAO.getObject(modelname,EduCourseCollect.class,collectid);
	}

	/**
	 *增加课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect addEduCourseCollect(EduCourseCollect eduCourseCollect){
		return (EduCourseCollect)baseDAO.addObject(modelname,eduCourseCollect);
	}

	/**
	 *删除课程收藏
	 *@param collectid Integer
	 *@return EduCourseCollect
	 */
	public EduCourseCollect delEduCourseCollect(String collectid){
		EduCourseCollect model = getEduCourseCollect(collectid);
		return (EduCourseCollect)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect delEduCourseCollect(EduCourseCollect eduCourseCollect){
		return (EduCourseCollect)baseDAO.delObject(modelname,eduCourseCollect);
	}

	/**
	 *修改课程收藏
	 *@param eduCourseCollect EduCourseCollect
	 *@return EduCourseCollect
	 */
	public EduCourseCollect updateEduCourseCollect(EduCourseCollect eduCourseCollect){
		return (EduCourseCollect)baseDAO.updateObject(modelname,eduCourseCollect);
	}

	/**
	 *获取课程收藏集合
 	 *@return List
	 */
	public List getEduCourseCollects(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseCollect",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程收藏集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseCollects (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseCollect","collectid",condition, orderby, start,pagesize);
	}

}