package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseStudy;
import com.wkmk.edu.service.EduCourseStudyManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程学习</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseStudyManagerImpl implements EduCourseStudyManager{

	private BaseDAO baseDAO;
	private String modelname = "课程学习";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy getEduCourseStudy(String studyid){
		Integer iid = Integer.valueOf(studyid);
		return  (EduCourseStudy)baseDAO.getObject(modelname,EduCourseStudy.class,iid);
	}

	/**
	 *根据id获取课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy getEduCourseStudy(Integer studyid){
		return  (EduCourseStudy)baseDAO.getObject(modelname,EduCourseStudy.class,studyid);
	}

	/**
	 *增加课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy addEduCourseStudy(EduCourseStudy eduCourseStudy){
		return (EduCourseStudy)baseDAO.addObject(modelname,eduCourseStudy);
	}

	/**
	 *删除课程学习
	 *@param studyid Integer
	 *@return EduCourseStudy
	 */
	public EduCourseStudy delEduCourseStudy(String studyid){
		EduCourseStudy model = getEduCourseStudy(studyid);
		return (EduCourseStudy)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy delEduCourseStudy(EduCourseStudy eduCourseStudy){
		return (EduCourseStudy)baseDAO.delObject(modelname,eduCourseStudy);
	}

	/**
	 *修改课程学习
	 *@param eduCourseStudy EduCourseStudy
	 *@return EduCourseStudy
	 */
	public EduCourseStudy updateEduCourseStudy(EduCourseStudy eduCourseStudy){
		return (EduCourseStudy)baseDAO.updateObject(modelname,eduCourseStudy);
	}

	/**
	 *获取课程学习集合
 	 *@return List
	 */
	public List getEduCourseStudys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseStudy",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程学习集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseStudys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseStudy","studyid",condition, orderby, start,pagesize);
	}

}