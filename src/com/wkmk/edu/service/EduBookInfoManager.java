package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduBookInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材课本</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduBookInfoManager {
	/**
	 *根据id获取教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo getEduBookInfo(String bookid);

	/**
	 *根据id获取教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo getEduBookInfo(Integer bookid);

	/**
	 *增加教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo addEduBookInfo(EduBookInfo eduBookInfo);

	/**
	 *删除教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo delEduBookInfo(String bookid);

	/**
	 *删除教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo delEduBookInfo(EduBookInfo eduBookInfo);

	/**
	 *修改教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo updateEduBookInfo(EduBookInfo eduBookInfo);

	/**
	 *获取教材课本集合
	 *@return List
	 */
	public List getEduBookInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页教材课本集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduBookInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}