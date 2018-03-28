package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentQuestion;
import com.wkmk.tk.service.TkBookContentQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 作业本内容关联试题
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentQuestionManagerImpl implements TkBookContentQuestionManager {

	private BaseDAO baseDAO;
	private String modelname = "作业本内容关联试题";

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
	 * 根据id获取作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion getTkBookContentQuestion(String tid) {
		Integer iid = Integer.valueOf(tid);
		return (TkBookContentQuestion) baseDAO.getObject(modelname, TkBookContentQuestion.class, iid);
	}

	/**
	 * 根据id获取作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion getTkBookContentQuestion(Integer tid) {
		return (TkBookContentQuestion) baseDAO.getObject(modelname, TkBookContentQuestion.class, tid);
	}

	/**
	 * 增加作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion addTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion) {
		return (TkBookContentQuestion) baseDAO.addObject(modelname, tkBookContentQuestion);
	}

	/**
	 * 删除作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion delTkBookContentQuestion(String tid) {
		TkBookContentQuestion model = getTkBookContentQuestion(tid);
		return (TkBookContentQuestion) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion delTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion) {
		return (TkBookContentQuestion) baseDAO.delObject(modelname, tkBookContentQuestion);
	}

	/**
	 * 修改作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion updateTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion) {
		return (TkBookContentQuestion) baseDAO.updateObject(modelname, tkBookContentQuestion);
	}

	/**
	 * 获取作业本内容关联试题集合
	 * 
	 * @return List
	 */
	public List getTkBookContentQuestions(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkBookContentQuestion", condition, orderby, pagesize);
	}

	/**
	 * 获取作业本内容关联试题集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsInfosByBookcontentid(String bookcontentid, String type, String isrightans) {
		String sql = "select b from TkBookContentQuestion as a, TkQuestionsInfo as b where a.questionid=b.questionid and a.type='" + type + "' and a.bookcontentid="
				+ bookcontentid;
		if (isrightans != null && !"".equals(isrightans)) {
			sql = sql + " and b.isrightans='" + isrightans + "'";// 除了教学案题，其他题只查询有标准答案的题供手机端显示
		}
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页作业本内容关联试题集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContentQuestions(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkBookContentQuestion", "tid", condition, orderby, start, pagesize);
	}

	public PageList getPageQuestions(String bookcontentid, String questionno, String title, String difficult, String type, String sorderindex, String typeid, int start,
			int pagesize) {
		String sql = "select a from TkQuestionsInfo as a,TkBookContentQuestion as b where a.questionid=b.questionid and b.bookcontentid=" + bookcontentid + " and b.type='" + type
				+ "' and a.status=1 ";
		String sqlconut = "select count(*) from TkQuestionsInfo as a,TkBookContentQuestion as b where a.questionid=b.questionid and b.bookcontentid=" + bookcontentid
				+ " and b.type='" + type + "' and a.status=1 ";
		if (!"".equals(questionno)) {
			sql += " and a.questionno like '%" + questionno + "%' ";
			sqlconut += " and a.questionno like '%" + questionno + "%' ";
		}
		if (!"".equals(title)) {
			sql += " and a.title like '%" + title + "%'";
			sqlconut += " and a.title like '%" + title + "%'";
		}
		if (!"".equals(difficult)) {
			sql += " and a.difficult ='" + difficult + "'";
			sqlconut += " and a.difficult ='" + difficult + "'";
		}
		if (!"".equals(typeid)) {
			sql += " and a.tkQuestionsType.typeid=" + typeid;
			sqlconut += " and a.tkQuestionsType.typeid=" + typeid;
		}
		if (!"".equals(sorderindex)) {
			sql += " order by " + sorderindex;
		}

		return baseDAO.getPageObjects(sqlconut, sql, start, pagesize);
	}

	public PageList getPageUnQuestions(String bookcontentid, String questionno, String title, String difficult, String type, String sorderindex, int subjectid, int gradeid,
			String typeid, int start, int pagesize) {
		String sql = "select a from TkQuestionsInfo as a where a.questionid not in(select b.questionid from TkBookContentQuestion as b where b.bookcontentid=" + bookcontentid
				+ " and b.type='" + type + "') and a.status=1 and a.parentid=0 and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		String sqlcount = "select count(*) from TkQuestionsInfo as a where a.questionid not in(select b.questionid from TkBookContentQuestion as b where b.bookcontentid="
				+ bookcontentid + " and b.type='" + type + "') and a.status=1 and a.parentid=0 and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		if (!"".equals(questionno)) {
			sql += " and a.questionno like '%" + questionno + "%' ";
			sqlcount += " and a.questionno like '%" + questionno + "%' ";
		}
		if (!"".equals(title)) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (!"".equals(difficult)) {
			sql += " and a.difficult ='" + difficult + "'";
			sqlcount += " and a.difficult ='" + difficult + "'";
		}
		if (!"".equals(typeid)) {
			sql += " and a.tkQuestionsType.typeid=" + typeid;
			sqlcount += " and a.tkQuestionsType.typeid=" + typeid;
		}
		if (!"".equals(sorderindex)) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}

	public void delQuestions(String type, String bookcontentid, String questionid) {
		String sql = "select a from TkBookContentQuestion as a where a.bookcontentid=" + bookcontentid + " and questionid=" + questionid + " and type='" + type + "'";
		baseDAO.delObjects(baseDAO.getObjects(sql));
	}
}