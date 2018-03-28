package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.service.SysUnitInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitInfoManagerImpl implements SysUnitInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统单位信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo getSysUnitInfo(String unitid){
		Integer iid = Integer.valueOf(unitid);
		return  (SysUnitInfo)baseDAO.getObject(modelname,SysUnitInfo.class,iid);
	}

	/**
	 *根据id获取系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo getSysUnitInfo(Integer unitid){
		return  (SysUnitInfo)baseDAO.getObject(modelname,SysUnitInfo.class,unitid);
	}

	/**
	 *增加系统单位信息
	 *@param sysUnitInfo SysUnitInfo
	 *@return SysUnitInfo
	 */
	public SysUnitInfo addSysUnitInfo(SysUnitInfo sysUnitInfo){
		return (SysUnitInfo)baseDAO.addObject(modelname,sysUnitInfo);
	}

	/**
	 *删除系统单位信息
	 *@param unitid Integer
	 *@return SysUnitInfo
	 */
	public SysUnitInfo delSysUnitInfo(String unitid){
		SysUnitInfo model = getSysUnitInfo(unitid);
		return (SysUnitInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *修改系统单位信息
	 *@param sysUnitInfo SysUnitInfo
	 *@return SysUnitInfo
	 */
	public SysUnitInfo updateSysUnitInfo(SysUnitInfo sysUnitInfo){
		return (SysUnitInfo)baseDAO.updateObject(modelname,sysUnitInfo);
	}

	/**
	 *获取系统单位信息集合
 	 *@return List
	 */
	public List getSysUnitInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUnitInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统单位信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUnitInfo","unitid",condition, orderby, start,pagesize);
	}

}