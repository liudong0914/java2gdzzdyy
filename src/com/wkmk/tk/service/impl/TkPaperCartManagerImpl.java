package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperCart;
import com.wkmk.tk.service.TkPaperCartManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库组卷试题蓝
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperCartManagerImpl implements TkPaperCartManager {

	private BaseDAO baseDAO;
	private String modelname = "题库组卷试题蓝";

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
	 * 根据id获取题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart getTkPaperCart(String cartid) {
		Integer iid = Integer.valueOf(cartid);
		return (TkPaperCart) baseDAO.getObject(modelname, TkPaperCart.class, iid);
	}

	/**
	 * 根据id获取题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart getTkPaperCart(Integer cartid) {
		return (TkPaperCart) baseDAO.getObject(modelname, TkPaperCart.class, cartid);
	}

	/**
	 * 增加题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart addTkPaperCart(TkPaperCart tkPaperCart) {
		return (TkPaperCart) baseDAO.addObject(modelname, tkPaperCart);
	}

	/**
	 * 删除题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart delTkPaperCart(String cartid) {
		TkPaperCart model = getTkPaperCart(cartid);
		return (TkPaperCart) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart delTkPaperCart(TkPaperCart tkPaperCart) {
		return (TkPaperCart) baseDAO.delObject(modelname, tkPaperCart);
	}

	/**
	 * 修改题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart updateTkPaperCart(TkPaperCart tkPaperCart) {
		return (TkPaperCart) baseDAO.updateObject(modelname, tkPaperCart);
	}

	/**
	 * 获取题库组卷试题蓝集合
	 * 
	 * @return List
	 */
	public List getTkPaperCarts(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkPaperCart", condition, orderby, pagesize);
	}

	/**
	 * 获取一页题库组卷试题蓝集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperCarts(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkPaperCart", "cartid", condition, orderby, start, pagesize);
	}

	public void deleteQuestion(String subjectid, String xueduanid, String questionid, String userid) {
		String sql = "select a from TkPaperCart as a where a.subjectid=" + subjectid + " and xueduanid=" + xueduanid + " and questionsid=" + questionid + " and userid=" + userid;
		baseDAO.delObjects(baseDAO.getObjects(sql));
	}

	public List getAllQuestionid(String subjectid, String xueduanid, String userid) {
		String sql = "select DISTINCT questionsid from TkPaperCart as a where a.subjectid=" + subjectid + " and xueduanid=" + xueduanid + " and userid=" + userid + "";
		return baseDAO.getObjects(sql);
	}

	public int getMaxOrderindex(String userid, String subjectid, String xueduanid) {
		String sqlcount = "select count(*) from TkPaperCart as a where a.userid=" + userid + " and a.subjectid=" + subjectid + " and xueduanid=" + xueduanid + "";
		String sql = "select max(a.orderindex) from TkPaperCart as a where a.userid=" + userid + " and a.subjectid=" + subjectid + " and xueduanid=" + xueduanid + "";
		if (((Long) baseDAO.getRecordNumber(sqlcount)).intValue() == 0) {
			return 0;
		} else {
			return ((Integer) baseDAO.getObjects(sql).get(0) + 1);
		}
	}

	public void delAllQuestions(String subjectid, String xueduanid, String userid) {
		String sql = "select a from TkPaperCart as a where a.userid=" + userid + " and subjectid=" + subjectid + " and xueduanid=" + xueduanid + "";
		baseDAO.delObjects(baseDAO.getObjects(sql));
	}

	public List getAllPaperQuestions(String subjectid, String xueduanid, String userid) {
		String sql = "select a from TkQuestionsInfo as a,TkPaperCart as p where a.questionid=p.questionsid and p.subjectid=" + subjectid + " and p.xueduanid=" + xueduanid
				+ " and p.userid=" + userid + " order by orderindex asc";
		return baseDAO.getObjects(sql);
	}

	public String getPaperCartScore(String subjectid, String xueduanid, String userid, String questionid) {
		String sql = "select a.score from TkPaperCart as a where a.subjectid=" + subjectid + " and a.xueduanid=" + xueduanid + " and a.userid=" + userid + " and a.questionsid="
				+ questionid + "";
		return baseDAO.getObjects(sql).get(0).toString();
	}

	public TkPaperCart getSmallTkPaperCart(String subjectid, String xueduanid, String userid, String orderindex) {
		String sql = "select a from TkPaperCart as a where a.subjectid=" + subjectid + " and a.userid=" + userid + " and a.xueduanid=" + xueduanid + " and a.orderindex<"
				+ orderindex + " ORDER BY a.orderindex DESC";
		if (baseDAO.getObjects(sql, 1) != null && baseDAO.getObjects(sql, 1).size() != 0) {
			return (TkPaperCart) baseDAO.getObjects(sql, 1).get(0);
		} else {
			return null;
		}
	}

	public TkPaperCart getBigTkPaperCart(String subjectid, String xueduanid, String userid, String orderindex) {
		String sql = "select a from TkPaperCart as a where a.subjectid=" + subjectid + " and a.userid=" + userid + " and a.xueduanid=" + xueduanid + " and a.orderindex>"
				+ orderindex + " ORDER BY a.orderindex ASC";
		if (baseDAO.getObjects(sql, 1) != null && baseDAO.getObjects(sql, 1).size() != 0) {
			return (TkPaperCart) baseDAO.getObjects(sql, 1).get(0);
		} else {
			return null;
		}
	}

	public void deleteList(List list) {
		baseDAO.delObjects(list);
	}
}