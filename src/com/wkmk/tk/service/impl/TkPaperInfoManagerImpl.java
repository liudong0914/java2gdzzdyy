package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.service.TkPaperInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 试卷信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperInfoManagerImpl implements TkPaperInfoManager {

	private BaseDAO baseDAO;
	private String modelname = "试卷信息";

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
	 * 根据id获取试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo getTkPaperInfo(String paperid) {
		Integer iid = Integer.valueOf(paperid);
		return (TkPaperInfo) baseDAO.getObject(modelname, TkPaperInfo.class, iid);
	}

	/**
	 * 根据id获取试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo getTkPaperInfo(Integer paperid) {
		return (TkPaperInfo) baseDAO.getObject(modelname, TkPaperInfo.class, paperid);
	}

	/**
	 * 增加试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo addTkPaperInfo(TkPaperInfo tkPaperInfo) {
		return (TkPaperInfo) baseDAO.addObject(modelname, tkPaperInfo);
	}

	/**
	 * 删除试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo delTkPaperInfo(String paperid) {
		TkPaperInfo model = getTkPaperInfo(paperid);
		return (TkPaperInfo) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo delTkPaperInfo(TkPaperInfo tkPaperInfo) {
		return (TkPaperInfo) baseDAO.delObject(modelname, tkPaperInfo);
	}

	/**
	 * 修改试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo updateTkPaperInfo(TkPaperInfo tkPaperInfo) {
		return (TkPaperInfo) baseDAO.updateObject(modelname, tkPaperInfo);
	}

	/**
	 * 获取试卷信息集合
	 * 
	 * @return List
	 */
	public List getTkPaperInfos(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkPaperInfo", condition, orderby, pagesize);
	}

	/**
	 * 获取一页试卷信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperInfos(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkPaperInfo", "paperid", condition, orderby, start, pagesize);
	}

	public List getAllbookcontentPaperid() {
		String sql = "select distinct a.paperid from TkBookContent as a";
		return baseDAO.getObjects(sql);
	}

	public List getPaperQuestions(Integer paperid) {
		String sql = "select q from TkQuestionsInfo as q ,TkPaperContent as tp where q.questionid=tp.questionid and tp.paperid=" + paperid + " order by orderindex asc";
		return baseDAO.getObjects(sql);
	}

}