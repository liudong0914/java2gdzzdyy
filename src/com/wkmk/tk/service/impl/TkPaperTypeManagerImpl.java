package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperType;
import com.wkmk.tk.service.TkPaperTypeManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件分类</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperTypeManagerImpl implements TkPaperTypeManager{

	private BaseDAO baseDAO;
	private String modelname = "试卷附件分类";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType getTkPaperType(String typeid){
		Integer iid = Integer.valueOf(typeid);
		return  (TkPaperType)baseDAO.getObject(modelname,TkPaperType.class,iid);
	}

	/**
	 *根据id获取试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType getTkPaperType(Integer typeid){
		return  (TkPaperType)baseDAO.getObject(modelname,TkPaperType.class,typeid);
	}

	/**
	 *增加试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType addTkPaperType(TkPaperType tkPaperType){
		return (TkPaperType)baseDAO.addObject(modelname,tkPaperType);
	}

	/**
	 *删除试卷附件分类
	 *@param typeid Integer
	 *@return TkPaperType
	 */
	public TkPaperType delTkPaperType(String typeid){
		TkPaperType model = getTkPaperType(typeid);
		return (TkPaperType)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType delTkPaperType(TkPaperType tkPaperType){
		return (TkPaperType)baseDAO.delObject(modelname,tkPaperType);
	}

	/**
	 *修改试卷附件分类
	 *@param tkPaperType TkPaperType
	 *@return TkPaperType
	 */
	public TkPaperType updateTkPaperType(TkPaperType tkPaperType){
		return (TkPaperType)baseDAO.updateObject(modelname,tkPaperType);
	}

	/**
	 *获取试卷附件分类集合
 	 *@return List
	 */
	public List getTkPaperTypes(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkPaperType",condition,orderby,pagesize);
	}

	/**
	 *获取一页试卷附件分类集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperTypes (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkPaperType","typeid",condition, orderby, start,pagesize);
	}

}