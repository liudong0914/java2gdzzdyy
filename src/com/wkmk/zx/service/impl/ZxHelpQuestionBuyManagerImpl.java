package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionBuy;
import com.wkmk.zx.service.ZxHelpQuestionBuyManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionBuyManagerImpl implements ZxHelpQuestionBuyManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑购买";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy getZxHelpQuestionBuy(String buyid){
		Integer iid = Integer.valueOf(buyid);
		return  (ZxHelpQuestionBuy)baseDAO.getObject(modelname,ZxHelpQuestionBuy.class,iid);
	}

	/**
	 *根据id获取在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy getZxHelpQuestionBuy(Integer buyid){
		return  (ZxHelpQuestionBuy)baseDAO.getObject(modelname,ZxHelpQuestionBuy.class,buyid);
	}

	/**
	 *增加在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy addZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy){
		return (ZxHelpQuestionBuy)baseDAO.addObject(modelname,zxHelpQuestionBuy);
	}

	/**
	 *删除在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy delZxHelpQuestionBuy(String buyid){
		ZxHelpQuestionBuy model = getZxHelpQuestionBuy(buyid);
		return (ZxHelpQuestionBuy)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy delZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy){
		return (ZxHelpQuestionBuy)baseDAO.delObject(modelname,zxHelpQuestionBuy);
	}

	/**
	 *修改在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy updateZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy){
		return (ZxHelpQuestionBuy)baseDAO.updateObject(modelname,zxHelpQuestionBuy);
	}

	/**
	 *获取在线答疑购买集合
 	 *@return List
	 */
	public List getZxHelpQuestionBuys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpQuestionBuy",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线答疑购买集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionBuys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpQuestionBuy","buyid",condition, orderby, start,pagesize);
	}

}