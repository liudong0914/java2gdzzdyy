package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkOrder;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkOrderManager {
	/**
	 *根据id获取在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder getZxHomeworkOrder(String orderid);

	/**
	 *根据id获取在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder getZxHomeworkOrder(Integer orderid);

	/**
	 *增加在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder addZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder);

	/**
	 *删除在线作业订单
	 *@param orderid Integer
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder delZxHomeworkOrder(String orderid);

	/**
	 *删除在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder delZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder);

	/**
	 *修改在线作业订单
	 *@param zxHomeworkOrder ZxHomeworkOrder
	 *@return ZxHomeworkOrder
	 */
	public ZxHomeworkOrder updateZxHomeworkOrder(ZxHomeworkOrder zxHomeworkOrder);

	/**
	 *获取在线作业订单集合
	 *@return List
	 */
	public List getZxHomeworkOrders (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业订单集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkOrders (List<SearchModel> condition, String orderby, int start, int pagesize);

}