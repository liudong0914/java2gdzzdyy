package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserSearchKeywords;
import com.wkmk.sys.service.SysUserSearchKeywordsManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 用户历史搜索关键词</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserSearchKeywordsManagerImpl implements SysUserSearchKeywordsManager{

	private BaseDAO baseDAO;
	private String modelname = "用户历史搜索关键词";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords getSysUserSearchKeywords(String searchid){
		Integer iid = Integer.valueOf(searchid);
		return  (SysUserSearchKeywords)baseDAO.getObject(modelname,SysUserSearchKeywords.class,iid);
	}

	/**
	 *根据id获取用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords getSysUserSearchKeywords(Integer searchid){
		return  (SysUserSearchKeywords)baseDAO.getObject(modelname,SysUserSearchKeywords.class,searchid);
	}

	/**
	 *增加用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords addSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords){
		return (SysUserSearchKeywords)baseDAO.addObject(modelname,sysUserSearchKeywords);
	}

	/**
	 *删除用户历史搜索关键词
	 *@param searchid Integer
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords delSysUserSearchKeywords(String searchid){
		SysUserSearchKeywords model = getSysUserSearchKeywords(searchid);
		return (SysUserSearchKeywords)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords delSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords){
		return (SysUserSearchKeywords)baseDAO.delObject(modelname,sysUserSearchKeywords);
	}

	/**
	 *修改用户历史搜索关键词
	 *@param sysUserSearchKeywords SysUserSearchKeywords
	 *@return SysUserSearchKeywords
	 */
	public SysUserSearchKeywords updateSysUserSearchKeywords(SysUserSearchKeywords sysUserSearchKeywords){
		return (SysUserSearchKeywords)baseDAO.updateObject(modelname,sysUserSearchKeywords);
	}

	/**
	 *获取用户历史搜索关键词集合
 	 *@return List
	 */
	public List getSysUserSearchKeywordss(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserSearchKeywords",condition,orderby,pagesize);
	}
	
	/**
	 *获取用户历史搜索关键词集合
 	 *@return List
	 */
	public List getSysUserSearchKeywordssByHistory(String userid, String searchtyp, String orderby,int pagesize){
		String sql = "select a from SysUserSearchKeywords as a where a.userid=" + userid + " and a.searchtype='" + searchtyp + "' group by a.keywords";
		if(orderby != null && !"".equals(orderby)){
			sql = sql + " order by " + orderby;
		}else {
			sql = sql + " order by a.createdate desc";
		}
		return baseDAO.getObjects(sql, pagesize);
	}

	/**
	 *获取一页用户历史搜索关键词集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserSearchKeywordss (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserSearchKeywords","searchid",condition, orderby, start,pagesize);
	}

}