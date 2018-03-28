package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkFile;
import com.wkmk.zx.service.ZxHomeworkFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkFileManagerImpl implements ZxHomeworkFileManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业附件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile getZxHomeworkFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (ZxHomeworkFile)baseDAO.getObject(modelname,ZxHomeworkFile.class,iid);
	}

	/**
	 *根据id获取在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile getZxHomeworkFile(Integer fileid){
		return  (ZxHomeworkFile)baseDAO.getObject(modelname,ZxHomeworkFile.class,fileid);
	}

	/**
	 *增加在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile addZxHomeworkFile(ZxHomeworkFile zxHomeworkFile){
		return (ZxHomeworkFile)baseDAO.addObject(modelname,zxHomeworkFile);
	}

	/**
	 *删除在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile delZxHomeworkFile(String fileid){
		ZxHomeworkFile model = getZxHomeworkFile(fileid);
		return (ZxHomeworkFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile delZxHomeworkFile(ZxHomeworkFile zxHomeworkFile){
		return (ZxHomeworkFile)baseDAO.delObject(modelname,zxHomeworkFile);
	}

	/**
	 *修改在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile updateZxHomeworkFile(ZxHomeworkFile zxHomeworkFile){
		return (ZxHomeworkFile)baseDAO.updateObject(modelname,zxHomeworkFile);
	}

	/**
	 *获取在线作业附件集合
 	 *@return List
	 */
	public List getZxHomeworkFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkFile",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkFile","fileid",condition, orderby, start,pagesize);
	}

}