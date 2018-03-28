package com.wkmk.tk.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkQuestionsTypeManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库试题类型
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsTypeManagerImpl implements TkQuestionsTypeManager {

	private BaseDAO baseDAO;
	private String modelname = "题库试题类型";

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
	 * 根据id获取题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType getTkQuestionsType(String typeid) {
		Integer iid = Integer.valueOf(typeid);
		return (TkQuestionsType) baseDAO.getObject(modelname, TkQuestionsType.class, iid);
	}

	/**
	 * 根据id获取题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType getTkQuestionsType(Integer typeid) {
		return (TkQuestionsType) baseDAO.getObject(modelname, TkQuestionsType.class, typeid);
	}

	/**
	 * 增加题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType addTkQuestionsType(TkQuestionsType tkQuestionsType) {
		return (TkQuestionsType) baseDAO.addObject(modelname, tkQuestionsType);
	}

	/**
	 * 删除题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType delTkQuestionsType(String typeid) {
		TkQuestionsType model = getTkQuestionsType(typeid);
		return (TkQuestionsType) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType delTkQuestionsType(TkQuestionsType tkQuestionsType) {
		return (TkQuestionsType) baseDAO.delObject(modelname, tkQuestionsType);
	}

	/**
	 * 修改题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType updateTkQuestionsType(TkQuestionsType tkQuestionsType) {
		return (TkQuestionsType) baseDAO.updateObject(modelname, tkQuestionsType);
	}

	/**
	 * 获取题库试题类型集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsTypes(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkQuestionsType", condition, orderby, pagesize);
	}

	/**
	 * 获取题库试题类型
	 */
	public TkQuestionsType getTkQuestionsType(String type, Integer subjectid) {
		String sql = "select a from TkQuestionsType as a where a.type='" + type + "' and a.subjectid=" + subjectid;
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return (TkQuestionsType) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取一页题库试题类型集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsTypes(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkQuestionsType", "typeid", condition, orderby, start, pagesize);
	}

	/**
	 * 查询该学科类型下的某种题目类型的个数
	 * */
	public int getTkQuestionsTypesNums(String subjectid, String xueduan, String questiontypeid, String userid) {
		String sql = "select DISTINCT count(*) from TkQuestionsInfo as a,TkPaperCart as tc where a.questionid=tc.questionsid  and tc.subjectid=" + subjectid
				+ " and tc.xueduanid=" + xueduan + " and tc.userid=" + userid + " and a.tkQuestionsType.typeid=" + questiontypeid + " and a.status='1'";
		return ((Long) baseDAO.getRecordNumber(sql)).intValue();
	}

	public String getTkQuestionsTypesPercent(String subjectid, String xueduan, String questiontypeid, String userid) {
		// 当前题型的个数
		int num = getTkQuestionsTypesNums(subjectid, xueduan, questiontypeid, userid);
		// 总个数
		String sql = "select count(*) from TkPaperCart as a where a.userid=" + userid + " and a.subjectid=" + subjectid + " and a.xueduanid=" + xueduan + "";
		int sum = ((Long) baseDAO.getRecordNumber(sql)).intValue();
		if (num == 0 && sum == 0) {
			return "0%";
		} else {
			double y = num * 1.0;
			double z = sum * 1.0;
			double result = y / z;
			DecimalFormat df = new DecimalFormat("##%");
			return df.format(result);
		}
	}

	public Double getTkQuestionsTypesScore(String subjectid, String xueduan, String questiontypeid, String userid) {
		String sql = "select sum(tc.score) from TkQuestionsInfo as a,TkPaperCart as tc where a.questionid=tc.questionsid  and tc.subjectid=" + subjectid + " and tc.xueduanid="
				+ xueduan + " and tc.userid=" + userid + " and a.tkQuestionsType.typeid=" + questiontypeid + "";
		if (baseDAO.getObjects(sql).get(0) == null) {
			return 0.0;
		} else {
			return Double.parseDouble(baseDAO.getObjects(sql).get(0).toString());
		}
	}

	public int getTkQuestionsSum(String subjectid, String xueduan, String userid) {
		String sql = "select count(*) from TkPaperCart as a where a.userid=" + userid + " and a.subjectid=" + subjectid + " and a.xueduanid=" + xueduan + "";
		return ((Long) baseDAO.getRecordNumber(sql)).intValue();
	}
}