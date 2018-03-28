package com.wkmk.edu.service;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFileDownload;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程资源下载记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface EduCourseFileDownloadManager {
	/**
	 *根据id获取课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload getEduCourseFileDownload(String downloadid);

	/**
	 *根据id获取课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload getEduCourseFileDownload(Integer downloadid);

	/**
	 *增加课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload addEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload);

	/**
	 *删除课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload delEduCourseFileDownload(String downloadid);

	/**
	 *删除课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload delEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload);

	/**
	 *修改课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload updateEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload);

	/**
	 *获取课程资源下载记录集合
	 *@return List
	 */
	public List getEduCourseFileDownloads (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取一页课程资源下载记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFileDownloads (List<SearchModel> condition, String orderby, int start, int pagesize);

}