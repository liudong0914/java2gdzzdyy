package com.wkmk.vwh.service;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmWatch;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微课观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface VwhFilmWatchManager {
	/**
	 *根据id获取微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch getVwhFilmWatch(String watchid);

	/**
	 *根据id获取微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch getVwhFilmWatch(Integer watchid);

	/**
	 *增加微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch addVwhFilmWatch(VwhFilmWatch vwhFilmWatch);

	/**
	 *删除微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch delVwhFilmWatch(String watchid);

	/**
	 *删除微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch delVwhFilmWatch(VwhFilmWatch vwhFilmWatch);

	/**
	 *修改微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch updateVwhFilmWatch(VwhFilmWatch vwhFilmWatch);

	/**
	 *获取微课观看记录集合
	 *@return List
	 */
	public List getVwhFilmWatchs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页微课观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmWatchs (List<SearchModel> condition, String orderby, int start, int pagesize);

}