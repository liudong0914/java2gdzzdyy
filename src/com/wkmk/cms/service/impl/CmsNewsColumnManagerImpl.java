package com.wkmk.cms.service.impl;

import java.util.List;

import com.wkmk.cms.bo.CmsNewsColumn;
import com.wkmk.cms.service.CmsNewsColumnManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 资讯栏目</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsColumnManagerImpl implements CmsNewsColumnManager{

	private BaseDAO baseDAO;
	private String modelname = "资讯栏目";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn getCmsNewsColumn(String columnid){
		Integer iid = Integer.valueOf(columnid);
		return  (CmsNewsColumn)baseDAO.getObject(modelname,CmsNewsColumn.class,iid);
	}

	/**
	 *根据id获取资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn getCmsNewsColumn(Integer columnid){
		return  (CmsNewsColumn)baseDAO.getObject(modelname,CmsNewsColumn.class,columnid);
	}

	/**
	 *增加资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn addCmsNewsColumn(CmsNewsColumn cmsNewsColumn){
		return (CmsNewsColumn)baseDAO.addObject(modelname,cmsNewsColumn);
	}

	/**
	 *删除资讯栏目
	 *@param columnid Integer
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn delCmsNewsColumn(String columnid){
		CmsNewsColumn model = getCmsNewsColumn(columnid);
		return (CmsNewsColumn)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn delCmsNewsColumn(CmsNewsColumn cmsNewsColumn){
		return (CmsNewsColumn)baseDAO.delObject(modelname,cmsNewsColumn);
	}

	/**
	 *修改资讯栏目
	 *@param cmsNewsColumn CmsNewsColumn
	 *@return CmsNewsColumn
	 */
	public CmsNewsColumn updateCmsNewsColumn(CmsNewsColumn cmsNewsColumn){
		return (CmsNewsColumn)baseDAO.updateObject(modelname,cmsNewsColumn);
	}

	/**
	 *获取资讯栏目集合
 	 *@return List
	 */
	public List getCmsNewsColumns(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("CmsNewsColumn",condition,orderby,pagesize);
	}

	/**
	 *获取一页资讯栏目集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsNewsColumns (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("CmsNewsColumn","columnid",condition, orderby, start,pagesize);
	}
	/**
	 * 获取所有已用到的父节点
	 * 
	 * @return List
	 */
	public List getAllParentNo() {
		String sql = "select distinct a.parentno from CmsNewsColumn as a where 1=1";
		return baseDAO.getObjects(sql);
	}

}