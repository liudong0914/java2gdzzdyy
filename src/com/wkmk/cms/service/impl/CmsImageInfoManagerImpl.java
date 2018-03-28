package com.wkmk.cms.service.impl;

import java.util.List;

import com.wkmk.cms.bo.CmsImageInfo;
import com.wkmk.cms.service.CmsImageInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 图片广告信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsImageInfoManagerImpl implements CmsImageInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "图片广告信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo getCmsImageInfo(String imageid){
		Integer iid = Integer.valueOf(imageid);
		return  (CmsImageInfo)baseDAO.getObject(modelname,CmsImageInfo.class,iid);
	}

	/**
	 *根据id获取图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo getCmsImageInfo(Integer imageid){
		return  (CmsImageInfo)baseDAO.getObject(modelname,CmsImageInfo.class,imageid);
	}

	/**
	 *增加图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo addCmsImageInfo(CmsImageInfo cmsImageInfo){
		return (CmsImageInfo)baseDAO.addObject(modelname,cmsImageInfo);
	}

	/**
	 *删除图片广告信息
	 *@param imageid Integer
	 *@return CmsImageInfo
	 */
	public CmsImageInfo delCmsImageInfo(String imageid){
		CmsImageInfo model = getCmsImageInfo(imageid);
		return (CmsImageInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo delCmsImageInfo(CmsImageInfo cmsImageInfo){
		return (CmsImageInfo)baseDAO.delObject(modelname,cmsImageInfo);
	}

	/**
	 *修改图片广告信息
	 *@param cmsImageInfo CmsImageInfo
	 *@return CmsImageInfo
	 */
	public CmsImageInfo updateCmsImageInfo(CmsImageInfo cmsImageInfo){
		return (CmsImageInfo)baseDAO.updateObject(modelname,cmsImageInfo);
	}

	/**
	 *获取图片广告信息集合
 	 *@return List
	 */
	public List getCmsImageInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("CmsImageInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页图片广告信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageCmsImageInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("CmsImageInfo","imageid",condition, orderby, start,pagesize);
	}

}