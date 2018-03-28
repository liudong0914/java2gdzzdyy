package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkReport;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业批改报告</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkReportManager {
	/**
	 *根据id获取在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport getZxHomeworkReport(String reportid);

	/**
	 *根据id获取在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport getZxHomeworkReport(Integer reportid);

	/**
	 *增加在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport addZxHomeworkReport(ZxHomeworkReport zxHomeworkReport);

	/**
	 *删除在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport delZxHomeworkReport(String reportid);

	/**
	 *删除在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport delZxHomeworkReport(ZxHomeworkReport zxHomeworkReport);

	/**
	 *修改在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport updateZxHomeworkReport(ZxHomeworkReport zxHomeworkReport);

	/**
	 *获取在线作业批改报告集合
	 *@return List
	 */
	public List getZxHomeworkReports (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业批改报告集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkReports (List<SearchModel> condition, String orderby, int start, int pagesize);

}