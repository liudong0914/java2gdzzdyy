package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysAutomationCache;
import com.wkmk.sys.service.SysAutomationCacheManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统自动化高速缓存</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAutomationCacheManagerImpl implements SysAutomationCacheManager{

	private BaseDAO baseDAO;
	private String modelname = "系统自动化高速缓存";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCache(String cacheid){
		Integer iid = Integer.valueOf(cacheid);
		return  (SysAutomationCache)baseDAO.getObject(modelname,SysAutomationCache.class,iid);
	}

	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCache(Integer cacheid){
		return  (SysAutomationCache)baseDAO.getObject(modelname,SysAutomationCache.class,cacheid);
	}
	
	/**
	 *根据id获取系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache getSysAutomationCacheByKeyid(String keyid){
		String sql = "select a from SysAutomationCache as where a.keyid='" + keyid + "' order by a.createdate desc";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysAutomationCache) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *增加系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache addSysAutomationCache(SysAutomationCache sysAutomationCache){
		return (SysAutomationCache)baseDAO.addObject(modelname,sysAutomationCache);
	}

	/**
	 *删除系统自动化高速缓存
	 *@param cacheid Integer
	 *@return SysAutomationCache
	 */
	public SysAutomationCache delSysAutomationCache(String cacheid){
		SysAutomationCache model = getSysAutomationCache(cacheid);
		return (SysAutomationCache)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache delSysAutomationCache(SysAutomationCache sysAutomationCache){
		return (SysAutomationCache)baseDAO.delObject(modelname,sysAutomationCache);
	}
	
	/**
	 *删除系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public void delSysAutomationCacheByKeyid(String keyid){
		String sql = "select a from SysAutomationCache as where a.keyid='" + keyid + "'";
		baseDAO.delObjects(sql);
	}

	/**
	 *修改系统自动化高速缓存
	 *@param sysAutomationCache SysAutomationCache
	 *@return SysAutomationCache
	 */
	public SysAutomationCache updateSysAutomationCache(SysAutomationCache sysAutomationCache){
		return (SysAutomationCache)baseDAO.updateObject(modelname,sysAutomationCache);
	}

	/**
	 *获取系统自动化高速缓存集合
 	 *@return List
	 */
	public List getSysAutomationCaches(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysAutomationCache",condition,orderby,pagesize);
	}
	
	/**
	 *获取系统自动化高速缓存集合
 	 *@return List
	 */
	public List getSysAutomationCaches(){
		String sql = "select a from SysAutomationCache as a";
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页系统自动化高速缓存集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysAutomationCaches (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysAutomationCache","cacheid",condition, orderby, start,pagesize);
	}

}