package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysSmsInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 手机短信</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysSmsInfoManager {
	/**
	 *根据id获取手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo getSysSmsInfo(String smsid);

	/**
	 *根据id获取手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo getSysSmsInfo(Integer smsid);

	/**
	 *增加手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo addSysSmsInfo(SysSmsInfo sysSmsInfo);

	/**
	 *删除手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo delSysSmsInfo(String smsid);

	/**
	 *删除手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo delSysSmsInfo(SysSmsInfo sysSmsInfo);

	/**
	 *修改手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo updateSysSmsInfo(SysSmsInfo sysSmsInfo);

	/**
	 *获取手机短信集合
	 *@return List
	 */
	public List getSysSmsInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页手机短信集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysSmsInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

	/* 
	 * 查询用户当天发送成功短信数
	 */
	public int getMobileCountMessage(String userid);
	
	/* 
	 * 修改支付密码根据手机号码查询当天发送给该手机号码的短信数
	 */
	public int getPasswordCountMessage(String userid);
	
	/**
	 *获取一页手机短信集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysSmsInfos (String mobile,String username,String state,String type,String orderby,int start,int pagesize);
}