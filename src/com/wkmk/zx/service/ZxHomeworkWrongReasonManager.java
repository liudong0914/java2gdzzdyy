package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkWrongReason;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业常见错误原因</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHomeworkWrongReasonManager {
	/**
	 *根据id获取在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason getZxHomeworkWrongReason(String wrid);

	/**
	 *根据id获取在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason getZxHomeworkWrongReason(Integer wrid);

	/**
	 *增加在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason addZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason);

	/**
	 *删除在线作业常见错误原因
	 *@param wrid Integer
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason delZxHomeworkWrongReason(String wrid);

	/**
	 *删除在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason delZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason);

	/**
	 *修改在线作业常见错误原因
	 *@param zxHomeworkWrongReason ZxHomeworkWrongReason
	 *@return ZxHomeworkWrongReason
	 */
	public ZxHomeworkWrongReason updateZxHomeworkWrongReason(ZxHomeworkWrongReason zxHomeworkWrongReason);

	/**
	 *获取在线作业常见错误原因集合
	 *@return List
	 */
	public List getZxHomeworkWrongReasons (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线作业常见错误原因集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkWrongReasons (List<SearchModel> condition, String orderby, int start, int pagesize);

}