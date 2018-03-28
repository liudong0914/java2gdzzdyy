package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysUserInfoDetailManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户扩展信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoDetailManagerImpl implements SysUserInfoDetailManager{

	private BaseDAO baseDAO;
	private String modelname = "系统用户扩展信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail getSysUserInfoDetail(String userid){
		Integer iid = Integer.valueOf(userid);
		return  (SysUserInfoDetail)baseDAO.getObject(modelname,SysUserInfoDetail.class,iid);
	}

	/**
	 *根据id获取系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail getSysUserInfoDetail(Integer userid){
		return  (SysUserInfoDetail)baseDAO.getObject(modelname,SysUserInfoDetail.class,userid);
	}

	/**
	 *增加系统用户扩展信息
	 *@param sysUserInfoDetail SysUserInfoDetail
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail addSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail){
		return (SysUserInfoDetail)baseDAO.addObject(modelname,sysUserInfoDetail);
	}

	/**
	 *删除系统用户扩展信息
	 *@param userid Integer
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail delSysUserInfoDetail(String userid){
		SysUserInfoDetail model = getSysUserInfoDetail(userid);
		return (SysUserInfoDetail)baseDAO.delObject(modelname,model);
	}

	/**
	 *修改系统用户扩展信息
	 *@param sysUserInfoDetail SysUserInfoDetail
	 *@return SysUserInfoDetail
	 */
	public SysUserInfoDetail updateSysUserInfoDetail(SysUserInfoDetail sysUserInfoDetail){
		return (SysUserInfoDetail)baseDAO.updateObject(modelname,sysUserInfoDetail);
	}

	/**
	 *获取系统用户扩展信息集合
 	 *@return List
	 */
	public List getSysUserInfoDetails(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserInfoDetail",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统用户扩展信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserInfoDetails (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserInfoDetail","userid",condition, orderby, start,pagesize);
	}

}