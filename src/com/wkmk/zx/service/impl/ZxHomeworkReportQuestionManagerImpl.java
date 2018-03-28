package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkReportQuestion;
import com.wkmk.zx.service.ZxHomeworkReportQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业批改报告错题内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReportQuestionManagerImpl implements ZxHomeworkReportQuestionManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业批改报告错题内容";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion getZxHomeworkReportQuestion(String rqid){
		Integer iid = Integer.valueOf(rqid);
		return  (ZxHomeworkReportQuestion)baseDAO.getObject(modelname,ZxHomeworkReportQuestion.class,iid);
	}

	/**
	 *根据id获取在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion getZxHomeworkReportQuestion(Integer rqid){
		return  (ZxHomeworkReportQuestion)baseDAO.getObject(modelname,ZxHomeworkReportQuestion.class,rqid);
	}

	/**
	 *增加在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion addZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion){
		return (ZxHomeworkReportQuestion)baseDAO.addObject(modelname,zxHomeworkReportQuestion);
	}

	/**
	 *删除在线作业批改报告错题内容
	 *@param rqid Integer
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion delZxHomeworkReportQuestion(String rqid){
		ZxHomeworkReportQuestion model = getZxHomeworkReportQuestion(rqid);
		return (ZxHomeworkReportQuestion)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion delZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion){
		return (ZxHomeworkReportQuestion)baseDAO.delObject(modelname,zxHomeworkReportQuestion);
	}

	/**
	 *修改在线作业批改报告错题内容
	 *@param zxHomeworkReportQuestion ZxHomeworkReportQuestion
	 *@return ZxHomeworkReportQuestion
	 */
	public ZxHomeworkReportQuestion updateZxHomeworkReportQuestion(ZxHomeworkReportQuestion zxHomeworkReportQuestion){
		return (ZxHomeworkReportQuestion)baseDAO.updateObject(modelname,zxHomeworkReportQuestion);
	}

	/**
	 *获取在线作业批改报告错题内容集合
 	 *@return List
	 */
	public List getZxHomeworkReportQuestions(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkReportQuestion",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业批改报告错题内容集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkReportQuestions (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkReportQuestion","rqid",condition, orderby, start,pagesize);
	}

}