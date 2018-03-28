package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsSimilarWatch;
import com.wkmk.tk.service.TkQuestionsSimilarWatchManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 举一反三观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilarWatchManagerImpl implements TkQuestionsSimilarWatchManager{

	private BaseDAO baseDAO;
	private String modelname = "举一反三观看记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch getTkQuestionsSimilarWatch(String watchid){
		Integer iid = Integer.valueOf(watchid);
		return  (TkQuestionsSimilarWatch)baseDAO.getObject(modelname,TkQuestionsSimilarWatch.class,iid);
	}

	/**
	 *根据id获取举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch getTkQuestionsSimilarWatch(Integer watchid){
		return  (TkQuestionsSimilarWatch)baseDAO.getObject(modelname,TkQuestionsSimilarWatch.class,watchid);
	}

	/**
	 *增加举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch addTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch){
		return (TkQuestionsSimilarWatch)baseDAO.addObject(modelname,tkQuestionsSimilarWatch);
	}

	/**
	 *删除举一反三观看记录
	 *@param watchid Integer
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch delTkQuestionsSimilarWatch(String watchid){
		TkQuestionsSimilarWatch model = getTkQuestionsSimilarWatch(watchid);
		return (TkQuestionsSimilarWatch)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch delTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch){
		return (TkQuestionsSimilarWatch)baseDAO.delObject(modelname,tkQuestionsSimilarWatch);
	}

	/**
	 *修改举一反三观看记录
	 *@param tkQuestionsSimilarWatch TkQuestionsSimilarWatch
	 *@return TkQuestionsSimilarWatch
	 */
	public TkQuestionsSimilarWatch updateTkQuestionsSimilarWatch(TkQuestionsSimilarWatch tkQuestionsSimilarWatch){
		return (TkQuestionsSimilarWatch)baseDAO.updateObject(modelname,tkQuestionsSimilarWatch);
	}

	/**
	 *获取举一反三观看记录集合
 	 *@return List
	 */
	public List getTkQuestionsSimilarWatchs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkQuestionsSimilarWatch",condition,orderby,pagesize);
	}

	/**
	 *获取一页举一反三观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkQuestionsSimilarWatchs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkQuestionsSimilarWatch","watchid",condition, orderby, start,pagesize);
	}

}