package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 试卷信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkPaperInfoManager {
	/**
	 * 根据id获取试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo getTkPaperInfo(String paperid);

	/**
	 * 根据id获取试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo getTkPaperInfo(Integer paperid);

	/**
	 * 增加试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo addTkPaperInfo(TkPaperInfo tkPaperInfo);

	/**
	 * 删除试卷信息
	 * 
	 * @param paperid
	 *            Integer
	 * @return TkPaperInfo
	 */
	public TkPaperInfo delTkPaperInfo(String paperid);

	/**
	 * 删除试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo delTkPaperInfo(TkPaperInfo tkPaperInfo);

	/**
	 * 修改试卷信息
	 * 
	 * @param tkPaperInfo
	 *            TkPaperInfo
	 * @return TkPaperInfo
	 */
	public TkPaperInfo updateTkPaperInfo(TkPaperInfo tkPaperInfo);

	/**
	 * 获取试卷信息集合
	 * 
	 * @return List
	 */
	public List getTkPaperInfos(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页试卷信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkPaperInfos(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 查询bookcontent的所欲paperid
	 * */
	public List getAllbookcontentPaperid();

	public List getPaperQuestions(Integer paperid);
}