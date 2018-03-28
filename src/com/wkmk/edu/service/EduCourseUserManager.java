package com.wkmk.edu.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseUser;

/**
 *<p>Description: 课程用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseUserManager {
	/**
	 *根据id获取课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser getEduCourseUser(String courseuserid);

	/**
	 *根据id获取课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser getEduCourseUser(Integer courseuserid);

	/**
	 *增加课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser addEduCourseUser(EduCourseUser eduCourseUser);

	/**
	 *删除课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser delEduCourseUser(String courseuserid);

	/**
	 *删除课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser delEduCourseUser(EduCourseUser eduCourseUser);

	/**
	 *修改课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser updateEduCourseUser(EduCourseUser eduCourseUser);

	/**
	 *获取课程用户集合
	 *@return List
	 */
	public List getEduCourseUsers (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程用户集合
 	 *@return List
	 */
	public List getEduCourseSysUserInfos(String courseclassids,String usertype);
	
	/**
	 *获取课程学生数量
	 */
	public int getStudentCounts(String courseid);
	
	/**
	 *获取课程集合
 	 *@return List
	 */
	public List getManagerEduCourseInfos(Integer userid, String status, String title);
	
	
	/**
	 *获取一页课程用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseUsers (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取一页课程集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourses (Integer userid,String classusertype,int start,int pagesize);
	
	public PageList getPageEduCoursesByVip (Integer userid,int start,String title,int pagesize);
	
	public PageList getEduCoursesOfPage(String courseclassid, String courseid, String username, String vip,String sorderindex,int start, int size);
	
	public PageList getEduCoursesOfPage2(String courseclassid, String courseid, String username, String vip,String sorderindex,int start, int size);
	
	public List getEduCourseUserByCourseid(Integer courseid, Integer userid);
	
	public List getEduCourseUserByUsertype(Integer courseid, Integer courseclassid,Integer userid,String usertype);
}