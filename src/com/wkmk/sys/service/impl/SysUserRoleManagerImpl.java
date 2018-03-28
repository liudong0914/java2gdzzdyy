package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserRole;
import com.wkmk.sys.service.SysUserRoleManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserRoleManagerImpl implements SysUserRoleManager{

	private BaseDAO baseDAO;
	private String modelname = "系统用户角色";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole getSysUserRole(String userroleid){
		Integer iid = Integer.valueOf(userroleid);
		return  (SysUserRole)baseDAO.getObject(modelname,SysUserRole.class,iid);
	}

	/**
	 *根据id获取系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole getSysUserRole(Integer userroleid){
		return  (SysUserRole)baseDAO.getObject(modelname,SysUserRole.class,userroleid);
	}

	/**
	 *增加系统用户角色
	 *@param sysUserRole SysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole addSysUserRole(SysUserRole sysUserRole){
		return (SysUserRole)baseDAO.addObject(modelname,sysUserRole);
	}

	/**
	 *删除系统用户角色
	 *@param userroleid Integer
	 *@return SysUserRole
	 */
	public SysUserRole delSysUserRole(String userroleid){
		SysUserRole model = getSysUserRole(userroleid);
		return (SysUserRole)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统用户角色
	 *@param SysUserRole sysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole delSysUserRole(SysUserRole sysUserRole){
		return (SysUserRole)baseDAO.delObject(modelname,sysUserRole);
	}

	/**
	 *修改系统用户角色
	 *@param sysUserRole SysUserRole
	 *@return SysUserRole
	 */
	public SysUserRole updateSysUserRole(SysUserRole sysUserRole){
		return (SysUserRole)baseDAO.updateObject(modelname,sysUserRole);
	}

	/**
	 *获取系统用户角色集合
 	 *@return List
	 */
	public List getSysUserRoles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserRole",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统用户角色集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserRoles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserRole","userroleid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取所有用户所关联的角色id
	 * @param userid
	 * @return List
	 */
	public List getAllRoleids(Integer userid) {
		String sql = "SELECT DISTINCT a.roleid FROM SysUserRole as a WHERE 1=1";
		if(userid != null && userid != -1){
			sql += " AND a.sysUserInfo.userid=" + userid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取某角色下所有的用户
	 * @param roleid
	 * @return
	 */
	public List getAllUserInfosByRoleid(Integer roleid){
		String sql = "SELECT a.sysUserInfo FROM SysUserRole as a WHERE 1=1";
		if(roleid != null && roleid != -1){
			sql += " AND a.roleid=" + roleid;
		}
		sql += " ORDER BY a.sysUserInfo.loginname";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取系统当前用户已有的角色
	 * @return List
	 */
	public List getSysUserRoles(Integer userid, Integer unitid) {
		String sql = null;
		if(unitid == 0){
			sql = "SELECT a FROM SysUserRole as a, SysRoleInfo as b WHERE a.sysUserInfo.userid=" + userid + " AND a.roleid=b.roleid AND (b.unitid=0 OR b.unitid=" + unitid + ")";
		}else {
			sql = "SELECT a FROM SysUserRole as a, SysRoleInfo as b WHERE a.sysUserInfo.userid=" + userid + " AND a.roleid=b.roleid AND b.unitid=" + unitid + "";
		}
		sql = sql + " ORDER BY b.roleno";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取所有用户所关联的模块id
	 * @param userid
	 * @return List
	 */
	public List getAllModuleids(Integer userid) {
		String sql = "SELECT DISTINCT a.sysModuleInfo.moduleid FROM SysRoleModule as a, SysUserRole as b WHERE a.roleid=b.roleid and b.sysUserInfo.userid=" + userid;
		return baseDAO.getObjects(sql);
	}
}