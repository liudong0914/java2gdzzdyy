package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentGoodfilm;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 精品微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentGoodfilmManager {
	/**
	 *根据id获取精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm getTkBookContentGoodfilm(String goodfilmid);

	/**
	 *根据id获取精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm getTkBookContentGoodfilm(Integer goodfilmid);

	/**
	 *增加精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm addTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm);

	/**
	 *删除精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm delTkBookContentGoodfilm(String goodfilmid);

	/**
	 *删除精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm delTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm);

	/**
	 *修改精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm updateTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm);

	/**
	 *获取精品微课集合
	 *@return List
	 */
	public List getTkBookContentGoodfilms (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取作业本id集合
 	 *@return List
	 */
	public List getAllBookid(String status);
	
	public List getAllBookContentid(String status);
	
	/**
	 *获取作业本内容集合
 	 *@return List
	 */
	public List getTkBookContentsByBookContentGoodFilm(String bookid, String vodstate, String keywords);
	/**
	 *获取一页存在微课的章节的集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContent(String bookid, String parentno, String title, String contentno, int start, int pagesize);
	/**
	 *获取一页精品微课集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentGoodfilms (List<SearchModel> condition, String orderby, int start, int pagesize);
	/**
	 *根据bookcontentid获取精品微课集合
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public List getTkBookContentGoodfilmByBookcontentid(Integer bookcontentid);
	/**
	 *根据bookcontentid获取精品微课集合
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm getTkBookContentGoodfilmByBookcontentidAndType(Integer bookcontentid,String type);
	/**
	 *根据bookcontentid从tk_book_content_film获取film的集合
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public List getTkBookContentFilmByBookcontentid(Integer bookcontentid);
}