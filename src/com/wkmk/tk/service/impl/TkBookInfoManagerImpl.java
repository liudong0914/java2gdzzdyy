package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookInfoManager;

import com.util.dao.BaseDAO;
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
public class TkBookInfoManagerImpl implements TkBookInfoManager {

	private BaseDAO baseDAO;
	private String modelname = "作业本信息";

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
	 * 根据id获取作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo getTkBookInfo(String bookid) {
		Integer iid = Integer.valueOf(bookid);
		return (TkBookInfo) baseDAO.getObject(modelname, TkBookInfo.class, iid);
	}

	/**
	 * 根据id获取作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo getTkBookInfo(Integer bookid) {
		return (TkBookInfo) baseDAO.getObject(modelname, TkBookInfo.class, bookid);
	}

	/**
	 * 增加作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo addTkBookInfo(TkBookInfo tkBookInfo) {
		return (TkBookInfo) baseDAO.addObject(modelname, tkBookInfo);
	}

	/**
	 * 删除作业本信息
	 * 
	 * @param bookid
	 *            Integer
	 * @return TkBookInfo
	 */
	public TkBookInfo delTkBookInfo(String bookid) {
		TkBookInfo model = getTkBookInfo(bookid);
		return (TkBookInfo) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo delTkBookInfo(TkBookInfo tkBookInfo) {
		return (TkBookInfo) baseDAO.delObject(modelname, tkBookInfo);
	}

	/**
	 * 修改作业本信息
	 * 
	 * @param tkBookInfo
	 *            TkBookInfo
	 * @return TkBookInfo
	 */
	public TkBookInfo updateTkBookInfo(TkBookInfo tkBookInfo) {
		return (TkBookInfo) baseDAO.updateObject(modelname, tkBookInfo);
	}

	/**
	 * 获取作业本信息集合
	 * 
	 * @return List
	 */
	public List getTkBookInfos(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkBookInfo", condition, orderby, pagesize);
	}

	/**
	 * 获取作业本信息集合
	 * 
	 * @return List
	 */
	public List getAllTkBookInfos() {
		String sql = "select a.bookid, a.title, b.gradeid, b.gradename, c.subjectid, c.subjectname from TkBookInfo as a, EduGradeInfo as b, EduSubjectInfo as c where a.gradeid=b.gradeid and a.subjectid=c.subjectid and a.status='1' order by c.orderindex asc, b.orderindex asc, a.part asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页作业本信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookInfos(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkBookInfo", "bookid", condition, orderby, start, pagesize);
	}

	public List getBookInfosByPower(Integer userid, String type) {
		String sql = "select b from TkBookInfo as b,TkBookContent as c,TkBookContentPower as t where b.bookid=c.bookid and t.tkBookContent.bookcontentid=c.bookcontentid and t.userid=" + userid + " and t.type='"
				+ type + "' group by b.bookid";
		return baseDAO.getObjects(sql);
	}
	
	public List getBookInfosByuserid(Integer userid) {
		String sql = "SELECT e FROM  TkBookInfo AS e,TkBookContent AS d ,TkBookContentFilm AS f where  e.bookid=d.bookid and d.bookcontentid=f.bookcontentid and f.sysUserInfo.userid=" + userid +" group by e.bookid";
		return baseDAO.getObjects(sql);
	}

	public List getBookInfosByFilm() {
		String sql = "select t1 from TkBookInfo as t1,TkBookContent as t2,TkBookContentFilm as t3 where t1.bookid=t2.bookid and t2.bookcontentid=t3.bookcontentid";
		return baseDAO.getObjects(sql);
	}
	
	
}