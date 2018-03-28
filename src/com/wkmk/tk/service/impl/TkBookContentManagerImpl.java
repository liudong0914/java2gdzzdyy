package com.wkmk.tk.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.service.TkBookContentManager;

/**
 * <p>
 * Description: 作业本内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentManagerImpl implements TkBookContentManager {

	private BaseDAO baseDAO;
	private String modelname = "作业本内容";

	/**
	 * 加载baseDAO
	 * 
	 * @param BaseDAO
	 *            baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 根据id获取作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent getTkBookContent(String bookcontentid) {
		Integer iid = Integer.valueOf(bookcontentid);
		return (TkBookContent) baseDAO.getObject(modelname, TkBookContent.class, iid);
	}

	/**
	 * 根据id获取作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent getTkBookContent(Integer bookcontentid) {
		return (TkBookContent) baseDAO.getObject(modelname, TkBookContent.class, bookcontentid);
	}

	/**
	 * 增加作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent addTkBookContent(TkBookContent tkBookContent) {
		return (TkBookContent) baseDAO.addObject(modelname, tkBookContent);
	}

	/**
	 * 删除作业本内容
	 * 
	 * @param contentid
	 *            Integer
	 * @return TkBookContent
	 */
	public TkBookContent delTkBookContent(String contentid) {
		TkBookContent model = getTkBookContent(contentid);
		return (TkBookContent) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent delTkBookContent(TkBookContent tkBookContent) {
		return (TkBookContent) baseDAO.delObject(modelname, tkBookContent);
	}

	/**
	 * 修改作业本内容
	 * 
	 * @param tkBookContent
	 *            TkBookContent
	 * @return TkBookContent
	 */
	public TkBookContent updateTkBookContent(TkBookContent tkBookContent) {
		return (TkBookContent) baseDAO.updateObject(modelname, tkBookContent);
	}

	/**
	 * 获取作业本内容集合
	 * 
	 * @return List
	 */
	public List getTkBookContents(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkBookContent", condition, orderby, pagesize);
	}

	/**
	 * 获取一页作业本内容集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContents(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkBookContent", "bookcontentid", condition, orderby, start, pagesize);
	}
	public PageList getTkBookContentsOfPage(String title,  String booktitle, String sorderindex,int start, int size) {
		String sql = "select distinct a from TkBookContent as a,TkBookInfo as b,TkBookContentFilm as c  where a.bookid=b.bookid  AND c.bookcontentid= a.bookcontentid  AND  c.bookid= b.bookid  and b.status= '1' and b.vodstate !='0' AND c.status='1'";
		String sqlcount = "select count(*) from TkBookContent as a,TkBookInfo as b,TkBookContentFilm as c  where a.bookid=b.bookid AND c.bookcontentid= a.bookcontentid  AND  c.bookid= b.bookid   and b.status='1' and b.vodstate !='0' AND c.status='1'";
		if (title != null && title.trim().length() > 0) {
			sql += " and (a.title like '%" + title + "%'";
			sqlcount += " and (a.title like '%" + title + "%'";
		}
		if (booktitle != null && booktitle.trim().length() > 0) {
			sql += " or b.title like '%" + booktitle + "%')";
			sqlcount += " or b.title like '%" + booktitle + "%')";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}

	public List getAllbookids() {
		String sql = "select distinct a.bookid from TkBookContent as a";
		return baseDAO.getObjects(sql);
	}
	
	public List getAllbookidsWithMp3path() {
		String sql = "select distinct a.bookid from TkBookContent as a where a.mp3path<>'' and a.mp3path is not null";
		return baseDAO.getObjects(sql);
	}
	
	public List getAllbookidsWithPaperid() {
		String sql = "select distinct a.bookid from TkBookContent as a where a.paperid>0";
		return baseDAO.getObjects(sql);
	}

	public List getAllparentnos(String bookid) {
		String sql = "select distinct a.parentno from TkBookContent as a where a.bookid=" + bookid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 根据bookid查询所有作业本内容
	 * */
	public List getAllBookContentByBook(String bookid) {
		String sql = "select distinct a from TkBookContent as a where bookid=" + bookid;
		return baseDAO.getObjects(sql);
	}

	public List getAllBookContentByBook(String bookid, String orderby) {
		String sql = "select distinct a from TkBookContent as a where bookid=" + bookid + " order by " + orderby;
		return baseDAO.getObjects(sql);
	}
	public List getAllBookContentByBook2(String bookid, String orderby) {
		String sql = "select distinct a from TkBookContent as a where bookid=" + bookid + " and parentno!='0000'  order by " + orderby;
		return baseDAO.getObjects(sql);
	}

	public List getErrorQuestions(String bookid, String classid) {
		String sql = "select  e from TkClassErrorQuestion as e ,TkBookContent as t where e.bookcontentid=t.bookcontentid and t.bookid=" + bookid + " and e.classid=" + classid
				+ " order by e.type desc ";
		return baseDAO.getObjects(sql);
	}

	public String getMaxContentno(String bookid) {
		String sql = "select max(contentno) from TkBookContent as a where bookid=" + bookid + " and parentno='0000'";
		if (baseDAO.getObjects(sql).get(0) != null) {
			return baseDAO.getObjects(sql).get(0).toString();
		} else {
			return "";
		}
	}

	public List getBookContentByPower(Integer userid, String type) {
		String sql = "select e from TkBookContent as e,TkBookContentPower as t where e.bookcontentid=t.tkBookContent.bookcontentid  and t.userid=" + userid + " and t.type='" + type + "' order by e.contentno asc";
		return baseDAO.getObjects(sql);
	}
	
	public List getBookContentByuserid(Integer userid) {
		String sql = "SELECT d FROM  TkBookContent AS d ,TkBookContentFilm AS f where d.bookcontentid=f.bookcontentid and f.sysUserInfo.userid="+ userid +" order by d.contentno asc";
		return baseDAO.getObjects(sql);
	}
	
	
	public List getBookContentByuserid2(Integer userid) {
		 List<TkBookContent> contentList = getBookContentByuserid(userid);
		 TkBookContent bookcontent = null;
		 List list = new ArrayList();
		 if(contentList !=null && contentList.size()>0){
			for (int i = 0; i < contentList.size(); i++) {
				bookcontent = (TkBookContent) contentList.get(i);
				String sql ="SELECT a FROM TkBookContent AS a where a.contentno= '" + bookcontent.getParentno() + "' and a.bookid =" +bookcontent.getBookid();
				List list1 = baseDAO.getObjects(sql);
				list.add(list1.get(0));
			}
		 }
		 return list;
	}
	
	/**
	 * 根据作业编号获取信息
	 */
	public TkBookContent getTkBookContentByContentno(String contentno, String bookid) {
		String sql = "SELECT a FROM TkBookContent AS a where a.contentno='"+ contentno +"' and a.bookid=" + bookid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (TkBookContent) list.get(0);
		}else {
			return null;
		}
	}
	/**
	  * 方法描述：搜索查询作业本内容
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-22 下午1:19:42
	 */
	public List getTkBookContentBySearch(String keywords){
		String sql = "select distinct a from TkBookContent as a, TkBookInfo as b where a.bookid=b.bookid and b.status='1' and b.vodstate !='0'";
		if(keywords != null && !"".equals(keywords)){
			sql = sql + " and a.title like '%" + keywords + "%'";
		}
		return baseDAO.getObjects(sql);
	}

	public List getTkBookContentByFilm() {
		String sql = "select a from TkBookContent as a,TkBookContentFilm as b where a.bookcontentid=b.bookcontentid";
		return baseDAO.getObjects(sql);
	}
}