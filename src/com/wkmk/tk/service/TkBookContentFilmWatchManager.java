package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmWatch;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentFilmWatchManager {
	/**
	 *根据id获取解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch getTkBookContentFilmWatch(String watchid);

	/**
	 *根据id获取解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch getTkBookContentFilmWatch(Integer watchid);

	/**
	 *增加解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch addTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch);

	/**
	 *删除解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch delTkBookContentFilmWatch(String watchid);

	/**
	 *删除解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch delTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch);

	/**
	 *修改解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch updateTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch);

	/**
	 *获取解题微课播放记录集合
	 *@return List
	 */
	public List getTkBookContentFilmWatchs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页解题微课播放记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentFilmWatchs (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 * 
	  * 方法描述：每天解题微课播放记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午5:51:24
	 */
	public List getTkBookContentFilmWatchsOfAll();
	
	/* 
	* 方法描述：每天解题微课播放记录最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getTkBookContentFilmWatchsOfMaxNum();
	
	/**
	 * 
	  * 方法描述：试听解题微课播放记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午5:55:10
	 */
	public List getTkBookContentFilmAuditionWatchOfAll();
	
	/* 
	* 方法描述：每天试听解题微课播放记录最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getTkBookContentFilmAuditionWatchOfMaxNum();

}