package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.service.SysPaymentAccountManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统支付账号</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPaymentAccountManagerImpl implements SysPaymentAccountManager{

	private BaseDAO baseDAO;
	private String modelname = "系统支付账号";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount getSysPaymentAccount(String paymentid){
		Integer iid = Integer.valueOf(paymentid);
		return  (SysPaymentAccount)baseDAO.getObject(modelname,SysPaymentAccount.class,iid);
	}

	/**
	 *根据id获取系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount getSysPaymentAccount(Integer paymentid){
		return  (SysPaymentAccount)baseDAO.getObject(modelname,SysPaymentAccount.class,paymentid);
	}

	/**
	 *增加系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount addSysPaymentAccount(SysPaymentAccount sysPaymentAccount){
		return (SysPaymentAccount)baseDAO.addObject(modelname,sysPaymentAccount);
	}

	/**
	 *删除系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount delSysPaymentAccount(String paymentid){
		SysPaymentAccount model = getSysPaymentAccount(paymentid);
		return (SysPaymentAccount)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount delSysPaymentAccount(SysPaymentAccount sysPaymentAccount){
		return (SysPaymentAccount)baseDAO.delObject(modelname,sysPaymentAccount);
	}

	/**
	 *修改系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount updateSysPaymentAccount(SysPaymentAccount sysPaymentAccount){
		return (SysPaymentAccount)baseDAO.updateObject(modelname,sysPaymentAccount);
	}

	/**
	 *获取系统支付账号集合
 	 *@return List
	 */
	public List getSysPaymentAccounts(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysPaymentAccount",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统支付账号集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysPaymentAccounts (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysPaymentAccount","paymentid",condition, orderby, start,pagesize);
	}

}