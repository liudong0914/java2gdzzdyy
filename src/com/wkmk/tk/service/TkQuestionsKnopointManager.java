package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsKnopoint;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库知识点
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkQuestionsKnopointManager {
	/**
	 * 根据id获取题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint getTkQuestionsKnopoint(String tid);

	/**
	 * 根据id获取题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint getTkQuestionsKnopoint(Integer tid);

	/**
	 * 增加题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint addTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint);

	/**
	 * 删除题库知识点
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint delTkQuestionsKnopoint(String tid);

	/**
	 * 删除题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint delTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint);

	/**
	 * 修改题库知识点
	 * 
	 * @param tkQuestionsKnopoint
	 *            TkQuestionsKnopoint
	 * @return TkQuestionsKnopoint
	 */
	public TkQuestionsKnopoint updateTkQuestionsKnopoint(TkQuestionsKnopoint tkQuestionsKnopoint);

	/**
	 * 获取题库知识点集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsKnopoints(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取所有知识点id
	 * 
	 * @return List
	 */
	public List getAllKnopointids();

	/**
	 * 获取一页题库知识点集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsKnopoints(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据questionid查询EduKnopointInfo对象
	 * */
	public List getEduKnopointInfoByQuestionid(Integer questionid);
	
	/**
	 * 删除questionid下的知识点
	 * */
	public void deleteTkQuestionKnopoints(Integer questionid);
}