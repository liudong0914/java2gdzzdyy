package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpFileManager {
	/**
	 *根据id获取在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile getZxHelpFile(String fileid);

	/**
	 *根据id获取在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile getZxHelpFile(Integer fileid);

	/**
	 *增加在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile addZxHelpFile(ZxHelpFile zxHelpFile);

	/**
	 *删除在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile delZxHelpFile(String fileid);

	/**
	 *删除在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile delZxHelpFile(ZxHelpFile zxHelpFile);

	/**
	 *修改在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile updateZxHelpFile(ZxHelpFile zxHelpFile);

	/**
	 *获取在线答疑附件集合
	 *@return List
	 */
	public List getZxHelpFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线答疑附件集合
 	 *@return List
	 */
	public List getZxHelpFilesByQuestionid(String questionid);
	
	/**
	 *获取在线答疑附件集合
 	 *@return List
	 */
	public List getZxHelpFilesByAnswerid(String answerid);
	
	/**
	 *获取一页在线答疑附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpFiles (List<SearchModel> condition, String orderby, int start, int pagesize);

}