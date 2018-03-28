package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmAudition;
import com.wkmk.tk.service.TkBookContentFilmAuditionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试听解题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionManagerImpl implements TkBookContentFilmAuditionManager{

	private BaseDAO baseDAO;
	private String modelname = "试听解题微课";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition getTkBookContentFilmAudition(String auditionid){
		Integer iid = Integer.valueOf(auditionid);
		return  (TkBookContentFilmAudition)baseDAO.getObject(modelname,TkBookContentFilmAudition.class,iid);
	}

	/**
	 *根据id获取试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition getTkBookContentFilmAudition(Integer auditionid){
		return  (TkBookContentFilmAudition)baseDAO.getObject(modelname,TkBookContentFilmAudition.class,auditionid);
	}

	/**
	 *增加试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition addTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition){
		return (TkBookContentFilmAudition)baseDAO.addObject(modelname,tkBookContentFilmAudition);
	}

	/**
	 *删除试听解题微课
	 *@param auditionid Integer
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition delTkBookContentFilmAudition(String auditionid){
		TkBookContentFilmAudition model = getTkBookContentFilmAudition(auditionid);
		return (TkBookContentFilmAudition)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition delTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition){
		return (TkBookContentFilmAudition)baseDAO.delObject(modelname,tkBookContentFilmAudition);
	}

	/**
	 *修改试听解题微课
	 *@param tkBookContentFilmAudition TkBookContentFilmAudition
	 *@return TkBookContentFilmAudition
	 */
	public TkBookContentFilmAudition updateTkBookContentFilmAudition(TkBookContentFilmAudition tkBookContentFilmAudition){
		return (TkBookContentFilmAudition)baseDAO.updateObject(modelname,tkBookContentFilmAudition);
	}

	/**
	 *获取试听解题微课集合
 	 *@return List
	 */
	public List getTkBookContentFilmAuditions(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentFilmAudition",condition,orderby,pagesize);
	}

	/**
	 *获取一页试听解题微课集合
	 */	
	public PageList getPageTkBookContentFilmAuditionsByFilm(String bookcontentid,String title,String orderby,int start,int size){
		String sql="select a,b from TkBookContentFilm as a,TkBookContentFilmAudition as b where a.fid=b.contentfilmid ";
		String sqlcount="select count(*) from TkBookContentFilm as a,TkBookContentFilmAudition as b where a.fid=b.contentfilmid ";
		if(!"".equals(bookcontentid)){
			sql+=" and a.bookcontentid="+bookcontentid;
			sqlcount+=" and a.bookcontentid="+bookcontentid;
		}
		if(!"".equals(title)){
			sql+=" and a.title like '%"+title+"%'";
			sqlcount+=" and a.title like '%"+title+"%'";
		}
		if(!"".equals(orderby)){
			sql+=" order by b."+orderby;
		}else{
			sql+=" order by b.orderindex desc";
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}
	
	/**
	 * 微信端试听微课
	 * 获取一页试听解题微课集合
	 */	
	public PageList getPageFilms(String title,String orderby,int start,int size){
		String sql="select a,b.fid,c.auditionid from VwhFilmInfo as a,TkBookContentFilm as b,TkBookContentFilmAudition as c where a.filmid=b.filmid and b.fid=c.contentfilmid and c.status='1'";
		String sqlcount="select count(*) from VwhFilmInfo as a,TkBookContentFilm as b,TkBookContentFilmAudition as c where a.filmid=b.filmid and b.fid=c.contentfilmid and c.status='1'";
		if(!"".equals(title)){
			sql+=" and a.title like '%"+title+"%'";
			sqlcount+=" and a.title like '%"+title+"%'";
		}
		if(!"".equals(orderby)){
			sql+=" order by c."+orderby;
		}else{
			sql+=" order by c.orderindex desc";
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}
	
	/**
	 * 根据作业本id查询未添加到试听微课列表中的作业本微课
	 * @param bookcontentid
	 * @return
	 */
	public PageList getContentFilm(String bookcontentid,String title,int start,int size){
		String sql="select a from TkBookContentFilm as a where a.status='1' and a.fid not in (select b.contentfilmid from TkBookContentFilmAudition as b)";
		String sqlcount="select count(a.fid) from TkBookContentFilm as a where a.status='1' and a.fid not in (select b.contentfilmid from TkBookContentFilmAudition as b)";
		if(!"".equals(title)){
			sql+=" and a.title like '%"+title+"%'";
			sqlcount+=" and a.title like '%"+title+"%'";
		}
		if(!"".equals(bookcontentid)){
			sql+=" and a.bookcontentid="+bookcontentid;
			sqlcount+=" and a.bookcontentid="+bookcontentid;
		}
		sql+=" order by a.orderindex ";
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}
	
	//获取最大排序数字
	public int getMaxOrderIndex(){
		String sql="select max(a.orderindex) from TkBookContentFilmAudition as a";
		Object index=baseDAO.getObjects(sql).get(0);
		if(index!=null){
			return Integer.parseInt(index.toString());
		}else{
			return 0;
		}
	}
}