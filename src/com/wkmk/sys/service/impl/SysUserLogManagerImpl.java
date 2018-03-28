package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.service.SysUserLogManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户日志信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserLogManagerImpl implements SysUserLogManager{

	private BaseDAO baseDAO;
	private String modelname = "系统用户日志信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog getSysUserLog(String logid){
		Integer iid = Integer.valueOf(logid);
		return  (SysUserLog)baseDAO.getObject(modelname,SysUserLog.class,iid);
	}

	/**
	 *根据id获取系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog getSysUserLog(Integer logid){
		return  (SysUserLog)baseDAO.getObject(modelname,SysUserLog.class,logid);
	}

	/**
	 *增加系统用户日志信息
	 *@param sysUserLog SysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog addSysUserLog(SysUserLog sysUserLog){
		return (SysUserLog)baseDAO.addObject(modelname,sysUserLog);
	}

	/**
	 *删除系统用户日志信息
	 *@param logid Integer
	 *@return SysUserLog
	 */
	public SysUserLog delSysUserLog(String logid){
		SysUserLog model = getSysUserLog(logid);
		return (SysUserLog)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统用户日志信息
	 *@param SysUserLog sysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog delSysUserLog(SysUserLog sysUserLog){
		return (SysUserLog)baseDAO.delObject(modelname,sysUserLog);
	}

	/**
	 *修改系统用户日志信息
	 *@param sysUserLog SysUserLog
	 *@return SysUserLog
	 */
	public SysUserLog updateSysUserLog(SysUserLog sysUserLog){
		return (SysUserLog)baseDAO.updateObject(modelname,sysUserLog);
	}

	/**
	 *获取系统用户日志信息集合
 	 *@return List
	 */
	public List getSysUserLogs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserLog",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统用户日志信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserLogs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserLog","logid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 按照用户注册日期排序，获取用户使用信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserLogs(String startTime,String endTime){
		String sql="select date_format(a.createdate,'%Y-%m-%d') as day,count(*) from SysUserLog as a where a.sysUserInfo.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2,3)) and date_format(a.createdate,'%Y-%m-%d') between '"+startTime+"' and '"+endTime+"' group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	/**
	 * 按照用户交易日期排序，获取用户交易信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserMoneys(String startTime,String endTime){
		String sql="select date_format(a.createdate,'%Y-%m-%d') as day,count(*) from SysUserMoney as a where a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2,3)) and date_format(a.createdate,'%Y-%m-%d') between '"+startTime+"' and '"+endTime+"' group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	/**
	 * 按照用户充值日期排序，获取用户充值信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserPays(String startTime,String endTime){
		String sql="select date_format(a.createdate,'%Y-%m-%d') as day,count(*) from SysUserPay as a where a.changetype=1 and a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2,3)) and date_format(a.createdate,'%Y-%m-%d') between '"+startTime+"' and '"+endTime+"' group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
/**
 * 	
  * 方法描述：按照用户充值日期排序，获取每天充值总额
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-24 上午9:58:17
 */
	public List getUserPaysOfchangemoney(String startTime,String endTime){
		String sql="select date_format(a.createdate,'%Y-%m-%d') as day,sum(changemoney) from SysUserPay as a where a.changetype=1 and a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2,3)) and date_format(a.createdate,'%Y-%m-%d') between '"+startTime+"' and '"+endTime+"' group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
}