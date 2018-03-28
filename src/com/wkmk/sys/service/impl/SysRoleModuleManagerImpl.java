package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysRoleModule;
import com.wkmk.sys.service.SysRoleModuleManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统角色模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleModuleManagerImpl implements SysRoleModuleManager{

	private BaseDAO baseDAO;
	private String modelname = "系统角色模块";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule getSysRoleModule(String rolemoduleid){
		Integer iid = Integer.valueOf(rolemoduleid);
		return  (SysRoleModule)baseDAO.getObject(modelname,SysRoleModule.class,iid);
	}

	/**
	 *根据id获取系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule getSysRoleModule(Integer rolemoduleid){
		return  (SysRoleModule)baseDAO.getObject(modelname,SysRoleModule.class,rolemoduleid);
	}

	/**
	 *增加系统角色模块
	 *@param sysRoleModule SysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule addSysRoleModule(SysRoleModule sysRoleModule){
		return (SysRoleModule)baseDAO.addObject(modelname,sysRoleModule);
	}

	/**
	 *删除系统角色模块
	 *@param rolemoduleid Integer
	 *@return SysRoleModule
	 */
	public SysRoleModule delSysRoleModule(String rolemoduleid){
		SysRoleModule model = getSysRoleModule(rolemoduleid);
		return (SysRoleModule)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统角色模块
	 *@param SysRoleModule sysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule delSysRoleModule(SysRoleModule sysRoleModule){
		return (SysRoleModule)baseDAO.delObject(modelname,sysRoleModule);
	}

	/**
	 *修改系统角色模块
	 *@param sysRoleModule SysRoleModule
	 *@return SysRoleModule
	 */
	public SysRoleModule updateSysRoleModule(SysRoleModule sysRoleModule){
		return (SysRoleModule)baseDAO.updateObject(modelname,sysRoleModule);
	}

	/**
	 *获取系统角色模块集合
 	 *@return List
	 */
	public List getSysRoleModules(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysRoleModule",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统角色模块集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysRoleModules (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysRoleModule","rolemoduleid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取当前单位所有角色所关联的模块id
	 * @return List
	 */
	public List getAllModuleidsByProduct(Integer productid) {
		String sql = "SELECT DISTINCT a.sysModuleInfo.moduleid FROM SysRoleModule as a WHERE 1=1";
		if(productid != null && productid.intValue()>0){
			sql = sql + " AND a.sysModuleInfo.productid=" + productid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取当前用户所关联的所有模块
	 * @return List
	 */
	public List getAllModulesByUserid(Integer userid, Integer productid) {
		String sql = "SELECT DISTINCT a.sysModuleInfo FROM SysRoleModule as a, SysUserRole as b WHERE a.roleid=b.roleid AND b.sysUserInfo.userid=" + userid + " AND a.sysModuleInfo.status='1'";
		if(productid != null && productid.intValue()>0){
			sql = sql + " AND a.sysModuleInfo.productid=" + productid;
		}
		sql = sql + " ORDER BY a.sysModuleInfo.moduleno asc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取当前单位所有模块所关联的角色id
	 * @return List
	 */
	public List getAllRoleidsByProduct(Integer productid) {
		String sql = "SELECT DISTINCT a.roleid FROM SysRoleModule as a WHERE 1=1";
		if(productid != null && productid.intValue()>0){
			sql = sql + " AND a.sysModuleInfo.productid=" + productid;
		}
		return baseDAO.getObjects(sql);
	}

}