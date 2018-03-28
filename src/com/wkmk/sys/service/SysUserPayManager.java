package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserPay;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线交易明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserPayManager {
	/**
	 *根据id获取在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay getSysUserPay(String payid);

	/**
	 *根据id获取在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay getSysUserPay(Integer payid);

	/**
	 *增加在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay addSysUserPay(SysUserPay sysUserPay);

	/**
	 *删除在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay delSysUserPay(String payid);

	/**
	 *删除在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay delSysUserPay(SysUserPay sysUserPay);

	/**
	 *修改在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay updateSysUserPay(SysUserPay sysUserPay);

	/**
	 *获取在线交易明细集合
	 *@return List
	 */
	public List getSysUserPays (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线交易明细
 	 *@return List
	 */
	public SysUserPay getSysUserPayByOuttradeno(String outtradeno);
	
	/**
	 *获取一页在线交易明细集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserPays (List<SearchModel> condition, String orderby, int start, int pagesize);

}