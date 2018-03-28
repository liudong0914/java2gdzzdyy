package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmAudition;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试听解题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentFilmAuditionManager {
	/**
	 *根据id获取试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition getTkBookContentFilmAudition(String auditionid);

	/**
	 *根据id获取试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition getTkBookContentFilmAudition(Integer auditionid);

	/**
	 *增加试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition addTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition);

	/**
	 *删除试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition delTkBookContentFilmAudition(String auditionid);

	/**
	 *删除试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition delTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition);

	/**
	 *修改试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition updateTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition);

	/**
	 *获取试听解题微课集合
	 *@return List
	 */
	public List getTkBookContentFilmAuditions (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页试听解题微课集合
	 * @param bookcontentid
	 * @param title
	 * @param orderby
	 * @param start
	 * @param size
	 * @return
	 */
	public PageList getPageTkBookContentFilmAuditionsByFilm(String bookcontentid,String title,String orderby,int start,int size);
	
	/**
	 * 微信端试听微课
	 * 获取一页试听解题微课集合
	 */	
	public PageList getPageFilms(String title,String orderby,int start,int size);
	
	/**
	 * 根据作业本id查询未添加到试听微课列表中的作业本微课
	 * @param bookcontentid
	 * @return
	 */
	public PageList getContentFilm(String bookcontentid,String title,int start,int size);
	
	//获取最大排序数字
	public int getMaxOrderIndex();
}