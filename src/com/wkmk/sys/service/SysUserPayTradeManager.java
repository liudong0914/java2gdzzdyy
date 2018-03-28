package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserPayTrade;

/**
 *<p>Description: 在线交易订单明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserPayTradeManager {
	/**
	 *根据id获取在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade getSysUserPayTrade(String tradeid);

	/**
	 *根据id获取在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade getSysUserPayTrade(Integer tradeid);

	/**
	 *增加在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade addSysUserPayTrade(SysUserPayTrade sysUserPayTrade);

	/**
	 *删除在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade delSysUserPayTrade(String tradeid);

	/**
	 *删除在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade delSysUserPayTrade(SysUserPayTrade sysUserPayTrade);

	/**
	 *修改在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade updateSysUserPayTrade(SysUserPayTrade sysUserPayTrade);

	/**
	 *获取在线交易订单明细集合
	 *@return List
	 */
	public List getSysUserPayTrades (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线交易订单明细
 	 *@return List
	 */
	public SysUserPayTrade getSysUserPayTradeByOuttradeno(String outtradeno);
	
	/**
	 *获取一页在线交易订单明细集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserPayTrades (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList getSysUserPayTradesOfPage(String subject, String username, String createdate, String body,String paytype,String state,String usertype,String xueduan,  String sorderindex,int start, int size); 

	public PageList getSysUserPayTradesOfPage2(Integer userid,String subject, String username, String createdate, String body,String paytype,String state,String usertype,String xueduan, String sorderindex,int start, int size);
}