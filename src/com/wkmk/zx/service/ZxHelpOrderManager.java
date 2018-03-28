package com.wkmk.zx.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.zx.bo.ZxHelpOrder;

/**
 *<p>Description: 在线答疑订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpOrderManager {
	/**
	 *根据id获取在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder getZxHelpOrder(String orderid);

	/**
	 *根据id获取在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder getZxHelpOrder(Integer orderid);

	/**
	 *增加在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder addZxHelpOrder(ZxHelpOrder zxHelpOrder);

	/**
	 *删除在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder delZxHelpOrder(String orderid);

	/**
	 *删除在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder delZxHelpOrder(ZxHelpOrder zxHelpOrder);

	/**
	 *修改在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder updateZxHelpOrder(ZxHelpOrder zxHelpOrder);

	/**
	 *获取在线答疑订单集合
	 *@return List
	 */
	public List getZxHelpOrders (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线答疑订单
 	 *@return List
	 */
	public ZxHelpOrder getZxHelpOrderByQuestionid(String questionid);
	
	/**
	 *获取老师未完成在线答疑订单数
 	 *@return List
	 */
	public int getCountZxHelpOrdersByUserid(String userid);
	
	/**
	 *获取一页在线答疑订单集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpOrders (List<SearchModel> condition, String orderby, int start, int pagesize);

	public PageList getZxHelpOrdersOfPage(String userid, String status, String sorderindex,int start, int size);
}