package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysRoleInfo;
import com.wkmk.sys.service.SysRoleInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统角色信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleInfoManagerImpl implements SysRoleInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统角色信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo getSysRoleInfo(String roleid){
		Integer iid = Integer.valueOf(roleid);
		return  (SysRoleInfo)baseDAO.getObject(modelname,SysRoleInfo.class,iid);
	}

	/**
	 *根据id获取系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo getSysRoleInfo(Integer roleid){
		return  (SysRoleInfo)baseDAO.getObject(modelname,SysRoleInfo.class,roleid);
	}

	/**
	 *增加系统角色信息
	 *@param sysRoleInfo SysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo addSysRoleInfo(SysRoleInfo sysRoleInfo){
		return (SysRoleInfo)baseDAO.addObject(modelname,sysRoleInfo);
	}

	/**
	 *删除系统角色信息
	 *@param roleid Integer
	 *@return SysRoleInfo
	 */
	public SysRoleInfo delSysRoleInfo(String roleid){
		SysRoleInfo model = getSysRoleInfo(roleid);
		return (SysRoleInfo)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统角色信息
	 *@param SysRoleInfo sysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo delSysRoleInfo(SysRoleInfo sysRoleInfo){
		return (SysRoleInfo)baseDAO.delObject(modelname,sysRoleInfo);
	}

	/**
	 *修改系统角色信息
	 *@param sysRoleInfo SysRoleInfo
	 *@return SysRoleInfo
	 */
	public SysRoleInfo updateSysRoleInfo(SysRoleInfo sysRoleInfo){
		return (SysRoleInfo)baseDAO.updateObject(modelname,sysRoleInfo);
	}

	/**
	 *获取系统角色信息集合
 	 *@return List
	 */
	public List getSysRoleInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysRoleInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统角色信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysRoleInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysRoleInfo","roleid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取系统角色信息集合
 	 *@return List
	 */
	public List getSysRoleInfos(Integer unitid){
		String hql = "SELECT a FROM SysRoleInfo AS a WHERE a.unitid=0 OR a.unitid=" + unitid + " ORDER BY a.unitid asc, a.roleno asc";
		return baseDAO.getObjects(hql);
	}

}