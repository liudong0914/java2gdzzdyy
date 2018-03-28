package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassErrorQuestion;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 班级错题集
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkClassErrorQuestionManager {
	/**
	 * 根据id获取班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion getTkClassErrorQuestion(String cerrorid);

	/**
	 * 根据id获取班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion getTkClassErrorQuestion(Integer cerrorid);

	/**
	 * 增加班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion addTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion);

	/**
	 * 删除班级错题集
	 * 
	 * @param cerrorid
	 *            Integer
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion delTkClassErrorQuestion(String cerrorid);

	/**
	 * 删除班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion delTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion);

	/**
	 * 修改班级错题集
	 * 
	 * @param tkClassErrorQuestion
	 *            TkClassErrorQuestion
	 * @return TkClassErrorQuestion
	 */
	public TkClassErrorQuestion updateTkClassErrorQuestion(TkClassErrorQuestion tkClassErrorQuestion);

	/**
	 * 获取班级错题集集合
	 * 
	 * @return List
	 */
	public List getTkClassErrorQuestions(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页班级错题集集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkClassErrorQuestions(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 查询所有错题集的type为1的contentid
	 * */
	public List getContentidByType(int bookcontentid, int classid, String type);

	public void delete(List list);

}