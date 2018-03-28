package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkPaperStyle;
import com.wkmk.tk.service.TkPaperStyleManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 题库试卷样式</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperStyleManagerImpl implements TkPaperStyleManager{

	private BaseDAO baseDAO;
	private String modelname = "题库试卷样式";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle getTkPaperStyle(String styleid){
		Integer iid = Integer.valueOf(styleid);
		return  (TkPaperStyle)baseDAO.getObject(modelname,TkPaperStyle.class,iid);
	}

	/**
	 *根据id获取题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle getTkPaperStyle(Integer styleid){
		return  (TkPaperStyle)baseDAO.getObject(modelname,TkPaperStyle.class,styleid);
	}

	/**
	 *增加题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle addTkPaperStyle(TkPaperStyle tkPaperStyle){
		return (TkPaperStyle)baseDAO.addObject(modelname,tkPaperStyle);
	}

	/**
	 *删除题库试卷样式
	 *@param styleid Integer
	 *@return TkPaperStyle
	 */
	public TkPaperStyle delTkPaperStyle(String styleid){
		TkPaperStyle model = getTkPaperStyle(styleid);
		return (TkPaperStyle)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle delTkPaperStyle(TkPaperStyle tkPaperStyle){
		return (TkPaperStyle)baseDAO.delObject(modelname,tkPaperStyle);
	}

	/**
	 *修改题库试卷样式
	 *@param tkPaperStyle TkPaperStyle
	 *@return TkPaperStyle
	 */
	public TkPaperStyle updateTkPaperStyle(TkPaperStyle tkPaperStyle){
		return (TkPaperStyle)baseDAO.updateObject(modelname,tkPaperStyle);
	}

	/**
	 *获取题库试卷样式集合
 	 *@return List
	 */
	public List getTkPaperStyles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkPaperStyle",condition,orderby,pagesize);
	}

	/**
	 *获取一页题库试卷样式集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperStyles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkPaperStyle","styleid",condition, orderby, start,pagesize);
	}

}