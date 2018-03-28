package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmAuditionWatch;
import com.wkmk.tk.service.TkBookContentFilmAuditionWatchManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试听解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionWatchManagerImpl implements TkBookContentFilmAuditionWatchManager{

	private BaseDAO baseDAO;
	private String modelname = "试听解题微课播放记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch getTkBookContentFilmAuditionWatch(String watchid){
		Integer iid = Integer.valueOf(watchid);
		return  (TkBookContentFilmAuditionWatch)baseDAO.getObject(modelname,TkBookContentFilmAuditionWatch.class,iid);
	}

	/**
	 *根据id获取试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch getTkBookContentFilmAuditionWatch(Integer watchid){
		return  (TkBookContentFilmAuditionWatch)baseDAO.getObject(modelname,TkBookContentFilmAuditionWatch.class,watchid);
	}

	/**
	 *增加试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch addTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch){
		return (TkBookContentFilmAuditionWatch)baseDAO.addObject(modelname,tkBookContentFilmAuditionWatch);
	}

	/**
	 *删除试听解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch delTkBookContentFilmAuditionWatch(String watchid){
		TkBookContentFilmAuditionWatch model = getTkBookContentFilmAuditionWatch(watchid);
		return (TkBookContentFilmAuditionWatch)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch delTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch){
		return (TkBookContentFilmAuditionWatch)baseDAO.delObject(modelname,tkBookContentFilmAuditionWatch);
	}

	/**
	 *修改试听解题微课播放记录
	 *@param tkBookContentFilmAuditionWatch TkBookContentFilmAuditionWatch
	 *@return TkBookContentFilmAuditionWatch
	 */
	public TkBookContentFilmAuditionWatch updateTkBookContentFilmAuditionWatch(TkBookContentFilmAuditionWatch tkBookContentFilmAuditionWatch){
		return (TkBookContentFilmAuditionWatch)baseDAO.updateObject(modelname,tkBookContentFilmAuditionWatch);
	}

	/**
	 *获取试听解题微课播放记录集合
 	 *@return List
	 */
	public List getTkBookContentFilmAuditionWatchs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentFilmAuditionWatch",condition,orderby,pagesize);
	}

	/**
	 *获取一页试听解题微课播放记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentFilmAuditionWatchs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentFilmAuditionWatch","watchid",condition, orderby, start,pagesize);
	}

}