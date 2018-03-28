package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.service.EduKnopointInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 知识点信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class EduKnopointInfoManagerImpl implements EduKnopointInfoManager {

	private BaseDAO baseDAO;
	private String modelname = "知识点信息";

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
	 * 根据id获取知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo getEduKnopointInfo(String knopointid) {
		Integer iid = Integer.valueOf(knopointid);
		return (EduKnopointInfo) baseDAO.getObject(modelname, EduKnopointInfo.class, iid);
	}

	/**
	 * 根据id获取知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo getEduKnopointInfo(Integer knopointid) {
		return (EduKnopointInfo) baseDAO.getObject(modelname, EduKnopointInfo.class, knopointid);
	}

	/**
	 * 增加知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo addEduKnopointInfo(EduKnopointInfo eduKnopointInfo) {
		return (EduKnopointInfo) baseDAO.addObject(modelname, eduKnopointInfo);
	}

	/**
	 * 删除知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo delEduKnopointInfo(String knopointid) {
		EduKnopointInfo model = getEduKnopointInfo(knopointid);
		return (EduKnopointInfo) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo delEduKnopointInfo(EduKnopointInfo eduKnopointInfo) {
		return (EduKnopointInfo) baseDAO.delObject(modelname, eduKnopointInfo);
	}

	/**
	 * 修改知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo updateEduKnopointInfo(EduKnopointInfo eduKnopointInfo) {
		return (EduKnopointInfo) baseDAO.updateObject(modelname, eduKnopointInfo);
	}

	/**
	 * 获取知识点信息集合
	 * 
	 * @return List
	 */
	public List getEduKnopointInfos(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("EduKnopointInfo", condition, orderby, pagesize);
	}

	/**
	 * 获取所有父编号
	 * 
	 * @return List
	 */
	public List getAllParentnos(String gradetype, String subjectid) {
		String sql = "select a.parentno from EduKnopointInfo as a where a.gradetype='" + gradetype + "' and a.subjectid=" + subjectid;
		return baseDAO.getObjects(sql);
	}

	/**
	 * 获取一页知识点信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageEduKnopointInfos(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("EduKnopointInfo", "knopointid", condition, orderby, start, pagesize);
	}

}