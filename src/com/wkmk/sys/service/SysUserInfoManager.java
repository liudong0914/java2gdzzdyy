package com.wkmk.sys.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserInfoManager {
	/**
	 *根据id获取系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(String userid);

	/**
	 *根据id获取系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(Integer userid);

	/**
	 *增加系统用户信息
	 *@param sysUserInfo SysUserInfo
	 *@return SysUserInfo
	 */
	public SysUserInfo addSysUserInfo(SysUserInfo sysUserInfo);

	/**
	 *删除系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo delSysUserInfo(String userid);

	/**
	 *修改系统用户信息
	 *@param sysUserInfo SysUserInfo
	 *@return SysUserInfo
	 */
	public SysUserInfo updateSysUserInfo(SysUserInfo sysUserInfo);

	/**
	 *获取系统用户信息集合
	 *@return List
	 */
	public List getSysUserInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统用户信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserInfos (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public List getSysUserInfosOfEcu(String unitid,String username,String usertype,String courseid,String courseclassid);
	public PageList getSysUserInfosOfEcuOfPage(String unitid,String username,String usertype,String courseid,String courseclassid,String sorderindex,int start, int size);
	/**
	 * 根据登录名获取用户信息
	 * @param loginname
	 * @return
	 */
	public SysUserInfo getUserInfoByLoginName(String loginname);
	
	/**
	 * 根据用户名获取用户信息
	 * @param loginname
	 * @return
	 */
	public SysUserInfo getUserInfoByUserName(String username);
	
	/**
	 * 根据某字段查询用户信息
	 * @param checkname 查询字段值
	 * @param checktype 查询字段类型 1登录名，2学号，3身份证号，4手机号，5邮箱
	 * @return
	 */
	public SysUserInfo getUserInfoByCheckName(String checkname, String checktype);
	
	/**
	 * 根据用户类型进行分组，并获取每一类的总数
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-9-27 下午6:21:13 
	* @lastModified  2016-9-27 下午6:21:13 
	* @version  1.0 
	* @param hql
	* @return
	 */
	public List getUserInfoCountByUserType(String strings);
	
	/**
	 * 按照用户注册日期排序，获取用户注册信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserInfos(String startTime,String endTime);
	
	public List getUserInfosOfAdd();
	
	/**
	 * 
	  * 方法描述：每天注册人数最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getUserInfosOfAddMaxNum();
	
	public List getUserInfosOfLog();
	/* 
	* 方法描述：每天使用人数最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getUserInfosOfLogMaxNum();
	
	public List getUserInfosOfMoney();
	
	/*
	 * 每天交易总人数
	 */
	public List getUserInfosOfMoneyCount();
	/* 
	* 方法描述：每天交易人数最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getUserInfosOfMoneyMaxNum();
	
	public List getUserInfosOfMoneyAll();
	public List getUserInfosOfMoneyOutAll();
	public List getUserInfosOfMoneyInAll();
	/* 
	* 方法描述：每天交易总额最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getUserInfosOfMoneyAllMaxNum();
	
	/**
	 * 交易总额
	 */
	public List getUserInfosOfCountMoney();
	/**
	 * 交易人数总数
	 */
	public List getUserInfosOfCountNum();
	/**
	 * 交易总人数
	 */
	public List getUserInfosOfCountNumProper();
	
	/**
	 * 支出总额
	 */
	public List getUserInfosOfCountMoneyOut();
	
	/**
	 * 收入总额
	 */
	public List getUserInfosOfCountMoneyIn();
	
	public List getUserInfosOfPay();
	/* 
	* 方法描述：每天充值人数最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getUserInfosOfPayMaxNum();
	/**
	 * 充值（平台收入）总额
	 */
	public List getUserInfosOfPayCountMoney();
	/**
	 * 交易总数
	 */
	public List getUserInfosOfPayCountNum();
	/**
	 * 交易总人数
	 */
	public List getUserInfosOfPayCountNumProper();
	
	public List getUserInfosOfPayAll();
	/* 
	* 方法描述：每天充值总额最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getUserInfosOfPayAllMaxNum();
	
	/**
	 * 
	  * 方法描述：个人支出
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 下午2:39:59
	 */
	public List getPersonalOfMoneyOut(Integer userid);
	
	/* 
	* 方法描述：每天支出最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getPersonalOfMoneyOutMaxNum(Integer userid);
	
	/**
	 * 
	  * 方法描述：个人收入
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 下午2:39:59
	 */
	public List getPersonalOfMoneyIn(Integer userid);
	
	/* 
	* 方法描述：每天收入最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getPersonalOfMoneyInMaxNum(Integer userid);
	
	/**
	 * 
	  * 方法描述：获取所有登录名
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-6-1 下午5:14:51
	 */
	public List getAllLoginname();
}