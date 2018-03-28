package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsSimilarWatch;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 举一反三观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkQuestionsSimilarWatchManager {
	/**
	 *根据id获取举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch getTkQuestionsSimilarWatch(String watchid);

	/**
	 *根据id获取举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch getTkQuestionsSimilarWatch(Integer watchid);

	/**
	 *增加举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch addTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch);

	/**
	 *删除举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch delTkQuestionsSimilarWatch(String watchid);

	/**
	 *删除举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch delTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch);

	/**
	 *修改举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch updateTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch);

	/**
	 *获取举一反三观看记录集合
	 *@return List
	 */
	public List getTkQuestionsSimilarWatchs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页举一反三观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkQuestionsSimilarWatchs (List<SearchModel> condition, String orderby, int start, int pagesize);

}