package com.wkmk.vwh.service;

import java.util.List;

import com.wkmk.vwh.bo.VwhComputerInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库服务器信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface VwhComputerInfoManager {
	/**
	 *根据id获取视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo getVwhComputerInfo(String computerid);

	/**
	 *根据id获取视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo getVwhComputerInfo(Integer computerid);

	/**
	 *增加视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo addVwhComputerInfo(VwhComputerInfo vwhComputerInfo);

	/**
	 *删除视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo delVwhComputerInfo(String computerid);

	/**
	 *删除视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo delVwhComputerInfo(VwhComputerInfo vwhComputerInfo);

	/**
	 *修改视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo updateVwhComputerInfo(VwhComputerInfo vwhComputerInfo);

	/**
	 *获取视频库服务器信息集合
	 *@return List
	 */
	public List getVwhComputerInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页视频库服务器信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhComputerInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}