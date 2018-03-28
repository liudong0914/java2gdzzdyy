package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysKeywordFilter;
import com.wkmk.sys.service.SysKeywordFilterManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统关键字过滤</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysKeywordFilterManagerImpl implements SysKeywordFilterManager{

	private BaseDAO baseDAO;
	private String modelname = "系统关键字过滤";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter getSysKeywordFilter(String keywordid){
		Integer iid = Integer.valueOf(keywordid);
		return  (SysKeywordFilter)baseDAO.getObject(modelname,SysKeywordFilter.class,iid);
	}

	/**
	 *根据id获取系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter getSysKeywordFilter(Integer keywordid){
		return  (SysKeywordFilter)baseDAO.getObject(modelname,SysKeywordFilter.class,keywordid);
	}

	/**
	 *增加系统关键字过滤
	 *@param sysKeywordFilter SysKeywordFilter
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter addSysKeywordFilter(SysKeywordFilter sysKeywordFilter){
		return (SysKeywordFilter)baseDAO.addObject(modelname,sysKeywordFilter);
	}

	/**
	 *删除系统关键字过滤
	 *@param keywordid Integer
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter delSysKeywordFilter(String keywordid){
		SysKeywordFilter model = getSysKeywordFilter(keywordid);
		return (SysKeywordFilter)baseDAO.delObject(modelname,model);
	}

	/**
	 *修改系统关键字过滤
	 *@param sysKeywordFilter SysKeywordFilter
	 *@return SysKeywordFilter
	 */
	public SysKeywordFilter updateSysKeywordFilter(SysKeywordFilter sysKeywordFilter){
		return (SysKeywordFilter)baseDAO.updateObject(modelname,sysKeywordFilter);
	}

	/**
	 *获取系统关键字过滤集合
 	 *@return List
	 */
	public List getSysKeywordFilters(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysKeywordFilter",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统关键字过滤集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysKeywordFilters (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysKeywordFilter","keywordid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取某单位的关键字过滤器
	 * @param product
	 * @param unitid
	 * @return
	 */
	public SysKeywordFilter getSysKeywordFilterByUnitid(Integer unitid){
		String sql = "SELECT a FROM SysKeywordFilter as a WHERE a.unitid=" + unitid;
		List lst = baseDAO.getObjects(sql);
		if(lst != null && lst.size() > 0){
			return (SysKeywordFilter) lst.get(0);
		}else {
			return null;
		}
	}

}