package com.wkmk.zx.service;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpQuestionBuy;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface ZxHelpQuestionBuyManager {
	/**
	 *根据id获取在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy getZxHelpQuestionBuy(String buyid);

	/**
	 *根据id获取在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy getZxHelpQuestionBuy(Integer buyid);

	/**
	 *增加在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy addZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy);

	/**
	 *删除在线答疑购买
	 *@param buyid Integer
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy delZxHelpQuestionBuy(String buyid);

	/**
	 *删除在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy delZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy);

	/**
	 *修改在线答疑购买
	 *@param zxHelpQuestionBuy ZxHelpQuestionBuy
	 *@return ZxHelpQuestionBuy
	 */
	public ZxHelpQuestionBuy updateZxHelpQuestionBuy(ZxHelpQuestionBuy zxHelpQuestionBuy);

	/**
	 *获取在线答疑购买集合
	 *@return List
	 */
	public List getZxHelpQuestionBuys (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页在线答疑购买集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpQuestionBuys (List<SearchModel> condition, String orderby, int start, int pagesize);

}