package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduGradeInfo;
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
 * @version 1.0
 */
public interface EduGradeInfoManager {
	/**
	 * 根据id获取教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo getEduGradeInfo(String gradeid);

	/**
	 * 根据id获取教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo getEduGradeInfo(Integer gradeid);

	/**
	 * 增加教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo addEduGradeInfo(EduGradeInfo eduGradeInfo);

	/**
	 * 删除教育年级信息
	 * 
	 * @param gradeid
	 *            Integer
	 * @return EduGradeInfo
	 */
	public EduGradeInfo delEduGradeInfo(String gradeid);

	/**
	 * 删除教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo delEduGradeInfo(EduGradeInfo eduGradeInfo);

	/**
	 * 修改教育年级信息
	 * 
	 * @param eduGradeInfo
	 *            EduGradeInfo
	 * @return EduGradeInfo
	 */
	public EduGradeInfo updateEduGradeInfo(EduGradeInfo eduGradeInfo);

	/**
	 * 获取教育年级信息集合
	 * 
	 * @return List
	 */
	public List getEduGradeInfos(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取所有学段id集合
	 * 
	 * @return List
	 */
	public List getAllXueduanids();

	/**
	 * 获取所有子学段id集合
	 * 
	 * @return List
	 */
	public List getAllCxueduanids();

	/**
	 * 获取所有学科id集合
	 * 
	 * @return List
	 */
	public List getAllSubjectids();
	/**
	 * 获取所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjects();

	/**
	 * 获取某学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByXueduanid(Integer xueduanid);

	/**
	 * 获取某学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByUnitType(String unittype);

	/**
	 * 获取某学段对应的所有年级集合
	 * 
	 * @return List
	 */
	public List getAllGradeBySubjectAndUnitType(Integer subjectid, String unittype);

	/**
	 * 获取某子学段对应的所有学科集合
	 * 
	 * @return List
	 */
	public List getAllSubjectByCxueduanid(Integer cxueduanid);
	
	/**
	 * 获取某子学段对应的所有年级id集合
	 * @return List
	 */
	public List getAllGradeidByCxueduanid(String cxueduanid);
	
	/**
	 * 获取某子学段对应的所有年级id集合
	 * @return List
	 */
	public List getAllGradeidByXueduanid(String xueduanid);

	/**
	 * 获取一页教育年级信息集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageEduGradeInfos(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 根据授权内容查询对应学科
	 * */
	public List getSubjectsByPower(Integer userid, String type);
	
	/**
	 * 根据用户获取对应学科
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-17 下午2:45:01 
	* @lastModified  2016-10-17 下午2:45:01 
	* @version  1.0 
	* @param userid
	* @return
	 */
	public List getSubjectsByuserid(Integer userid);
}