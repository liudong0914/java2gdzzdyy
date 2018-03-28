package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.service.SysAreaInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 地区信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAreaInfoManagerImpl implements SysAreaInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "地区信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfo(String areaid){
		Integer iid = Integer.valueOf(areaid);
		return  (SysAreaInfo)baseDAO.getObject(modelname,SysAreaInfo.class,iid);
	}

	/**
	 *根据id获取地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfo(Integer areaid){
		return  (SysAreaInfo)baseDAO.getObject(modelname,SysAreaInfo.class,areaid);
	}

	/**
	 *增加地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo addSysAreaInfo(SysAreaInfo sysAreaInfo){
		return (SysAreaInfo)baseDAO.addObject(modelname,sysAreaInfo);
	}

	/**
	 *删除地区信息
	 *@param areaid Integer
	 *@return SysAreaInfo
	 */
	public SysAreaInfo delSysAreaInfo(String areaid){
		SysAreaInfo model = getSysAreaInfo(areaid);
		return (SysAreaInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo delSysAreaInfo(SysAreaInfo sysAreaInfo){
		return (SysAreaInfo)baseDAO.delObject(modelname,sysAreaInfo);
	}

	/**
	 *修改地区信息
	 *@param sysAreaInfo SysAreaInfo
	 *@return SysAreaInfo
	 */
	public SysAreaInfo updateSysAreaInfo(SysAreaInfo sysAreaInfo){
		return (SysAreaInfo)baseDAO.updateObject(modelname,sysAreaInfo);
	}

	/**
	 *获取地区信息集合
 	 *@return List
	 */
	public List getSysAreaInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysAreaInfo",condition,orderby,pagesize);
	}
	
	/**
	 *获取地区信息集合
 	 *@return List
	 */
	public List getSysAreaInfosByParentno(String parentno){
		String sql = "select a from SysAreaInfo as a where parentno='" + parentno + "' order by areano asc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取地区信息
 	 *@return SysAreaInfo
	 */
	public SysAreaInfo getSysAreaInfosByCitycode(String citycode){
		String sql = "select a from SysAreaInfo as a where citycode='" + citycode + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysAreaInfo) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页地区信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysAreaInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysAreaInfo","areaid",condition, orderby, start,pagesize);
	}

}