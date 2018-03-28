package com.wkmk.vwh.service.impl;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmWatch;
import com.wkmk.vwh.service.VwhFilmWatchManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微课观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmWatchManagerImpl implements VwhFilmWatchManager{

	private BaseDAO baseDAO;
	private String modelname = "微课观看记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch getVwhFilmWatch(String watchid){
		Integer iid = Integer.valueOf(watchid);
		return  (VwhFilmWatch)baseDAO.getObject(modelname,VwhFilmWatch.class,iid);
	}

	/**
	 *根据id获取微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch getVwhFilmWatch(Integer watchid){
		return  (VwhFilmWatch)baseDAO.getObject(modelname,VwhFilmWatch.class,watchid);
	}

	/**
	 *增加微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch addVwhFilmWatch(VwhFilmWatch vwhFilmWatch){
		return (VwhFilmWatch)baseDAO.addObject(modelname,vwhFilmWatch);
	}

	/**
	 *删除微课观看记录
	 *@param watchid Integer
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch delVwhFilmWatch(String watchid){
		VwhFilmWatch model = getVwhFilmWatch(watchid);
		return (VwhFilmWatch)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch delVwhFilmWatch(VwhFilmWatch vwhFilmWatch){
		return (VwhFilmWatch)baseDAO.delObject(modelname,vwhFilmWatch);
	}

	/**
	 *修改微课观看记录
	 *@param vwhFilmWatch VwhFilmWatch
	 *@return VwhFilmWatch
	 */
	public VwhFilmWatch updateVwhFilmWatch(VwhFilmWatch vwhFilmWatch){
		return (VwhFilmWatch)baseDAO.updateObject(modelname,vwhFilmWatch);
	}

	/**
	 *获取微课观看记录集合
 	 *@return List
	 */
	public List getVwhFilmWatchs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("VwhFilmWatch",condition,orderby,pagesize);
	}

	/**
	 *获取一页微课观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmWatchs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("VwhFilmWatch","watchid",condition, orderby, start,pagesize);
	}

}