package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperContent;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 试卷内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkPaperContentManager {
	/**
	 * 根据id获取试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent getTkPaperContent(String contentid);

	/**
	 * 根据id获取试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent getTkPaperContent(Integer contentid);

	/**
	 * 增加试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent addTkPaperContent(TkPaperContent tkPaperContent);

	/**
	 * 删除试卷内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkPaperContent
	 */
	public TkPaperContent delTkPaperContent(String contentid);

	/**
	 * 删除试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent delTkPaperContent(TkPaperContent tkPaperContent);

	/**
	 * 修改试卷内容
	 * 
	 * @param tkPaperContent
	 *            TkPaperContent
	 * @return TkPaperContent
	 */
	public TkPaperContent updateTkPaperContent(TkPaperContent tkPaperContent);

	/**
	 * 获取试卷内容集合
	 * 
	 * @return List
	 */
	public List getTkPaperContents(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取移动端试卷内容集合【有标准答案】
	 * 
	 * @return List
	 */
	public List getMobileTkPaperContents(Integer paperid);

	/**
	 * 获取试卷内容条数
	 */
	public int getTkPaperContentCounts(Integer paperid);

	/**
	 * 获取一页试卷内容集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperContents(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 查询试卷中的试题
	 * */
	public List getQuestions(int paperid);

	/**
	 * 查询试卷中有固定标准答案的题返回contentid
	 * */
	public List getContentids(int paperid);

	/**
	 * 获取试卷最大的orderindex
	 * */
	public int getMaxOrderindex(int paperid);

	/**
	 * 查询试卷中的试题
	 * */
	public PageList getQuestions(int paperid, String questionno, String title, String difficult, String sorderindex, int start, int size);
}