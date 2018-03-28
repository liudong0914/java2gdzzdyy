package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkFileManager {
	/**
	 *根据id获取在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile getZxHomeworkFile(String fileid);

	/**
	 *根据id获取在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile getZxHomeworkFile(Integer fileid);

	/**
	 *增加在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile addZxHomeworkFile(ZxHomeworkFile zxHomeworkFile);

	/**
	 *删除在线作业附件
	 *@param fileid Integer
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile delZxHomeworkFile(String fileid);

	/**
	 *删除在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile delZxHomeworkFile(ZxHomeworkFile zxHomeworkFile);

	/**
	 *修改在线作业附件
	 *@param zxHomeworkFile ZxHomeworkFile
	 *@return ZxHomeworkFile
	 */
	public ZxHomeworkFile updateZxHomeworkFile(ZxHomeworkFile zxHomeworkFile);

	/**
	 *获取在线作业附件集合
	 *@return List
	 */
	public List getZxHomeworkFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkFiles (List<SearchModel> condition, String orderby, int start, int pagesize);

}