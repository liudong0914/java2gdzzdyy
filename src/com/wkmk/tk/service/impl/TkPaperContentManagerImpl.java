package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.service.TkPaperContentManager;
import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 试卷内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperContentManagerImpl implements TkPaperContentManager {

	private BaseDAO baseDAO;
	private String modelname = "试卷内容";

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
	 * 根据id获取试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent getTkPaperContent(String contentid) {
		Integer iid = Integer.valueOf(contentid);
		return (TkPaperContent) baseDAO.getObject(modelname, TkPaperContent.class, iid);
	}

	/**
	 * 根据id获取试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent getTkPaperContent(Integer contentid) {
		return (TkPaperContent) baseDAO.getObject(modelname, TkPaperContent.class, contentid);
	}

	/**
	 * 增加试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent addTkPaperContent(TkPaperContent tkPaperContent) {
		return (TkPaperContent) baseDAO.addObject(modelname, tkPaperContent);
	}

	/**
	 * 删除试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent delTkPaperContent(String contentid) {
		TkPaperContent model = getTkPaperContent(contentid);
		return (TkPaperContent) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent delTkPaperContent(TkPaperContent tkPaperContent) {
		return (TkPaperContent) baseDAO.delObject(modelname, tkPaperContent);
	}

	/**
	 * 修改试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent updateTkPaperContent(TkPaperContent tkPaperContent) {
		return (TkPaperContent) baseDAO.updateObject(modelname, tkPaperContent);
	}

	/**
	 * 获取试卷内容集合
	 * 
	 * @return List
	 */
	public List getTkPaperContents(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkPaperContent", condition, orderby, pagesize);
	}

	/**
	 * 获取移动端试卷内容集合【有标准答案】
	 * 
	 * @return List
	 */
	public List getMobileTkPaperContents(Integer paperid) {
		String sql = "select a from TkPaperContent as a, TkQuestionsInfo as b where a.questionid=b.questionid and b.status='1' and b.isrightans='1' and a.paperid=" + paperid
				+ " order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取试卷内容条数
	 */
	public int getTkPaperContentCounts(Integer paperid) {
		String sql = "select count(*) from TkPaperContent as a where a.paperid=" + paperid;
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return ((Long) list.get(0)).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 获取一页试卷内容集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperContents(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkPaperContent", "contentid", condition, orderby, start, pagesize);
	}

	public List getQuestions(int paperid) {
		String sql = "select q from TkQuestionsInfo as q,TkPaperContent as tp where q.questionid=tp.questionid and tp.paperid=" + paperid + " order by tp.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	public List getContentids(int paperid) {
		String sql = "select pc.contentid from TkPaperContent pc,TkQuestionsInfo q where pc.questionid=q.questionid and q.isrightans=1 and pc.paperid=" + paperid + "  order by pc.orderindex asc ";
		return baseDAO.getObjects(sql);
	}

	public int getMaxOrderindex(int paperid) {
		String sql = "select max(orderindex) from TkPaperContent where paperid=" + paperid;
		if (null != baseDAO.getObjects(sql).get(0)) {
			return (Integer) baseDAO.getObjects(sql).get(0);
		} else {
			return 0;
		}

	}

	public PageList getQuestions(int paperid, String questionno, String title, String difficult, String sorderindex, int start, int size) {
		String sql = "select q from TkQuestionsInfo as q,TkPaperContent as tp where q.questionid=tp.questionid and tp.paperid=" + paperid;
		String sqlcount = "select count(*) from TkQuestionsInfo as q,TkPaperContent as tp where q.questionid=tp.questionid and tp.paperid=" + paperid;
		if (!"".equals(questionno)) {
			sql += " and q.questionno like '%" + questionno + "%'";
			sqlcount += " and q.questionno like '%" + questionno + "%'";
		}
		if (!"".equals(title)) {
			sql += " and q.title like '%" + title + "%'";
			sqlcount += " and q.title like '%" + title + "%'";
		}
		if (!"".equals(difficult)) {
			sql += " and q.difficult='" + difficult + "'";
			sqlcount += " and q.difficult='" + difficult + "'";
		}
		//if (sorderindex != null && sorderindex.trim().length() > 0) {
		//	sql += " order by " + sorderindex;
		//}
		sql += " order by tp.orderindex asc";
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}
}