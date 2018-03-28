package com.wkmk.tk.service;

import java.util.List;
import java.util.Map;

import com.wkmk.tk.bo.TkPaperAnswer;
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
@SuppressWarnings("rawtypes")
public interface TkPaperAnswerManager {
	/**
	 * 根据id获取试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer getTkPaperAnswer(String answerid);

	/**
	 * 根据id获取试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer getTkPaperAnswer(Integer answerid);

	/**
	 * 增加试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer addTkPaperAnswer(TkPaperAnswer tkPaperAnswer);

	/**
	 * 删除试卷作答信息
	 * 
	 * @param answerid
	 *            Integer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer delTkPaperAnswer(String answerid);

	/**
	 * 删除试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer delTkPaperAnswer(TkPaperAnswer tkPaperAnswer);

	/**
	 * 修改试卷作答信息
	 * 
	 * @param tkPaperAnswer
	 *            TkPaperAnswer
	 * @return TkPaperAnswer
	 */
	public TkPaperAnswer updateTkPaperAnswer(TkPaperAnswer tkPaperAnswer);

	/**
	 * 获取试卷作答信息集合
	 * 
	 * @return List
	 */
	public List getTkPaperAnswers(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取试卷作答信息
	 */
	public TkPaperAnswer getTkPaperAnswer(String userid, String taskid, String contentid, String type);

	/**
	 * 获取试卷子题作答信息集合
	 */
	public List getAllChildTkPaperAnswer(String userid, String taskid, String contentid, String type);

	/**
	 * 获取试卷作答信息条数
	 */
	public int getTkPaperAnswerCountsByBookcontentid(Integer bookcontentid);
	
	/**
	 *获取试卷作答信息条数
	 */
	public int getTkPaperAnswerCountsByBookid(String bookid, String userid);
	
	/**
	 *获取班级已作答学生列表
	 */
	public List getTkPaperAnswerStudentsByClassid(String bookcontentid, String classid);

	/**
	 * 获取一页试卷作答信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperAnswers(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 查询班级内的学员
	 * 
	 * @param classid
	 * @return
	 */
	public List getStudentsByClass(Integer classid);

	/**
	 * 查询作业内容信息
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @return
	 */
	public List getPaperContent(Integer paperid);

	/**
	 * 查询试题答案
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */
	public String getPaperAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer questionid);

	/**
	 * 查询试卷内的试题信息
	 * 
	 * @param paperid
	 * @return
	 */
	public List getQuestionsByPaper(Integer paperid);

	/**
	 * 学员对题的详细作答情况
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */
	public List getQuestionsAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer questionid);

	/**
	 * 查询复合体子题
	 * 
	 * @param parentid
	 * @return
	 */
	public List getChildQuestions(Integer parentid);

	/**
	 * 复合体字体答题详情
	 * 
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @param questionid
	 * @return
	 */
	public List getChildAnswer(Integer classid, Integer userid, Integer bookcontentid, Integer questionid);

	/**
	 * 作业完成情况统计
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param contentid
	 * @return
	 */
	public Map getStatQuestions(Integer classid, Integer bookcontentid, Integer contentid,String state);

	/**
	 * 饼状图统计每个题型做错的次数
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param paperid
	 * @return
	 */
	public List getPiechartStat(Integer classid, Integer subjectid, Integer bookcontentid, Integer paperid);

	/**
	 * 饼状图统计每个题型首次作答做错的次数
	 * 
	 * @param classid
	 * @param bookcontentid
	 * @param paperid
	 * @return
	 */
	public List getFirstPiechartStat(Integer classid, Integer subjectid, Integer bookcontentid, Integer paperid);

	/**
	 * 求答题次数
	 * */
	public int getNumberQuestions(Integer contentid, Integer bookcontentid, Integer classid);

	/**
	 * 求错误答题次数
	 * */
	public int getErrorNumberQuestions(Integer contentid, Integer bookcontetid, Integer classid);
}