package com.wkmk.tk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wkmk.tk.bo.TkPaperAnswer;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkPaperAnswerManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 试卷作答信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TkPaperAnswerManagerImpl implements TkPaperAnswerManager {

	private BaseDAO baseDAO;
	private String modelname = "试卷作答信息";

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
	 * 根据id获取试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer getTkPaperAnswer(String answerid) {
		Integer iid = Integer.valueOf(answerid);
		return (TkPaperAnswer) baseDAO.getObject(modelname, TkPaperAnswer.class, iid);
	}

	/**
	 * 根据id获取试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer getTkPaperAnswer(Integer answerid) {
		return (TkPaperAnswer) baseDAO.getObject(modelname, TkPaperAnswer.class, answerid);
	}

	/**
	 * 增加试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer addTkPaperAnswer(TkPaperAnswer tkPaperAnswer) {
		return (TkPaperAnswer) baseDAO.addObject(modelname, tkPaperAnswer);
	}

	/**
	 * 删除试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer delTkPaperAnswer(String answerid) {
		TkPaperAnswer model = getTkPaperAnswer(answerid);
		return (TkPaperAnswer) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer delTkPaperAnswer(TkPaperAnswer tkPaperAnswer) {
		return (TkPaperAnswer) baseDAO.delObject(modelname, tkPaperAnswer);
	}

	/**
	 * 修改试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer updateTkPaperAnswer(TkPaperAnswer tkPaperAnswer) {
		return (TkPaperAnswer) baseDAO.updateObject(modelname, tkPaperAnswer);
	}

	/**
	 * 获取试卷作答信息集合
	 * 
	 * @return List
	 */
	public List getTkPaperAnswers(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkPaperAnswer", condition, orderby, pagesize);
	}

	/**
	 * 获取试卷作答信息
	 */
	public TkPaperAnswer getTkPaperAnswer(String userid, String taskid, String contentid, String type) {
		// 因为taskid包含了bookcontentid，所以在此查询可以不用bookcontentid作为查询条件
		String sql = "select a from TkPaperAnswer as a where a.type='" + type + "' and a.userid=" + userid + " and a.taskid=" + taskid + " and a.contentid=" + contentid;
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return (TkPaperAnswer) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取试卷子题作答信息集合
	 */
	public List getAllChildTkPaperAnswer(String userid, String taskid, String contentid, String type) {
		// 因为taskid包含了bookcontentid，所以在此查询可以不用bookcontentid作为查询条件
		String sql = "select a from TkPaperAnswer as a where a.type='" + type + "' and a.userid=" + userid + " and a.taskid=" + taskid + " and childid<>0 and a.contentid=" + contentid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取试卷作答信息条数
	 */
	public int getTkPaperAnswerCountsByBookcontentid(Integer bookcontentid) {
		String sql = "select count(*) from TkPaperAnswer as a where a.bookcontentid=" + bookcontentid;
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return ((Long) list.get(0)).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 获取试卷作答信息条数
	 */
	public int getTkPaperAnswerCountsByBookid(String bookid, String userid) {
		String sql = "select count(a.answerid) from TkPaperAnswer as a, TkBookContent as b where a.bookcontentid=b.bookcontentid and a.classid=0 and b.bookid=" + bookid + " and a.userid=" + userid;
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return ((Long) list.get(0)).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 获取班级已作答学生列表
	 */
	public List getTkPaperAnswerStudentsByClassid(String bookcontentid, String classid) {
		String sql = "select distinct a.userid from TkPaperAnswer as a where a.classid=" + classid + " and a.bookcontentid=" + bookcontentid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页试卷作答信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperAnswers(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkPaperAnswer", "answerid", condition, orderby, start, pagesize);
	}

	/**
	 * 查询班级内的学员
	 * 
	 * @param classid
	 * @return
	 */
	public List getStudentsByClass(Integer classid) {
		String sql = "select u from SysUserInfo u,TkClassUser cu where cu.userid=u.userid and cu.classid=" + classid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 查询作业内容信息
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @return
	 */
	public List getPaperContent(Integer paperid) {
		String sql = "select pc from TkPaperContent pc,TkQuestionsInfo q where pc.questionid=q.questionid and q.isrightans=1 and pc.paperid=" + paperid + " order by pc.orderindex asc ";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 查询试题答案
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */
	public String getPaperAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer contentid) {
		String sql = "SELECT pa FROM TkPaperAnswer pa WHERE pa.classid=" + classid + " AND pa.userid=" + userid + " AND pa.bookcontentid=" + bookcontentid + " AND pa.contentid=" + contentid;
		List<TkPaperAnswer> pas = baseDAO.getObjects(sql);
		if (pas.size() > 0) {
			if (pas.get(0).getChildid() != 0) {
				boolean bl = true;
				for (int i = 0; i < pas.size(); i++) {
					TkPaperAnswer model = pas.get(i);
					if (model.getIsright().equals("0")) {
						bl = false;
						break;
					}
				}
				if (bl) {
					return "1";
				} else {
					return "0";
				}
			} else {
				return pas.get(0).getIsright();
			}
		} else {
			return "2";
		}
	}

	/**
	 * 查询试卷内的试题信息
	 * 
	 * @param paperid
	 * @return
	 */
	public List getQuestionsByPaper(Integer paperid) {
		String sql = "SELECT q FROM TkQuestionsInfo q,TkPaperContent p WHERE p.questionid=q.questionid and isrightans=1 AND p.paperid=" + paperid + " ORDER BY p.orderindex ASC";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 学员对题的详细作答情况
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */
	public List getQuestionsAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer questionid) {
		String sql = "SELECT pa.answer,pa.score,pa.isright,t.createdate FROM TkPaperAnswer pa,TkPaperContent pc,TkQuestionsInfo q,TkBookContentTask t "
				+ "WHERE t.taskid=pa.taskid AND pc.questionid=q.questionid AND pc.contentid=pa.contentid and isrightans=1 " + "AND q.questionid=" + questionid + " AND pa.classid=" + classid
				+ " AND pa.bookcontentid=" + bookcontentid + " AND pa.userid=" + userid + " order by t.createdate asc";
		List answers = baseDAO.getObjects(sql);
		List questionsAnswer = new ArrayList();
		for (int i = 0; i < answers.size(); i++) {
			Object[] objs = (Object[]) answers.get(i);
			Map m = new HashMap();
			m.put("answer", objs[0]);
			m.put("score", objs[1]);
			m.put("isright", objs[2]);
			m.put("createdate", objs[3]);
			questionsAnswer.add(m);
		}
		return questionsAnswer;
	}

	/**
	 * 查询复合体子题
	 * 
	 * @param parentid
	 * @return
	 */
	public List getChildQuestions(Integer parentid) {
		String sql = "select q from TkQuestionsInfo q where q.parentid=" + parentid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 复合体字体答题详情
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */

	public List getChildAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer questionid) {
		String sql = "SELECT pa.answer,pa.score,pa.isright,t.createdate FROM TkPaperAnswer pa,TkBookContentTask t " + "WHERE t.taskid=pa.taskid AND pa.childid=" + questionid + " AND pa.classid="
				+ classid + " AND pa.bookcontentid=" + bookcontentid + " AND pa.userid=" + userid + " order by t.createdate asc";
		List answers = baseDAO.getObjects(sql);
		List questionsAnswer = new ArrayList();
		for (int i = 0; i < answers.size(); i++) {
			Object[] objs = (Object[]) answers.get(i);
			Map m = new HashMap();
			m.put("answer", objs[0]);
			m.put("score", objs[1]);
			m.put("isright", objs[2]);
			m.put("createdate", objs[3]);
			questionsAnswer.add(m);
		}
		return questionsAnswer;
	}

	/**
	 * 作业完成情况统计
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param contentid
	 * @return
	 */
	public Map getStatQuestions(Integer classid, Integer bookcontentid, Integer contentid, String state) {
		String sql = "SELECT q.questionid,q.questionno,q.titlecontent,q.tkQuestionsType.type,q.tkQuestionsType.typename,q.option1,q.option2,q.option3,q.option4,q.option5,q.option6,q.option7,q.option8,q.option9,q.option10,(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pc.contentid and bookcontentid="
				+ bookcontentid
				+ " and classid="
				+ classid
				+ "),(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pc.contentid and isright=1  and bookcontentid="
				+ bookcontentid
				+ " and classid="
				+ classid
				+ "),(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pc.contentid and isright=0 and bookcontentid="
				+ bookcontentid
				+ " and classid="
				+ classid
				+ ") FROM TkQuestionsInfo q,TkPaperContent pc WHERE pc.questionid=q.questionid AND pc.contentid=" + contentid;
		List que = baseDAO.getObjects(sql);
		Map map = new HashMap();
		Object[] o = (Object[]) que.get(0);
		if (state.equals("1") || (state.equals("0") && Integer.parseInt(o[17].toString()) > 0)) {
			map.put("questionid", o[0]);
			map.put("questionno", o[1]);
			map.put("titlecontent", o[2]);
			map.put("questiontype", o[3]);
			map.put("typename", o[4]);
			map.put("option1", o[5]);
			map.put("option2", o[6]);
			map.put("option3", o[7]);
			map.put("option4", o[8]);
			map.put("option5", o[9]);
			map.put("option6", o[10]);
			map.put("option7", o[11]);
			map.put("option8", o[12]);
			map.put("option9", o[13]);
			map.put("option10", o[14]);
			map.put("paCount", o[15]);
			map.put("correctCount", o[16]);
			map.put("errorCount", o[17]);
			map.put("userStat", userAnswerStat(classid, bookcontentid, contentid, Integer.parseInt(o[0].toString())));
		}
		return map;
	}

	private List userAnswerStat(Integer classid, Integer bookcontentid, Integer contentid, Integer qid) {
		List userAnswerStat = new ArrayList();
		String sql = "SELECT u.userid,u.loginname,u.username,(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pa.contentid AND bookcontentid=pa.bookcontentid AND classid=pa.classid AND userid=pa.userid),(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pa.contentid AND bookcontentid=pa.bookcontentid AND classid=pa.classid AND userid=pa.userid AND isright=1),(SELECT COUNT(*) FROM TkPaperAnswer WHERE contentid=pa.contentid AND bookcontentid=pa.bookcontentid AND classid=pa.classid AND userid=pa.userid AND isright=0) FROM TkPaperAnswer pa,SysUserInfo u WHERE pa.userid=u.userid AND pa.contentid="
				+ contentid + " AND pa.bookcontentid=" + bookcontentid + " AND pa.classid=" + classid + " GROUP BY pa.userid";
		List baseSql = baseDAO.getObjects(sql);

		for (Object o : baseSql) {
			Object[] obj = (Object[]) o;
			Map m = new HashMap();
			m.put("loginname", obj[1]);
			m.put("username", obj[2]);
			m.put("paCount", obj[3]);
			m.put("correctCount", obj[4]);
			m.put("errorCount", obj[5]);
			m.put("childStat", getChildQuestionAnswer(classid, bookcontentid, Integer.parseInt(obj[0].toString()), qid));
			userAnswerStat.add(m);
		}
		return userAnswerStat;
	}

	private List getChildQuestionAnswer(Integer classid, Integer bookcontentid, Integer uid, Integer qid) {
		String sqlSum = "SELECT COUNT(*) FROM TkPaperAnswer WHERE classid=" + classid + " AND bookcontentid=" + bookcontentid + " AND userid=" + uid + " and childid=";
		String sqlError = "SELECT COUNT(*) FROM TkPaperAnswer WHERE isright=0 and classid=" + classid + " AND bookcontentid=" + bookcontentid + " AND userid=" + uid + " and childid=";
		String sqlCorrect = "SELECT COUNT(*) FROM TkPaperAnswer WHERE isright=1 and classid=" + classid + " AND bookcontentid=" + bookcontentid + " AND userid=" + uid + " and childid=";
		List list = new ArrayList();
		List<TkQuestionsInfo> childQuestion = getChildQuestions(qid);
		for (TkQuestionsInfo q : childQuestion) {
			Object sum = baseDAO.getObjects(sqlSum + q.getQuestionid()).get(0);
			Object err = baseDAO.getObjects(sqlError + q.getQuestionid()).get(0);
			Object corr = baseDAO.getObjects(sqlCorrect + q.getQuestionid()).get(0);
			Map m = new HashMap();
			m.put("sumCount", sum);
			m.put("errorCount", err);
			m.put("correctCount", corr);
			list.add(m);
		}
		return list;
	}

	/**
	 * 饼状图统计每个题型做错的次数
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param paperid
	 * @return
	 */
	public List getPiechartStat(Integer classid, Integer subjectid, Integer bookcontentid, Integer paperid) {
		String sql = "select qt from TkQuestionsType qt where subjectid=" + subjectid;
		List<TkQuestionsType> types = baseDAO.getObjects(sql);
		List list = new ArrayList();
		for (TkQuestionsType t : types) {
			String sqlCount = "SELECT COUNT(*) FROM TkPaperAnswer pa WHERE pa.classid=" + classid + " AND pa.bookcontentid=" + bookcontentid
					+ " AND pa.isright=0 AND pa.contentid IN(SELECT pc.contentid FROM TkPaperContent pc,TkQuestionsInfo q WHERE q.questionid=pc.questionid AND q.tkQuestionsType.typeid="
					+ t.getTypeid() + " AND pc.paperid=" + paperid + ")";
			int errorCount = ((Long) baseDAO.getRecordNumber(sqlCount)).intValue();
			Map m = new HashMap();
			m.put("typename", t.getTypename());
			m.put("errorCount", errorCount);
			list.add(m);
		}
		return list;
	}

	/**
	 * 饼状图统计每个题型首次作答做错的次数
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param paperid
	 * @return
	 */
	public List getFirstPiechartStat(Integer classid, Integer subjectid, Integer bookcontentid, Integer paperid) {
		String sql = "select qt from TkQuestionsType qt where subjectid=" + subjectid;
		List<TkQuestionsType> types = baseDAO.getObjects(sql);
		List list = new ArrayList();
		for (TkQuestionsType t : types) {
			String sqlCount = "SELECT COUNT(*) FROM TkPaperAnswer pa WHERE pa.tasktype=0 and pa.classid=" + classid + " AND pa.bookcontentid=" + bookcontentid
					+ " AND pa.isright=0 AND pa.contentid IN(SELECT pc.contentid FROM TkPaperContent pc,TkQuestionsInfo q WHERE q.questionid=pc.questionid AND q.tkQuestionsType.typeid="
					+ t.getTypeid() + " AND pc.paperid=" + paperid + ")";
			int errorCount = ((Long) baseDAO.getRecordNumber(sqlCount)).intValue();
			Map m = new HashMap();
			m.put("typename", t.getTypename());
			m.put("errorCount", errorCount);
			list.add(m);
		}
		return list;
	}

	public int getNumberQuestions(Integer contentid, Integer bookcontentid, Integer classid) {
		String sql = "select count(p.answerid) from TkPaperAnswer p where p.contentid=" + contentid + " and p.bookcontentid=" + bookcontentid + " and p.classid=" + classid + " and type=1";
		return Integer.parseInt(baseDAO.getObjects(sql).get(0).toString());

	}

	public int getErrorNumberQuestions(Integer contentid, Integer bookcontetid, Integer classid) {
		String sql = "select count(p.answerid) from TkPaperAnswer p where p.contentid=" + contentid + " and p.bookcontentid=" + bookcontetid + " and p.classid=" + classid
				+ " and type=1 and isright=0";
		return Integer.parseInt(baseDAO.getObjects(sql).get(0).toString());
	}
}