package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsKnopoint;
import com.wkmk.tk.service.TkQuestionsKnopointManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库知识点
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsKnopointManagerImpl implements TkQuestionsKnopointManager {

	private BaseDAO baseDAO;
	private String modelname = "题库知识点";

	/**
	 * 加载baseDAO
	 * 
	 * @param BaseDAO
	 *            baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 根据id获取题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint getTkQuestionsKnopoint(String tid) {
		Integer iid = Integer.valueOf(tid);
		return (TkQuestionsKnopoint) baseDAO.getObject(modelname, TkQuestionsKnopoint.class, iid);
	}

	/**
	 * 根据id获取题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint getTkQuestionsKnopoint(Integer tid) {
		return (TkQuestionsKnopoint) baseDAO.getObject(modelname, TkQuestionsKnopoint.class, tid);
	}

	/**
	 * 增加题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint addTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint) {
		return (TkQuestionsKnopoint) baseDAO.addObject(modelname, tkQuestionsKnopoint);
	}

	/**
	 * 删除题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint delTkQuestionsKnopoint(String tid) {
		TkQuestionsKnopoint model = getTkQuestionsKnopoint(tid);
		return (TkQuestionsKnopoint) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint delTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint) {
		return (TkQuestionsKnopoint) baseDAO.delObject(modelname, tkQuestionsKnopoint);
	}

	/**
	 * 修改题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint updateTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint) {
		return (TkQuestionsKnopoint) baseDAO.updateObject(modelname, tkQuestionsKnopoint);
	}

	/**
	 * 获取题库知识点集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsKnopoints(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkQuestionsKnopoint", condition, orderby, pagesize);
	}

	/**
	 * 获取所有知识点id
	 * 
	 * @return List
	 */
	public List getAllKnopointids() {
		String sql = "select distinct a.knopointid from TkQuestionsKnopoint as a";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页题库知识点集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsKnopoints(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkQuestionsKnopoint", "tid", condition, orderby, start, pagesize);
	}

	/**
	 * 根据questionid查询EduKnopointInfo对象
	 * */
	public List getEduKnopointInfoByQuestionid(Integer questionid) {
		String sql = "select a from EduKnopointInfo as a,TkQuestionsKnopoint as b where a.knopointid=b.knopointid and b.questionid=" + questionid;
		return baseDAO.getObjects(sql);
	}

	public void deleteTkQuestionKnopoints(Integer questionid) {
		String sql = "select a from TkQuestionsKnopoint as a where questionid=" + questionid;
	    baseDAO.delObjects(sql);
	}
}