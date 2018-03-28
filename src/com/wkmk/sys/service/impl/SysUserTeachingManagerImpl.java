package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.sys.bo.SysUserTeaching;
import com.wkmk.sys.service.SysUserTeachingManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统用户教学设置</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserTeachingManagerImpl implements SysUserTeachingManager{

	private BaseDAO baseDAO;
	private String modelname = "系统用户教学设置";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching getSysUserTeaching(String teachingid){
		Integer iid = Integer.valueOf(teachingid);
		return  (SysUserTeaching)baseDAO.getObject(modelname,SysUserTeaching.class,iid);
	}

	/**
	 *根据id获取系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching getSysUserTeaching(Integer teachingid){
		return  (SysUserTeaching)baseDAO.getObject(modelname,SysUserTeaching.class,teachingid);
	}

	/**
	 *增加系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching addSysUserTeaching(SysUserTeaching sysUserTeaching){
		return (SysUserTeaching)baseDAO.addObject(modelname,sysUserTeaching);
	}

	/**
	 *删除系统用户教学设置
	 *@param teachingid Integer
	 *@return SysUserTeaching
	 */
	public SysUserTeaching delSysUserTeaching(String teachingid){
		SysUserTeaching model = getSysUserTeaching(teachingid);
		return (SysUserTeaching)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching delSysUserTeaching(SysUserTeaching sysUserTeaching){
		return (SysUserTeaching)baseDAO.delObject(modelname,sysUserTeaching);
	}
	
	/**
	 *删除用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public void delSysUserTeachingByUserid(Integer userid){
		String sql = "select a from SysUserTeaching as a where a.userid=" + userid;
		baseDAO.delObjects(sql);
	}

	/**
	 *修改系统用户教学设置
	 *@param sysUserTeaching SysUserTeaching
	 *@return SysUserTeaching
	 */
	public SysUserTeaching updateSysUserTeaching(SysUserTeaching sysUserTeaching){
		return (SysUserTeaching)baseDAO.updateObject(modelname,sysUserTeaching);
	}

	/**
	 *获取系统用户教学设置集合
 	 *@return List
	 */
	public List getSysUserTeachings(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserTeaching",condition,orderby,pagesize);
	}
	
	/**
	 *获取系统用户教学设置集合
 	 *@return List
	 */
	public List getSysUserTeachings(Integer userid){
		String sql = "select a from SysUserTeaching as a where a.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页系统用户教学设置集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserTeachings (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserTeaching","teachingid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 获取教师前五天教学信息设置
	 * @param userid
	 * @return
	 */
	public List getFiveTeaching(String userid){
		String sql="select a.teachingid,b.subjectname,c.gradename from SysUserTeaching as a,EduSubjectInfo as b,EduGradeInfo as c where a.subjectid=b.subjectid and a.gradeid=c.gradeid and a.userid="+userid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *学科id,年级id判断用户是否已经设置过年级
	 * @return
	 */
	public boolean gradeExists(String userid,String subjectid,String gradeid){
		String sql="select a from SysUserTeaching as a where a.userid="+userid+" and a.subjectid="+subjectid+" and a.gradeid="+gradeid;
		List list=baseDAO.getObjects(sql);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
}