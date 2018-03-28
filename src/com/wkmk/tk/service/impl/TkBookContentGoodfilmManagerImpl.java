package com.wkmk.tk.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentGoodfilm;
import com.wkmk.tk.service.TkBookContentGoodfilmManager;
import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;

/**
 *<p>Description: 精品微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentGoodfilmManagerImpl implements TkBookContentGoodfilmManager{

	private BaseDAO baseDAO;
	private String modelname = "精品微课";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm getTkBookContentGoodfilm(String goodfilmid){
		Integer iid = Integer.valueOf(goodfilmid);
		return  (TkBookContentGoodfilm)baseDAO.getObject(modelname,TkBookContentGoodfilm.class,iid);
	}

	/**
	 *根据id获取精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm getTkBookContentGoodfilm(Integer goodfilmid){
		return  (TkBookContentGoodfilm)baseDAO.getObject(modelname,TkBookContentGoodfilm.class,goodfilmid);
	}

	/**
	 *增加精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm addTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm){
		return (TkBookContentGoodfilm)baseDAO.addObject(modelname,tkBookContentGoodfilm);
	}

	/**
	 *删除精品微课
	 *@param goodfilmid Integer
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm delTkBookContentGoodfilm(String goodfilmid){
		TkBookContentGoodfilm model = getTkBookContentGoodfilm(goodfilmid);
		return (TkBookContentGoodfilm)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm delTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm){
		return (TkBookContentGoodfilm)baseDAO.delObject(modelname,tkBookContentGoodfilm);
	}

	/**
	 *修改精品微课
	 *@param tkBookContentGoodfilm TkBookContentGoodfilm
	 *@return TkBookContentGoodfilm
	 */
	public TkBookContentGoodfilm updateTkBookContentGoodfilm(TkBookContentGoodfilm tkBookContentGoodfilm){
		return (TkBookContentGoodfilm)baseDAO.updateObject(modelname,tkBookContentGoodfilm);
	}

	/**
	 *获取精品微课集合
 	 *@return List
	 */
	public List getTkBookContentGoodfilms(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentGoodfilm",condition,orderby,pagesize);
	}
	
	/**
	 *获取作业本id集合
 	 *@return List
	 */
	public List getAllBookid(String status){
		String sql = "select distinct a.bookid from TkBookContentGoodfilm as a";
		if(status != null && !"".equals(status)){
			sql = sql + " where a.status='" + status + "'";
		}
		return baseDAO.getObjects(sql);
	}
	
	public List getAllBookContentid(String status){
		String sql = "select distinct a.bookcontentid from TkBookContentGoodfilm as a";
		if(status != null && !"".equals(status)){
			sql = sql + " where a.status='" + status + "'";
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取作业本内容集合
 	 *@return List
	 */
	public List getTkBookContentsByBookContentGoodFilm(String bookid, String vodstate, String keywords){
		//vodstate和TkBookContentFilm.type对应
		String sql = "select distinct a from TkBookContent as a, TkBookContentGoodfilm as b where a.bookcontentid=b.bookcontentid and b.status='1' and b.bookid=" + bookid;
		if("1".equals(vodstate) || "2".equals(vodstate)){
			sql = sql + " and b.type='" + vodstate + "'";
		}
		if(keywords != null && !"".equals(keywords)){
			sql = sql + " and a.title like '%" + keywords + "%'";
		}
		sql = sql + " order by a.contentno asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页精品微课集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentGoodfilms (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentGoodfilm","goodfilmid",condition, orderby, start,pagesize);
	}

	public List getTkBookContentGoodfilmByBookcontentid(Integer bookcontentid) {
		String sql = "from TkBookContentGoodfilm as a where a.bookcontentid="+bookcontentid;
		return baseDAO.getObjects(sql);
	}

	public List getTkBookContentFilmByBookcontentid(Integer bookcontentid) {
		String sql = "from TkBookContentFilm as b where b.bookcontentid="+bookcontentid ;
		return baseDAO.getObjects(sql);
	}

	public TkBookContentGoodfilm getTkBookContentGoodfilmByBookcontentidAndType(Integer bookcontentid, String type) {
		List<SearchModel>condition = new ArrayList<SearchModel>();
		TkBookContentGoodfilm model = null;
		SearchCondition.addCondition(condition, "bookcontentid", "=", bookcontentid);
		SearchCondition.addCondition(condition, "type", "=", type);
		List list = baseDAO.getObjects("TkBookContentGoodfilm", condition, "", 0);
		if(list.size() > 0){
			model = (TkBookContentGoodfilm)list.get(0);
		}
		return model;
	}

	public PageList getPageTkBookContent(String bookid, String parentno, String title, String contentno,
			 int start, int pagesize) {
		if(!"".equals(parentno)){
			String sql = "select distinct t1 from TkBookContent as t1 ,TkBookContentFilm as t2 where t1.bookcontentid = t2.bookcontentid and t1.bookid='"+bookid+"'and t1.parentno='"+parentno+"'";
			String sqlcount = "select  count(distinct t1) from  TkBookContent as t1 ,TkBookContentFilm as t2 where t1.bookcontentid = t2.bookcontentid and t1.bookid='"+bookid+"'and t1.parentno='"+parentno+"'";
			if(!"".equals(title)){
				sql += " and t1.title like '%" + title + "%'";
				sqlcount += " and t1.title like '%" + title + "%'";
			}
			if(!"".equals(contentno)){
				sql += " and t1.contentno like '%" + contentno + "%'";
				sqlcount += " and t1.contentno like '%" + contentno + "%'";
			}
			return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
		}else{
			String sql = "select distinct t2 from TkBookInfo as t1 ,TkBookContent as t2 ,TkBookContentFilm as t3 where t1.bookid = t2.bookid and t2.bookcontentid = t3.bookcontentid and t1.bookid = '"+bookid+"'";
			String sqlcount = "select count(distinct t2) from TkBookInfo as t1 ,TkBookContent as t2 ,TkBookContentFilm as t3 where t1.bookid = t2.bookid and t2.bookcontentid = t3.bookcontentid and t2.bookid = '"+bookid+"'";
			if(!"".equals(title)){
				sql += " and t2.title like '%" + title + "%'";
				sqlcount += " and t2.title like '%" + title + "%'";
			}
			if(!"".equals(contentno)){
				sql += " and t2.contentno like '%" + contentno + "%'";
				sqlcount += " and t2.contentno like '%" + contentno + "%'";
			}
			return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
		}
	}
}