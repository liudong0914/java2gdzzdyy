package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsSimilar;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 习题举一反三
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkQuestionsSimilarManager {
	/**
	 * 根据id获取习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar getTkQuestionsSimilar(String tid);

	/**
	 * 根据id获取习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar getTkQuestionsSimilar(Integer tid);

	/**
	 * 增加习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar addTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar);

	/**
	 * 删除习题举一反三
	 * 
	 * @param tid
	 *            Integer
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar delTkQuestionsSimilar(String tid);

	/**
	 * 删除习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar delTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar);

	/**
	 * 修改习题举一反三
	 * 
	 * @param tkQuestionsSimilar
	 *            TkQuestionsSimilar
	 * @return TkQuestionsSimilar
	 */
	public TkQuestionsSimilar updateTkQuestionsSimilar(TkQuestionsSimilar tkQuestionsSimilar);

	/**
	 * 获取习题举一反三集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsSimilars(List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 * 获取习题举一反三集合
	 * @return List
	 */
	public List getTkQuestionsSimilars(String questionid);

	public int getCountTkQuestionsSimilars(Integer questionid);
	
	/**
	 * 获取一页习题举一反三集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsSimilars(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据questionid与similarquestionid删除数据
	 * */
	public void delTkQuestionsSimilars(String questionid, String similarquestionid);
}