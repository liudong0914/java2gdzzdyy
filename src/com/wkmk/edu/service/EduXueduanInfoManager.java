package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduXueduanInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教育学段信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduXueduanInfoManager {
	/**
	 *根据id获取教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo getEduXueduanInfo(String xueduanid);

	/**
	 *根据id获取教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo getEduXueduanInfo(Integer xueduanid);

	/**
	 *增加教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo addEduXueduanInfo(EduXueduanInfo eduXueduanInfo);

	/**
	 *删除教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo delEduXueduanInfo(String xueduanid);

	/**
	 *删除教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo delEduXueduanInfo(EduXueduanInfo eduXueduanInfo);

	/**
	 *修改教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo updateEduXueduanInfo(EduXueduanInfo eduXueduanInfo);

	/**
	 *获取教育学段信息集合
	 *@return List
	 */
	public List getEduXueduanInfos (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取教育学段父id集合
 	 *@return List
	 */
	public List getAllParentids();

	/**
	 *获取一页教育学段信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduXueduanInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}