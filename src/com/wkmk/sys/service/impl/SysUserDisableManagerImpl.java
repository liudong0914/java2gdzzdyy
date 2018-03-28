package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.service.SysUserDisableManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户禁用记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserDisableManagerImpl implements SysUserDisableManager{

	private BaseDAO baseDAO;
	private String modelname = "用户禁用记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable getSysUserDisable(String disableid){
		Integer iid = Integer.valueOf(disableid);
		return  (SysUserDisable)baseDAO.getObject(modelname,SysUserDisable.class,iid);
	}

	/**
	 *根据id获取用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable getSysUserDisable(Integer disableid){
		return  (SysUserDisable)baseDAO.getObject(modelname,SysUserDisable.class,disableid);
	}

	/**
	 *增加用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable addSysUserDisable(SysUserDisable sysUserDisable){
		return (SysUserDisable)baseDAO.addObject(modelname,sysUserDisable);
	}

	/**
	 *删除用户禁用记录
	 *@param disableid Integer
	 *@return SysUserDisable
	 */
	public SysUserDisable delSysUserDisable(String disableid){
		SysUserDisable model = getSysUserDisable(disableid);
		return (SysUserDisable)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable delSysUserDisable(SysUserDisable sysUserDisable){
		return (SysUserDisable)baseDAO.delObject(modelname,sysUserDisable);
	}

	/**
	 *修改用户禁用记录
	 *@param sysUserDisable SysUserDisable
	 *@return SysUserDisable
	 */
	public SysUserDisable updateSysUserDisable(SysUserDisable sysUserDisable){
		return (SysUserDisable)baseDAO.updateObject(modelname,sysUserDisable);
	}

	/**
	 *获取用户禁用记录集合
 	 *@return List
	 */
	public List getSysUserDisables(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserDisable",condition,orderby,pagesize);
	}

	/**
	 *获取一页用户禁用记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserDisables (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserDisable","disableid",condition, orderby, start,pagesize);
	}

}