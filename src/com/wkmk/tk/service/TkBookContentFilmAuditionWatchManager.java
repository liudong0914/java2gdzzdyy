package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmAuditionWatch;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试听解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentFilmAuditionWatchManager {
	/**
	 *根据id获取试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch getTkBookContentFilmAuditionWatch(String watchid);

	/**
	 *根据id获取试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch getTkBookContentFilmAuditionWatch(Integer watchid);

	/**
	 *增加试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch addTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch);

	/**
	 *删除试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch delTkBookContentFilmAuditionWatch(String watchid);

	/**
	 *删除试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch delTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch);

	/**
	 *修改试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch updateTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch);

	/**
	 *获取试听解题微课播放记录集合
	 *@return List
	 */
	public List getTkBookContentFilmAuditionWatchs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页试听解题微课播放记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentFilmAuditionWatchs (List<SearchModel> condition, String orderby, int start, int pagesize);

}