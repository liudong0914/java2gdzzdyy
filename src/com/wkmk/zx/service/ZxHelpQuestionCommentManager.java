package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionComment;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑评论</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpQuestionCommentManager {
	/**
	 *根据id获取在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment getZxHelpQuestionComment(String commentid);

	/**
	 *根据id获取在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment getZxHelpQuestionComment(Integer commentid);

	/**
	 *增加在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment addZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment);

	/**
	 *删除在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment delZxHelpQuestionComment(String commentid);

	/**
	 *删除在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment delZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment);

	/**
	 *修改在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment updateZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment);

	/**
	 *获取在线答疑评论集合
	 *@return List
	 */
	public List getZxHelpQuestionComments (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线答疑评分集合
 	 *@return List
	 */
	public List getZxHelpQuestionCommentScore(String questionid);
	
	/**
	 *获取一页在线答疑评论集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionComments (List<SearchModel> condition, String orderby, int start, int pagesize);

}