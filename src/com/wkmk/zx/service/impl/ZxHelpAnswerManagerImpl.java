package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpAnswer;
import com.wkmk.zx.service.ZxHelpAnswerManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpAnswerManagerImpl implements ZxHelpAnswerManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑回复";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer getZxHelpAnswer(String answerid){
		Integer iid = Integer.valueOf(answerid);
		return  (ZxHelpAnswer)baseDAO.getObject(modelname,ZxHelpAnswer.class,iid);
	}

	/**
	 *根据id获取在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer getZxHelpAnswer(Integer answerid){
		return  (ZxHelpAnswer)baseDAO.getObject(modelname,ZxHelpAnswer.class,answerid);
	}

	/**
	 *增加在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer addZxHelpAnswer(ZxHelpAnswer zxHelpAnswer){
		return (ZxHelpAnswer)baseDAO.addObject(modelname,zxHelpAnswer);
	}

	/**
	 *删除在线答疑回复
	 *@param answerid Integer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer delZxHelpAnswer(String answerid){
		ZxHelpAnswer model = getZxHelpAnswer(answerid);
		return (ZxHelpAnswer)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer delZxHelpAnswer(ZxHelpAnswer zxHelpAnswer){
		return (ZxHelpAnswer)baseDAO.delObject(modelname,zxHelpAnswer);
	}

	/**
	 *修改在线答疑回复
	 *@param zxHelpAnswer ZxHelpAnswer
	 *@return ZxHelpAnswer
	 */
	public ZxHelpAnswer updateZxHelpAnswer(ZxHelpAnswer zxHelpAnswer){
		return (ZxHelpAnswer)baseDAO.updateObject(modelname,zxHelpAnswer);
	}

	/**
	 *获取在线答疑回复集合
 	 *@return List
	 */
	public List getZxHelpAnswers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpAnswer",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线答疑回复
 	 *@return List
	 */
	public ZxHelpAnswer getZxHelpAnswerByOrderid(String orderid){
		String sql = "select a from ZxHelpAnswer as a where a.orderid=" + orderid + " order by a.status desc, a.createdate desc";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (ZxHelpAnswer) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页在线答疑回复集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpAnswers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpAnswer","answerid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取在线答疑回复学科id集合
 	 *@return List
	 */
	public List getAllSubjectidByUserid(String userid){
		String sql = "SELECT DISTINCT a.subjectid FROM ZxHelpQuestion AS a,ZxHelpOrder AS b WHERE a.questionid=b.questionid AND b.userid=" + userid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取在线答疑教师抢单成功子学段id集合
 	 *@return List
	 */
	public List getAllCxueduanidByUserid(String userid){
		String sql = "SELECT DISTINCT a.cxueduanid FROM ZxHelpQuestion AS a,ZxHelpOrder AS b WHERE a.questionid=b.questionid AND b.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

}