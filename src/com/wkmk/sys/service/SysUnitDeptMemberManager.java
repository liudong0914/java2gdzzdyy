package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUnitDeptMember;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位机构成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUnitDeptMemberManager {
	/**
	 *根据id获取系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember getSysUnitDeptMember(String memberid);

	/**
	 *根据id获取系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember getSysUnitDeptMember(Integer memberid);

	/**
	 *增加系统单位机构成员
	 *@param sysUnitDeptMember SysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember addSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember);

	/**
	 *删除系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember delSysUnitDeptMember(String memberid);
	
	/**
	 *删除系统单位机构成员
	 *@param SysUnitDeptMember sysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember delSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember);

	/**
	 *修改系统单位机构成员
	 *@param sysUnitDeptMember SysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember updateSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember);

	/**
	 *获取系统单位机构成员集合
	 *@return List
	 */
	public List getSysUnitDeptMembers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统单位机构成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDeptMembers (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取一页系统用户组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDeptMembers (String condition,String username,Integer unitid,String deptno,String orderby,int start,int pagesize);
	
	/**
	 *获取系统用户组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public List getSysUnitDeptMembers (String condition,String username,Integer unitid,String deptno,String orderby,int pagesize);
	
	/**
	 *获取所以组id集合
	 *@return List
	 */
	public List getAllDeptidsByUnitid(Integer unitid);
	
	/**
	 *获取所以组id集合
	 *@return List
	 */
	public List getAllDeptidsByUserid(Integer userid);
}