package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookFile;
import com.wkmk.tk.service.TkBookFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookFileManagerImpl implements TkBookFileManager{

	private BaseDAO baseDAO;
	private String modelname = "作业本附件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile getTkBookFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (TkBookFile)baseDAO.getObject(modelname,TkBookFile.class,iid);
	}

	/**
	 *根据id获取作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile getTkBookFile(Integer fileid){
		return  (TkBookFile)baseDAO.getObject(modelname,TkBookFile.class,fileid);
	}

	/**
	 *增加作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile addTkBookFile(TkBookFile tkBookFile){
		return (TkBookFile)baseDAO.addObject(modelname,tkBookFile);
	}

	/**
	 *删除作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile delTkBookFile(String fileid){
		TkBookFile model = getTkBookFile(fileid);
		return (TkBookFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile delTkBookFile(TkBookFile tkBookFile){
		return (TkBookFile)baseDAO.delObject(modelname,tkBookFile);
	}

	/**
	 *修改作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile updateTkBookFile(TkBookFile tkBookFile){
		return (TkBookFile)baseDAO.updateObject(modelname,tkBookFile);
	}

	/**
	 *获取作业本附件集合
 	 *@return List
	 */
	public List getTkBookFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookFile",condition,orderby,pagesize);
	}

	/**
	 *获取一页作业本附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookFile","fileid",condition, orderby, start,pagesize);
	}

}