package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassUpload;
import com.wkmk.tk.service.TkClassUploadManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级学生拍照上传</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUploadManagerImpl implements TkClassUploadManager{

	private BaseDAO baseDAO;
	private String modelname = "班级学生拍照上传";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload getTkClassUpload(String uploadid){
		Integer iid = Integer.valueOf(uploadid);
		return  (TkClassUpload)baseDAO.getObject(modelname,TkClassUpload.class,iid);
	}

	/**
	 *根据id获取班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload getTkClassUpload(Integer uploadid){
		return  (TkClassUpload)baseDAO.getObject(modelname,TkClassUpload.class,uploadid);
	}

	/**
	 *增加班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload addTkClassUpload(TkClassUpload tkClassUpload){
		return (TkClassUpload)baseDAO.addObject(modelname,tkClassUpload);
	}

	/**
	 *删除班级学生拍照上传
	 *@param uploadid Integer
	 *@return TkClassUpload
	 */
	public TkClassUpload delTkClassUpload(String uploadid){
		TkClassUpload model = getTkClassUpload(uploadid);
		return (TkClassUpload)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload delTkClassUpload(TkClassUpload tkClassUpload){
		return (TkClassUpload)baseDAO.delObject(modelname,tkClassUpload);
	}

	/**
	 *修改班级学生拍照上传
	 *@param tkClassUpload TkClassUpload
	 *@return TkClassUpload
	 */
	public TkClassUpload updateTkClassUpload(TkClassUpload tkClassUpload){
		return (TkClassUpload)baseDAO.updateObject(modelname,tkClassUpload);
	}

	/**
	 *获取班级学生拍照上传集合
 	 *@return List
	 */
	public List getTkClassUploads(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassUpload",condition,orderby,pagesize);
	}
	
	/**
	 *获取班级学生拍照上传数量
 	 *@return int
	 */
	public int getCountTkClassUpload(String bookcontentid, String userid){
		String sql = "select count(*) from TkClassUpload as a where a.bookcontentid=" + bookcontentid + " and a.userid=" + userid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			return 0;
		}
	}
	
	public List getStudentsOfClassUpload(String bookcontentid, String classid){
		String sql = "select distinct a.userid from TkClassUpload as a where a.bookcontentid=" + bookcontentid + " and a.classid=" + classid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页班级学生拍照上传集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassUploads (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassUpload","uploadid",condition, orderby, start,pagesize);
	}

}