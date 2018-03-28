package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkPaperFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkPaperFileManager {
	/**
	 *根据id获取试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile getTkPaperFile(String fileid);

	/**
	 *根据id获取试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile getTkPaperFile(Integer fileid);

	/**
	 *增加试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile addTkPaperFile(TkPaperFile tkPaperFile);

	/**
	 *删除试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile delTkPaperFile(String fileid);

	/**
	 *删除试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile delTkPaperFile(TkPaperFile tkPaperFile);

	/**
	 *修改试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile updateTkPaperFile(TkPaperFile tkPaperFile);

	/**
	 *获取试卷附件集合
	 *@return List
	 */
	public List getTkPaperFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页试卷附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFiles (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 *获取一页试卷附件集合，和所属学科名称
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFilesAndSubjectName (List<SearchModel> condition,String orderby,int start,int pagesize);
	
	/**
	 * 查询所有被使用的试卷分类id
	 */
	public List getPaperTypeids();
	
	/**
	 * 查询试卷附件中的所有年份
	 */
	public List getPaperFileYears();
	
	/**
	 * 按照学科分类查询试卷被下载总次数
	 */
	public List getSubjectCount();
	
	/**
	 * 按照学科分类查询试卷被下载次数
	 */
	public List getDownloadCount();
	
	/**
	 * 查询有试卷的学科分类
	 */
	public List getSubjectList();
	
	/**
	 * 查询各学科试卷每天被下载次数
	 */
	public List getDownloadCountByDate();
}