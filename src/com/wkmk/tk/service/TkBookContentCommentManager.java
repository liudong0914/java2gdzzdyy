package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentComment;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业解题微课评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentCommentManager {
	/**
	 *根据id获取作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment getTkBookContentComment(String commentid);

	/**
	 *根据id获取作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment getTkBookContentComment(Integer commentid);

	/**
	 *增加作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment addTkBookContentComment(TkBookContentComment tkBookContentComment);

	/**
	 *删除作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment delTkBookContentComment(String commentid);

	/**
	 *删除作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment delTkBookContentComment(TkBookContentComment tkBookContentComment);

	/**
	 *修改作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment updateTkBookContentComment(TkBookContentComment tkBookContentComment);

	/**
	 *获取作业解题微课评价集合
	 *@return List
	 */
	public List getTkBookContentComments (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取作业解题微课评分集合
 	 *@return List
	 */
	public List getTkBookContentCommentScore(String bookcontentid, String type);
	
	/**
	 *获取一页作业解题微课评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentComments (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 *获取一页作业解题微课评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentComments1 (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	//获取有评价的作业本集合
	public List getBookListByContentComments();
	//获取有评价的作业本章节集合
	public List getBookContentListByContentComments();
}