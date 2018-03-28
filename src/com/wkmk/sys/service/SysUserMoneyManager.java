package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserMoney;

/**
 *<p>Description: 用户交易记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserMoneyManager {
	/**
	 *根据id获取用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney getSysUserMoney(String moneyid);

	/**
	 *根据id获取用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney getSysUserMoney(Integer moneyid);

	/**
	 *增加用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney addSysUserMoney(SysUserMoney sysUserMoney);

	/**
	 *删除用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney delSysUserMoney(String moneyid);

	/**
	 *删除用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney delSysUserMoney(SysUserMoney sysUserMoney);

	/**
	 *修改用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney updateSysUserMoney(SysUserMoney sysUserMoney);

	/**
	 *获取用户交易记录集合
	 *@return List
	 */
	public List getSysUserMoneys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页用户交易记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserMoneys (List<SearchModel> condition, String orderby, int start, int pagesize);

	public PageList getSysUserMoneysOfPage(String title, String username, String createdate, String descript,String changetype,String usertype,String xueduan, String sorderindex,int start, int size);

	public PageList getSysUserMoneysOfPage2(Integer userid, String title, String username, String createdate, String descript,String changetype,String usertype,String xueduan, String sorderindex,int start, int size);
}