package com.wkmk.tk.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkBookContentWatch;

/**
 *<p>Description: 音频文件观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentWatchManager {
	/**
	 *根据id获取音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch getTkBookContentWatch(String watchid);

	/**
	 *根据id获取音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch getTkBookContentWatch(Integer watchid);

	/**
	 *增加音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch addTkBookContentWatch(TkBookContentWatch tkBookContentWatch);

	/**
	 *删除音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch delTkBookContentWatch(String watchid);

	/**
	 *删除音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch delTkBookContentWatch(TkBookContentWatch tkBookContentWatch);

	/**
	 *修改音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch updateTkBookContentWatch(TkBookContentWatch tkBookContentWatch);

	/**
	 *获取音频文件观看记录集合
	 *@return List
	 */
	public List getTkBookContentWatchs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页音频文件观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentWatchs (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 * 
	  * 方法描述：每天听力播放次数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getTkBookContentWatchsOfDay();
	/**
	 * 
	  * 方法描述：每天听力播放次数最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getTkBookContentWatchsOfDayMaxNum();

}