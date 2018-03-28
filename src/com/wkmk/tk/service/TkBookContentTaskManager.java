package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentTask;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业任务信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentTaskManager {
	/**
	 *根据id获取作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask getTkBookContentTask(String taskid);

	/**
	 *根据id获取作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask getTkBookContentTask(Integer taskid);

	/**
	 *增加作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask addTkBookContentTask(TkBookContentTask tkBookContentTask);

	/**
	 *删除作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask delTkBookContentTask(String taskid);

	/**
	 *删除作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask delTkBookContentTask(TkBookContentTask tkBookContentTask);

	/**
	 *修改作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask updateTkBookContentTask(TkBookContentTask tkBookContentTask);

	/**
	 *获取作业任务信息集合
	 *@return List
	 */
	public List getTkBookContentTasks (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页作业任务信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentTasks (List<SearchModel> condition, String orderby, int start, int pagesize);

}