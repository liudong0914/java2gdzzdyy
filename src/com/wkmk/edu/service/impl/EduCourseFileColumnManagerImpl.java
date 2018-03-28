package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFileColumn;
import com.wkmk.edu.service.EduCourseFileColumnManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程资源目录信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileColumnManagerImpl implements EduCourseFileColumnManager{

	private BaseDAO baseDAO;
	private String modelname = "课程资源目录信息表";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn getEduCourseFileColumn(String filecolumnid){
		Integer iid = Integer.valueOf(filecolumnid);
		return  (EduCourseFileColumn)baseDAO.getObject(modelname,EduCourseFileColumn.class,iid);
	}

	/**
	 *根据id获取课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn getEduCourseFileColumn(Integer filecolumnid){
		return  (EduCourseFileColumn)baseDAO.getObject(modelname,EduCourseFileColumn.class,filecolumnid);
	}

	/**
	 *增加课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn addEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn){
		return (EduCourseFileColumn)baseDAO.addObject(modelname,eduCourseFileColumn);
	}

	/**
	 *删除课程资源目录信息表
	 *@param filecolumnid Integer
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn delEduCourseFileColumn(String filecolumnid){
		EduCourseFileColumn model = getEduCourseFileColumn(filecolumnid);
		return (EduCourseFileColumn)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn delEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn){
		return (EduCourseFileColumn)baseDAO.delObject(modelname,eduCourseFileColumn);
	}

	/**
	 *修改课程资源目录信息表
	 *@param eduCourseFileColumn EduCourseFileColumn
	 *@return EduCourseFileColumn
	 */
	public EduCourseFileColumn updateEduCourseFileColumn(EduCourseFileColumn eduCourseFileColumn){
		return (EduCourseFileColumn)baseDAO.updateObject(modelname,eduCourseFileColumn);
	}

	/**
	 *获取课程资源目录信息表集合
 	 *@return List
	 */
	public List getEduCourseFileColumns(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseFileColumn",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程资源目录信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFileColumns (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseFileColumn","filecolumnid",condition, orderby, start,pagesize);
	}

}