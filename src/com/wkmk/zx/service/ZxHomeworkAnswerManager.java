package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkAnswer;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkAnswerManager {
	/**
	 *根据id获取在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer getZxHomeworkAnswer(String answerid);

	/**
	 *根据id获取在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer getZxHomeworkAnswer(Integer answerid);

	/**
	 *增加在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer addZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer);

	/**
	 *删除在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer delZxHomeworkAnswer(String answerid);

	/**
	 *删除在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer delZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer);

	/**
	 *修改在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer updateZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer);

	/**
	 *获取在线作业回复集合
	 *@return List
	 */
	public List getZxHomeworkAnswers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业回复集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkAnswers (List<SearchModel> condition, String orderby, int start, int pagesize);

}