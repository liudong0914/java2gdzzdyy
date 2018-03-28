package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperCart;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库组卷试题蓝
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkPaperCartManager {
	/**
	 * 根据id获取题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart getTkPaperCart(String cartid);

	/**
	 * 根据id获取题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart getTkPaperCart(Integer cartid);

	/**
	 * 增加题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart addTkPaperCart(TkPaperCart tkPaperCart);

	/**
	 * 删除题库组卷试题蓝
	 * 
	 * @param cartid
	 *            Integer
	 * @return TkPaperCart
	 */
	public TkPaperCart delTkPaperCart(String cartid);

	/**
	 * 删除题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart delTkPaperCart(TkPaperCart tkPaperCart);

	/**
	 * 修改题库组卷试题蓝
	 * 
	 * @param tkPaperCart
	 *            TkPaperCart
	 * @return TkPaperCart
	 */
	public TkPaperCart updateTkPaperCart(TkPaperCart tkPaperCart);

	/**
	 * 获取题库组卷试题蓝集合
	 * 
	 * @return List
	 */
	public List getTkPaperCarts(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页题库组卷试题蓝集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperCarts(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 删除试题
	 * */
	public void deleteQuestion(String subjectid, String xueduanid, String questionid, String userid);

	/**
	 * 查询已经含有的questionid
	 * */
	public List getAllQuestionid(String subjectid, String xueduanid, String userid);

	/**
	 * 查询当前试卷中最大的orderindex
	 * */
	public int getMaxOrderindex(String userid, String subjectid, String xueduanid);

	/**
	 * 清空试卷中的试题
	 * */
	public void delAllQuestions(String subjectid, String xueduanid, String userid);

	/**
	 * 获取试卷中的所有试题
	 * */
	public List getAllPaperQuestions(String subjectid, String xueduanid, String userid);

	/**
	 * 查询试卷具体分数
	 * */
	public String getPaperCartScore(String subjectid, String xueduanid, String userid, String questionid);

	/**
	 * 排序前查询orderindex小的对象
	 * */
	public TkPaperCart getSmallTkPaperCart(String subjectid, String xueduanid, String userid, String orderindex);

	/**
	 * 排序前查询orderindex大的对象
	 * */
	public TkPaperCart getBigTkPaperCart(String subjectid, String xueduanid, String userid, String orderindex);

	/**
	 * 删除LIST
	 * */
	public void deleteList(List list);
}
