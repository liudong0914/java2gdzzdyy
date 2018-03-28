package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperStyle;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 题库试卷样式</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkPaperStyleManager {
	/**
	 *根据id获取题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle getTkPaperStyle(String styleid);

	/**
	 *根据id获取题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle getTkPaperStyle(Integer styleid);

	/**
	 *增加题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle addTkPaperStyle(TkPaperStyle tkPaperStyle);

	/**
	 *删除题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle delTkPaperStyle(String styleid);

	/**
	 *删除题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle delTkPaperStyle(TkPaperStyle tkPaperStyle);

	/**
	 *修改题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle updateTkPaperStyle(TkPaperStyle tkPaperStyle);

	/**
	 *获取题库试卷样式集合
	 *@return List
	 */
	public List getTkPaperStyles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页题库试卷样式集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperStyles (List<SearchModel> condition, String orderby, int start, int pagesize);

}