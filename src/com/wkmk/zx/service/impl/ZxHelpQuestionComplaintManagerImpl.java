package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionComplaint;
import com.wkmk.zx.service.ZxHelpQuestionComplaintManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionComplaintManagerImpl implements ZxHelpQuestionComplaintManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑投诉";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint getZxHelpQuestionComplaint(String complaintid){
		Integer iid = Integer.valueOf(complaintid);
		return  (ZxHelpQuestionComplaint)baseDAO.getObject(modelname,ZxHelpQuestionComplaint.class,iid);
	}

	/**
	 *根据id获取在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint getZxHelpQuestionComplaint(Integer complaintid){
		return  (ZxHelpQuestionComplaint)baseDAO.getObject(modelname,ZxHelpQuestionComplaint.class,complaintid);
	}

	/**
	 *增加在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint addZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint){
		return (ZxHelpQuestionComplaint)baseDAO.addObject(modelname,zxHelpQuestionComplaint);
	}

	/**
	 *删除在线答疑投诉
	 *@param complaintid Integer
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint delZxHelpQuestionComplaint(String complaintid){
		ZxHelpQuestionComplaint model = getZxHelpQuestionComplaint(complaintid);
		return (ZxHelpQuestionComplaint)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint delZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint){
		return (ZxHelpQuestionComplaint)baseDAO.delObject(modelname,zxHelpQuestionComplaint);
	}

	/**
	 *修改在线答疑投诉
	 *@param zxHelpQuestionComplaint ZxHelpQuestionComplaint
	 *@return ZxHelpQuestionComplaint
	 */
	public ZxHelpQuestionComplaint updateZxHelpQuestionComplaint(ZxHelpQuestionComplaint zxHelpQuestionComplaint){
		return (ZxHelpQuestionComplaint)baseDAO.updateObject(modelname,zxHelpQuestionComplaint);
	}

	/**
	 *获取在线答疑投诉集合
 	 *@return List
	 */
	public List getZxHelpQuestionComplaints(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpQuestionComplaint",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线答疑投诉集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionComplaints (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpQuestionComplaint","complaintid",condition, orderby, start,pagesize);
	}

}