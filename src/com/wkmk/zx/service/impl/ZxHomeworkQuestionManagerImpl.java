package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkQuestion;
import com.wkmk.zx.service.ZxHomeworkQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkQuestionManagerImpl implements ZxHomeworkQuestionManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业提问";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion getZxHomeworkQuestion(String questionid){
		Integer iid = Integer.valueOf(questionid);
		return  (ZxHomeworkQuestion)baseDAO.getObject(modelname,ZxHomeworkQuestion.class,iid);
	}

	/**
	 *根据id获取在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion getZxHomeworkQuestion(Integer questionid){
		return  (ZxHomeworkQuestion)baseDAO.getObject(modelname,ZxHomeworkQuestion.class,questionid);
	}

	/**
	 *增加在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion addZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion){
		return (ZxHomeworkQuestion)baseDAO.addObject(modelname,zxHomeworkQuestion);
	}

	/**
	 *删除在线作业提问
	 *@param questionid Integer
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion delZxHomeworkQuestion(String questionid){
		ZxHomeworkQuestion model = getZxHomeworkQuestion(questionid);
		return (ZxHomeworkQuestion)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion delZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion){
		return (ZxHomeworkQuestion)baseDAO.delObject(modelname,zxHomeworkQuestion);
	}

	/**
	 *修改在线作业提问
	 *@param zxHomeworkQuestion ZxHomeworkQuestion
	 *@return ZxHomeworkQuestion
	 */
	public ZxHomeworkQuestion updateZxHomeworkQuestion(ZxHomeworkQuestion zxHomeworkQuestion){
		return (ZxHomeworkQuestion)baseDAO.updateObject(modelname,zxHomeworkQuestion);
	}

	/**
	 *获取在线作业提问集合
 	 *@return List
	 */
	public List getZxHomeworkQuestions(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkQuestion",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业提问集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkQuestions (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkQuestion","questionid",condition, orderby, start,pagesize);
	}

}