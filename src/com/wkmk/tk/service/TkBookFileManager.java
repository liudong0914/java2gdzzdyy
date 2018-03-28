package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookFileManager {
	/**
	 *根据id获取作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile getTkBookFile(String fileid);

	/**
	 *根据id获取作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile getTkBookFile(Integer fileid);

	/**
	 *增加作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile addTkBookFile(TkBookFile tkBookFile);

	/**
	 *删除作业本附件
	 *@param fileid Integer
	 *@return TkBookFile
	 */
	public TkBookFile delTkBookFile(String fileid);

	/**
	 *删除作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile delTkBookFile(TkBookFile tkBookFile);

	/**
	 *修改作业本附件
	 *@param tkBookFile TkBookFile
	 *@return TkBookFile
	 */
	public TkBookFile updateTkBookFile(TkBookFile tkBookFile);

	/**
	 *获取作业本附件集合
	 *@return List
	 */
	public List getTkBookFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页作业本附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookFiles (List<SearchModel> condition, String orderby, int start, int pagesize);

}