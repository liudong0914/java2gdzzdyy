package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsInfoFile;
import com.wkmk.tk.service.TkQuestionsInfoFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 题库碎片化附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsInfoFileManagerImpl implements TkQuestionsInfoFileManager{

	private BaseDAO baseDAO;
	private String modelname = "题库碎片化附件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile getTkQuestionsInfoFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (TkQuestionsInfoFile)baseDAO.getObject(modelname,TkQuestionsInfoFile.class,iid);
	}

	/**
	 *根据id获取题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile getTkQuestionsInfoFile(Integer fileid){
		return  (TkQuestionsInfoFile)baseDAO.getObject(modelname,TkQuestionsInfoFile.class,fileid);
	}

	/**
	 *增加题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile addTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile){
		return (TkQuestionsInfoFile)baseDAO.addObject(modelname,tkQuestionsInfoFile);
	}

	/**
	 *删除题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile delTkQuestionsInfoFile(String fileid){
		TkQuestionsInfoFile model = getTkQuestionsInfoFile(fileid);
		return (TkQuestionsInfoFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile delTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile){
		return (TkQuestionsInfoFile)baseDAO.delObject(modelname,tkQuestionsInfoFile);
	}

	/**
	 *修改题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile updateTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile){
		return (TkQuestionsInfoFile)baseDAO.updateObject(modelname,tkQuestionsInfoFile);
	}

	/**
	 *获取题库碎片化附件集合
 	 *@return List
	 */
	public List getTkQuestionsInfoFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkQuestionsInfoFile",condition,orderby,pagesize);
	}

	/**
	 *获取一页题库碎片化附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkQuestionsInfoFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkQuestionsInfoFile","fileid",condition, orderby, start,pagesize);
	}

}