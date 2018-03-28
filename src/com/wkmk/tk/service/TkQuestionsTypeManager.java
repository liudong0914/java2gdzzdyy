package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkQuestionsType;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 题库试题类型
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkQuestionsTypeManager {
	/**
	 * 根据id获取题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType getTkQuestionsType(String typeid);

	/**
	 * 根据id获取题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType getTkQuestionsType(Integer typeid);

	/**
	 * 增加题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType addTkQuestionsType(TkQuestionsType tkQuestionsType);

	/**
	 * 删除题库试题类型
	 * 
	 * @param typeid
	 *            Integer
	 * @return TkQuestionsType
	 */
	public TkQuestionsType delTkQuestionsType(String typeid);

	/**
	 * 删除题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType delTkQuestionsType(TkQuestionsType tkQuestionsType);

	/**
	 * 修改题库试题类型
	 * 
	 * @param tkQuestionsType
	 *            TkQuestionsType
	 * @return TkQuestionsType
	 */
	public TkQuestionsType updateTkQuestionsType(TkQuestionsType tkQuestionsType);

	/**
	 * 获取题库试题类型集合
	 * 
	 * @return List
	 */
	public List getTkQuestionsTypes(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取题库试题类型
	 */
	public TkQuestionsType getTkQuestionsType(String type, Integer subjectid);

	/**
	 * 获取一页题库试题类型集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkQuestionsTypes(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 查询该学科类型下的某种题目类型的个数
	 * */
	public int getTkQuestionsTypesNums(String subjectid, String xueduan, String questiontypeid, String userid);

	/**
	 * 查询该学科类型下的某种题目的百分比
	 * */
	public String getTkQuestionsTypesPercent(String subjectid, String xueduan, String questiontypeid, String userid);

	/**
	 * 求各学科类型下的某种题目的总分
	 * */
	public Double getTkQuestionsTypesScore(String subjectid, String xueduan, String questiontypeid, String userid);

	/**
	 * 获取总试题总个数
	 * */
	public int getTkQuestionsSum(String subjectid, String xueduan, String userid);
}