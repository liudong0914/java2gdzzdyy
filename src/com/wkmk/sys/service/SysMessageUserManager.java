package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysMessageUser;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 消息用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysMessageUserManager {
	/**
	 *根据id获取消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser getSysMessageUser(String messageuserid);

	/**
	 *根据id获取消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser getSysMessageUser(Integer messageuserid);

	/**
	 *增加消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser addSysMessageUser(SysMessageUser sysMessageUser);

	/**
	 *删除消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser delSysMessageUser(String messageuserid);

	/**
	 *删除消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser delSysMessageUser(SysMessageUser sysMessageUser);

	/**
	 *修改消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser updateSysMessageUser(SysMessageUser sysMessageUser);

	/**
	 *获取消息用户集合
	 *@return List
	 */
	public List getSysMessageUsers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 统计用户未读消息数量
	 * @param userid
	 * @return
	 */
	public String getUnreadMessageCount(String userid);
	
	/**
	 *获取一页消息用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageUsers (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取一页消息用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageUsers (String messageid,String username,String usertype,String xueduan,String isread,String orderby, int start, int pagesize);

}