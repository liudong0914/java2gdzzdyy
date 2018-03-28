package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFileDownload;
import com.wkmk.edu.service.EduCourseFileDownloadManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程资源下载记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileDownloadManagerImpl implements EduCourseFileDownloadManager{

	private BaseDAO baseDAO;
	private String modelname = "课程资源下载记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload getEduCourseFileDownload(String downloadid){
		Integer iid = Integer.valueOf(downloadid);
		return  (EduCourseFileDownload)baseDAO.getObject(modelname,EduCourseFileDownload.class,iid);
	}

	/**
	 *根据id获取课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload getEduCourseFileDownload(Integer downloadid){
		return  (EduCourseFileDownload)baseDAO.getObject(modelname,EduCourseFileDownload.class,downloadid);
	}

	/**
	 *增加课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload addEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload){
		return (EduCourseFileDownload)baseDAO.addObject(modelname,eduCourseFileDownload);
	}

	/**
	 *删除课程资源下载记录
	 *@param downloadid Integer
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload delEduCourseFileDownload(String downloadid){
		EduCourseFileDownload model = getEduCourseFileDownload(downloadid);
		return (EduCourseFileDownload)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload delEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload){
		return (EduCourseFileDownload)baseDAO.delObject(modelname,eduCourseFileDownload);
	}

	/**
	 *修改课程资源下载记录
	 *@param eduCourseFileDownload EduCourseFileDownload
	 *@return EduCourseFileDownload
	 */
	public EduCourseFileDownload updateEduCourseFileDownload(EduCourseFileDownload eduCourseFileDownload){
		return (EduCourseFileDownload)baseDAO.updateObject(modelname,eduCourseFileDownload);
	}

	/**
	 *获取课程资源下载记录集合
 	 *@return List
	 */
	public List getEduCourseFileDownloads(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseFileDownload",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程资源下载记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFileDownloads (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseFileDownload","downloadid",condition, orderby, start,pagesize);
	}

}