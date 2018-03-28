package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysPaymentAccount;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统支付账号</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysPaymentAccountManager {
	/**
	 *根据id获取系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount getSysPaymentAccount(String paymentid);

	/**
	 *根据id获取系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount getSysPaymentAccount(Integer paymentid);

	/**
	 *增加系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount addSysPaymentAccount(SysPaymentAccount sysPaymentAccount);

	/**
	 *删除系统支付账号
	 *@param paymentid Integer
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount delSysPaymentAccount(String paymentid);

	/**
	 *删除系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount delSysPaymentAccount(SysPaymentAccount sysPaymentAccount);

	/**
	 *修改系统支付账号
	 *@param sysPaymentAccount SysPaymentAccount
	 *@return SysPaymentAccount
	 */
	public SysPaymentAccount updateSysPaymentAccount(SysPaymentAccount sysPaymentAccount);

	/**
	 *获取系统支付账号集合
	 *@return List
	 */
	public List getSysPaymentAccounts (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统支付账号集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysPaymentAccounts (List<SearchModel> condition, String orderby, int start, int pagesize);

}