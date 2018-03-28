package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkQuestionManager {
	/**
	 *根据id获取在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion getZxHomeworkQuestion(String questionid);

	/**
	 *根据id获取在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion getZxHomeworkQuestion(Integer questionid);

	/**
	 *增加在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion addZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion);

	/**
	 *删除在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion delZxHomeworkQuestion(String questionid);

	/**
	 *删除在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion delZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion);

	/**
	 *修改在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion updateZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion);

	/**
	 *获取在线作业提问集合
	 *@return List
	 */
	public List getZxHomeworkQuestions (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业提问集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkQuestions (List<SearchModel> condition, String orderby, int start, int pagesize);

}