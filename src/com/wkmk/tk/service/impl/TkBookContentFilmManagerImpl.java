package com.wkmk.tk.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.service.TkBookContentFilmManager;

/**
 * <p>
 * Description: 作业本微课
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentFilmManagerImpl implements TkBookContentFilmManager {

	private BaseDAO baseDAO;
	private String modelname = "作业本微课";

	/**
	 * 加载baseDAO
	 * 
	 * @param BaseDAO
	 *            baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 根据id获取作业本微课
	 * 
	 * @param fid
	 *            Integer
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm getTkBookContentFilm(String fid) {
		Integer iid = Integer.valueOf(fid);
		return (TkBookContentFilm) baseDAO.getObject(modelname, TkBookContentFilm.class, iid);
	}

	/**
	 * 根据id获取作业本微课
	 * 
	 * @param fid
	 *            Integer
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm getTkBookContentFilm(Integer fid) {
		return (TkBookContentFilm) baseDAO.getObject(modelname, TkBookContentFilm.class, fid);
	}

	/**
	 * 增加作业本微课
	 * 
	 * @param tkBookContentFilm
	 *            TkBookContentFilm
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm addTkBookContentFilm(TkBookContentFilm tkBookContentFilm) {
		return (TkBookContentFilm) baseDAO.addObject(modelname, tkBookContentFilm);
	}

	/**
	 * 删除作业本微课
	 * 
	 * @param fid
	 *            Integer
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm delTkBookContentFilm(String fid) {
		TkBookContentFilm model = getTkBookContentFilm(fid);
		return (TkBookContentFilm) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除作业本微课
	 * 
	 * @param tkBookContentFilm
	 *            TkBookContentFilm
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm delTkBookContentFilm(TkBookContentFilm tkBookContentFilm) {
		return (TkBookContentFilm) baseDAO.delObject(modelname, tkBookContentFilm);
	}

	/**
	 * 修改作业本微课
	 * 
	 * @param tkBookContentFilm
	 *            TkBookContentFilm
	 * @return TkBookContentFilm
	 */
	public TkBookContentFilm updateTkBookContentFilm(TkBookContentFilm tkBookContentFilm) {
		return (TkBookContentFilm) baseDAO.updateObject(modelname, tkBookContentFilm);
	}

	/**
	 * 获取作业本微课集合
	 * 
	 * @return List
	 */
	public List getTkBookContentFilms(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkBookContentFilm", condition, orderby, pagesize);
	}

	/**
	 * 获取作业本id集合
	 * 
	 * @return List
	 */
	public List getAllBookid(String status) {
		String sql = "select distinct a.bookid from TkBookContentFilm as a";
		if (status != null && !"".equals(status)) {
			sql = sql + " where a.status='" + status + "'";
		}
		return baseDAO.getObjects(sql);
	}

	public List getAllBookContentid(String status) {
		String sql = "select distinct a.bookcontentid from TkBookContentFilm as a";
		if (status != null && !"".equals(status)) {
			sql = sql + " where a.status='" + status + "'";
		}
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取作业本内容集合
	 * 
	 * @return List
	 */
	public List getTkBookContentsByBookContentFilm(String bookid, String vodstate, String keywords) {
		// vodstate和TkBookContentFilm.type对应
		String sql = "select distinct a from TkBookContent as a, TkBookContentFilm as b where a.bookcontentid=b.bookcontentid and b.status='1' and b.bookid=" + bookid;
		if ("1".equals(vodstate) || "2".equals(vodstate)) {
			sql = sql + " and b.type='" + vodstate + "'";
		}
		if (keywords != null && !"".equals(keywords)) {
			sql = sql + " and a.title like '%" + keywords + "%'";
		}
		sql = sql + " order by a.contentno asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页作业本微课集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContentFilms(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkBookContentFilm", "fid", condition, orderby, start, pagesize);
	}

	/**
	 * 获取一页已购作业本微课
	 * 
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
	public PageList getTkBookContentFilmsOfPage(Integer userid, String enddate, String type, String sorderindex, int start, int size) {
		String sql = "select b from TkBookContentFilm as a,TkBookContentBuy as b ,VwhFilmInfo as c where a.bookcontentid=b.contentid and a.bookid=b.bookid and a.filmid=c.filmid and a.status= '1' and b.userid =" + userid;
		String sqlcount = "select count(*) from TkBookContentFilm as a,TkBookContentBuy as b,VwhFilmInfo as c  where a.bookcontentid=b.contentid and a.bookid=b.bookid and a.filmid=c.filmid and a.status= '1' and b.userid=" + userid;
		if (enddate != null && enddate.trim().length() > 0) {
			sql += " and b.enddate  >='" + enddate + "'";
			sqlcount += " and b.enddate >='" + enddate + "'";
		}
		if (type != null && type.trim().length() > 0) {
			sql += " and b.type  ='" + type + "'";
			sqlcount += " and b.type ='" + type + "'";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	// 根据作业本id及作业本内容id查询微课
	public List getFilmByBookContent(Integer bookid, Integer bookcontentid) {
		String sql = "select a from VwhFilmInfo as a,TkBookContentFilm as b where a.filmid=b.filmid";
		if (bookid != null && bookid.intValue() > 0) {
			sql += " and b.bookid=" + bookid;
		}
		if (bookcontentid != null && bookcontentid.intValue() > 0) {
			sql += " and b.bookcontentid=" + bookcontentid;
		}
		return baseDAO.getObjects(sql);
	}
}