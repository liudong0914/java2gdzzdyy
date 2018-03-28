package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsFilm;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 习题微课
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkQuestionsFilmManager {
	/**
	 * 根据id获取习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm getTkQuestionsFilm(String tid);

	/**
	 * 根据id获取习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm getTkQuestionsFilm(Integer tid);

	/**
	 * 增加习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm addTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm);

	/**
	 * 删除习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm delTkQuestionsFilm(String tid);

	/**
	 * 删除习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm delTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm);

	/**
	 * 修改习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm updateTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm);

	/**
	 * 获取习题微课集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsFilms(List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 * 获取习题微课集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsFilms(String questionid);

	public int getCountTkQuestionsFilms(Integer questionid);
	
	/**
	 * 获取一页习题微课集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsFilms(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据questionid与filmid删除对象
	 * */
	public void delTkQuestionsFilms(String questionid, String filmid);
}