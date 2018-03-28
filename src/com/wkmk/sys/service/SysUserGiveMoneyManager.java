package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserGiveMoney;

/**
 *<p>Description: 用户赠送学币</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserGiveMoneyManager {
	/**
	 *根据id获取用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney getSysUserGiveMoney(String moneyid);

	/**
	 *根据id获取用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney getSysUserGiveMoney(Integer moneyid);

	/**
	 *增加用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney addSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney);

	/**
	 *删除用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney delSysUserGiveMoney(String moneyid);

	/**
	 *删除用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney delSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney);

	/**
	 *修改用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney updateSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney);

	/**
	 *获取用户赠送龙币集合
	 *@return List
	 */
	public List getSysUserGiveMoneys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取用户赠送龙币总值
 	 *@return List
	 */
	public Float getSysUserGiveMoneyByUserid(Integer userid, String startdate, String enddate, String type);
	
	/**
	 *获取一页用户赠送龙币集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserGiveMoneys (List<SearchModel> condition, String orderby, int start, int pagesize);

}