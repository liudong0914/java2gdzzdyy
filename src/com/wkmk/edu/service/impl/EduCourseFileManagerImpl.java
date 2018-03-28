package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.edu.service.EduCourseFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程文件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileManagerImpl implements EduCourseFileManager{

	private BaseDAO baseDAO;
	private String modelname = "课程文件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile getEduCourseFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (EduCourseFile)baseDAO.getObject(modelname,EduCourseFile.class,iid);
	}

	/**
	 *根据id获取课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile getEduCourseFile(Integer fileid){
		return  (EduCourseFile)baseDAO.getObject(modelname,EduCourseFile.class,fileid);
	}

	/**
	 *增加课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile addEduCourseFile(EduCourseFile eduCourseFile){
		return (EduCourseFile)baseDAO.addObject(modelname,eduCourseFile);
	}

	/**
	 *删除课程文件
	 *@param fileid Integer
	 *@return EduCourseFile
	 */
	public EduCourseFile delEduCourseFile(String fileid){
		EduCourseFile model = getEduCourseFile(fileid);
		return (EduCourseFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile delEduCourseFile(EduCourseFile eduCourseFile){
		return (EduCourseFile)baseDAO.delObject(modelname,eduCourseFile);
	}

	/**
	 *修改课程文件
	 *@param eduCourseFile EduCourseFile
	 *@return EduCourseFile
	 */
	public EduCourseFile updateEduCourseFile(EduCourseFile eduCourseFile){
		return (EduCourseFile)baseDAO.updateObject(modelname,eduCourseFile);
	}

	/**
	 *获取课程文件集合
 	 *@return List
	 */
	public List getEduCourseFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseFile",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程目录id集合
 	 *@return List
	 */
	public List getAllCoursecolumnids(String courseid){
		String sql = "select distinct a.coursecolumnid from EduCourseFile as a";
		if(courseid != null && !"".equals(courseid)){
			sql = sql + " where a.courseid=" + courseid;
		}
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页课程文件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseFile","fileid",condition, orderby, start,pagesize);
	}

	public PageList geteEduCourseFilesOfPage(String coursecolumnid, String courseid,  String filename,String filetype,String isopen,String sorderindex,int start, int size) {
        String sql = "select a from EduCourseFile as a,EduCourseFileColumn as b   where a.coursefiletype = b.filecolumnid and b.status ='1'  ";
        String sqlcount = "select count(a.fileid) from EduCourseFile as a,EduCourseFileColumn as b  where a.coursefiletype = b.filecolumnid and b.status ='1'  ";
       if(isopen !=null && isopen.trim().length() > 0){
    	   sql +="and b.isopen='1' ";
    	   sqlcount +="and b.isopen='1' ";
       }
        
        if (coursecolumnid != null && coursecolumnid.trim().length() > 0) {
            sql += " and a.coursecolumnid = " + coursecolumnid;
            sqlcount += " and a.coursecolumnid = " + coursecolumnid;
        }
        if (courseid != null && courseid.trim().length() > 0) {
            sql += " and a.courseid = " + courseid;
            sqlcount += " and a.courseid = " + courseid;
        }
        if (filename != null && filename.trim().length() > 0) {
            sql += " and a.filename  like '%" + filename + "%'";
            sqlcount += " and a.filename  like '%" + filename + "%'";
        }
        if (filetype != null && filetype.trim().length() > 0) {
            sql += " and a.filetype = " + filetype;
            sqlcount += " and a.filetype = " + filetype;
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
}