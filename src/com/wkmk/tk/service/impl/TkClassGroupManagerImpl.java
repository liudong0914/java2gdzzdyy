package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassGroup;
import com.wkmk.tk.service.TkClassGroupManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级分组信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassGroupManagerImpl implements TkClassGroupManager{

	private BaseDAO baseDAO;
	private String modelname = "班级分组信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup getTkClassGroup(String groupid){
		Integer iid = Integer.valueOf(groupid);
		return  (TkClassGroup)baseDAO.getObject(modelname,TkClassGroup.class,iid);
	}

	/**
	 *根据id获取班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup getTkClassGroup(Integer groupid){
		return  (TkClassGroup)baseDAO.getObject(modelname,TkClassGroup.class,groupid);
	}

	/**
	 *增加班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup addTkClassGroup(TkClassGroup tkClassGroup){
		return (TkClassGroup)baseDAO.addObject(modelname,tkClassGroup);
	}

	/**
	 *删除班级分组信息
	 *@param groupid Integer
	 *@return TkClassGroup
	 */
	public TkClassGroup delTkClassGroup(String groupid){
		TkClassGroup model = getTkClassGroup(groupid);
		return (TkClassGroup)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup delTkClassGroup(TkClassGroup tkClassGroup){
		return (TkClassGroup)baseDAO.delObject(modelname,tkClassGroup);
	}

	/**
	 *修改班级分组信息
	 *@param tkClassGroup TkClassGroup
	 *@return TkClassGroup
	 */
	public TkClassGroup updateTkClassGroup(TkClassGroup tkClassGroup){
		return (TkClassGroup)baseDAO.updateObject(modelname,tkClassGroup);
	}

	/**
	 *获取班级分组信息集合
 	 *@return List
	 */
	public List getTkClassGroups(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassGroup",condition,orderby,pagesize);
	}

	/**
	 *获取一页班级分组信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassGroups (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassGroup","groupid",condition, orderby, start,pagesize);
	}

}