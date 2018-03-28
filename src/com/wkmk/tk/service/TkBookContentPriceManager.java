package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentPrice;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业本内容定价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkBookContentPriceManager {
	/**
	 *根据id获取作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice getTkBookContentPrice(String priceid);

	/**
	 *根据id获取作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice getTkBookContentPrice(Integer priceid);

	/**
	 *增加作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice addTkBookContentPrice(TkBookContentPrice tkBookContentPrice);

	/**
	 *删除作业本内容定价
	 *@param priceid Integer
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice delTkBookContentPrice(String priceid);

	/**
	 *删除作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice delTkBookContentPrice(TkBookContentPrice tkBookContentPrice);

	/**
	 *修改作业本内容定价
	 *@param tkBookContentPrice TkBookContentPrice
	 *@return TkBookContentPrice
	 */
	public TkBookContentPrice updateTkBookContentPrice(TkBookContentPrice tkBookContentPrice);

	/**
	 *获取作业本内容定价集合
	 *@return List
	 */
	public List getTkBookContentPrices (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页作业本内容定价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentPrices (List<SearchModel> condition, String orderby, int start, int pagesize);

}