package com.wkmk.cms.service;

import java.util.List;

import com.wkmk.cms.bo.CmsNewsInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 资讯信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface CmsNewsInfoManager {
	/**
	 *根据id获取资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo getCmsNewsInfo(String newsid);

	/**
	 *根据id获取资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo getCmsNewsInfo(Integer newsid);

	/**
	 *增加资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo addCmsNewsInfo(CmsNewsInfo cmsNewsInfo);

	/**
	 *删除资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo delCmsNewsInfo(String newsid);

	/**
	 *删除资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo delCmsNewsInfo(CmsNewsInfo cmsNewsInfo);

	/**
	 *修改资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo updateCmsNewsInfo(CmsNewsInfo cmsNewsInfo);

	/**
	 *获取资讯信息集合
	 *@return List
	 */
	public List getCmsNewsInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页资讯信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsNewsInfos (List<SearchModel> condition, String orderby, int start, int pagesize);
	/**
	 * 获取所有用到的栏目
	 * 
	 * @param unitid
	 *            String
	 * @return List
	 */
	public List<String> getAllColumnIds();

}