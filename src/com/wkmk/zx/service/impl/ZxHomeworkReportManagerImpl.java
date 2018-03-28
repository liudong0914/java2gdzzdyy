package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkReport;
import com.wkmk.zx.service.ZxHomeworkReportManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业批改报告</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReportManagerImpl implements ZxHomeworkReportManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业批改报告";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport getZxHomeworkReport(String reportid){
		Integer iid = Integer.valueOf(reportid);
		return  (ZxHomeworkReport)baseDAO.getObject(modelname,ZxHomeworkReport.class,iid);
	}

	/**
	 *根据id获取在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport getZxHomeworkReport(Integer reportid){
		return  (ZxHomeworkReport)baseDAO.getObject(modelname,ZxHomeworkReport.class,reportid);
	}

	/**
	 *增加在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport addZxHomeworkReport(ZxHomeworkReport zxHomeworkReport){
		return (ZxHomeworkReport)baseDAO.addObject(modelname,zxHomeworkReport);
	}

	/**
	 *删除在线作业批改报告
	 *@param reportid Integer
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport delZxHomeworkReport(String reportid){
		ZxHomeworkReport model = getZxHomeworkReport(reportid);
		return (ZxHomeworkReport)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport delZxHomeworkReport(ZxHomeworkReport zxHomeworkReport){
		return (ZxHomeworkReport)baseDAO.delObject(modelname,zxHomeworkReport);
	}

	/**
	 *修改在线作业批改报告
	 *@param zxHomeworkReport ZxHomeworkReport
	 *@return ZxHomeworkReport
	 */
	public ZxHomeworkReport updateZxHomeworkReport(ZxHomeworkReport zxHomeworkReport){
		return (ZxHomeworkReport)baseDAO.updateObject(modelname,zxHomeworkReport);
	}

	/**
	 *获取在线作业批改报告集合
 	 *@return List
	 */
	public List getZxHomeworkReports(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkReport",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业批改报告集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkReports (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkReport","reportid",condition, orderby, start,pagesize);
	}

}