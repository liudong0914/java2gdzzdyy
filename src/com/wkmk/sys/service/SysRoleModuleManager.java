package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysRoleModule;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统角色模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysRoleModuleManager {
	/**
	 *根据id获取系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule getSysRoleModule(String rolemoduleid);

	/**
	 *根据id获取系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule getSysRoleModule(Integer rolemoduleid);

	/**
	 *增加系统角色模块
	 *@param sysRoleModule SysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule addSysRoleModule(SysRoleModule sysRoleModule);

	/**
	 *删除系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule delSysRoleModule(String rolemoduleid);
	
	/**
	 *删除系统角色模块
	 *@param SysRoleModule sysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule delSysRoleModule(SysRoleModule sysRoleModule);

	/**
	 *修改系统角色模块
	 *@param sysRoleModule SysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule updateSysRoleModule(SysRoleModule sysRoleModule);

	/**
	 *获取系统角色模块集合
	 *@return List
	 */
	public List getSysRoleModules (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页系统角色模块集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysRoleModules (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 获取当前单位所有角色所关联的模块id
	 * @return List
	 */
	public List getAllModuleidsByProduct(Integer productid);
	
	/**
	 * 获取当前用户所关联的所有模块
	 * @return List
	 */
	public List getAllModulesByUserid(Integer userid, Integer productid);
	
	/**
	 * 获取当前单位所有模块所关联的角色id
	 * @return List
	 */
	public List getAllRoleidsByProduct(Integer productid);
}