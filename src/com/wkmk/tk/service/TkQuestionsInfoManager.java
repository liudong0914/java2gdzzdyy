package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

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
public interface TkQuestionsInfoManager {
	/**
	 * 根据id获取题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo getTkQuestionsInfo(String questionid);

	/**
	 * 根据id获取题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo getTkQuestionsInfo(Integer questionid);

	/**
	 * 增加题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo addTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo);

	/**
	 * 删除题库试题信息
	 * 
	 * @param questionid
	 *            Integer
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo delTkQuestionsInfo(String questionid);

	/**
	 * 删除题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo delTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo);

	/**
	 * 修改题库试题信息
	 * 
	 * @param tkQuestionsInfo
	 *            TkQuestionsInfo
	 * @return TkQuestionsInfo
	 */
	public TkQuestionsInfo updateTkQuestionsInfo(TkQuestionsInfo tkQuestionsInfo);

	/**
	 * 获取题库试题信息集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsInfos(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取题库所有子题信息集合
	 * 
	 * @return List
	 */
	public List getAllChildTkQuestionsInfos(Integer parentid);

	/**
	 * 获取一页题库试题信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsInfos(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 获取搜有typeid
	 * */
	public List getAlltypeids();

	/**
	 * 获取所有parentid
	 * */
	public List getAllparentids();

	/**
	 * 获取举一反三的题
	 * */
	public PageList getAllsimilarQuestions(String questionid, String questionno, String title, String difficult, String sorderindex, String type, int subjectid, int gradeid,
			int start, int size);

	/**
	 * 获取举一反三未关联的题
	 * */
	public PageList getUnAllsimilarQuestions(String questionid, String questionno, String title, String difficult, String sorderindex, String type, int subjectid, int gradeid,
			int start, int size);

	/**
	 * 获取已关联微课
	 * */
	public PageList getAllfimInfo(String questionid, String title, String actor, String sorderindex, int start, int size);

	/**
	 * 获取该学科该年年级下未关联微课
	 * */
	public PageList getUnAllfimInfo(String questionid, String title, String actor, String gradeid, String sorderindex, int start, int size);

	/**
	 * 根据学科 和知识点查询试题
	 * */
	public PageList getQuestionsInfo(String title, String subjectid, String knopointid, String knopointno, String questiontypeid, String difficult, int start, int size);

	/**
	 * 根据学科和学段查询试题
	 * */
	public PageList getQuestionsInfo(String title, String subjectid, String gradetype, String questiontypeid, String difficult, int start, int pagesize);

	/**
	 * 根据条件查询题(智能组卷)
	 * */
	public List getQuestionsInfo(String questiontype, String difficult, int num);

	/**
	 * 根据条件查询题(智能组卷)
	 * */
	public List getQuestionsInfo(String questiontype, String difficult, int num, String knopoints);

	/**
	 * 查询answer表中错误的个数
	 * */
	public int getErrorNum(String contentid, String classid);

	/***
	 * 查询子题中是否含有没有固定标准答案的题
	 * */
	public boolean getishaveIsrights(int questionid);
	/**
	 * 得到题库中questionid的最大值
	 * */
	public int getMaxQuestionid();
	/**
	 * 得到题型中typeno的最大值
	 * */
	public int getMaxTypeno();
	/**
	 * 获取学科下的年级
	 * */
	public List getGradeids(int subjectid);
}