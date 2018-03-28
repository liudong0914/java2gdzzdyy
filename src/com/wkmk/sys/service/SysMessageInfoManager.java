package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysMessageInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统消息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysMessageInfoManager {
	/**
	 *根据id获取系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo getSysMessageInfo(String messageid);

	/**
	 *根据id获取系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo getSysMessageInfo(Integer messageid);

	/**
	 *增加系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo addSysMessageInfo(SysMessageInfo sysMessageInfo);

	/**
	 *删除系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo delSysMessageInfo(String messageid);

	/**
	 *删除系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo delSysMessageInfo(SysMessageInfo sysMessageInfo);

	/**
	 *修改系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo updateSysMessageInfo(SysMessageInfo sysMessageInfo);

	/**
	 *获取系统消息集合
	 *@return List
	 */
	public List getSysMessageInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统消息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageInfos (List<SearchModel> condition,String orderby,int start,int pagesize);
	
	/**
	 *获取一页系统消息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageInfos (String title,String username,String orderby,int start,int pagesize);
	
	/**
	 * 统计用户未读消息数量
	 * @param userid
	 * @return
	 */
	public String getUnreadMessage(Integer userid);
}