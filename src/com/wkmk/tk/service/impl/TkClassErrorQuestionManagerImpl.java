package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassErrorQuestion;
import com.wkmk.tk.service.TkClassErrorQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 班级错题集
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkClassErrorQuestionManagerImpl implements TkClassErrorQuestionManager {

	private BaseDAO baseDAO;
	private String modelname = "班级错题集";

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
	 * 根据id获取班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion getTkClassErrorQuestion(String cerrorid) {
		Integer iid = Integer.valueOf(cerrorid);
		return (TkClassErrorQuestion) baseDAO.getObject(modelname, TkClassErrorQuestion.class, iid);
	}

	/**
	 * 根据id获取班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion getTkClassErrorQuestion(Integer cerrorid) {
		return (TkClassErrorQuestion) baseDAO.getObject(modelname, TkClassErrorQuestion.class, cerrorid);
	}

	/**
	 * 增加班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion addTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion) {
		return (TkClassErrorQuestion) baseDAO.addObject(modelname, tkClassErrorQuestion);
	}

	/**
	 * 删除班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion delTkClassErrorQuestion(String cerrorid) {
		TkClassErrorQuestion model = getTkClassErrorQuestion(cerrorid);
		return (TkClassErrorQuestion) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion delTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion) {
		return (TkClassErrorQuestion) baseDAO.delObject(modelname, tkClassErrorQuestion);
	}

	/**
	 * 修改班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion updateTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion) {
		return (TkClassErrorQuestion) baseDAO.updateObject(modelname, tkClassErrorQuestion);
	}

	/**
	 * 获取班级错题集集合
	 * 
	 * @return List
	 */
	public List getTkClassErrorQuestions(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkClassErrorQuestion", condition, orderby, pagesize);
	}

	/**
	 * 获取一页班级错题集集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkClassErrorQuestions(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkClassErrorQuestion", "cerrorid", condition, orderby, start, pagesize);
	}

	public void delete(List list) {
		baseDAO.delObjects(list);
	}

	public List getContentidByType(int bookcontentid, int classid, String type) {
		String sql = "select distinct contentid from TkClassErrorQuestion as q where q.bookcontentid=" + bookcontentid + " and q.classid=" + classid + " and type='" + type + "'";
		return baseDAO.getObjects(sql);
	}

}