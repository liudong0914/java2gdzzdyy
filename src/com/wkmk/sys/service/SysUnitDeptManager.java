package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUnitDept;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位机构</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUnitDeptManager {
	/**
	 *根据id获取系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept getSysUnitDept(String deptid);

	/**
	 *根据id获取系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept getSysUnitDept(Integer deptid);

	/**
	 *增加系统单位机构
	 *@param sysUnitDept SysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept addSysUnitDept(SysUnitDept sysUnitDept);

	/**
	 *删除系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept delSysUnitDept(String deptid);
	
	/**
	 *删除系统单位机构
	 *@param SysUnitDept sysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept delSysUnitDept(SysUnitDept sysUnitDept);

	/**
	 *修改系统单位机构
	 *@param sysUnitDept SysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept updateSysUnitDept(SysUnitDept sysUnitDept);

	/**
	 *获取系统单位机构集合
	 *@return List
	 */
	public List getSysUnitDepts (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统单位机构集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDepts (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取系统用户组父编号集合
	 *@return List
	 */
	public List getAllParentno(Integer unitid);
}