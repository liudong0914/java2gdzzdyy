package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkUserBookContent;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 客户端用户作业本内容授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkUserBookContentManager {
	/**
	 *根据id获取客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent getTkUserBookContent(String tid);

	/**
	 *根据id获取客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent getTkUserBookContent(Integer tid);

	/**
	 *增加客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent addTkUserBookContent(TkUserBookContent tkUserBookContent);

	/**
	 *删除客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent delTkUserBookContent(String tid);

	/**
	 *删除客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent delTkUserBookContent(TkUserBookContent tkUserBookContent);

	/**
	 *修改客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent updateTkUserBookContent(TkUserBookContent tkUserBookContent);

	/**
	 *获取客户端用户作业本内容授权集合
	 *@return List
	 */
	public List getTkUserBookContents (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取客户端用户作业本内容授权id集合
 	 *@return List
	 */
	public List getTkUserBookContentOfIds(Integer userid, String objid);
	
	/**
	 *获取一页客户端用户作业本内容授权集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkUserBookContents (List<SearchModel> condition, String orderby, int start, int pagesize);

	
	/**
	 * 获取指定用户被授权的作业内容
	 * @param userid
	 * @return
	 */
	public List findBookContentsByUser(String userid);
	
	/**
	 * 根据userid和bookid删除作业本内容
	 * @param userid
	 * @param bookid
	 */
	public void delBookContentByUserAndBook(String userid,String bookid);
}