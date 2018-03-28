package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentTask;
import com.wkmk.tk.service.TkBookContentTaskManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业任务信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentTaskManagerImpl implements TkBookContentTaskManager{

	private BaseDAO baseDAO;
	private String modelname = "作业任务信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask getTkBookContentTask(String taskid){
		Integer iid = Integer.valueOf(taskid);
		return  (TkBookContentTask)baseDAO.getObject(modelname,TkBookContentTask.class,iid);
	}

	/**
	 *根据id获取作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask getTkBookContentTask(Integer taskid){
		return  (TkBookContentTask)baseDAO.getObject(modelname,TkBookContentTask.class,taskid);
	}

	/**
	 *增加作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask addTkBookContentTask(TkBookContentTask tkBookContentTask){
		return (TkBookContentTask)baseDAO.addObject(modelname,tkBookContentTask);
	}

	/**
	 *删除作业任务信息
	 *@param taskid Integer
	 *@return TkBookContentTask
	 */
	public TkBookContentTask delTkBookContentTask(String taskid){
		TkBookContentTask model = getTkBookContentTask(taskid);
		return (TkBookContentTask)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask delTkBookContentTask(TkBookContentTask tkBookContentTask){
		return (TkBookContentTask)baseDAO.delObject(modelname,tkBookContentTask);
	}

	/**
	 *修改作业任务信息
	 *@param tkBookContentTask TkBookContentTask
	 *@return TkBookContentTask
	 */
	public TkBookContentTask updateTkBookContentTask(TkBookContentTask tkBookContentTask){
		return (TkBookContentTask)baseDAO.updateObject(modelname,tkBookContentTask);
	}

	/**
	 *获取作业任务信息集合
 	 *@return List
	 */
	public List getTkBookContentTasks(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentTask",condition,orderby,pagesize);
	}

	/**
	 *获取一页作业任务信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentTasks (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentTask","taskid",condition, orderby, start,pagesize);
	}

}