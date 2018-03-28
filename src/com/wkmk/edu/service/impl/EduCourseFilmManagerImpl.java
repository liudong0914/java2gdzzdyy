package com.wkmk.edu.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.service.EduCourseFilmManager;

/**
 *<p>Description: 课程视频</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFilmManagerImpl implements EduCourseFilmManager{

	private BaseDAO baseDAO;
	private String modelname = "课程视频";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm getEduCourseFilm(String coursefilmid){
		Integer iid = Integer.valueOf(coursefilmid);
		return  (EduCourseFilm)baseDAO.getObject(modelname,EduCourseFilm.class,iid);
	}

	/**
	 *根据id获取课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm getEduCourseFilm(Integer coursefilmid){
		return  (EduCourseFilm)baseDAO.getObject(modelname,EduCourseFilm.class,coursefilmid);
	}

	/**
	 *增加课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm addEduCourseFilm(EduCourseFilm eduCourseFilm){
		return (EduCourseFilm)baseDAO.addObject(modelname,eduCourseFilm);
	}

	/**
	 *删除课程视频
	 *@param coursefilmid Integer
	 *@return EduCourseFilm
	 */
	public EduCourseFilm delEduCourseFilm(String coursefilmid){
		EduCourseFilm model = getEduCourseFilm(coursefilmid);
		return (EduCourseFilm)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm delEduCourseFilm(EduCourseFilm eduCourseFilm){
		return (EduCourseFilm)baseDAO.delObject(modelname,eduCourseFilm);
	}

	/**
	 *修改课程视频
	 *@param eduCourseFilm EduCourseFilm
	 *@return EduCourseFilm
	 */
	public EduCourseFilm updateEduCourseFilm(EduCourseFilm eduCourseFilm){
		return (EduCourseFilm)baseDAO.updateObject(modelname,eduCourseFilm);
	}

	/**
	 *获取课程视频集合
 	 *@return List
	 */
	public List getEduCourseFilms(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseFilm",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程目录id集合
 	 *@return List
	 */
	public List getAllCoursecolumnids(String courseid){
		String sql = "select distinct a.coursecolumnid from EduCourseFilm as a";
		if(courseid != null && !"".equals(courseid)){
			sql = sql + " where a.courseid=" + courseid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取课程视频信息
	 */
	public EduCourseFilm getEduCourseFilmByQrcodeno(String qrcodeno){
		String sql = "select a from EduCourseFilm as a where a.qrcodeno='" + qrcodeno + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (EduCourseFilm) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页课程视频集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseFilms (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseFilm","coursefilmid",condition, orderby, start,pagesize);
	}

	public PageList getEduCourseFilmsOfPage(String coursecolumnid, String courseid, String status, String title,String sorderindex,int start, int size) {
        //String sql = "select a,b from EduCourseFilm as a,VwhFilmInfo as b ,EduCourseInfo as c  where a.filmid = b.filmid and a.courseid = c.courseid and c.status ='1'";
        //String sqlcount = "select count(a.coursefilmid) from EduCourseFilm as a,VwhFilmInfo as b,EduCourseInfo as c  where a.filmid = b.filmid and a.courseid = c.courseid and c.status ='1'";
        String sql = "select a,b from EduCourseFilm as a,VwhFilmInfo as b ,EduCourseInfo as c  where a.filmid = b.filmid and a.courseid = c.courseid";
        String sqlcount = "select count(a.coursefilmid) from EduCourseFilm as a,VwhFilmInfo as b,EduCourseInfo as c  where a.filmid = b.filmid and a.courseid = c.courseid";
        if (coursecolumnid != null && coursecolumnid.trim().length() > 0) {
            sql += " and a.coursecolumnid = " + coursecolumnid;
            sqlcount += " and a.coursecolumnid = " + coursecolumnid;
        }
        if (courseid != null && courseid.trim().length() > 0) {
            sql += " and a.courseid = " + courseid;
            sqlcount += " and a.courseid = " + courseid;
        }
        if (status != null && status.trim().length() > 0) {
            sql += " and a.status = '" + status + "'";
            sqlcount += " and a.status = '" + status + "'";
        }
        if (title != null && title.trim().length() > 0) {
            sql += " and b.title  like '%" + title + "%'";
            sqlcount += " and b.title  like '%" + title + "%'";
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
	
	public List getEduCourseFilmsByCourseid( String courseid, String title) {
        String sql = "select b from EduCourseFilm as a,VwhFilmInfo as b where a.filmid = b.filmid and a.status ='1'";
        if (courseid != null && courseid.trim().length() > 0) {
            sql += " and a.courseid = " + courseid;
        }
        if (title != null && title.trim().length() > 0) {
            sql += " and b.title  like '%" + title + "%'";
        }
        return baseDAO.getObjects(sql);
    }
}