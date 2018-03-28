package com.wkmk.edu.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseColumn;

/**
 *<p>Description: 课程目录信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseColumnManager {
	/**
	 *根据id获取课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn getEduCourseColumn(String columnid);

	/**
	 *根据id获取课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn getEduCourseColumn(Integer columnid);

	/**
	 *增加课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn addEduCourseColumn(EduCourseColumn eduCourseColumn);

	/**
	 *删除课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn delEduCourseColumn(String columnid);

	/**
	 *删除课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn delEduCourseColumn(EduCourseColumn eduCourseColumn);

	/**
	 *修改课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn updateEduCourseColumn(EduCourseColumn eduCourseColumn);

	/**
	 *获取课程目录信息集合
	 *@return List
	 */
	public List getEduCourseColumns (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程目录信息集合
 	 *@return List
	 */
	public EduCourseColumn getEduCourseColumnByParentno(String courseid, String parentno);
	
	/**
	 *获取课程目录信息父编号集合
 	 *@return List
	 */
	public List getAllParentnos(String courseid);
	
	/**
	 *获取一页课程目录信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseColumns (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 * 根据课程id查询课程目录信息
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-14 下午4:39:54 
	* @lastModified ; 2017-2-14 下午4:39:54 
	* @version ; 1.0 
	* @param courseid
	* @return
	 */
	public List getEduCourseColumnByCourseid(Integer courseid,Integer unitid);

}