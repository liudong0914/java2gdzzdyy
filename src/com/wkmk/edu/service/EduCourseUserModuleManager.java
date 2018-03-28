package com.wkmk.edu.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseUserModule;

/**
 *<p>Description: 课程用户模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseUserModuleManager {
	/**
	 *根据id获取课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule getEduCourseUserModule(String usermoduleid);

	/**
	 *根据id获取课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule getEduCourseUserModule(Integer usermoduleid);

	/**
	 *增加课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule addEduCourseUserModule(EduCourseUserModule eduCourseUserModule);

	/**
	 *删除课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule delEduCourseUserModule(String usermoduleid);

	/**
	 *删除课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule delEduCourseUserModule(EduCourseUserModule eduCourseUserModule);

	/**
	 *修改课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule updateEduCourseUserModule(EduCourseUserModule eduCourseUserModule);

	/**
	 *获取课程用户模块集合
	 *@return List
	 */
	public List getEduCourseUserModules (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程用户模块集合
 	 *@return List
	 */
	public List getEduCourseUserModules(String userid, String courseid);
	
	public List getEduCourseUserModules2(String userid, String courseid,String courseclassid);
	
	/**
	 *获取一页课程用户模块集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseUserModules (List<SearchModel> condition, String orderby, int start, int pagesize);

}