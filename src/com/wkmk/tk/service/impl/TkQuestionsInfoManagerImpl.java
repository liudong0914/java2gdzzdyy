package com.wkmk.tk.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.service.TkQuestionsInfoManager;

/**
 * <p>
 * Description: 题库试题信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsInfoManagerImpl implements TkQuestionsInfoManager {

	private BaseDAO baseDAO;
	private String modelname = "题库试题信息";

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
	 * 根据id获取题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo getTkQuestionsInfo(String questionid) {
		Integer iid = Integer.valueOf(questionid);
		return (TkQuestionsInfo) baseDAO.getObject(modelname, TkQuestionsInfo.class, iid);
	}

	/**
	 * 根据id获取题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo getTkQuestionsInfo(Integer questionid) {
		return (TkQuestionsInfo) baseDAO.getObject(modelname, TkQuestionsInfo.class, questionid);
	}

	/**
	 * 增加题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo addTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo) {
		return (TkQuestionsInfo) baseDAO.addObject(modelname, tkQuestionsInfo);
	}

	/**
	 * 删除题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo delTkQuestionsInfo(String questionid) {
		TkQuestionsInfo model = getTkQuestionsInfo(questionid);
		return (TkQuestionsInfo) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo delTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo) {
		return (TkQuestionsInfo) baseDAO.delObject(modelname, tkQuestionsInfo);
	}

	/**
	 * 修改题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo updateTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo) {
		return (TkQuestionsInfo) baseDAO.updateObject(modelname, tkQuestionsInfo);
	}

	/**
	 * 获取题库试题信息集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsInfos(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkQuestionsInfo", condition, orderby, pagesize);
	}

	/**
	 * 获取题库所有子题信息集合
	 * 
	 * @return List
	 */
	public List getAllChildTkQuestionsInfos(Integer parentid) {
		String sql = "select a from TkQuestionsInfo as a where a.parentid=" + parentid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页题库试题信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsInfos(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkQuestionsInfo", "questionid", condition, orderby, start, pagesize);
	}

	public List getAlltypeids() {
		String sql = "select distinct a.tkQuestionsType.typeid  from TkQuestionsInfo as a";
		return baseDAO.getObjects(sql);
	}

	public List getAllparentids() {
		String sql = "select distinct a.parentid from TkQuestionsInfo as a";
		return baseDAO.getObjects(sql);
	}

	public PageList getAllsimilarQuestions(String questionid, String questionno, String title, String difficult, String sorderindex, String type, int subjectid, int gradeid, int start, int size) {
		String sql = "select a from TkQuestionsInfo as a,TkQuestionsSimilar as s where a.questionid=s.similarquestionid and s.questionid=" + questionid + " and a.parentid='0' and a.status=1 ";
		sql += " and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		String sqlcount = "select count(*) from TkQuestionsInfo as a,TkQuestionsSimilar as s where a.questionid=s.similarquestionid and s.questionid=" + questionid
				+ " and a.parentid='0' and a.status=1 ";
		sqlcount += " and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		if (questionno != null && questionno.trim().length() > 0) {
			sql += " and a.questionno like '%" + questionno + "%'";
			sqlcount += " and a.questionno like '%" + questionno + "%'";
		}
		if (title != null && title.trim().length() > 0) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (difficult != null && difficult.trim().length() > 0) {
			sql += " and a.difficult ='" + difficult + "'";
			sqlcount += " and a.difficult ='" + difficult + "'";
		}
		if (type != null && type.trim().length() > 0) {
			sql += " and a.tkQuestionsType.typeid=" + type;
			sqlcount += " and a.tkQuestionsType.typeid=" + type;
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	public PageList getUnAllsimilarQuestions(String questionid, String questionno, String title, String difficult, String sorderindex, String type, int subjectid, int gradeid, int start, int size) {
		String sql = "select a from TkQuestionsInfo as a where a.questionid not in(select s.similarquestionid from TkQuestionsSimilar as s where s.questionid=" + questionid + ") and a.questionid!="
				+ questionid + " and a.parentid='0' and a.status=1 ";
		sql += " and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		String sqlcount = "select count(*) from TkQuestionsInfo as a where a.questionid not in(select s.similarquestionid from TkQuestionsSimilar as s where s.questionid=" + questionid
				+ ") and a.questionid!=" + questionid + " and a.parentid='0' and a.status=1 ";
		sqlcount += " and a.subjectid=" + subjectid + " and a.gradeid=" + gradeid;
		if (questionno != null && questionno.trim().length() > 0) {
			sql += " and a.questionno like '%" + questionno + "%'";
			sqlcount += " and a.questionno like '%" + questionno + "%'";
		}
		if (title != null && title.trim().length() > 0) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (difficult != null && difficult.trim().length() > 0) {
			sql += " and a.difficult ='" + difficult + "'";
			sqlcount += " and a.difficult ='" + difficult + "'";
		}
		if (type != null && type.trim().length() > 0) {
			sql += " and tkQuestionsType.typeid=" + type;
			sqlcount += "  and tkQuestionsType.typeid=" + type;
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	public PageList getAllfimInfo(String questionid, String title, String actor, String sorderindex, int start, int size) {
		String sql = "select a from VwhFilmInfo as a,TkQuestionsFilm as t where a.filmid=t.filmid and t.questionid=" + questionid + " and a.status=1 ";
		String sqlcount = "select count(*) from VwhFilmInfo as a,TkQuestionsFilm as t where a.filmid=t.filmid and t.questionid=" + questionid + " and a.status=1 ";
		if (title != null && title.trim().length() > 0) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (actor != null && actor.trim().length() > 0) {
			sql += " and a.actor like '%" + actor + "%'";
			sqlcount += " and a.actor like '%" + actor + "%'";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	public PageList getUnAllfimInfo(String questionid, String title, String actor, String gradeid, String sorderindex, int start, int size) {
		String sql = "select a from VwhFilmInfo as a where a.filmid not in(select t.filmid from  TkQuestionsFilm as t where t.questionid=" + questionid + ") and a.eduGradeInfo.gradeid=" + gradeid
				+ " and a.status=1";
		String sqlcount = "select count(*) from VwhFilmInfo as a where a.filmid not in(select t.filmid from  TkQuestionsFilm as t where t.questionid=" + questionid + ") and a.eduGradeInfo.gradeid="
				+ gradeid + " and a.status=1";
		if (title != null && title.trim().length() > 0) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (actor != null && actor.trim().length() > 0) {
			sql += " and a.actor like '%" + actor + "%'";
			sqlcount += " and a.actor like '%" + actor + "%'";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	public PageList getQuestionsInfo(String title, String subjectid, String knopointid, String knopointno, String questiontypeid, String difficult, int start, int pagesize) {
		String sql = "select DISTINCT q from TkQuestionsInfo as q,TkQuestionsKnopoint as tk,EduKnopointInfo as e where q.subjectid=e.subjectid and q.questionid=tk.questionid and tk.knopointid=e.knopointid and e.subjectid="
				+ subjectid + " and e.knopointno like '" + knopointno + "%' and q.tkQuestionsType.typeid=" + questiontypeid + " and q.status='1' and q.parentid='0'  order by q.questionno asc";
		String sqlcount = "select DISTINCT count(*) from TkQuestionsInfo as q,TkQuestionsKnopoint as tk,EduKnopointInfo as e where q.subjectid=e.subjectid and q.questionid=tk.questionid and tk.knopointid=e.knopointid and e.subjectid="
				+ subjectid + " and e.knopointid like '" + knopointno + "%' and q.tkQuestionsType.typeid=" + questiontypeid + " and q.status='1' and q.parentid='0' ";
		if (!"".equals(difficult)) {
			sql += " and q.difficult='" + difficult + "'";
			sqlcount += " and q.difficult='" + difficult + "'";
		}
		if (!"".equals(title)) {
			sql += " and (q.title like '%" + title + "%' or q.tag like '%" + title + "%')";
			sqlcount += " and (q.title like '%" + title + "%' or q.tag like '%" + title + "%')";
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}

	public PageList getQuestionsInfo(String title, String subjectid, String gradetype, String questiontypeid, String difficult, int start, int pagesize) {
		String sql = "select distinct q from TkQuestionsInfo as q,EduGradeInfo as e where q.gradeid=e.gradeid and e.subjectid=" + subjectid + " and e.xueduanid=" + gradetype
				+ " and q.tkQuestionsType.typeid=" + questiontypeid + " and q.status='1' and q.parentid='0' ";
		String sqlcount = "select distinct count(*) from TkQuestionsInfo as q,EduGradeInfo as e where q.gradeid=e.gradeid and e.subjectid=" + subjectid + " and e.xueduanid=" + gradetype
				+ " and q.tkQuestionsType.typeid=" + questiontypeid + " and q.status='1' and q.parentid='0' ";
		if (!"".equals(difficult)) {
			sql += " and q.difficult='" + difficult + "'";
			sqlcount += " and  q.difficult='" + difficult + "'";
		}
		if (!"".equals(title)) {
			sql += " and (q.title like '%" + title + "%' or q.tag like '%" + title + "%')";
			sqlcount += " and (q.title like '%" + title + "%' or q.tag like '%" + title + "%')";
		}
		// String sql =
		// "select DISTINCT q from TkQuestionsInfo as q,TkQuestionsKnopoint as tk,EduKnopointInfo as e where q.subjectid=e.subjectid and q.questionid=tk.questionid and tk.knopointid=e.knopointid and e.subjectid='"
		// + subjectid + "' and e.gradetype='" + gradetype +
		// "' and q.tkQuestionsType.typeid='" + questiontypeid +
		// "'  and q.status='1' order by q.questionno asc";
		// String sqlcount =
		// "select DISTINCT count(*) from TkQuestionsInfo as q,TkQuestionsKnopoint as tk,EduKnopointInfo as e where q.subjectid=e.subjectid and q.questionid=tk.questionid and tk.knopointid=e.knopointid and e.subjectid='"
		// + subjectid + "' and e.gradetype='" + gradetype +
		// "' and q.tkQuestionsType.typeid='" + questiontypeid +
		// "' and q.status='1' order by q.questionno asc";
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}

	public List getQuestionsInfo(String questiontype, String difficult, int num) {
		if (num == 0) {
			return new ArrayList();
		} else {
			String sql = "select q from TkQuestionsInfo as q where q.tkQuestionsType.type='" + questiontype + "' and q.status='1' and q.parentid='0'";
			if (!"".equals(difficult)) {
				sql += " and difficult='" + difficult + "'";
			}
			sql += "  ORDER BY RAND()";
			return baseDAO.getObjects(sql, num);
		}
	}

	public List getQuestionsInfo(String questiontype, String difficult, int num, String knopoints) {
		if (num == 0) {
			return new ArrayList();
		} else {
			String sql = "select q from TkQuestionsInfo as q,TkQuestionsKnopoint as t where q.questionid=t.questionid and q.tkQuestionsType.type='" + questiontype
					+ "' and q.status='1' and q.parentid='0' and (" + knopoints + ")";
			if (!"".equals(difficult)) {
				sql += " and difficult='" + difficult + "'";
			}
			sql += "  ORDER BY RAND()";
			return baseDAO.getObjects(sql, num);
		}
	}

	public int getErrorNum(String contentid, String classid) {
		String sql = "select count(*) from TkPaperAnswer as q where q.contentid=" + contentid + " and q.classid=" + classid + " and isright='0'";
		return Integer.parseInt(baseDAO.getObjects(sql).get(0).toString());
	}

	public boolean getishaveIsrights(int questionid) {
		String sql = "select  a from TkQuestionsInfo as a where parentid=" + questionid + " and a.isrightans=0";
		List list = baseDAO.getObjects(sql);
		if (list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getMaxQuestionid() {
		String sql = "select max(questionid) from TkQuestionsInfo as a ";
		List list = baseDAO.getObjects(sql);
		if (baseDAO.getObjects(sql).get(0) != null) {
			return Integer.parseInt(baseDAO.getObjects(sql).get(0).toString());
		} else {
			return 0;
		}
	}

	public int getMaxTypeno() {
		String sql = "select max(typeno) from TkQuestionsType as t";
		List list = baseDAO.getObjects(sql);
		if (baseDAO.getObjects(sql).get(0) != null) {
			return Integer.parseInt(baseDAO.getObjects(sql).get(0).toString());
		} else {
			return 0;
		}
	}

	public List getGradeids(int subjectid) {
		String sql = "select DISTINCT q.gradeid from TkQuestionsInfo as q where q.subjectid=" + subjectid;
		return baseDAO.getObjects(sql);
	}
}