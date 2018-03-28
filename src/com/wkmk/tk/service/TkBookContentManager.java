package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContent;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 作业本内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkBookContentManager {
	/**
	 * 根据id获取作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent getTkBookContent(String bookcontentid);

	/**
	 * 根据id获取作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent getTkBookContent(Integer bookcontentid);

	/**
	 * 增加作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent addTkBookContent(TkBookContent tkBookContent);

	/**
	 * 删除作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent delTkBookContent(String contentid);

	/**
	 * 删除作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent delTkBookContent(TkBookContent tkBookContent);

	/**
	 * 修改作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent updateTkBookContent(TkBookContent tkBookContent);

	/**
	 * 获取作业本内容集合
	 * 
	 * @return List
	 */
	public List getTkBookContents(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页作业本内容集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContents(List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList getTkBookContentsOfPage(String title,  String booktitle, String sorderindex,int start, int size);

	/**
	 * 获取所有bookid
	 * */
	public List getAllbookids();
	
	public List getAllbookidsWithMp3path();
	
	public List getAllbookidsWithPaperid();

	/**
	 * 获取所有parentno
	 * */
	public List getAllparentnos(String bookid);

	/**
	 * 根据bookid查询所有作业本内容
	 * */
	public List getAllBookContentByBook(String bookid);

	public List getAllBookContentByBook(String bookid, String orderby);

	public List getAllBookContentByBook2(String bookid, String orderby);

	/**
	 * 根据bookid查询作业本错题
	 * */
	public List getErrorQuestions(String bookid, String classid);

	/**
	 * 获取某一本书的最大contentno
	 * */
	public String getMaxContentno(String bookid);

	/**
	 * 根据授权查询作业本章节
	 * */
	public List getBookContentByPower(Integer userid, String type);
	
	/**
	 * 根据用户查询作业本章节
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-17 下午2:59:16 
	* @lastModified  2016-10-17 下午2:59:16 
	* @version  1.0 
	* @param userid
	* @return
	 */
	public List getBookContentByuserid(Integer userid);
	
	public List getBookContentByuserid2(Integer userid);
	
	/**
	 * 根据作业编号获取信息
	 */
	public TkBookContent getTkBookContentByContentno(String contentno, String bookid);
	
	/**
	 * 
	  * 方法描述：搜索查询作业本内容
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-22 下午1:24:52
	 */
	public List getTkBookContentBySearch(String keywords);
	/**
	 * 
	  * 根据章节下是否有微课查询章节
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 
	  * @version: 2016-11-22 下午1:24:52
	 */
	public List getTkBookContentByFilm();
}