package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserComplaint;
import com.wkmk.sys.service.SysUserComplaintManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户被投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserComplaintManagerImpl implements SysUserComplaintManager{

	private BaseDAO baseDAO;
	private String modelname = "用户被投诉";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint getSysUserComplaint(String usercomplaintid){
		Integer iid = Integer.valueOf(usercomplaintid);
		return  (SysUserComplaint)baseDAO.getObject(modelname,SysUserComplaint.class,iid);
	}

	/**
	 *根据id获取用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint getSysUserComplaint(Integer usercomplaintid){
		return  (SysUserComplaint)baseDAO.getObject(modelname,SysUserComplaint.class,usercomplaintid);
	}

	/**
	 *增加用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint addSysUserComplaint(SysUserComplaint sysUserComplaint){
		return (SysUserComplaint)baseDAO.addObject(modelname,sysUserComplaint);
	}

	/**
	 *删除用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint delSysUserComplaint(String usercomplaintid){
		SysUserComplaint model = getSysUserComplaint(usercomplaintid);
		return (SysUserComplaint)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint delSysUserComplaint(SysUserComplaint sysUserComplaint){
		return (SysUserComplaint)baseDAO.delObject(modelname,sysUserComplaint);
	}

	/**
	 *修改用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint updateSysUserComplaint(SysUserComplaint sysUserComplaint){
		return (SysUserComplaint)baseDAO.updateObject(modelname,sysUserComplaint);
	}

	/**
	 *获取用户被投诉集合
 	 *@return List
	 */
	public List getSysUserComplaints(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserComplaint",condition,orderby,pagesize);
	}

	/**
	 *获取一页用户被投诉集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserComplaints (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserComplaint","usercomplaintid",condition, orderby, start,pagesize);
	}

}