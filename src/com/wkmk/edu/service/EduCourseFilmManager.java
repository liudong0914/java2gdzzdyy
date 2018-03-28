package com.wkmk.edu.service;

import java.util.List;

import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseFilm;

/**
 *<p>Description: 课程视频</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseFilmManager {
	/**
	 *根据id获取课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm getEduCourseFilm(String coursefilmid);

	/**
	 *根据id获取课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm getEduCourseFilm(Integer coursefilmid);

	/**
	 *增加课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm addEduCourseFilm(EduCourseFilm eduCourseFilm);

	/**
	 *删除课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm delEduCourseFilm(String coursefilmid);

	/**
	 *删除课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm delEduCourseFilm(EduCourseFilm eduCourseFilm);

	/**
	 *修改课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm updateEduCourseFilm(EduCourseFilm eduCourseFilm);

	/**
	 *获取课程视频集合
	 *@return List
	 */
	public List getEduCourseFilms (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程目录id集合
 	 *@return List
	 */
	public List getAllCoursecolumnids(String courseid);
	
	/**
	 *获取课程视频信息
	 */
	public EduCourseFilm getEduCourseFilmByQrcodeno(String qrcodeno);
	
	/**
	 *获取一页课程视频集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFilms (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList getEduCourseFilmsOfPage(String coursecolumnid, String courseid, String status, String title,String sorderindex,int start, int size);
	public List getEduCourseFilmsByCourseid( String courseid, String title);

}