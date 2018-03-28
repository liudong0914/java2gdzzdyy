package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduSubjectInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduSubjectInfoManagerImpl implements EduSubjectInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "教育学科信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo getEduSubjectInfo(String subjectid){
		Integer iid = Integer.valueOf(subjectid);
		return  (EduSubjectInfo)baseDAO.getObject(modelname,EduSubjectInfo.class,iid);
	}

	/**
	 *根据id获取教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo getEduSubjectInfo(Integer subjectid){
		return  (EduSubjectInfo)baseDAO.getObject(modelname,EduSubjectInfo.class,subjectid);
	}

	/**
	 *增加教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo addEduSubjectInfo(EduSubjectInfo eduSubjectInfo){
		return (EduSubjectInfo)baseDAO.addObject(modelname,eduSubjectInfo);
	}

	/**
	 *删除教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo delEduSubjectInfo(String subjectid){
		EduSubjectInfo model = getEduSubjectInfo(subjectid);
		return (EduSubjectInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo delEduSubjectInfo(EduSubjectInfo eduSubjectInfo){
		return (EduSubjectInfo)baseDAO.delObject(modelname,eduSubjectInfo);
	}

	/**
	 *修改教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo updateEduSubjectInfo(EduSubjectInfo eduSubjectInfo){
		return (EduSubjectInfo)baseDAO.updateObject(modelname,eduSubjectInfo);
	}

	/**
	 *获取教育学科信息集合
 	 *@return List
	 */
	public List getEduSubjectInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduSubjectInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页教育学科信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduSubjectInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduSubjectInfo","subjectid",condition, orderby, start,pagesize);
	}

}