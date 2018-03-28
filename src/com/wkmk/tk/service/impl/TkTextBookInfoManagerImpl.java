package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkTextBookInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookInfoManagerImpl implements TkTextBookInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "教材基本信息表";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo getTkTextBookInfo(String textbookid){
		Integer iid = Integer.valueOf(textbookid);
		return  (TkTextBookInfo)baseDAO.getObject(modelname,TkTextBookInfo.class,iid);
	}

	/**
	 *根据id获取教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo getTkTextBookInfo(Integer textbookid){
		return  (TkTextBookInfo)baseDAO.getObject(modelname,TkTextBookInfo.class,textbookid);
	}

	/**
	 *增加教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo addTkTextBookInfo(TkTextBookInfo tkTextBookInfo){
		return (TkTextBookInfo)baseDAO.addObject(modelname,tkTextBookInfo);
	}

	/**
	 *删除教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo delTkTextBookInfo(String textbookid){
		TkTextBookInfo model = getTkTextBookInfo(textbookid);
		return (TkTextBookInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo delTkTextBookInfo(TkTextBookInfo tkTextBookInfo){
		return (TkTextBookInfo)baseDAO.delObject(modelname,tkTextBookInfo);
	}

	/**
	 *修改教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo updateTkTextBookInfo(TkTextBookInfo tkTextBookInfo){
		return (TkTextBookInfo)baseDAO.updateObject(modelname,tkTextBookInfo);
	}

	/**
	 *获取教材基本信息表集合
 	 *@return List
	 */
	public List getTkTextBookInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkTextBookInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页教材基本信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkTextBookInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkTextBookInfo","textbookid",condition, orderby, start,pagesize);
	}

}