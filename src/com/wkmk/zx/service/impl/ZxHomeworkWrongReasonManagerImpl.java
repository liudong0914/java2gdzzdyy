package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkWrongReason;
import com.wkmk.zx.service.ZxHomeworkWrongReasonManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业常见错误原因</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkWrongReasonManagerImpl implements ZxHomeworkWrongReasonManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业常见错误原因";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason getZxHomeworkWrongReason(String wrid){
		Integer iid = Integer.valueOf(wrid);
		return  (ZxHomeworkWrongReason)baseDAO.getObject(modelname,ZxHomeworkWrongReason.class,iid);
	}

	/**
	 *根据id获取在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason getZxHomeworkWrongReason(Integer wrid){
		return  (ZxHomeworkWrongReason)baseDAO.getObject(modelname,ZxHomeworkWrongReason.class,wrid);
	}

	/**
	 *增加在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason addZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason){
		return (ZxHomeworkWrongReason)baseDAO.addObject(modelname,zxHomeworkWrongReason);
	}

	/**
	 *删除在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason delZxHomeworkWrongReason(String wrid){
		ZxHomeworkWrongReason model = getZxHomeworkWrongReason(wrid);
		return (ZxHomeworkWrongReason)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason delZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason){
		return (ZxHomeworkWrongReason)baseDAO.delObject(modelname,zxHomeworkWrongReason);
	}

	/**
	 *修改在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason updateZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason){
		return (ZxHomeworkWrongReason)baseDAO.updateObject(modelname,zxHomeworkWrongReason);
	}

	/**
	 *获取在线作业常见错误原因集合
 	 *@return List
	 */
	public List getZxHomeworkWrongReasons(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkWrongReason",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业常见错误原因集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkWrongReasons (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkWrongReason","wrid",condition, orderby, start,pagesize);
	}

}