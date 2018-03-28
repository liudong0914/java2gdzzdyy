package com.wkmk.zx.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.zx.bo.ZxHelpOrder;
import com.wkmk.zx.service.ZxHelpOrderManager;

/**
 *<p>Description: 在线答疑订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpOrderManagerImpl implements ZxHelpOrderManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑订单";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder getZxHelpOrder(String orderid){
		Integer iid = Integer.valueOf(orderid);
		return  (ZxHelpOrder)baseDAO.getObject(modelname,ZxHelpOrder.class,iid);
	}

	/**
	 *根据id获取在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder getZxHelpOrder(Integer orderid){
		return  (ZxHelpOrder)baseDAO.getObject(modelname,ZxHelpOrder.class,orderid);
	}

	/**
	 *增加在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder addZxHelpOrder(ZxHelpOrder zxHelpOrder){
		return (ZxHelpOrder)baseDAO.addObject(modelname,zxHelpOrder);
	}

	/**
	 *删除在线答疑订单
	 *@param orderid Integer
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder delZxHelpOrder(String orderid){
		ZxHelpOrder model = getZxHelpOrder(orderid);
		return (ZxHelpOrder)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder delZxHelpOrder(ZxHelpOrder zxHelpOrder){
		return (ZxHelpOrder)baseDAO.delObject(modelname,zxHelpOrder);
	}

	/**
	 *修改在线答疑订单
	 *@param zxHelpOrder ZxHelpOrder
	 *@return ZxHelpOrder
	 */
	public ZxHelpOrder updateZxHelpOrder(ZxHelpOrder zxHelpOrder){
		return (ZxHelpOrder)baseDAO.updateObject(modelname,zxHelpOrder);
	}

	/**
	 *获取在线答疑订单集合
 	 *@return List
	 */
	public List getZxHelpOrders(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpOrder",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线答疑订单
 	 *@return List
	 */
	public ZxHelpOrder getZxHelpOrderByQuestionid(String questionid){
		String sql = "select a from ZxHelpOrder as a where a.questionid=" + questionid + " order by a.createdate desc";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (ZxHelpOrder) list.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 *获取老师未完成在线答疑订单数
 	 *@return List
	 */
	public int getCountZxHelpOrdersByUserid(String userid){
		String sql = "select count(*) from ZxHelpOrder as a where a.status='1' and a.enddate<'" + DateTime.getDate() + "' and a.userid=" + userid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long) list.get(0)).intValue();
		}else {
			return 0;
		}
	}

	/**
	 *获取一页在线答疑订单集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpOrders (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpOrder","orderid",condition, orderby, start,pagesize);
	}

	public PageList getZxHelpOrdersOfPage(String userid, String status, String sorderindex,int start, int size) {
        String sql = "select a from ZxHelpOrder as a,ZxHelpQuestionComplaint as b where a.orderid=b.orderid  and a.questionid= b.questionid";
        String sqlcount = "select count(*) from ZxHelpOrder as a,ZxHelpQuestionComplaint as b where a.orderid=b.orderid  and  a.questionid= b.questionid ";
        if (userid != null && userid.trim().length() > 0) {
            sql += " and a.userid =" + userid ;
            sqlcount += " and a.userid =" + userid;
        }
        if (status != null && status.trim().length() > 0) {
            sql += " and a.status ='" + status + "'";
            sqlcount += " and a.status ='" + status + "'";
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }

}