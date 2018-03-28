package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduSubjectInfoManager {
	/**
	 *根据id获取教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo getEduSubjectInfo(String subjectid);

	/**
	 *根据id获取教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo getEduSubjectInfo(Integer subjectid);

	/**
	 *增加教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo addEduSubjectInfo(EduSubjectInfo eduSubjectInfo);

	/**
	 *删除教育学科信息
	 *@param subjectid Integer
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo delEduSubjectInfo(String subjectid);

	/**
	 *删除教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo delEduSubjectInfo(EduSubjectInfo eduSubjectInfo);

	/**
	 *修改教育学科信息
	 *@param eduSubjectInfo EduSubjectInfo
	 *@return EduSubjectInfo
	 */
	public EduSubjectInfo updateEduSubjectInfo(EduSubjectInfo eduSubjectInfo);

	/**
	 *获取教育学科信息集合
	 *@return List
	 */
	public List getEduSubjectInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页教育学科信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduSubjectInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}