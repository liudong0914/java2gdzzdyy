package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduKnopointInfo;
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
public interface EduKnopointInfoManager {
	/**
	 * 根据id获取知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo getEduKnopointInfo(String knopointid);

	/**
	 * 根据id获取知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo getEduKnopointInfo(Integer knopointid);

	/**
	 * 增加知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo addEduKnopointInfo(EduKnopointInfo eduKnopointInfo);

	/**
	 * 删除知识点信息
	 * 
	 * @param knopointid
	 *            Integer
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo delEduKnopointInfo(String knopointid);

	/**
	 * 删除知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo delEduKnopointInfo(EduKnopointInfo eduKnopointInfo);

	/**
	 * 修改知识点信息
	 * 
	 * @param eduKnopointInfo
	 *            EduKnopointInfo
	 * @return EduKnopointInfo
	 */
	public EduKnopointInfo updateEduKnopointInfo(EduKnopointInfo eduKnopointInfo);

	/**
	 * 获取知识点信息集合
	 * 
	 * @return List
	 */
	public List getEduKnopointInfos(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取所有父编号
	 * 
	 * @return List
	 */
	public List getAllParentnos(String gradetype, String subjectid);

	/**
	 * 获取一页知识点信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageEduKnopointInfos(List<SearchModel> condition, String orderby, int start, int pagesize);

}