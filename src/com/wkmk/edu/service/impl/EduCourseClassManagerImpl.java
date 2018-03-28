package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.service.EduCourseClassManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程班</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseClassManagerImpl implements EduCourseClassManager{

	private BaseDAO baseDAO;
	private String modelname = "课程班";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass getEduCourseClass(String courseclassid){
		Integer iid = Integer.valueOf(courseclassid);
		return  (EduCourseClass)baseDAO.getObject(modelname,EduCourseClass.class,iid);
	}

	/**
	 *根据id获取课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass getEduCourseClass(Integer courseclassid){
		return  (EduCourseClass)baseDAO.getObject(modelname,EduCourseClass.class,courseclassid);
	}

	/**
	 *增加课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass addEduCourseClass(EduCourseClass eduCourseClass){
		return (EduCourseClass)baseDAO.addObject(modelname,eduCourseClass);
	}

	/**
	 *删除课程班
	 *@param courseclassid Integer
	 *@return EduCourseClass
	 */
	public EduCourseClass delEduCourseClass(String courseclassid){
		EduCourseClass model = getEduCourseClass(courseclassid);
		return (EduCourseClass)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass delEduCourseClass(EduCourseClass eduCourseClass){
		return (EduCourseClass)baseDAO.delObject(modelname,eduCourseClass);
	}

	/**
	 *修改课程班
	 *@param eduCourseClass EduCourseClass
	 *@return EduCourseClass
	 */
	public EduCourseClass updateEduCourseClass(EduCourseClass eduCourseClass){
		return (EduCourseClass)baseDAO.updateObject(modelname,eduCourseClass);
	}

	/**
	 *获取课程班集合
 	 *@return List
	 */
	public List getEduCourseClasss(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseClass",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程班集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseClasss (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseClass","courseclassid",condition, orderby, start,pagesize);
	}

}