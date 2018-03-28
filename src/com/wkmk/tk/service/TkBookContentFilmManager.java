package com.wkmk.tk.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkBookContentFilm;

/**
 *<p>Description: 作业本微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentFilmManager {
	/**
	 *根据id获取作业本微课
	 *@param fid Integer
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm getTkBookContentFilm(String fid);

	/**
	 *根据id获取作业本微课
	 *@param fid Integer
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm getTkBookContentFilm(Integer fid);

	/**
	 *增加作业本微课
	 *@param tkBookContentFilm TkBookContentFilm
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm addTkBookContentFilm(TkBookContentFilm tkBookContentFilm);

	/**
	 *删除作业本微课
	 *@param fid Integer
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm delTkBookContentFilm(String fid);

	/**
	 *删除作业本微课
	 *@param tkBookContentFilm TkBookContentFilm
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm delTkBookContentFilm(TkBookContentFilm tkBookContentFilm);

	/**
	 *修改作业本微课
	 *@param tkBookContentFilm TkBookContentFilm
	 *@return TkBookContentFilm
	 */
	public TkBookContentFilm updateTkBookContentFilm(TkBookContentFilm tkBookContentFilm);

	/**
	 *获取作业本微课集合
	 *@return List
	 */
	public List getTkBookContentFilms (List<SearchModel> condition, String orderby, int pagesize);

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
	public List getTkBookContentsByBookContentFilm(String bookid, String vodstate, String keywords);
	
	/**
     * 获取一页已购作业本微课
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-1-17 上午11:05:18 
    * @lastModified ; 2017-1-17 上午11:05:18 
    * @version ; 1.0 
    * @param title
    * @param username
    * @param createdate
    * @param descript
    * @param changetype
    * @param usertype
    * @param xueduan
    * @param sorderindex
    * @param unitid
    * @param start
    * @param size
    * @return
     */
    public PageList getTkBookContentFilmsOfPage(Integer userid, String enddate, String type,String sorderindex,int start, int size);
	/**
	 *获取一页作业本微课集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentFilms (List<SearchModel> condition, String orderby, int start, int pagesize);

	//根据作业本id及作业本内容id查询微课
	public List getFilmByBookContent(Integer bookid,Integer bookcontentid);
}