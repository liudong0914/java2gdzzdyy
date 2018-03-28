package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.service.ZxHelpQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 在线答疑提问
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class ZxHelpQuestionManagerImpl implements ZxHelpQuestionManager {

	private BaseDAO baseDAO;
	private String modelname = "在线答疑提问";

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
	 * 根据id获取在线答疑提问
	 * 
	 * @param questionid
	 *            Integer
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion getZxHelpQuestion(String questionid) {
		Integer iid = Integer.valueOf(questionid);
		return (ZxHelpQuestion) baseDAO.getObject(modelname, ZxHelpQuestion.class, iid);
	}

	/**
	 * 根据id获取在线答疑提问
	 * 
	 * @param questionid
	 *            Integer
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion getZxHelpQuestion(Integer questionid) {
		return (ZxHelpQuestion) baseDAO.getObject(modelname, ZxHelpQuestion.class, questionid);
	}

	/**
	 * 增加在线答疑提问
	 * 
	 * @param zxHelpQuestion
	 *            ZxHelpQuestion
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion addZxHelpQuestion(ZxHelpQuestion zxHelpQuestion) {
		return (ZxHelpQuestion) baseDAO.addObject(modelname, zxHelpQuestion);
	}

	/**
	 * 删除在线答疑提问
	 * 
	 * @param questionid
	 *            Integer
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion delZxHelpQuestion(String questionid) {
		ZxHelpQuestion model = getZxHelpQuestion(questionid);
		return (ZxHelpQuestion) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除在线答疑提问
	 * 
	 * @param zxHelpQuestion
	 *            ZxHelpQuestion
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion delZxHelpQuestion(ZxHelpQuestion zxHelpQuestion) {
		return (ZxHelpQuestion) baseDAO.delObject(modelname, zxHelpQuestion);
	}

	/**
	 * 修改在线答疑提问
	 * 
	 * @param zxHelpQuestion
	 *            ZxHelpQuestion
	 * @return ZxHelpQuestion
	 */
	public ZxHelpQuestion updateZxHelpQuestion(ZxHelpQuestion zxHelpQuestion) {
		return (ZxHelpQuestion) baseDAO.updateObject(modelname, zxHelpQuestion);
	}

	/**
	 * 获取在线答疑提问集合
	 * 
	 * @return List
	 */
	public List getZxHelpQuestions(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("ZxHelpQuestion", condition, orderby, pagesize);
	}

	/**
	 * 获取已购买答疑
	 * 
	 * @return List
	 */
	public PageList alreadyBuyQuestion(List<SearchModel> condition, String orderby, int start, int pagesize, String userid) {
		String sql1 = null;
		String sql2 = null;
		try {
			sql1 = " from ZxHelpQuestion AS a , ZxHelpQuestionBuy as b where a.questionid=b.questionid and b.userid=" + userid;
			sql2 = " from ZxHelpQuestion AS a , ZxHelpQuestionBuy as b where a.questionid=b.questionid and b.userid=" + userid;
			if (condition != null && condition.size() > 0) {
				SearchModel model = null;
				for (int i = 0, size = condition.size(); i < size; i++) {
					model = (SearchModel) condition.get(i);
					if ("0".equals(model.getType())) {
						if ("in".equals(model.getRelation().toLowerCase())) {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
						} else {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
						}
					} else if ("1".equals(model.getType())) {
						sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue();
						sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue();
					}
				}
			}
			if (!"".equals(orderby)) {
				sql2 += " order by " + orderby;
			} else {
				sql2 += " order by b.createdate desc,b.enddate asc";
			}
			sql1 = "select count(*)" + sql1;
			sql2 = "select a" + sql2;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseDAO.getPageObjects(sql1, sql2, start, pagesize);
	}

	/**
	 * 获取我的投诉
	 * 
	 * @return List
	 */
	public PageList studentComplaintQuestion(List<SearchModel> condition, String orderby, int start, int pagesize, String userid) {
		String sql1 = null;
		String sql2 = null;
		try {
			sql1 = " from ZxHelpQuestion AS a , ZxHelpQuestionComplaint as b where a.questionid=b.questionid and b.userid=" + userid;
			sql2 = " from ZxHelpQuestion AS a , ZxHelpQuestionComplaint as b where a.questionid=b.questionid and b.userid=" + userid;
			if (condition != null && condition.size() > 0) {
				SearchModel model = null;
				for (int i = 0, size = condition.size(); i < size; i++) {
					model = (SearchModel) condition.get(i);
					if (model != null && model.getIvalue() != null && model.getIvalue().intValue() > 0) {
						if ("in".equals(model.getRelation().toLowerCase())) {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
						} else {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
						}
					}
				}
			}
			if (!"".equals(orderby)) {
				sql2 += " order by " + orderby;
			} else {
				sql2 += " order by b.createdate desc";
			}
			sql1 = "select count(*)" + sql1;
			sql2 = "select a" + sql2;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseDAO.getPageObjects(sql1, sql2, start, pagesize);
	}

	/**
	 * 教师-->获取我的回复
	 * 
	 * @return List
	 */
	public PageList myReply(List<SearchModel> condition, String orderby, int start, int pagesize, String userid) {
		String sql1 = null;
		String sql2 = null;
		try {
			sql1 = " from ZxHelpQuestion AS a , ZxHelpOrder as b where a.questionid=b.questionid and b.userid=" + userid;
			sql2 = " from ZxHelpQuestion AS a , ZxHelpOrder as b where a.questionid=b.questionid and b.userid=" + userid;
			if (condition != null && condition.size() > 0) {
				SearchModel model = null;
				for (int i = 0, size = condition.size(); i < size; i++) {
					model = (SearchModel) condition.get(i);
					if (model != null && model.getIvalue() != null && model.getIvalue().intValue() > 0) {
						if ("in".equals(model.getRelation().toLowerCase())) {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
						} else {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
						}
					}
				}
			}
			if (!"".equals(orderby)) {
				sql2 += " order by " + orderby;
			} else {
				sql2 += " order by b.createdate desc";
			}
			sql1 = "select count(*)" + sql1;
			sql2 = "select a" + sql2;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseDAO.getPageObjects(sql1, sql2, start, pagesize);
	}

	/**
	 * 获取我的投诉
	 * 
	 * @return List
	 */
	public PageList teacherComplaintQuestion(List<SearchModel> condition, String orderby, int start, int pagesize, String userid) {
		String sql1 = null;
		String sql2 = null;
		try {
			sql1 = " from ZxHelpQuestion AS a , ZxHelpQuestionComplaint as b where a.questionid=b.questionid and b.teacherid=" + userid;
			sql2 = " from ZxHelpQuestion AS a , ZxHelpQuestionComplaint as b where a.questionid=b.questionid and b.teacherid=" + userid;
			if (condition != null && condition.size() > 0) {
				SearchModel model = null;
				for (int i = 0, size = condition.size(); i < size; i++) {
					model = (SearchModel) condition.get(i);
					if (model != null && model.getIvalue() != null && model.getIvalue().intValue() > 0) {
						if ("in".equals(model.getRelation().toLowerCase())) {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
						} else {
							sql1 = sql1 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
							sql2 = sql2 + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getIvalue() + "'";
						}
					}
				}
			}
			if (!"".equals(orderby)) {
				sql2 += " order by " + orderby;
			} else {
				sql2 += " order by b.createdate desc";
			}
			sql1 = "select count(*)" + sql1;
			sql2 = "select a" + sql2;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseDAO.getPageObjects(sql1, sql2, start, pagesize);
	}

	/**
	 * 获取在线答疑提问学科id集合
	 * 
	 * @return List
	 */
	public List getAllSubjectidByUserid(String userid) {
		String sql = "select distinct a.subjectid from ZxHelpQuestion as a where a.sysUserInfo.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取在线答疑提问子学段id集合
	 * 
	 * @return List
	 */
	public List getAllCxueduanidByUserid(String userid) {
		String sql = "select distinct a.cxueduanid from ZxHelpQuestion as a where a.sysUserInfo.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页在线答疑提问集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageZxHelpQuestions(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("ZxHelpQuestion", "questionid", condition, orderby, start, pagesize);
	}
}