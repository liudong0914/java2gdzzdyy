package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 作业本信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public interface TkBookInfoManager {
	/**
	 * 根据id获取作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo getTkBookInfo(String bookid);

	/**
	 * 根据id获取作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo getTkBookInfo(Integer bookid);

	/**
	 * 增加作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo addTkBookInfo(TkBookInfo tkBookInfo);

	/**
	 * 删除作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo delTkBookInfo(String bookid);

	/**
	 * 删除作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo delTkBookInfo(TkBookInfo tkBookInfo);

	/**
	 * 修改作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo updateTkBookInfo(TkBookInfo tkBookInfo);

	/**
	 * 获取作业本信息集合
	 * 
	 * @return List
	 */
	public List getTkBookInfos(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取作业本信息集合
	 * 
	 * @return List
	 */
	public List getAllTkBookInfos();

	/**
	 * 获取一页作业本信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookInfos(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据授权查询作业本
	 * */
	public List getBookInfosByPower(Integer userid, String type);
	
	/**
	 * 根源用户获取作业本
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-17 下午2:53:57 
	* @lastModified  2016-10-17 下午2:53:57 
	* @version  1.0 
	* @param userid
	* @return
	 */
	public List getBookInfosByuserid(Integer userid);
	/**
	 * 根据作业本下面是否有微课查询作业本
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-17 下午2:53:57 
	* @lastModified  2016-10-17 下午2:53:57 
	* @version  1.0 
	* @param userid
	* @return
	 */
	public List getBookInfosByFilm();
}