package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookFileDownload;
import com.wkmk.tk.service.TkBookFileDownloadManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本附件下载详情</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookFileDownloadManagerImpl implements TkBookFileDownloadManager{

	private BaseDAO baseDAO;
	private String modelname = "作业本附件下载详情";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload getTkBookFileDownload(String downloadid){
		Integer iid = Integer.valueOf(downloadid);
		return  (TkBookFileDownload)baseDAO.getObject(modelname,TkBookFileDownload.class,iid);
	}

	/**
	 *根据id获取作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload getTkBookFileDownload(Integer downloadid){
		return  (TkBookFileDownload)baseDAO.getObject(modelname,TkBookFileDownload.class,downloadid);
	}

	/**
	 *增加作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload addTkBookFileDownload(TkBookFileDownload tkBookFileDownload){
		return (TkBookFileDownload)baseDAO.addObject(modelname,tkBookFileDownload);
	}

	/**
	 *删除作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload delTkBookFileDownload(String downloadid){
		TkBookFileDownload model = getTkBookFileDownload(downloadid);
		return (TkBookFileDownload)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload delTkBookFileDownload(TkBookFileDownload tkBookFileDownload){
		return (TkBookFileDownload)baseDAO.delObject(modelname,tkBookFileDownload);
	}

	/**
	 *修改作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload updateTkBookFileDownload(TkBookFileDownload tkBookFileDownload){
		return (TkBookFileDownload)baseDAO.updateObject(modelname,tkBookFileDownload);
	}

	/**
	 *获取作业本附件下载详情集合
 	 *@return List
	 */
	public List getTkBookFileDownloads(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookFileDownload",condition,orderby,pagesize);
	}

	/**
	 *获取一页作业本附件下载详情集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookFileDownloads (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookFileDownload","downloadid",condition, orderby, start,pagesize);
	}

}