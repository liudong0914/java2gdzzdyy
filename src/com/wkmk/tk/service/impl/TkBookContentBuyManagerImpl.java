package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentBuy;
import com.wkmk.tk.service.TkBookContentBuyManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本内容购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentBuyManagerImpl implements TkBookContentBuyManager{

	private BaseDAO baseDAO;
	private String modelname = "作业本内容购买";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy getTkBookContentBuy(String buyid){
		Integer iid = Integer.valueOf(buyid);
		return  (TkBookContentBuy)baseDAO.getObject(modelname,TkBookContentBuy.class,iid);
	}

	/**
	 *根据id获取作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy getTkBookContentBuy(Integer buyid){
		return  (TkBookContentBuy)baseDAO.getObject(modelname,TkBookContentBuy.class,buyid);
	}

	/**
	 *增加作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy addTkBookContentBuy(TkBookContentBuy tkBookContentBuy){
		return (TkBookContentBuy)baseDAO.addObject(modelname,tkBookContentBuy);
	}

	/**
	 *删除作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy delTkBookContentBuy(String buyid){
		TkBookContentBuy model = getTkBookContentBuy(buyid);
		return (TkBookContentBuy)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy delTkBookContentBuy(TkBookContentBuy tkBookContentBuy){
		return (TkBookContentBuy)baseDAO.delObject(modelname,tkBookContentBuy);
	}

	/**
	 *修改作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy updateTkBookContentBuy(TkBookContentBuy tkBookContentBuy){
		return (TkBookContentBuy)baseDAO.updateObject(modelname,tkBookContentBuy);
	}

	/**
	 *获取作业本内容购买集合
 	 *@return List
	 */
	public List getTkBookContentBuys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentBuy",condition,orderby,pagesize);
	}

	/**
	 *获取一页作业本内容购买集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentBuys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentBuy","buyid",condition, orderby, start,pagesize);
	}

}