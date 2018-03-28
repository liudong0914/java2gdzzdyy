package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserComplaint;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户被投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserComplaintManager {
	/**
	 *根据id获取用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint getSysUserComplaint(String usercomplaintid);

	/**
	 *根据id获取用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint getSysUserComplaint(Integer usercomplaintid);

	/**
	 *增加用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint addSysUserComplaint(SysUserComplaint sysUserComplaint);

	/**
	 *删除用户被投诉
	 *@param usercomplaintid Integer
	 *@return SysUserComplaint
	 */
	public SysUserComplaint delSysUserComplaint(String usercomplaintid);

	/**
	 *删除用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint delSysUserComplaint(SysUserComplaint sysUserComplaint);

	/**
	 *修改用户被投诉
	 *@param sysUserComplaint SysUserComplaint
	 *@return SysUserComplaint
	 */
	public SysUserComplaint updateSysUserComplaint(SysUserComplaint sysUserComplaint);

	/**
	 *获取用户被投诉集合
	 *@return List
	 */
	public List getSysUserComplaints (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页用户被投诉集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserComplaints (List<SearchModel> condition, String orderby, int start, int pagesize);

}