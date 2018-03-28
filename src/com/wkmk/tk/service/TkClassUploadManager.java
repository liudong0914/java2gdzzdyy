package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkClassUpload;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级学生拍照上传</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassUploadManager {
	/**
	 *根据id获取班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload getTkClassUpload(String uploadid);

	/**
	 *根据id获取班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload getTkClassUpload(Integer uploadid);

	/**
	 *增加班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload addTkClassUpload(TkClassUpload tkClassUpload);

	/**
	 *删除班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload delTkClassUpload(String uploadid);

	/**
	 *删除班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload delTkClassUpload(TkClassUpload tkClassUpload);

	/**
	 *修改班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload updateTkClassUpload(TkClassUpload tkClassUpload);

	/**
	 *获取班级学生拍照上传集合
	 *@return List
	 */
	public List getTkClassUploads (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取班级学生拍照上传数量
 	 *@return int
	 */
	public int getCountTkClassUpload(String bookcontentid, String userid);
	
	public List getStudentsOfClassUpload(String bookcontentid, String classid);
	
	/**
	 *获取一页班级学生拍照上传集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassUploads (List<SearchModel> condition, String orderby, int start, int pagesize);

}