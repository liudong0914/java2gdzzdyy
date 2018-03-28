package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHomeworkAnswer;
import com.wkmk.zx.service.ZxHomeworkAnswerManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线作业回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkAnswerManagerImpl implements ZxHomeworkAnswerManager{

	private BaseDAO baseDAO;
	private String modelname = "在线作业回复";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer getZxHomeworkAnswer(String answerid){
		Integer iid = Integer.valueOf(answerid);
		return  (ZxHomeworkAnswer)baseDAO.getObject(modelname,ZxHomeworkAnswer.class,iid);
	}

	/**
	 *根据id获取在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer getZxHomeworkAnswer(Integer answerid){
		return  (ZxHomeworkAnswer)baseDAO.getObject(modelname,ZxHomeworkAnswer.class,answerid);
	}

	/**
	 *增加在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer addZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer){
		return (ZxHomeworkAnswer)baseDAO.addObject(modelname,zxHomeworkAnswer);
	}

	/**
	 *删除在线作业回复
	 *@param answerid Integer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer delZxHomeworkAnswer(String answerid){
		ZxHomeworkAnswer model = getZxHomeworkAnswer(answerid);
		return (ZxHomeworkAnswer)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer delZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer){
		return (ZxHomeworkAnswer)baseDAO.delObject(modelname,zxHomeworkAnswer);
	}

	/**
	 *修改在线作业回复
	 *@param zxHomeworkAnswer ZxHomeworkAnswer
	 *@return ZxHomeworkAnswer
	 */
	public ZxHomeworkAnswer updateZxHomeworkAnswer(ZxHomeworkAnswer zxHomeworkAnswer){
		return (ZxHomeworkAnswer)baseDAO.updateObject(modelname,zxHomeworkAnswer);
	}

	/**
	 *获取在线作业回复集合
 	 *@return List
	 */
	public List getZxHomeworkAnswers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHomeworkAnswer",condition,orderby,pagesize);
	}

	/**
	 *获取一页在线作业回复集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHomeworkAnswers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHomeworkAnswer","answerid",condition, orderby, start,pagesize);
	}

}