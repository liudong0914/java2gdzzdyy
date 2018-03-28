package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduBookInfo;
import com.wkmk.edu.service.EduBookInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材课本</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduBookInfoManagerImpl implements EduBookInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "教材课本";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo getEduBookInfo(String bookid){
		Integer iid = Integer.valueOf(bookid);
		return  (EduBookInfo)baseDAO.getObject(modelname,EduBookInfo.class,iid);
	}

	/**
	 *根据id获取教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo getEduBookInfo(Integer bookid){
		return  (EduBookInfo)baseDAO.getObject(modelname,EduBookInfo.class,bookid);
	}

	/**
	 *增加教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo addEduBookInfo(EduBookInfo eduBookInfo){
		return (EduBookInfo)baseDAO.addObject(modelname,eduBookInfo);
	}

	/**
	 *删除教材课本
	 *@param bookid Integer
	 *@return EduBookInfo
	 */
	public EduBookInfo delEduBookInfo(String bookid){
		EduBookInfo model = getEduBookInfo(bookid);
		return (EduBookInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo delEduBookInfo(EduBookInfo eduBookInfo){
		return (EduBookInfo)baseDAO.delObject(modelname,eduBookInfo);
	}

	/**
	 *修改教材课本
	 *@param eduBookInfo EduBookInfo
	 *@return EduBookInfo
	 */
	public EduBookInfo updateEduBookInfo(EduBookInfo eduBookInfo){
		return (EduBookInfo)baseDAO.updateObject(modelname,eduBookInfo);
	}

	/**
	 *获取教材课本集合
 	 *@return List
	 */
	public List getEduBookInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduBookInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页教材课本集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduBookInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduBookInfo","bookid",condition, orderby, start,pagesize);
	}

}