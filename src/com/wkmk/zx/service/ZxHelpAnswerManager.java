package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpAnswer;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpAnswerManager {
	/**
	 *根据id获取在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer getZxHelpAnswer(String answerid);

	/**
	 *根据id获取在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer getZxHelpAnswer(Integer answerid);

	/**
	 *增加在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer addZxHelpAnswer(ZxHelpAnswer zxHelpAnswer);

	/**
	 *删除在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer delZxHelpAnswer(String answerid);

	/**
	 *删除在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer delZxHelpAnswer(ZxHelpAnswer zxHelpAnswer);

	/**
	 *修改在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer updateZxHelpAnswer(ZxHelpAnswer zxHelpAnswer);

	/**
	 *获取在线答疑回复集合
	 *@return List
	 */
	public List getZxHelpAnswers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取在线答疑回复
 	 *@return List
	 */
	public ZxHelpAnswer getZxHelpAnswerByOrderid(String orderid);
	
	/**
	 *获取一页在线答疑回复集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpAnswers (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取在线答疑教师抢单成功学科id集合
 	 *@return List
	 */
	public List getAllSubjectidByUserid(String userid);
	
	/**
	 *获取在线答疑教师抢单成功子学段id集合
 	 *@return List
	 */
	public List getAllCxueduanidByUserid(String userid);
	
}