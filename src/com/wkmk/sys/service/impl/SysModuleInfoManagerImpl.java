package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysModuleInfo;
import com.wkmk.sys.service.SysModuleInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统模块信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysModuleInfoManagerImpl implements SysModuleInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统模块信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo getSysModuleInfo(String moduleid){
		Integer iid = Integer.valueOf(moduleid);
		return  (SysModuleInfo)baseDAO.getObject(modelname,SysModuleInfo.class,iid);
	}

	/**
	 *根据id获取系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo getSysModuleInfo(Integer moduleid){
		return  (SysModuleInfo)baseDAO.getObject(modelname,SysModuleInfo.class,moduleid);
	}

	/**
	 *增加系统模块信息
	 *@param sysModuleInfo SysModuleInfo
	 *@return SysModuleInfo
	 */
	public SysModuleInfo addSysModuleInfo(SysModuleInfo sysModuleInfo){
		return (SysModuleInfo)baseDAO.addObject(modelname,sysModuleInfo);
	}

	/**
	 *删除系统模块信息
	 *@param moduleid Integer
	 *@return SysModuleInfo
	 */
	public SysModuleInfo delSysModuleInfo(String moduleid){
		SysModuleInfo model = getSysModuleInfo(moduleid);
		return (SysModuleInfo)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统模块信息
	 *@param SysModuleInfo sysModuleInfo
	 *@return SysModuleInfo
	 */
	public SysModuleInfo delSysModuleInfo(SysModuleInfo sysModuleInfo){
		return (SysModuleInfo)baseDAO.delObject(modelname,sysModuleInfo);
	}

	/**
	 *修改系统模块信息
	 *@param sysModuleInfo SysModuleInfo
	 *@return SysModuleInfo
	 */
	public SysModuleInfo updateSysModuleInfo(SysModuleInfo sysModuleInfo){
		return (SysModuleInfo)baseDAO.updateObject(modelname,sysModuleInfo);
	}

	/**
	 *获取系统模块信息集合
 	 *@return List
	 */
	public List getSysModuleInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysModuleInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统模块信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysModuleInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysModuleInfo","moduleid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取系统当前用户在当前单位当前产品所有的模块集合
	 * @return List
	 */
	public List getPermissionModuleList(Integer userid, Integer unitid, Integer productid) {
		String sql = "SELECT DISTINCT c.sysModuleInfo FROM SysUserRole as a, SysRoleInfo as b , SysRoleModule as c WHERE a.sysUserInfo.userid=" + userid + " AND a.roleid=b.roleid AND b.status='1' AND (b.unitid=0 OR b.unitid=" + unitid + ") AND b.roleid=c.roleid";
		if(productid != null && productid != 0){
			sql = sql + " AND c.sysModuleInfo.productid=" + productid;
		}
		sql = sql + " AND c.sysModuleInfo.status='1' ORDER BY c.sysModuleInfo.moduleno asc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取一级菜单模块集合
	 * @return List
	 */
	public List getAllParentModuleList(Integer productid) {
		String sql = "";
		if(productid != null && productid != 0){
			sql = "SELECT a FROM SysModuleInfo as a WHERE a.status='1' AND a.parentno='0000' AND a.productid=" + productid + " ORDER BY a.moduleno asc";
		}else {
			sql = "SELECT a FROM SysModuleInfo as a, SysProductInfo as b WHERE a.productid=b.productid AND a.status='1' AND a.parentno='0000' ORDER BY b.orderindex asc, a.moduleno asc";
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取当前单位所有父模块编号
	 * @return List
	 */
	public List getAllParentnoByProduct(Integer productid) {
		String sql = "SELECT DISTINCT a.parentno FROM SysModuleInfo as a WHERE 1=1";
		if(productid != null && productid != 0){
			sql = sql + " AND a.productid=" + productid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取没有子模块的模块id
	 * @return List
	 */
	public List getNoSubModuleids(Integer productid) {
		String sql = null;
		if(productid != null && productid != 0){
			sql = "SELECT a.moduleid FROM SysModuleInfo as a WHERE a.productid=" + productid +" AND a.moduleno NOT IN (SELECT DISTINCT b.parentno FROM SysModuleInfo as b WHERE b.productid=" + productid +")";
		}else {
			sql = "SELECT a.moduleid FROM SysModuleInfo as a WHERE a.moduleno NOT IN (SELECT DISTINCT b.parentno FROM SysModuleInfo as b )";
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取所有菜单模块集合
	 * @return List
	 */
	public List getAllModuleList(Integer productid) {
		String sql = "";
		if(productid != null && productid != 0){
			sql = "SELECT a FROM SysModuleInfo as a WHERE a.status='1' AND a.productid=" + productid + " ORDER BY a.moduleno asc";
		}else {
			sql = "SELECT a FROM SysModuleInfo as a, SysProductInfo as b WHERE a.productid=b.productid AND a.status='1' ORDER BY b.orderindex asc, a.moduleno asc";
		}
		return baseDAO.getObjects(sql);
	}
}