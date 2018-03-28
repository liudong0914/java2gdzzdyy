package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassGroupUser;
import com.wkmk.tk.service.TkClassGroupUserManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级组成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassGroupUserManagerImpl implements TkClassGroupUserManager{

	private BaseDAO baseDAO;
	private String modelname = "班级组成员";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser getTkClassGroupUser(String groupuserid){
		Integer iid = Integer.valueOf(groupuserid);
		return  (TkClassGroupUser)baseDAO.getObject(modelname,TkClassGroupUser.class,iid);
	}

	/**
	 *根据id获取班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser getTkClassGroupUser(Integer groupuserid){
		return  (TkClassGroupUser)baseDAO.getObject(modelname,TkClassGroupUser.class,groupuserid);
	}

	/**
	 *增加班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser addTkClassGroupUser(TkClassGroupUser tkClassGroupUser){
		return (TkClassGroupUser)baseDAO.addObject(modelname,tkClassGroupUser);
	}

	/**
	 *删除班级组成员
	 *@param groupuserid Integer
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser delTkClassGroupUser(String groupuserid){
		TkClassGroupUser model = getTkClassGroupUser(groupuserid);
		return (TkClassGroupUser)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser delTkClassGroupUser(TkClassGroupUser tkClassGroupUser){
		return (TkClassGroupUser)baseDAO.delObject(modelname,tkClassGroupUser);
	}

	/**
	 *修改班级组成员
	 *@param tkClassGroupUser TkClassGroupUser
	 *@return TkClassGroupUser
	 */
	public TkClassGroupUser updateTkClassGroupUser(TkClassGroupUser tkClassGroupUser){
		return (TkClassGroupUser)baseDAO.updateObject(modelname,tkClassGroupUser);
	}

	/**
	 *获取班级组成员集合
 	 *@return List
	 */
	public List getTkClassGroupUsers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassGroupUser",condition,orderby,pagesize);
	}

	/**
	 *获取一页班级组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassGroupUsers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassGroupUser","groupuserid",condition, orderby, start,pagesize);
	}

}