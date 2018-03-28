package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;

/**
 *<p>Description: 用户赠送学币活动</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserGiveMoneyActivityManager {
	/**
	 *根据id获取用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity getSysUserGiveMoneyActivity(String activityid);

	/**
	 *根据id获取用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity getSysUserGiveMoneyActivity(Integer activityid);

	/**
	 *增加用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity addSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity);

	/**
	 *删除用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity delSysUserGiveMoneyActivity(String activityid);

	/**
	 *删除用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity delSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity);

	/**
	 *修改用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity updateSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity);

	/**
	 *获取用户赠送龙币活动集合
	 *@return List
	 */
	public List getSysUserGiveMoneyActivitys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页用户赠送龙币活动集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserGiveMoneyActivitys (List<SearchModel> condition, String orderby, int start, int pagesize);

}