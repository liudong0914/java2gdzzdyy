package com.wkmk.edu.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseUserManager;

/**
 *<p>Description: 课程用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserManagerImpl implements EduCourseUserManager{

	private BaseDAO baseDAO;
	private String modelname = "课程用户";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser getEduCourseUser(String courseuserid){
		Integer iid = Integer.valueOf(courseuserid);
		return  (EduCourseUser)baseDAO.getObject(modelname,EduCourseUser.class,iid);
	}

	/**
	 *根据id获取课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser getEduCourseUser(Integer courseuserid){
		return  (EduCourseUser)baseDAO.getObject(modelname,EduCourseUser.class,courseuserid);
	}

	/**
	 *增加课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser addEduCourseUser(EduCourseUser eduCourseUser){
		return (EduCourseUser)baseDAO.addObject(modelname,eduCourseUser);
	}

	/**
	 *删除课程用户
	 *@param courseuserid Integer
	 *@return EduCourseUser
	 */
	public EduCourseUser delEduCourseUser(String courseuserid){
		EduCourseUser model = getEduCourseUser(courseuserid);
		return (EduCourseUser)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser delEduCourseUser(EduCourseUser eduCourseUser){
		return (EduCourseUser)baseDAO.delObject(modelname,eduCourseUser);
	}

	/**
	 *修改课程用户
	 *@param eduCourseUser EduCourseUser
	 *@return EduCourseUser
	 */
	public EduCourseUser updateEduCourseUser(EduCourseUser eduCourseUser){
		return (EduCourseUser)baseDAO.updateObject(modelname,eduCourseUser);
	}

	/**
	 *获取课程用户集合
 	 *@return List
	 */
	public List getEduCourseUsers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseUser",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程用户集合
 	 *@return List
	 */
	public List getEduCourseSysUserInfos(String courseclassids,String usertype){
		String sql = "select a, b from SysUserInfo as a, EduCourseUser as b where a.userid=b.userid and b.status='1'";
		if(courseclassids != null && !"".equals(courseclassids)){
			sql = sql + " and b.courseclassid in (" + courseclassids + ")";
		}
		if(usertype != null && !"".equals(usertype)){
			sql = sql + " and b.usertype='" + usertype + "'";
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取课程学生数量
	 */
	public int getStudentCounts(String courseid){
		String sql = "select count(a.courseuserid) from EduCourseUser as a where a.usertype='3'";
		if(courseid != null && !"".equals(courseid)){
			sql = sql + " and a.courseid=" + courseid;
		}
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			return 0;
		}
	}
	
	/**
	 *获取课程集合
 	 *@return List
	 */
	public List getManagerEduCourseInfos(Integer userid, String status, String title){
		String sql = "select b, a from EduCourseClass as a, EduCourseInfo as b, EduCourseUser as c where a.courseid=b.courseid and a.courseclassid=c.courseclassid and c.status='1'  and c.userid=" + userid + " and (c.usertype='1' or c.usertype='2')";
		if(status != null && !"".equals(status)){
			sql = sql + " and a.status='" + status + "'";
		}
		if(title != null && !"".equals(title)){
			sql = sql + " and b.title like '%" + title + "%'";
		}
		sql = sql + " order by a.enddate desc";
		return baseDAO.getObjects(sql);
	}
	

	/**
	 *获取一页课程用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseUsers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseUser","courseuserid",condition, orderby, start,pagesize);
	}

	/**
	 *获取一页课程集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourses (Integer userid,String classusertype,int start,int pagesize){
		String curdate = DateTime.getDate();
		String sql = " from EduCourseClass as a, EduCourseInfo as b, EduCourseUser as c where a.courseid=b.courseid and a.courseclassid=c.courseclassid and c.status='1' and b.status='1' and a.status='1' and c.userid=" + userid + " and a.startdate<'" + curdate + "'";
		if(classusertype != null && !"".equals(classusertype)){
			sql = sql + " and c.usertype='" + classusertype + "'";
		}
		String listsql = "select b, a" + sql + " order by b.courseno asc";
		String totlalsql = "select count(c.courseuserid)" + sql;
		return baseDAO.getPageObjects(totlalsql,listsql, start,pagesize);
	}
	
	public PageList getPageEduCoursesByVip (Integer userid,int start,String title,int pagesize){
        String sql = " from EduCourseInfo as b where  b.status='1' and b.courseid in(select distinct(a.courseid) from EduCourseUser as a where a.status='1' and a.vip='1' and a.userid="+userid+")";
        if(title != null && !"".equals(title)){
			sql = sql + " and b.title like '%" + title + "%'";
		}
        String listsql = "select b" + sql + " order by b.courseno asc";
        String totlalsql = "select count(b.courseid)" + sql;
        return baseDAO.getPageObjects(totlalsql,listsql, start,pagesize);
    }
	
	public PageList getEduCoursesOfPage(String courseclassid, String courseid, String username, String vip,String sorderindex,int start, int size) {
        String sql = "select a,b from EduCourseUser as a,SysUserInfo as b where a.userid = b.userid   and a.status= '1' and a.usertype='3'";
        String sqlcount = "select count(a.courseuserid) from EduCourseUser as a,SysUserInfo as b where a.userid = b.userid  and a.status= '1' and a.usertype='3'";
        if (courseclassid != null && courseclassid.trim().length() > 0) {
            sql += " and a.courseclassid = " + courseclassid;
            sqlcount += " and a.courseclassid = " + courseclassid;
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and b.username like '%" + username + "%'";
            sqlcount += " and b.username like '%" + username + "%'";
        }
        if (courseid != null && courseid.trim().length() > 0) {
            sql += " and a.courseid = " + courseid;
            sqlcount += "  and a.courseid = " + courseid;
        }
        if (vip != null && vip.trim().length() > 0) {
            sql += " and a.vip ='" + vip + "'";
            sqlcount += " and a.vip ='" + vip + "'";
        }
       
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
	
	public PageList getEduCoursesOfPage2(String courseclassid, String courseid, String username, String vip,String sorderindex,int start, int size) {
        String sql = "select a,b from EduCourseUser as a,SysUserInfo as b where a.userid = b.userid   and a.status= '1' and a.usertype='2'";
        String sqlcount = "select count(a.courseuserid) from EduCourseUser as a,SysUserInfo as b where a.userid = b.userid  and a.status= '1' and a.usertype='2'";
        if (courseclassid != null && courseclassid.trim().length() > 0) {
            sql += " and a.courseclassid = " + courseclassid;
            sqlcount += " and a.courseclassid = " + courseclassid;
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and b.username like '%" + username + "%'";
            sqlcount += " and b.username like '%" + username + "%'";
        }
        if (courseid != null && courseid.trim().length() > 0) {
            sql += " and a.courseid = " + courseid;
            sqlcount += "  and a.courseid = " + courseid;
        }
        if (vip != null && vip.trim().length() > 0) {
            sql += " and a.vip ='" + vip + "'";
            sqlcount += " and a.vip ='" + vip + "'";
        }
       
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
	
	   public List getEduCourseUserByCourseid(Integer courseid, Integer userid){
	        String sql = "select distinct(b.filmid) from EduCourseUser AS a ,EduCourseFilm as b where a.courseid=b.courseid and a.userid="+userid+" and a.status='1' and(a.usertype ='1' OR a.vip ='1')";
	        if (courseid != null) {
	            sql += " and a.courseid = " + courseid;
	        }
	        return baseDAO.getObjects(sql);
	    }
	   
	   public List getEduCourseUserByUsertype(Integer courseid, Integer courseclassid,Integer userid,String usertype){
           String sql = "select a from EduCourseUser AS a  where  a.courseid="+courseid+" and a.courseclassid="+courseclassid+" and a.usertype="+usertype+" and a.userid="+userid+" and a.status='1'";
           return baseDAO.getObjects(sql);
       }
}