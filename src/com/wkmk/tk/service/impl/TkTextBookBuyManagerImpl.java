package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.service.TkTextBookBuyManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookBuyManagerImpl implements TkTextBookBuyManager{

	private BaseDAO baseDAO;
	private String modelname = "教材基本信息表";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy getTkTextBookBuy(String textbookbuyid){
		Integer iid = Integer.valueOf(textbookbuyid);
		return  (TkTextBookBuy)baseDAO.getObject(modelname,TkTextBookBuy.class,iid);
	}

	/**
	 *根据id获取教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy getTkTextBookBuy(Integer textbookbuyid){
		return  (TkTextBookBuy)baseDAO.getObject(modelname,TkTextBookBuy.class,textbookbuyid);
	}

	/**
	 *增加教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy addTkTextBookBuy(TkTextBookBuy tkTextBookBuy){
		return (TkTextBookBuy)baseDAO.addObject(modelname,tkTextBookBuy);
	}

	/**
	 *删除教材基本信息表
	 *@param textbookbuyid Integer
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy delTkTextBookBuy(String textbookbuyid){
		TkTextBookBuy model = getTkTextBookBuy(textbookbuyid);
		return (TkTextBookBuy)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy delTkTextBookBuy(TkTextBookBuy tkTextBookBuy){
		return (TkTextBookBuy)baseDAO.delObject(modelname,tkTextBookBuy);
	}

	/**
	 *修改教材基本信息表
	 *@param tkTextBookBuy TkTextBookBuy
	 *@return TkTextBookBuy
	 */
	public TkTextBookBuy updateTkTextBookBuy(TkTextBookBuy tkTextBookBuy){
		return (TkTextBookBuy)baseDAO.updateObject(modelname,tkTextBookBuy);
	}

	/**
	 *获取教材基本信息表集合
 	 *@return List
	 */
	public List getTkTextBookBuys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkTextBookBuy",condition,orderby,pagesize);
	}

	/**
	 *获取一页教材基本信息表集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkTextBookBuys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkTextBookBuy","textbookbuyid",condition, orderby, start,pagesize);
	}
	
	public PageList getTkTextBookBuysOfPage(String textbookname, String username, String createdate, String recipientname, String isdelivery,String sorderindex,int start, int size) {
		String sql = "select a,b,c from TkTextBookBuy as a,TkTextBookInfo as b,SysUserInfo as c where a.textbookid=b.textbookid and a.buyuserid=c.userid and a.status='1'";
		String sqlcount = "select count(*) from TkTextBookBuy as a,TkTextBookInfo as b,SysUserInfo as c where a.textbookid=b.textbookid and a.buyuserid=c.userid and a.status='1'";
		if (textbookname != null && textbookname.trim().length() > 0) {
			sql += " and b.textbookname like '%" + textbookname + "%'";
			sqlcount += " and b.textbookname like '%" + textbookname + "%'";
		}
		if (username != null && username.trim().length() > 0) {
			sql += " and c.username like '%" + username + "%'";
			sqlcount += " and c.username like '%" + username + "%'";
		}
		if (createdate != null && createdate.trim().length() > 0) {
			sql += " and a.createdate like '%" + createdate + "%'";
			sqlcount += " and a.createdate like '%" + createdate + "%'";
		}
		if (recipientname != null && recipientname.trim().length() > 0) {
			sql += " and a.recipientname like '%" + recipientname + "%'";
			sqlcount += " and a.recipientname like '%" + recipientname + "%'";
		}
		if (isdelivery != null && isdelivery.trim().length() > 0) {
			sql += " and a.isdelivery = " + isdelivery +"";
			sqlcount += " and a.isdelivery = " + isdelivery + "";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

}