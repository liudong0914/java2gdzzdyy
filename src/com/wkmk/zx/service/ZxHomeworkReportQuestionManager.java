package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkReportQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业批改报告错题内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkReportQuestionManager {
	/**
	 *根据id获取在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion getZxHomeworkReportQuestion(String rqid);

	/**
	 *根据id获取在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion getZxHomeworkReportQuestion(Integer rqid);

	/**
	 *增加在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion addZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion);

	/**
	 *删除在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion delZxHomeworkReportQuestion(String rqid);

	/**
	 *删除在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion delZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion);

	/**
	 *修改在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion updateZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion);

	/**
	 *获取在线作业批改报告错题内容集合
	 *@return List
	 */
	public List getZxHomeworkReportQuestions (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业批改报告错题内容集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkReportQuestions (List<SearchModel> condition, String orderby, int start, int pagesize);

}