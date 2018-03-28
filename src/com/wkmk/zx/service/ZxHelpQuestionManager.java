package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpQuestionManager {
	/**
	 *根据id获取在线答疑提问
	 *@param questionid Integer
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion getZxHelpQuestion(String questionid);

	/**
	 *根据id获取在线答疑提问
	 *@param questionid Integer
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion getZxHelpQuestion(Integer questionid);

	/**
	 *增加在线答疑提问
	 *@param zxHelpQuestion ZxHelpQuestion
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion addZxHelpQuestion(ZxHelpQuestion zxHelpQuestion);

	/**
	 *删除在线答疑提问
	 *@param questionid Integer
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion delZxHelpQuestion(String questionid);

	/**
	 *删除在线答疑提问
	 *@param zxHelpQuestion ZxHelpQuestion
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion delZxHelpQuestion(ZxHelpQuestion zxHelpQuestion);

	/**
	 *修改在线答疑提问
	 *@param zxHelpQuestion ZxHelpQuestion
	 *@return ZxHelpQuestion
	 */
	public ZxHelpQuestion updateZxHelpQuestion(ZxHelpQuestion zxHelpQuestion);

	/**
	 *获取在线答疑提问集合
	 *@return List
	 */
	public List getZxHelpQuestions (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取已购买答疑
 	 *@return List
	 */
	public PageList alreadyBuyQuestion(List<SearchModel> condition,String orderby, int start, int pagesize, String userid);
	
	/**
	 *学生-->获取我的投诉
 	 *@return List
	 */
	public PageList studentComplaintQuestion(List<SearchModel> condition,String orderby, int start,int pagesize, String userid);
	
	/**
	 *教师-->获取我的回复
 	 *@return List
	 */
	public PageList myReply(List<SearchModel> condition,String orderby, int start,int pagesize,String userid);
	
	/**
	 *教师-->获取我的投诉
 	 *@return List
	 */
	public PageList teacherComplaintQuestion(List<SearchModel> condition,String orderby, int start,int pagesize, String userid);
	
	/**
	 *获取在线答疑提问学科id集合
 	 *@return List
	 */
	public List getAllSubjectidByUserid(String userid);
	
	/**
	 *获取在线答疑提问子学段id集合
 	 *@return List
	 */
	public List getAllCxueduanidByUserid(String userid);
	
	/**
	 *获取一页在线答疑提问集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestions (List<SearchModel> condition, String orderby, int start, int pagesize);

}