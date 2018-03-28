package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFile;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程文件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseFileManager {
	/**
	 *根据id获取课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile getEduCourseFile(String fileid);

	/**
	 *根据id获取课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile getEduCourseFile(Integer fileid);

	/**
	 *增加课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile addEduCourseFile(EduCourseFile eduCourseFile);

	/**
	 *删除课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile delEduCourseFile(String fileid);

	/**
	 *删除课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile delEduCourseFile(EduCourseFile eduCourseFile);

	/**
	 *修改课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile updateEduCourseFile(EduCourseFile eduCourseFile);

	/**
	 *获取课程文件集合
	 *@return List
	 */
	public List getEduCourseFiles (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取课程目录id集合
 	 *@return List
	 */
	public List getAllCoursecolumnids(String courseid);
	
	/**
	 *获取一页课程文件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFiles (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	public PageList geteEduCourseFilesOfPage(String coursecolumnid, String courseid,  String filename,String filetype,String isopen,String sorderindex,int start, int size);

}