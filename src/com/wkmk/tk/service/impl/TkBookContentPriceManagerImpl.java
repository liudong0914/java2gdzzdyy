package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentPrice;
import com.wkmk.tk.service.TkBookContentPriceManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本内容定价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentPriceManagerImpl implements TkBookContentPriceManager{

	private BaseDAO baseDAO;
	private String modelname = "作业本内容定价";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice getTkBookContentPrice(String priceid){
		Integer iid = Integer.valueOf(priceid);
		return  (TkBookContentPrice)baseDAO.getObject(modelname,TkBookContentPrice.class,iid);
	}

	/**
	 *根据id获取作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice getTkBookContentPrice(Integer priceid){
		return  (TkBookContentPrice)baseDAO.getObject(modelname,TkBookContentPrice.class,priceid);
	}

	/**
	 *增加作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice addTkBookContentPrice(TkBookContentPrice tkBookContentPrice){
		return (TkBookContentPrice)baseDAO.addObject(modelname,tkBookContentPrice);
	}

	/**
	 *删除作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice delTkBookContentPrice(String priceid){
		TkBookContentPrice model = getTkBookContentPrice(priceid);
		return (TkBookContentPrice)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice delTkBookContentPrice(TkBookContentPrice tkBookContentPrice){
		return (TkBookContentPrice)baseDAO.delObject(modelname,tkBookContentPrice);
	}

	/**
	 *修改作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice updateTkBookContentPrice(TkBookContentPrice tkBookContentPrice){
		return (TkBookContentPrice)baseDAO.updateObject(modelname,tkBookContentPrice);
	}

	/**
	 *获取作业本内容定价集合
 	 *@return List
	 */
	public List getTkBookContentPrices(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentPrice",condition,orderby,pagesize);
	}

	/**
	 *获取一页作业本内容定价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentPrices (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentPrice","priceid",condition, orderby, start,pagesize);
	}

}