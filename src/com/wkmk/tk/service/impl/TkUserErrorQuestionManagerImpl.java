package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkUserErrorQuestion;
import com.wkmk.tk.service.TkUserErrorQuestionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 个人错题集
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkUserErrorQuestionManagerImpl implements TkUserErrorQuestionManager {

	private BaseDAO baseDAO;
	private String modelname = "个人错题集";

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
	 * 根据id获取个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion getTkUserErrorQuestion(String uerrorid) {
		Integer iid = Integer.valueOf(uerrorid);
		return (TkUserErrorQuestion) baseDAO.getObject(modelname, TkUserErrorQuestion.class, iid);
	}

	/**
	 * 根据id获取个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion getTkUserErrorQuestion(Integer uerrorid) {
		return (TkUserErrorQuestion) baseDAO.getObject(modelname, TkUserErrorQuestion.class, uerrorid);
	}

	/**
	 * 增加个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion addTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion) {
		return (TkUserErrorQuestion) baseDAO.addObject(modelname, tkUserErrorQuestion);
	}

	/**
	 * 删除个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion delTkUserErrorQuestion(String uerrorid) {
		TkUserErrorQuestion model = getTkUserErrorQuestion(uerrorid);
		return (TkUserErrorQuestion) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion delTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion) {
		return (TkUserErrorQuestion) baseDAO.delObject(modelname, tkUserErrorQuestion);
	}

	/**
	 * 修改个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion updateTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion) {
		return (TkUserErrorQuestion) baseDAO.updateObject(modelname, tkUserErrorQuestion);
	}

	/**
	 * 获取个人错题集集合
	 * 
	 * @return List
	 */
	public List getTkUserErrorQuestions(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkUserErrorQuestion", condition, orderby, pagesize);
	}

	/**
	 * 获取个人错题集集合
	 * 
	 * @return List
	 */
	public List getTkUserErrorQuestions(String userid, String bookcontentid, String classid, String type) {
		String sql = "";
		if ("1".equals(type)) {
			sql = "select c.questionid, c.title, c.titlecontent, a.type, a.uerrorid from TkUserErrorQuestion as a, TkPaperContent as b, TkQuestionsInfo as c where a.contentid=b.contentid and b.questionid=c.questionid and a.type='" + type + "' and a.userid=" + userid + " and a.bookcontentid=" + bookcontentid + " and a.classid=" + classid;
		} else {
			sql = "select b.questionid, b.title, b.titlecontent, a.type, a.uerrorid from TkUserErrorQuestion as a, TkQuestionsInfo as b where a.contentid=b.questionid and a.type='" + type + "' and a.userid=" + userid + " and a.bookcontentid=" + bookcontentid + " and a.classid=" + classid;
		}
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页个人错题集集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkUserErrorQuestions(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkUserErrorQuestion", "uerrorid", condition, orderby, start, pagesize);
	}

	public List getUserErrorQuestions(String bookid, String classid, String userid) {
		String sql = "select e from TkUserErrorQuestion as e,TkBookContent as t where e.bookcontentid=t.bookcontentid and t.bookid=" + bookid + " and e.classid=" + classid + " and e.userid=" + userid + " order by e.type desc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 错题统计，'作业本题',数量
	 */
	public List getUserErrorQuestionsOfType1() {
		String sql = "SELECT DATE_FORMAT(a.createdate,'%Y/%m/%d') AS DAY,COUNT(*) FROM TkUserErrorQuestion AS a WHERE a.type='1'  GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，'课前预习题',数量
	 */
	public List getUserErrorQuestionsOfType2() {
		String sql = "SELECT DATE_FORMAT(a.createdate,'%Y/%m/%d') AS DAY,COUNT(*) FROM TkUserErrorQuestion AS a WHERE a.type='2'  GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，'举一反三题',数量
	 */
	public List getUserErrorQuestionsOfType3() {
		String sql = "SELECT DATE_FORMAT(a.createdate,'%Y/%m/%d') AS DAY,COUNT(*) FROM TkUserErrorQuestion AS a WHERE a.type='3'  GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，总数量
	 */
	public List getUserErrorQuestionsOfAll() {
		String sql = "SELECT DATE_FORMAT(a.createdate,'%Y/%m/%d') AS DAY,COUNT(*) FROM TkUserErrorQuestion AS a   GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，总数量最大值
	 */
	public List getUserErrorQuestionsOfAllMaxNum() {
		String sql = "SELECT COUNT(*) FROM TkUserErrorQuestion AS a   GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，人数最大值
	 */
	public List getUserErrorQuestionsOfCountUserMaxNum() {
		String sql = "SELECT COUNT(DISTINCT a.userid) FROM TkUserErrorQuestion AS a   GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，人数
	 */
	public List getUserErrorQuestionsOfCountUser() {
		String sql = "SELECT DATE_FORMAT(a.createdate,'%Y/%m/%d') AS DAY,COUNT(DISTINCT a.userid) FROM TkUserErrorQuestion AS a   GROUP BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，作业本题总数
	 */
	public List getUserErrorQuestionsOfCountType1() {
		String sql = "select count(*) from TkUserErrorQuestion as a where a.type='1'  ";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，课前预习题总数
	 */
	public List getUserErrorQuestionsOfCountType2() {
		String sql = "select count(*) from TkUserErrorQuestion as a where a.type='2'  ";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题统计，举一反三题总数
	 */
	public List getUserErrorQuestionsOfCountType3() {
		String sql = "select count(*) from TkUserErrorQuestion as a where a.type='3'  ";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题总数
	 */
	public List getUserErrorQuestionsOfCountType() {
		String sql = "select count(*) from TkUserErrorQuestion as a  ";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 错题人总数
	 */
	public List getUserErrorQuestionsOfCountUserAll() {
		String sql = "select count(DISTINCT a.userid) from TkUserErrorQuestion as a  ";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1书本总使用数
	 */
	public List getUserErrorQuestionsOfBook(String bookid) {
		String sql = "select ";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += " b.paperid,b.title,a.version,";
		} else {
			sql += " a.bookid, a.title,a.version, ";
		}
		sql += "COUNT(*) from TkBookInfo as a ,TkBookContent as b ,TkPaperInfo as c ,TkPaperContent as d ,TkQuestionsInfo as e ,TkPaperAnswer as f ";
		sql += "where  ";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += " a.bookid =" + bookid + "and ";
		}
		sql += " f.contentid= d.contentid and a.bookid=b.bookid and b.paperid = c.paperid and c.paperid = d.paperid and d.questionid= e.questionid and f.bookcontentid = b.bookcontentid";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += "  GROUP BY b.bookcontentid  ORDER BY b.contentno";
		} else {
			sql += "  GROUP BY a.bookid  ORDER BY a.bookno";
		}
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1书本总题数
	 */
	public List getUserErrorQuestionsOfBookNum(String bookid) {
		String sql = "SELECT b.paperid,b.title,a.version,COUNT(*) FROM TkBookInfo AS a ,TkBookContent AS b ,TkPaperInfo AS c ,TkPaperContent AS d ,TkQuestionsInfo AS e " + "WHERE  a.bookid =" + bookid + "  AND a.bookid=b.bookid AND b.paperid = c.paperid AND c.paperid = d.paperid AND d.questionid= e.questionid " + "GROUP BY b.bookcontentid  ORDER BY b.contentno	";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1书本总题数最大值
	 */
	public List getUserErrorQuestionsOfBookMax(String bookid) {
		String sql = "select  COUNT(*) from TkBookInfo as a ,TkBookContent as b ,TkPaperInfo as c ,TkPaperContent as d ,TkQuestionsInfo as e ";
		if (bookid == null || bookid.trim().length() <= 0) {
			sql += ",TkPaperAnswer as f ";
		}
		sql += "where  ";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += " a.bookid =" + bookid;
		}
		if (bookid == null || bookid.trim().length() <= 0) {
			sql += " f.contentid= d.contentid  ";
		}
		sql += "  and a.bookid=b.bookid and b.paperid = c.paperid and c.paperid = d.paperid and d.questionid= e.questionid ";
		if (bookid == null || bookid.trim().length() <= 0) {
			sql += "and f.bookcontentid = b.bookcontentid";
		}
		if (bookid != null && bookid.trim().length() > 0) {
			sql += "  GROUP BY b.bookcontentid  ORDER BY b.contentno";
		} else {
			sql += "  GROUP BY a.bookid  ORDER BY a.bookno";
		}
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1书本总错题数
	 */
	public List getUserErrorQuestionsOfBookError(String bookid) {
		String sql = "select ";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += " b.paperid,b.title,a.version,";
		} else {
			sql += " a.bookid, a.title,a.version, ";
		}
		sql += "COUNT(*) from TkBookInfo as a ,TkBookContent as b ,TkPaperInfo as c ,TkPaperContent as d ,TkQuestionsInfo as e ,TkPaperAnswer as f " + "where  ";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += " a.bookid =" + bookid + " and ";
		}
		sql += "  f.contentid= d.contentid  and a.bookid=b.bookid and b.paperid = c.paperid and c.paperid = d.paperid and d.questionid= e.questionid and f.bookcontentid = b.bookcontentid and f.isright='0'";
		if (bookid != null && bookid.trim().length() > 0) {
			sql += "  GROUP BY b.bookcontentid  ORDER BY b.contentno";
		} else {
			sql += "  GROUP BY a.bookid  ORDER BY a.bookno";
		}
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1试题总使用数
	 */
	public List getUserErrorQuestionsOfQuestions(String paperid) {
		String sql = "SELECT c.paperid,d.orderindex,a.version,COUNT(d.contentid) FROM TkBookInfo AS a ,TkBookContent AS b , TkPaperInfo AS c ,TkPaperContent AS d ,TkQuestionsInfo AS e,TkPaperAnswer AS f WHERE" + "  c.paperid=" + paperid + "  AND  c.paperid = d.paperid AND e.questionid=d.questionid AND f.contentid =d.contentid AND a.bookid =b.bookid AND b.bookcontentid = f.bookcontentid" + "  GROUP BY d.questionid  ORDER BY d.orderindex";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1试题总题数最大值
	 */
	public List getUserErrorQuestionsOfQuestionsMax(String paperid) {
		String sql = "SELECT COUNT(d.contentid) FROM TkBookInfo AS a ,TkBookContent AS b , TkPaperInfo AS c ,TkPaperContent AS d ,TkQuestionsInfo AS e,TkPaperAnswer AS f WHERE" + "  c.paperid=" + paperid + " AND  c.paperid = d.paperid AND e.questionid=d.questionid AND f.contentid =d.contentid AND a.bookid =b.bookid AND b.bookcontentid = f.bookcontentid" + "  GROUP BY d.questionid  ORDER BY d.orderindex";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1试题总错题数
	 */
	public List getUserErrorQuestionsOfQuestionsError(String paperid) {
		String sql = "SELECT c.paperid,d.orderindex,a.version,COUNT(d.contentid) FROM TkBookInfo AS a ,TkBookContent AS b , TkPaperInfo AS c ,TkPaperContent AS d ,TkQuestionsInfo AS e,TkPaperAnswer AS f WHERE" + "  c.paperid=" + paperid + " AND f.isright ='0' AND  c.paperid = d.paperid AND e.questionid=d.questionid AND f.contentid =d.contentid AND a.bookid =b.bookid AND b.bookcontentid = f.bookcontentid" + "  GROUP BY d.questionid  ORDER BY d.orderindex";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 1试题总题数
	 */
	public List getUserErrorQuestionsOfQuestionsNum(String paperid) {
		String sql = "SELECT c.paperid,d.orderindex,a.version,e.questionid  FROM TkBookInfo AS a ,TkBookContent AS b , TkPaperInfo AS c ,TkPaperContent AS d ,TkQuestionsInfo AS e WHERE" + "  c.paperid=" + paperid + "  AND  a.bookid =b.bookid AND b.paperid=c.paperid AND  c.paperid = d.paperid AND d.questionid=e.questionid    " + "  GROUP BY d.questionid  ORDER BY d.orderindex";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}