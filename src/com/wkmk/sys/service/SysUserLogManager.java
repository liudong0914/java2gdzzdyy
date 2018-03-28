package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserLog;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户日志信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserLogManager {
	/**
	 *根据id获取系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog getSysUserLog(String logid);

	/**
	 *根据id获取系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog getSysUserLog(Integer logid);

	/**
	 *增加系统用户日志信息
	 *@param sysUserLog SysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog addSysUserLog(SysUserLog sysUserLog);

	/**
	 *删除系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog delSysUserLog(String logid);
	
	/**
	 *删除系统用户日志信息
	 *@param SysUserLog sysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog delSysUserLog(SysUserLog sysUserLog);

	/**
	 *修改系统用户日志信息
	 *@param sysUserLog SysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog updateSysUserLog(SysUserLog sysUserLog);

	/**
	 *获取系统用户日志信息集合
	 *@return List
	 */
	public List getSysUserLogs (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统用户日志信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserLogs (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 * 按照用户注册日期排序，获取用户使用信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserLogs(String startTime,String endTime);

	/**
	 * 按照用户交易日期排序，获取用户交易信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserMoneys(String startTime,String endTime);
	
	/**
	 * 按照用户充值日期排序，获取用户充值信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserPays(String startTime,String endTime);
	/**
	 * 	
	  * 方法描述：按照用户充值日期排序，获取每天充值总额
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-24 上午9:58:17
	 */
		public List getUserPaysOfchangemoney(String startTime,String endTime);
}