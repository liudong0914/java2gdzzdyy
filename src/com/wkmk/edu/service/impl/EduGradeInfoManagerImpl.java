package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 教育年级信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0f
 */
public class EduGradeInfoManagerImpl implements EduGradeInfoManager {

	private BaseDAO baseDAO;
	private String modelname = "教育年级信息";

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
	 * 根据id获取教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo getEduGradeInfo(String gradeid) {
		Integer iid = Integer.valueOf(gradeid);
		return (EduGradeInfo) baseDAO.getObject(modelname, EduGradeInfo.class, iid);
	}

	/**
	 * 根据id获取教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo getEduGradeInfo(Integer gradeid) {
		return (EduGradeInfo) baseDAO.getObject(modelname, EduGradeInfo.class, gradeid);
	}

	/**
	 * 增加教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo addEduGradeInfo(EduGradeInfo eduGradeInfo) {
		return (EduGradeInfo) baseDAO.addObject(modelname, eduGradeInfo);
	}

	/**
	 * 删除教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo delEduGradeInfo(String gradeid) {
		EduGradeInfo model = getEduGradeInfo(gradeid);
		return (EduGradeInfo) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo delEduGradeInfo(EduGradeInfo eduGradeInfo) {
		return (EduGradeInfo) baseDAO.delObject(modelname, eduGradeInfo);
	}

	/**
	 * 修改教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo updateEduGradeInfo(EduGradeInfo eduGradeInfo) {
		return (EduGradeInfo) baseDAO.updateObject(modelname, eduGradeInfo);
	}

	/**
	 * 获取教育年级信息集合
	 * 
	 * @return List
	 */
	public List getEduGradeInfos(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("EduGradeInfo", condition, orderby, pagesize);
	}

	/**
	 * 获取所有学段id集合
	 * 
	 * @return List
	 */
	public List getAllXueduanids() {
		String sql = "select distinct a.xueduanid from EduGradeInfo as a";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取所有子学段id集合
	 * 
	 * @return List
	 */
	public List getAllCxueduanids() {
		String sql = "select distinct a.cxueduanid from EduGradeInfo as a";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取所有学科id集合
	 * 
	 * @return List
	 */
	public List getAllSubjectids() {
		String sql = "select distinct a.subjectid from EduGradeInfo as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjects() {
		String sql = "select a from EduSubjectInfo as a";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取某学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByXueduanid(Integer xueduanid) {
		String sql = "select distinct a from EduSubjectInfo as a, EduGradeInfo as b where a.subjectid=b.subjectid and a.status='1' and b.xueduanid=" + xueduanid + " order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取某学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByUnitType(String unittype) {
		String sql = "select distinct a from EduSubjectInfo as a, EduGradeInfo as b where a.subjectid=b.subjectid and a.status='1' and b.xueduanid in (" + unittype + ") order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取某学段对应的所有年级集合
	 * 
	 * @return List
	 */
	public List getAllGradeBySubjectAndUnitType(Integer subjectid, String unittype) {
		String sql = "select distinct a from EduGradeInfo as a where a.status='1' and a.xueduanid in (" + unittype + ")";
		if (subjectid != null && subjectid != 0) {
			sql = sql + " and a.subjectid=" + subjectid;
		}
		sql = sql + " order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取某子学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByCxueduanid(Integer cxueduanid) {
		String sql = "select distinct a from EduSubjectInfo as a, EduGradeInfo as b where a.subjectid=b.subjectid and a.status='1' and b.cxueduanid=" + cxueduanid + " order by a.orderindex asc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取某子学段对应的所有年级id集合
	 * @return List
	 */
	public List getAllGradeidByCxueduanid(String cxueduanid) {
		String sql = "select a.gradeid from EduGradeInfo as a where a.cxueduanid=" + cxueduanid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 获取某子学段对应的所有年级id集合
	 * @return List
	 */
	public List getAllGradeidByXueduanid(String xueduanid) {
		String sql = "select a.gradeid from EduGradeInfo as a where a.xueduanid=" + xueduanid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页教育年级信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageEduGradeInfos(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("EduGradeInfo", "gradeid", condition, orderby, start, pagesize);
	}

	public List getSubjectsByPower(Integer userid, String type) {
		String sql = "select a from  EduSubjectInfo as a,TkBookContentPower as e where a.subjectid=e.subjectid and e.userid=" + userid + " and e.type='" + type + "' group by a.subjectid order by a.orderindex";
		return baseDAO.getObjects(sql);
	}
	
	public List getSubjectsByuserid(Integer userid) {
		String sql = "SELECT a from  EduSubjectInfo AS a,TkBookInfo AS e,TkBookContent AS d ,TkBookContentFilm AS f where a.subjectid=e.subjectid and e.bookid=d.bookid and d.bookcontentid=f.bookcontentid and f.sysUserInfo.userid =" + userid + " group by a.subjectid order by a.orderindex";
		return baseDAO.getObjects(sql);
	}


}