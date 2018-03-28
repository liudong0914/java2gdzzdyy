package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperFileDownload;
import com.wkmk.tk.service.TkPaperFileDownloadManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件下载</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperFileDownloadManagerImpl implements TkPaperFileDownloadManager{

	private BaseDAO baseDAO;
	private String modelname = "试卷附件下载";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload getTkPaperFileDownload(String downloadid){
		Integer iid = Integer.valueOf(downloadid);
		return  (TkPaperFileDownload)baseDAO.getObject(modelname,TkPaperFileDownload.class,iid);
	}

	/**
	 *根据id获取试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload getTkPaperFileDownload(Integer downloadid){
		return  (TkPaperFileDownload)baseDAO.getObject(modelname,TkPaperFileDownload.class,downloadid);
	}

	/**
	 *增加试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload addTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload){
		return (TkPaperFileDownload)baseDAO.addObject(modelname,tkPaperFileDownload);
	}

	/**
	 *删除试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload delTkPaperFileDownload(String downloadid){
		TkPaperFileDownload model = getTkPaperFileDownload(downloadid);
		return (TkPaperFileDownload)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload delTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload){
		return (TkPaperFileDownload)baseDAO.delObject(modelname,tkPaperFileDownload);
	}

	/**
	 *修改试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload updateTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload){
		return (TkPaperFileDownload)baseDAO.updateObject(modelname,tkPaperFileDownload);
	}

	/**
	 *获取试卷附件下载集合
 	 *@return List
	 */
	public List getTkPaperFileDownloads(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkPaperFileDownload",condition,orderby,pagesize);
	}

	/**
	 *获取一页试卷附件下载集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFileDownloads (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkPaperFileDownload","downloadid",condition, orderby, start,pagesize);
	}

}