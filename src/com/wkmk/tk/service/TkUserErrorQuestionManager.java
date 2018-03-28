package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkUserErrorQuestion;
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
public interface TkUserErrorQuestionManager {
	/**
	 * 根据id获取个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion getTkUserErrorQuestion(String uerrorid);

	/**
	 * 根据id获取个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion getTkUserErrorQuestion(Integer uerrorid);

	/**
	 * 增加个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion addTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion);

	/**
	 * 删除个人错题集
	 * 
	 * @param uerrorid
	 *            Integer
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion delTkUserErrorQuestion(String uerrorid);

	/**
	 * 删除个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion delTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion);

	/**
	 * 修改个人错题集
	 * 
	 * @param tkUserErrorQuestion
	 *            TkUserErrorQuestion
	 * @return TkUserErrorQuestion
	 */
	public TkUserErrorQuestion updateTkUserErrorQuestion(TkUserErrorQuestion tkUserErrorQuestion);

	/**
	 * 获取个人错题集集合
	 * 
	 * @return List
	 */
	public List getTkUserErrorQuestions(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取个人错题集集合
	 * 
	 * @return List
	 */
	public List getTkUserErrorQuestions(String userid, String bookcontentid, String classid, String type);

	/**
	 * 获取一页个人错题集集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkUserErrorQuestions(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据条件查询错题集
	 */
	public List getUserErrorQuestions(String bookid, String classid, String userid);
	
	/**
	 * 错题统计，'作业本题',数量
	 */
	public List getUserErrorQuestionsOfType1();
	
	/**
	 * 错题统计，'课前预习题',数量
	 */
	public List getUserErrorQuestionsOfType2();
	
	/**
	 * 错题统计，'举一反三题',数量
	 */
	public List getUserErrorQuestionsOfType3();
	/**
	 * 错题统计，总数量
	 */
	public List getUserErrorQuestionsOfAll();
	/**
	 * 错题统计，总数量最大值
	 */
	public List getUserErrorQuestionsOfAllMaxNum();
	
	/**
	 * 错题统计，人数
	 */
	public List getUserErrorQuestionsOfCountUser();
	/**
	 * 错题统计，人数最大值
	 */
	public List getUserErrorQuestionsOfCountUserMaxNum();
	/**
	 * 错题统计，作业本题总数
	 */
	public List getUserErrorQuestionsOfCountType1();
	/**
	 * 错题统计，课前预习题总数
	 */
	public List getUserErrorQuestionsOfCountType2();
	/**
	 * 错题统计，举一反三题总数
	 */
	public List getUserErrorQuestionsOfCountType3();
	/**
	 * 错题总数
	 */
	public List getUserErrorQuestionsOfCountType();
	/**
	 * 错题人总数
	 */
	public List getUserErrorQuestionsOfCountUserAll();
	/**
	 * 1书本总题数
	 */
	public List getUserErrorQuestionsOfBook(String bookid);
	/**
	 * 1书本总题数
	 */
	public List getUserErrorQuestionsOfBookNum(String bookid);
	/**
	 * 1书本总题数最大值
	 */
	public List getUserErrorQuestionsOfBookMax(String bookid);
	/**
	 * 1书本总错题数
	 */
	public List getUserErrorQuestionsOfBookError(String bookid);
	/**
	 * 1试题总题数
	 */  
	public List getUserErrorQuestionsOfQuestions(String paperid);
	/**
	 * 1试题总题数最大值
	 */  
	public List getUserErrorQuestionsOfQuestionsMax(String paperid);
	/**
	 * 1试题总错题数
	 */  
	public List getUserErrorQuestionsOfQuestionsError(String paperid);
	/**
	 * 1试题总题数
	 */  
	public List getUserErrorQuestionsOfQuestionsNum(String paperid);
}