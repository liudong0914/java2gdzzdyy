package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperType;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件分类</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkPaperTypeManager {
	/**
	 *根据id获取试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType getTkPaperType(String typeid);

	/**
	 *根据id获取试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType getTkPaperType(Integer typeid);

	/**
	 *增加试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType addTkPaperType(TkPaperType tkPaperType);

	/**
	 *删除试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType delTkPaperType(String typeid);

	/**
	 *删除试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType delTkPaperType(TkPaperType tkPaperType);

	/**
	 *修改试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType updateTkPaperType(TkPaperType tkPaperType);

	/**
	 *获取试卷附件分类集合
	 *@return List
	 */
	public List getTkPaperTypes (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页试卷附件分类集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperTypes (List<SearchModel> condition, String orderby, int start, int pagesize);

}