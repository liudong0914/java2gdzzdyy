package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 作业本内容关联试题
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkBookContentQuestionManager {
	/**
	 * 根据id获取作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion getTkBookContentQuestion(String tid);

	/**
	 * 根据id获取作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion getTkBookContentQuestion(Integer tid);

	/**
	 * 增加作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion addTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion);

	/**
	 * 删除作业本内容关联试题
	 * 
	 * @param tid
	 *            Integer
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion delTkBookContentQuestion(String tid);

	/**
	 * 删除作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion delTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion);

	/**
	 * 修改作业本内容关联试题
	 * 
	 * @param tkBookContentQuestion
	 *            TkBookContentQuestion
	 * @return TkBookContentQuestion
	 */
	public TkBookContentQuestion updateTkBookContentQuestion(TkBookContentQuestion tkBookContentQuestion);

	/**
	 * 获取作业本内容关联试题集合
	 * 
	 * @return List
	 */
	public List getTkBookContentQuestions(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取作业本内容关联试题集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsInfosByBookcontentid(String bookcontentid, String type, String isrightans);

	/**
	 * 获取一页作业本内容关联试题集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContentQuestions(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据bookcontent获取关联题目
	 * */
	public PageList getPageQuestions(String bookcontentid, String questionno, String title, String difficult, String type, String sorderindex, String typeid, int start,
			int pagesize);

	/**
	 * 获取未关联的题
	 * */
	public PageList getPageUnQuestions(String bookcontentid, String questionno, String title, String difficult, String type, String sorderindex, int subjectid, int gradeid,
			String typeid, int start, int pagesize);

	/**
	 * 根据条件删除关联题
	 * */
	public void delQuestions(String type, String bookcontentid, String questionid);
}