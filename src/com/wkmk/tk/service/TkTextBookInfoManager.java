package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkTextBookInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkTextBookInfoManager {
	/**
	 *根据id获取教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo getTkTextBookInfo(String textbookid);

	/**
	 *根据id获取教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo getTkTextBookInfo(Integer textbookid);

	/**
	 *增加教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo addTkTextBookInfo(TkTextBookInfo tkTextBookInfo);

	/**
	 *删除教材基本信息表
	 *@param textbookid Integer
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo delTkTextBookInfo(String textbookid);

	/**
	 *删除教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo delTkTextBookInfo(TkTextBookInfo tkTextBookInfo);

	/**
	 *修改教材基本信息表
	 *@param tkTextBookInfo TkTextBookInfo
	 *@return TkTextBookInfo
	 */
	public TkTextBookInfo updateTkTextBookInfo(TkTextBookInfo tkTextBookInfo);

	/**
	 *获取教材基本信息表集合
	 *@return List
	 */
	public List getTkTextBookInfos (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页教材基本信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkTextBookInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

}