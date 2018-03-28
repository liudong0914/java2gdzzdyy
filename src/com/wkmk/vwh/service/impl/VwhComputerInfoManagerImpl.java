package com.wkmk.vwh.service.impl;

import java.util.List;

import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.service.VwhComputerInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库服务器信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhComputerInfoManagerImpl implements VwhComputerInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "视频库服务器信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo getVwhComputerInfo(String computerid){
		Integer iid = Integer.valueOf(computerid);
		return  (VwhComputerInfo)baseDAO.getObject(modelname,VwhComputerInfo.class,iid);
	}

	/**
	 *根据id获取视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo getVwhComputerInfo(Integer computerid){
		return  (VwhComputerInfo)baseDAO.getObject(modelname,VwhComputerInfo.class,computerid);
	}

	/**
	 *增加视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo addVwhComputerInfo(VwhComputerInfo vwhComputerInfo){
		return (VwhComputerInfo)baseDAO.addObject(modelname,vwhComputerInfo);
	}

	/**
	 *删除视频库服务器信息
	 *@param computerid Integer
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo delVwhComputerInfo(String computerid){
		VwhComputerInfo model = getVwhComputerInfo(computerid);
		return (VwhComputerInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo delVwhComputerInfo(VwhComputerInfo vwhComputerInfo){
		return (VwhComputerInfo)baseDAO.delObject(modelname,vwhComputerInfo);
	}

	/**
	 *修改视频库服务器信息
	 *@param vwhComputerInfo VwhComputerInfo
	 *@return VwhComputerInfo
	 */
	public VwhComputerInfo updateVwhComputerInfo(VwhComputerInfo vwhComputerInfo){
		return (VwhComputerInfo)baseDAO.updateObject(modelname,vwhComputerInfo);
	}

	/**
	 *获取视频库服务器信息集合
 	 *@return List
	 */
	public List getVwhComputerInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("VwhComputerInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页视频库服务器信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhComputerInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("VwhComputerInfo","computerid",condition, orderby, start,pagesize);
	}

}