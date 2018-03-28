package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysProductInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统产品信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysProductInfoManager {
	/**
	 *根据id获取系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo getSysProductInfo(String productid);

	/**
	 *根据id获取系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo getSysProductInfo(Integer productid);

	/**
	 *增加系统产品信息
	 *@param sysProductInfo SysProductInfo
	 *@return SysProductInfo
	 */
	public SysProductInfo addSysProductInfo(SysProductInfo sysProductInfo);

	/**
	 *删除系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo delSysProductInfo(String productid);

	/**
	 *修改系统产品信息
	 *@param sysProductInfo SysProductInfo
	 *@return SysProductInfo
	 */
	public SysProductInfo updateSysProductInfo(SysProductInfo sysProductInfo);

	/**
	 *获取系统产品信息集合
	 *@return List
	 */
	public List getSysProductInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取用户关联产品
 	 *@return List
	 */
	public List getUserProducts(Integer userid);
	
	/**
	 *获取一页系统产品信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysProductInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}