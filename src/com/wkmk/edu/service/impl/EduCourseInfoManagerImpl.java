package com.wkmk.edu.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.service.EduCourseInfoManager;

/**
 *<p>Description: 课程信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseInfoManagerImpl implements EduCourseInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "课程信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo getEduCourseInfo(String courseid){
		Integer iid = Integer.valueOf(courseid);
		return  (EduCourseInfo)baseDAO.getObject(modelname,EduCourseInfo.class,iid);
	}

	/**
	 *根据id获取课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo getEduCourseInfo(Integer courseid){
		return  (EduCourseInfo)baseDAO.getObject(modelname,EduCourseInfo.class,courseid);
	}

	/**
	 *增加课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo addEduCourseInfo(EduCourseInfo eduCourseInfo){
		return (EduCourseInfo)baseDAO.addObject(modelname,eduCourseInfo);
	}

	/**
	 *删除课程信息
	 *@param courseid Integer
	 *@return EduCourseInfo
	 */
	public EduCourseInfo delEduCourseInfo(String courseid){
		EduCourseInfo model = getEduCourseInfo(courseid);
		return (EduCourseInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo delEduCourseInfo(EduCourseInfo eduCourseInfo){
		return (EduCourseInfo)baseDAO.delObject(modelname,eduCourseInfo);
	}

	/**
	 *修改课程信息
	 *@param eduCourseInfo EduCourseInfo
	 *@return EduCourseInfo
	 */
	public EduCourseInfo updateEduCourseInfo(EduCourseInfo eduCourseInfo){
		return (EduCourseInfo)baseDAO.updateObject(modelname,eduCourseInfo);
	}

	/**
	 *获取课程信息集合
 	 *@return List
	 */
	public List getEduCourseInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页课程信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseInfo","courseid",condition, orderby, start,pagesize);
	}

	public PageList getEduCourseInfosOfPage(String title, String coursetypeid, String createdate,String sorderindex,int start, int size) {
        String sql = "select a,b from EduCourseInfo as a,EduCourseClass as b where a.courseid = b.courseid   and b.status= '2'";
        String sqlcount = "select count(b.courseclassid) from EduCourseInfo as a,EduCourseClass as b where a.courseid = b.courseid   and b.status= '2'";
        if (title != null && title.trim().length() > 0) {
            sql += " and a.title like '%" + title + "%'";
            sqlcount += " and a.title like '%" + title + "%'";
        }
        if (coursetypeid != null && coursetypeid.trim().length() > 0) {
            sql += " and a.coursetypeid = " + coursetypeid;
            sqlcount += "  and a.coursetypeid = " + coursetypeid;
        }
        if (createdate != null && createdate.trim().length() > 0) {
            sql += " and a.createdate like '%" + createdate + "%'";
            sqlcount += " and a.createdate  like '%" + createdate + "%'";
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
}