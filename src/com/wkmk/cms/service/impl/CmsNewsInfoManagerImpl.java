package com.wkmk.cms.service.impl;

import java.util.List;

import com.wkmk.cms.bo.CmsNewsInfo;
import com.wkmk.cms.service.CmsNewsInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 资讯信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsInfoManagerImpl implements CmsNewsInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "资讯信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo getCmsNewsInfo(String newsid){
		Integer iid = Integer.valueOf(newsid);
		return  (CmsNewsInfo)baseDAO.getObject(modelname,CmsNewsInfo.class,iid);
	}

	/**
	 *根据id获取资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo getCmsNewsInfo(Integer newsid){
		return  (CmsNewsInfo)baseDAO.getObject(modelname,CmsNewsInfo.class,newsid);
	}

	/**
	 *增加资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo addCmsNewsInfo(CmsNewsInfo cmsNewsInfo){
		return (CmsNewsInfo)baseDAO.addObject(modelname,cmsNewsInfo);
	}

	/**
	 *删除资讯信息
	 *@param newsid Integer
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo delCmsNewsInfo(String newsid){
		CmsNewsInfo model = getCmsNewsInfo(newsid);
		return (CmsNewsInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo delCmsNewsInfo(CmsNewsInfo cmsNewsInfo){
		return (CmsNewsInfo)baseDAO.delObject(modelname,cmsNewsInfo);
	}

	/**
	 *修改资讯信息
	 *@param cmsNewsInfo CmsNewsInfo
	 *@return CmsNewsInfo
	 */
	public CmsNewsInfo updateCmsNewsInfo(CmsNewsInfo cmsNewsInfo){
		return (CmsNewsInfo)baseDAO.updateObject(modelname,cmsNewsInfo);
	}

	/**
	 *获取资讯信息集合
 	 *@return List
	 */
	public List getCmsNewsInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("CmsNewsInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页资讯信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsNewsInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("CmsNewsInfo","newsid",condition, orderby, start,pagesize);
	}
	/**
	 * 获取所有用到的栏目
	 * 
	 * @param unitid
	 *            String
	 * @return List
	 */
	public List getAllColumnIds() {
		String sql = "select distinct a.cmsNewsColumn.columnid from CmsNewsInfo as a where 1=1";

		return baseDAO.getObjects(sql);
	}

}