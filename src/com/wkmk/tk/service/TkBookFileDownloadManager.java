package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookFileDownload;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本附件下载详情</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookFileDownloadManager {
	/**
	 *根据id获取作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload getTkBookFileDownload(String downloadid);

	/**
	 *根据id获取作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload getTkBookFileDownload(Integer downloadid);

	/**
	 *增加作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload addTkBookFileDownload(TkBookFileDownload tkBookFileDownload);

	/**
	 *删除作业本附件下载详情
	 *@param downloadid Integer
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload delTkBookFileDownload(String downloadid);

	/**
	 *删除作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload delTkBookFileDownload(TkBookFileDownload tkBookFileDownload);

	/**
	 *修改作业本附件下载详情
	 *@param tkBookFileDownload TkBookFileDownload
	 *@return TkBookFileDownload
	 */
	public TkBookFileDownload updateTkBookFileDownload(TkBookFileDownload tkBookFileDownload);

	/**
	 *获取作业本附件下载详情集合
	 *@return List
	 */
	public List getTkBookFileDownloads (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页作业本附件下载详情集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookFileDownloads (List<SearchModel> condition, String orderby, int start, int pagesize);

}