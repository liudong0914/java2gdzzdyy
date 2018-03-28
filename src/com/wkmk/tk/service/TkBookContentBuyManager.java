package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentBuy;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本内容购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentBuyManager {
	/**
	 *根据id获取作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy getTkBookContentBuy(String buyid);

	/**
	 *根据id获取作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy getTkBookContentBuy(Integer buyid);

	/**
	 *增加作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy addTkBookContentBuy(TkBookContentBuy tkBookContentBuy);

	/**
	 *删除作业本内容购买
	 *@param buyid Integer
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy delTkBookContentBuy(String buyid);

	/**
	 *删除作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy delTkBookContentBuy(TkBookContentBuy tkBookContentBuy);

	/**
	 *修改作业本内容购买
	 *@param tkBookContentBuy TkBookContentBuy
	 *@return TkBookContentBuy
	 */
	public TkBookContentBuy updateTkBookContentBuy(TkBookContentBuy tkBookContentBuy);

	/**
	 *获取作业本内容购买集合
	 *@return List
	 */
	public List getTkBookContentBuys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页作业本内容购买集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentBuys (List<SearchModel> condition, String orderby, int start, int pagesize);

}