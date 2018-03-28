package com.wkmk.cms.service;

import java.util.List;

import com.wkmk.cms.bo.CmsNewsColumn;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 资讯栏目</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface CmsNewsColumnManager {
	/**
	 *根据id获取资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn getCmsNewsColumn(String columnid);

	/**
	 *根据id获取资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn getCmsNewsColumn(Integer columnid);

	/**
	 *增加资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn addCmsNewsColumn(CmsNewsColumn cmsNewsColumn);

	/**
	 *删除资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn delCmsNewsColumn(String columnid);

	/**
	 *删除资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn delCmsNewsColumn(CmsNewsColumn cmsNewsColumn);

	/**
	 *修改资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn updateCmsNewsColumn(CmsNewsColumn cmsNewsColumn);

	/**
	 *获取资讯栏目集合
	 *@return List
	 */
	public List getCmsNewsColumns (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页资讯栏目集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsNewsColumns (List<SearchModel> condition, String orderby, int start, int pagesize);
	/**
	 * 获取所有已用到的父节点
	 * 
	 * @return List
	 */
	public List<String> getAllParentNo();

}