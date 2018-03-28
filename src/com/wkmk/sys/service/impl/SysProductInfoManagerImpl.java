package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysProductInfo;
import com.wkmk.sys.service.SysProductInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统产品信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysProductInfoManagerImpl implements SysProductInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统产品信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo getSysProductInfo(String productid){
		Integer iid = Integer.valueOf(productid);
		return  (SysProductInfo)baseDAO.getObject(modelname,SysProductInfo.class,iid);
	}

	/**
	 *根据id获取系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo getSysProductInfo(Integer productid){
		return  (SysProductInfo)baseDAO.getObject(modelname,SysProductInfo.class,productid);
	}

	/**
	 *增加系统产品信息
	 *@param sysProductInfo SysProductInfo
	 *@return SysProductInfo
	 */
	public SysProductInfo addSysProductInfo(SysProductInfo sysProductInfo){
		return (SysProductInfo)baseDAO.addObject(modelname,sysProductInfo);
	}

	/**
	 *删除系统产品信息
	 *@param productid Integer
	 *@return SysProductInfo
	 */
	public SysProductInfo delSysProductInfo(String productid){
		SysProductInfo model = getSysProductInfo(productid);
		return (SysProductInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *修改系统产品信息
	 *@param sysProductInfo SysProductInfo
	 *@return SysProductInfo
	 */
	public SysProductInfo updateSysProductInfo(SysProductInfo sysProductInfo){
		return (SysProductInfo)baseDAO.updateObject(modelname,sysProductInfo);
	}

	/**
	 *获取系统产品信息集合
 	 *@return List
	 */
	public List getSysProductInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysProductInfo",condition,orderby,pagesize);
	}
	
	/**
	 *获取用户关联产品
 	 *@return List
	 */
	public List getUserProducts(Integer userid){
		String sql = "select distinct a from SysProductInfo as a, SysRoleModule as b, SysUserRole as c where a.status='1' and a.productid=b.sysModuleInfo.productid and b.roleid=c.roleid and c.sysUserInfo.userid=" + userid + " order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页系统产品信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysProductInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysProductInfo","productid",condition, orderby, start,pagesize);
	}

}