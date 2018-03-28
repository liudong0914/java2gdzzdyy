 package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsSimilar;
import com.wkmk.tk.service.TkQuestionsSimilarManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 习题举一反三
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsSimilarManagerImpl implements TkQuestionsSimilarManager {

	private BaseDAO baseDAO;
	private String modelname = "习题举一反三";

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
	 * 根据id获取习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar getTkQuestionsSimilar(String tid) {
		Integer iid = Integer.valueOf(tid);
		return (TkQuestionsSimilar) baseDAO.getObject(modelname, TkQuestionsSimilar.class, iid);
	}

	/**
	 * 根据id获取习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar getTkQuestionsSimilar(Integer tid) {
		return (TkQuestionsSimilar) baseDAO.getObject(modelname, TkQuestionsSimilar.class, tid);
	}

	/**
	 * 增加习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar addTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar) {
		return (TkQuestionsSimilar) baseDAO.addObject(modelname, tkQuestionsSimilar);
	}

	/**
	 * 删除习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar delTkQuestionsSimilar(String tid) {
		TkQuestionsSimilar model = getTkQuestionsSimilar(tid);
		return (TkQuestionsSimilar) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar delTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar) {
		return (TkQuestionsSimilar) baseDAO.delObject(modelname, tkQuestionsSimilar);
	}

	/**
	 * 修改习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar updateTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar) {
		return (TkQuestionsSimilar) baseDAO.updateObject(modelname, tkQuestionsSimilar);
	}

	/**
	 * 获取习题举一反三集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsSimilars(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkQuestionsSimilar", condition, orderby, pagesize);
	}
	
	/**
	 * 获取习题举一反三集合
	 * @return List
	 */
	public List getTkQuestionsSimilars(String questionid) {
		String sql = "select b from TkQuestionsSimilar as a, TkQuestionsInfo as b where a.similarquestionid=b.questionid and a.questionid=" + questionid;
		return baseDAO.getObjects(sql);
	}

	public int getCountTkQuestionsSimilars(Integer questionid) {
		String sql = "select count(*) from TkQuestionsSimilar as a where a.questionid=" + questionid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			return 0;
		}
	}
	
	/**
	 * 获取一页习题举一反三集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsSimilars(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkQuestionsSimilar", "tid", condition, orderby, start, pagesize);
	}

	public void delTkQuestionsSimilars(String questionid, String similarquestionid) {
		String sql = "select t from TkQuestionsSimilar as t where t.questionid='" + questionid + "' and t.similarquestionid='" + similarquestionid
				+ "'";
		baseDAO.delObjects(baseDAO.getObjects(sql));
	}

}