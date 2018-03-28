package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassPassword;
import com.wkmk.tk.service.TkClassPasswordManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级密码</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassPasswordManagerImpl implements TkClassPasswordManager{

	private BaseDAO baseDAO;
	private String modelname = "班级密码";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword getTkClassPassword(String passwordid){
		Integer iid = Integer.valueOf(passwordid);
		return  (TkClassPassword)baseDAO.getObject(modelname,TkClassPassword.class,iid);
	}

	/**
	 *根据id获取班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword getTkClassPassword(Integer passwordid){
		return  (TkClassPassword)baseDAO.getObject(modelname,TkClassPassword.class,passwordid);
	}

	/**
	 *增加班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword addTkClassPassword(TkClassPassword tkClassPassword){
		return (TkClassPassword)baseDAO.addObject(modelname,tkClassPassword);
	}

	/**
	 *删除班级密码
	 *@param passwordid Integer
	 *@return TkClassPassword
	 */
	public TkClassPassword delTkClassPassword(String passwordid){
		TkClassPassword model = getTkClassPassword(passwordid);
		return (TkClassPassword)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword delTkClassPassword(TkClassPassword tkClassPassword){
		return (TkClassPassword)baseDAO.delObject(modelname,tkClassPassword);
	}

	/**
	 *修改班级密码
	 *@param tkClassPassword TkClassPassword
	 *@return TkClassPassword
	 */
	public TkClassPassword updateTkClassPassword(TkClassPassword tkClassPassword){
		return (TkClassPassword)baseDAO.updateObject(modelname,tkClassPassword);
	}

	/**
	 *获取班级密码集合
 	 *@return List
	 */
	public List getTkClassPasswords(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassPassword",condition,orderby,pagesize);
	}

	/**
	 *获取班级密码
 	 *@return List
	 */
	public TkClassPassword getTkClassPassword(String password, String classid){
		String sql = "select a from TkClassPassword as a where a.password='" + password + "' and a.classid=" + classid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (TkClassPassword) list.get(0);
		}else {
			 return null;
		}
	}
	
	/**
	 *删除班级密码
 	 *@return List
	 */
	public void delTkClassPasswordByClassid(String classid){
		String sql = "select a from TkClassPassword as a where a.classid=" + classid;
		baseDAO.delObjects(sql);
	}
	
	/**
	 *获取一页班级密码集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassPasswords (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassPassword","passwordid",condition, orderby, start,pagesize);
	}

}