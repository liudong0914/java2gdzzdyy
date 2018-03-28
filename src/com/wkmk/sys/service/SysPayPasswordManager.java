package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysPayPassword;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 支付密码错误记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysPayPasswordManager {
	/**
	 *根据id获取支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword getSysPayPassword(String paypasswordid);

	/**
	 *根据id获取支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword getSysPayPassword(Integer paypasswordid);

	/**
	 *增加支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword addSysPayPassword(SysPayPassword sysPayPassword);

	/**
	 *删除支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword delSysPayPassword(String paypasswordid);

	/**
	 *删除支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword delSysPayPassword(SysPayPassword sysPayPassword);

	/**
	 *修改支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword updateSysPayPassword(SysPayPassword sysPayPassword);

	/**
	 *获取支付密码错误记录集合
	 *@return List
	 */
	public List getSysPayPasswords (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取支付密码错误记录次数
 	 *@return List
	 */
	public int getCountSysPayPassword(String userid);
	
	/**
	 *获取一页支付密码错误记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysPayPasswords (List<SearchModel> condition, String orderby, int start, int pagesize);

}