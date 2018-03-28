package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperFileDownload;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件下载</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkPaperFileDownloadManager {
	/**
	 *根据id获取试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload getTkPaperFileDownload(String downloadid);

	/**
	 *根据id获取试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload getTkPaperFileDownload(Integer downloadid);

	/**
	 *增加试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload addTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload);

	/**
	 *删除试卷附件下载
	 *@param downloadid Integer
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload delTkPaperFileDownload(String downloadid);

	/**
	 *删除试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload delTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload);

	/**
	 *修改试卷附件下载
	 *@param tkPaperFileDownload TkPaperFileDownload
	 *@return TkPaperFileDownload
	 */
	public TkPaperFileDownload updateTkPaperFileDownload(TkPaperFileDownload tkPaperFileDownload);

	/**
	 *获取试卷附件下载集合
	 *@return List
	 */
	public List getTkPaperFileDownloads (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页试卷附件下载集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFileDownloads (List<SearchModel> condition, String orderby, int start, int pagesize);

}