package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentComment;
import com.wkmk.tk.service.TkBookContentCommentManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 作业解题微课评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentCommentManagerImpl implements TkBookContentCommentManager{

	private BaseDAO baseDAO;
	private String modelname = "作业解题微课评价";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment getTkBookContentComment(String commentid){
		Integer iid = Integer.valueOf(commentid);
		return  (TkBookContentComment)baseDAO.getObject(modelname,TkBookContentComment.class,iid);
	}

	/**
	 *根据id获取作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment getTkBookContentComment(Integer commentid){
		return  (TkBookContentComment)baseDAO.getObject(modelname,TkBookContentComment.class,commentid);
	}

	/**
	 *增加作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment addTkBookContentComment(TkBookContentComment tkBookContentComment){
		return (TkBookContentComment)baseDAO.addObject(modelname,tkBookContentComment);
	}

	/**
	 *删除作业解题微课评价
	 *@param commentid Integer
	 *@return TkBookContentComment
	 */
	public TkBookContentComment delTkBookContentComment(String commentid){
		TkBookContentComment model = getTkBookContentComment(commentid);
		return (TkBookContentComment)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment delTkBookContentComment(TkBookContentComment tkBookContentComment){
		return (TkBookContentComment)baseDAO.delObject(modelname,tkBookContentComment);
	}

	/**
	 *修改作业解题微课评价
	 *@param tkBookContentComment TkBookContentComment
	 *@return TkBookContentComment
	 */
	public TkBookContentComment updateTkBookContentComment(TkBookContentComment tkBookContentComment){
		return (TkBookContentComment)baseDAO.updateObject(modelname,tkBookContentComment);
	}

	/**
	 *获取作业解题微课评价集合
 	 *@return List
	 */
	public List getTkBookContentComments(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentComment",condition,orderby,pagesize);
	}
	
	/**
	 *获取作业解题微课评分集合
 	 *@return List
	 */
	public List getTkBookContentCommentScore(String bookcontentid, String type){
		String sql = "select a.score from TkBookContentComment as a where a.status='1' and a.type='" + type + "' and a.bookcontentid=" + bookcontentid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页作业解题微课评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentComments (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentComment","commentid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取一页作业解题微课评价集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentComments1 (List<SearchModel> condition,String orderby,int start,int pagesize){
		StringBuffer sqls = new StringBuffer();
		sqls.append(" from TkBookContentComment as a,TkBookContent as b where a.bookcontentid=b.bookcontentid ");
		if (condition != null && condition.size() > 0) {
			SearchModel model = null;
			for (int i = 0, size = condition.size(); i < size; i++) {
				model = (SearchModel) condition.get(i);
				if("0".equals(model.getType()) && model.getSvalue() != null && !"".equals(model.getSvalue())){
					if ("in".equals(model.getRelation().toLowerCase())) {
						sqls.append(" AND ").append(model.getItem()).append(" ").append(model.getRelation()).append(" (").append(model.getSvalue()).append(")");
					} else {
						sqls.append(" AND ").append(model.getItem()).append(" ").append(model.getRelation()).append(" '").append(model.getSvalue()).append("'");
					}
				}
				if("1".equals(model.getType()) && model.getIvalue() != null && model.getIvalue() != -01 && !"-01".equals(model.getIvalue().toString())){
					if ("in".equals(model.getRelation().toLowerCase())) {
						sqls.append(" AND ").append(model.getItem()).append(" ").append(model.getRelation()).append(" (").append(model.getIvalue()).append(")");
					} else {
						sqls.append(" AND ").append(model.getItem()).append(" ").append(model.getRelation()).append(" ").append(model.getIvalue());
					}
				}
			}
		}
		String sql = sqls.toString();
		if(!"".equals(orderby)){
			sql+=" order by "+orderby;
		}
		String totalsql="select count(a.commentid) "+sql;
		String listsql="select a "+sql;
		return baseDAO.getPageObjects(totalsql, listsql, start, pagesize);
	}
	
	//获取有评价的作业本集合
	public List getBookListByContentComments(){
		String sql="select DISTINCT a from TkBookInfo as a,TkBookContent as b,TkBookContentComment as c where a.bookid=b.bookid and b.bookcontentid=c.bookcontentid order by a.bookno asc";
		return baseDAO.getObjects(sql);
	}
	
	//获取有评价的作业本集合
	public List getBookContentListByContentComments(){
		String sql="select DISTINCT a from TkBookContent as a,TkBookContentComment as b where a.bookcontentid=b.bookcontentid order by a.contentno asc";
		return baseDAO.getObjects(sql);
	}
}