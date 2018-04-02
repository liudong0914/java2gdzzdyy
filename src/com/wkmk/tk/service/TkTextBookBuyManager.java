package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkTextBookBuy;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkTextBookBuyManager {
	/**
	 *根据id获取教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy getTkTextBookBuy(String textbookbuyid);

	/**
	 *根据id获取教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy getTkTextBookBuy(Integer textbookbuyid);

	/**
	 *增加教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy addTkTextBookBuy(TkTextBookBuy tkTextBookBuy);

	/**
	 *删除教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy delTkTextBookBuy(String textbookbuyid);

	/**
	 *删除教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy delTkTextBookBuy(TkTextBookBuy tkTextBookBuy);

	/**
	 *修改教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy updateTkTextBookBuy(TkTextBookBuy tkTextBookBuy);

	/**
	 *获取教材基本信息表集合
	 *@return List
	 */
	public List getTkTextBookBuys (List<SearchModel> condition, String orderby, int pagesize);

	public List getTkTextBookBuys(String textbookbuyids,String sorderindex);
	/**
	 *获取一页教材基本信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkTextBookBuys (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList getTkTextBookBuysOfPage(String textbookname, String username, String createdate, String recipientname, String isdelivery,String sorderindex,int start, int size);

}