package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkOrder;
import com.wkmk.zx.service.ZxHomeworkOrderManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkOrderManagerImpl implements ZxHomeworkOrderManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业订单";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder getZxHomeworkOrder(String orderid){
		Integer iid = Integer.valueOf(orderid);
		return  (ZxHomeworkOrder)baseDAO.getObject(modelname,ZxHomeworkOrder.class,iid);
	}

	/**
	 *根据id获取在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder getZxHomeworkOrder(Integer orderid){
		return  (ZxHomeworkOrder)baseDAO.getObject(modelname,ZxHomeworkOrder.class,orderid);
	}

	/**
	 *增加在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder addZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder){
		return (ZxHomeworkOrder)baseDAO.addObject(modelname,zxHomeworkOrder);
	}

	/**
	 *删除在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder delZxHomeworkOrder(String orderid){
		ZxHomeworkOrder model = getZxHomeworkOrder(orderid);
		return (ZxHomeworkOrder)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder delZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder){
		return (ZxHomeworkOrder)baseDAO.delObject(modelname,zxHomeworkOrder);
	}

	/**
	 *修改在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder updateZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder){
		return (ZxHomeworkOrder)baseDAO.updateObject(modelname,zxHomeworkOrder);
	}

	/**
	 *获取在线作业订单集合
 	 *@return List
	 */
	public List getZxHomeworkOrders(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkOrder",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业订单集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkOrders (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkOrder","orderid",condition, orderby, start,pagesize);
	}

}