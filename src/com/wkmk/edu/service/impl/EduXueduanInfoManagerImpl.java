package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduXueduanInfo;
import com.wkmk.edu.service.EduXueduanInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教育学段信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduXueduanInfoManagerImpl implements EduXueduanInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "教育学段信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo getEduXueduanInfo(String xueduanid){
		Integer iid = Integer.valueOf(xueduanid);
		return  (EduXueduanInfo)baseDAO.getObject(modelname,EduXueduanInfo.class,iid);
	}

	/**
	 *根据id获取教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo getEduXueduanInfo(Integer xueduanid){
		return  (EduXueduanInfo)baseDAO.getObject(modelname,EduXueduanInfo.class,xueduanid);
	}

	/**
	 *增加教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo addEduXueduanInfo(EduXueduanInfo eduXueduanInfo){
		return (EduXueduanInfo)baseDAO.addObject(modelname,eduXueduanInfo);
	}

	/**
	 *删除教育学段信息
	 *@param xueduanid Integer
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo delEduXueduanInfo(String xueduanid){
		EduXueduanInfo model = getEduXueduanInfo(xueduanid);
		return (EduXueduanInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo delEduXueduanInfo(EduXueduanInfo eduXueduanInfo){
		return (EduXueduanInfo)baseDAO.delObject(modelname,eduXueduanInfo);
	}

	/**
	 *修改教育学段信息
	 *@param eduXueduanInfo EduXueduanInfo
	 *@return EduXueduanInfo
	 */
	public EduXueduanInfo updateEduXueduanInfo(EduXueduanInfo eduXueduanInfo){
		return (EduXueduanInfo)baseDAO.updateObject(modelname,eduXueduanInfo);
	}

	/**
	 *获取教育学段父id集合
 	 *@return List
	 */
	public List getAllParentids(){
		String sql = "select distinct a.parentid from EduXueduanInfo as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取教育学段信息集合
 	 *@return List
	 */
	public List getEduXueduanInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduXueduanInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页教育学段信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduXueduanInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduXueduanInfo","xueduanid",condition, orderby, start,pagesize);
	}

}