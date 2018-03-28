package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionComplaint;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpQuestionComplaintManager {
	/**
	 *根据id获取在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint getZxHelpQuestionComplaint(String complaintid);

	/**
	 *根据id获取在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint getZxHelpQuestionComplaint(Integer complaintid);

	/**
	 *增加在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint addZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint);

	/**
	 *删除在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint delZxHelpQuestionComplaint(String complaintid);

	/**
	 *删除在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint delZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint);

	/**
	 *修改在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint updateZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint);

	/**
	 *获取在线答疑投诉集合
	 *@return List
	 */
	public List getZxHelpQuestionComplaints (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线答疑投诉集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionComplaints (List<SearchModel> condition, String orderby, int start, int pagesize);

}