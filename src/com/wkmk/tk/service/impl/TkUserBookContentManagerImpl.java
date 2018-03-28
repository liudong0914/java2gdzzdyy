package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkUserBookContent;
import com.wkmk.tk.service.TkUserBookContentManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 客户端用户作业本内容授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserBookContentManagerImpl implements TkUserBookContentManager{

	private BaseDAO baseDAO;
	private String modelname = "客户端用户作业本内容授权";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent getTkUserBookContent(String tid){
		Integer iid = Integer.valueOf(tid);
		return  (TkUserBookContent)baseDAO.getObject(modelname,TkUserBookContent.class,iid);
	}

	/**
	 *根据id获取客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent getTkUserBookContent(Integer tid){
		return  (TkUserBookContent)baseDAO.getObject(modelname,TkUserBookContent.class,tid);
	}

	/**
	 *增加客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent addTkUserBookContent(TkUserBookContent tkUserBookContent){
		return (TkUserBookContent)baseDAO.addObject(modelname,tkUserBookContent);
	}

	/**
	 *删除客户端用户作业本内容授权
	 *@param tid Integer
	 *@return TkUserBookContent
	 */
	public TkUserBookContent delTkUserBookContent(String tid){
		TkUserBookContent model = getTkUserBookContent(tid);
		return (TkUserBookContent)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent delTkUserBookContent(TkUserBookContent tkUserBookContent){
		return (TkUserBookContent)baseDAO.delObject(modelname,tkUserBookContent);
	}

	/**
	 *修改客户端用户作业本内容授权
	 *@param tkUserBookContent TkUserBookContent
	 *@return TkUserBookContent
	 */
	public TkUserBookContent updateTkUserBookContent(TkUserBookContent tkUserBookContent){
		return (TkUserBookContent)baseDAO.updateObject(modelname,tkUserBookContent);
	}

	/**
	 *获取客户端用户作业本内容授权集合
 	 *@return List
	 */
	public List getTkUserBookContents(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkUserBookContent",condition,orderby,pagesize);
	}
	
	/**
	 *获取客户端用户作业本内容授权id集合
 	 *@return List
	 */
	public List getTkUserBookContentOfIds(Integer userid, String objid){
		String sql = "select distinct a." + objid + " from TkUserBookContent as a where a.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页客户端用户作业本内容授权集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkUserBookContents (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkUserBookContent","tid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取指定用户被授权的作业内容
	 * @param userid
	 * @return
	 */
	public List findBookContentsByUser(String userid){
		String sql="select a.bookcontentid from TkUserBookContent as a where a.userid="+userid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 根据userid和bookid删除作业本内容
	 * @param userid
	 * @param bookid
	 */
	public void delBookContentByUserAndBook(String userid,String bookid){
		baseDAO.delObjects(baseDAO.getObjects("select distinct a from TkUserBookContent as a where bookid="+bookid+" and userid="+userid));
	}

}