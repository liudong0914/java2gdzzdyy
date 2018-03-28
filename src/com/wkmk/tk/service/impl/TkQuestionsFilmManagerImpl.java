package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsFilm;
import com.wkmk.tk.service.TkQuestionsFilmManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 习题微课
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsFilmManagerImpl implements TkQuestionsFilmManager {

	private BaseDAO baseDAO;
	private String modelname = "习题微课";

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
	 * 根据id获取习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm getTkQuestionsFilm(String tid) {
		Integer iid = Integer.valueOf(tid);
		return (TkQuestionsFilm) baseDAO.getObject(modelname, TkQuestionsFilm.class, iid);
	}

	/**
	 * 根据id获取习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm getTkQuestionsFilm(Integer tid) {
		return (TkQuestionsFilm) baseDAO.getObject(modelname, TkQuestionsFilm.class, tid);
	}

	/**
	 * 增加习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm addTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm) {
		return (TkQuestionsFilm) baseDAO.addObject(modelname, tkQuestionsFilm);
	}

	/**
	 * 删除习题微课
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm delTkQuestionsFilm(String tid) {
		TkQuestionsFilm model = getTkQuestionsFilm(tid);
		return (TkQuestionsFilm) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm delTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm) {
		return (TkQuestionsFilm) baseDAO.delObject(modelname, tkQuestionsFilm);
	}

	/**
	 * 修改习题微课
	 * 
	 * @param tkQuestionsFilm
	 *            TkQuestionsFilm
	 * @return TkQuestionsFilm
	 */
	public TkQuestionsFilm updateTkQuestionsFilm(TkQuestionsFilm tkQuestionsFilm) {
		return (TkQuestionsFilm) baseDAO.updateObject(modelname, tkQuestionsFilm);
	}

	/**
	 * 获取习题微课集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsFilms(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkQuestionsFilm", condition, orderby, pagesize);
	}
	
	/**
	 * 获取习题微课集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsFilms(String questionid) {
		String sql = "select b from TkQuestionsFilm as a, VwhFilmInfo as b where a.filmid=b.filmid and a.questionid=" + questionid;
		return baseDAO.getObjects(sql);
	}
	
	public int getCountTkQuestionsFilms(Integer questionid) {
		String sql = "select count(*) from TkQuestionsFilm as a where a.questionid=" + questionid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			return 0;
		}
	}

	/**
	 * 获取一页习题微课集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsFilms(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkQuestionsFilm", "tid", condition, orderby, start, pagesize);
	}

	public void delTkQuestionsFilms(String questionid, String filmid) {
		String sql = "select a from TkQuestionsFilm as a where a.questionid='" + questionid + "' and filmid='" + filmid + "'";
		baseDAO.delObjects(baseDAO.getObjects(sql));
	}

}