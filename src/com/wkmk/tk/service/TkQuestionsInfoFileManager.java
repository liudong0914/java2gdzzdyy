package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsInfoFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 题库碎片化附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkQuestionsInfoFileManager {
	/**
	 *根据id获取题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile getTkQuestionsInfoFile(String fileid);

	/**
	 *根据id获取题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile getTkQuestionsInfoFile(Integer fileid);

	/**
	 *增加题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile addTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile);

	/**
	 *删除题库碎片化附件
	 *@param fileid Integer
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile delTkQuestionsInfoFile(String fileid);

	/**
	 *删除题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile delTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile);

	/**
	 *修改题库碎片化附件
	 *@param tkQuestionsInfoFile TkQuestionsInfoFile
	 *@return TkQuestionsInfoFile
	 */
	public TkQuestionsInfoFile updateTkQuestionsInfoFile(TkQuestionsInfoFile tkQuestionsInfoFile);

	/**
	 *获取题库碎片化附件集合
	 *@return List
	 */
	public List getTkQuestionsInfoFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页题库碎片化附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkQuestionsInfoFiles (List<SearchModel> condition, String orderby, int start, int pagesize);

}