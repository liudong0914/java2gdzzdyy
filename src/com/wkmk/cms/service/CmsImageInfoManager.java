package com.wkmk.cms.service;

import java.util.List;

import com.wkmk.cms.bo.CmsImageInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 图片广告信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface CmsImageInfoManager {
	/**
	 *根据id获取图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo getCmsImageInfo(String imageid);

	/**
	 *根据id获取图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo getCmsImageInfo(Integer imageid);

	/**
	 *增加图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo addCmsImageInfo(CmsImageInfo cmsImageInfo);

	/**
	 *删除图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo delCmsImageInfo(String imageid);

	/**
	 *删除图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo delCmsImageInfo(CmsImageInfo cmsImageInfo);

	/**
	 *修改图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo updateCmsImageInfo(CmsImageInfo cmsImageInfo);

	/**
	 *获取图片广告信息集合
	 *@return List
	 */
	public List getCmsImageInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页图片广告信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsImageInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}