package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionComment;
import com.wkmk.zx.service.ZxHelpQuestionCommentManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑评论</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionCommentManagerImpl implements ZxHelpQuestionCommentManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑评论";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment getZxHelpQuestionComment(String commentid){
		Integer iid = Integer.valueOf(commentid);
		return  (ZxHelpQuestionComment)baseDAO.getObject(modelname,ZxHelpQuestionComment.class,iid);
	}

	/**
	 *根据id获取在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment getZxHelpQuestionComment(Integer commentid){
		return  (ZxHelpQuestionComment)baseDAO.getObject(modelname,ZxHelpQuestionComment.class,commentid);
	}

	/**
	 *增加在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment addZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment){
		return (ZxHelpQuestionComment)baseDAO.addObject(modelname,zxHelpQuestionComment);
	}

	/**
	 *删除在线答疑评论
	 *@param commentid Integer
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment delZxHelpQuestionComment(String commentid){
		ZxHelpQuestionComment model = getZxHelpQuestionComment(commentid);
		return (ZxHelpQuestionComment)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment delZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment){
		return (ZxHelpQuestionComment)baseDAO.delObject(modelname,zxHelpQuestionComment);
	}

	/**
	 *修改在线答疑评论
	 *@param zxHelpQuestionComment ZxHelpQuestionComment
	 *@return ZxHelpQuestionComment
	 */
	public ZxHelpQuestionComment updateZxHelpQuestionComment(ZxHelpQuestionComment zxHelpQuestionComment){
		return (ZxHelpQuestionComment)baseDAO.updateObject(modelname,zxHelpQuestionComment);
	}

	/**
	 *获取在线答疑评论集合
 	 *@return List
	 */
	public List getZxHelpQuestionComments(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpQuestionComment",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线答疑评分集合
 	 *@return List
	 */
	public List getZxHelpQuestionCommentScore(String questionid){
		String sql = "select a.score from ZxHelpQuestionComment as a where a.status='1' and a.questionid=" + questionid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页在线答疑评论集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionComments (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpQuestionComment","commentid",condition, orderby, start,pagesize);
	}

}