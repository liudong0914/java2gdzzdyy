package com.wkmk.edu.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseUserModule;
import com.wkmk.edu.service.EduCourseUserModuleManager;

/**
 *<p>Description: 课程用户模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserModuleManagerImpl implements EduCourseUserModuleManager{

	private BaseDAO baseDAO;
	private String modelname = "课程用户模块";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule getEduCourseUserModule(String usermoduleid){
		Integer iid = Integer.valueOf(usermoduleid);
		return  (EduCourseUserModule)baseDAO.getObject(modelname,EduCourseUserModule.class,iid);
	}

	/**
	 *根据id获取课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule getEduCourseUserModule(Integer usermoduleid){
		return  (EduCourseUserModule)baseDAO.getObject(modelname,EduCourseUserModule.class,usermoduleid);
	}

	/**
	 *增加课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule addEduCourseUserModule(EduCourseUserModule eduCourseUserModule){
		return (EduCourseUserModule)baseDAO.addObject(modelname,eduCourseUserModule);
	}

	/**
	 *删除课程用户模块
	 *@param usermoduleid Integer
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule delEduCourseUserModule(String usermoduleid){
		EduCourseUserModule model = getEduCourseUserModule(usermoduleid);
		return (EduCourseUserModule)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule delEduCourseUserModule(EduCourseUserModule eduCourseUserModule){
		return (EduCourseUserModule)baseDAO.delObject(modelname,eduCourseUserModule);
	}

	/**
	 *修改课程用户模块
	 *@param eduCourseUserModule EduCourseUserModule
	 *@return EduCourseUserModule
	 */
	public EduCourseUserModule updateEduCourseUserModule(EduCourseUserModule eduCourseUserModule){
		return (EduCourseUserModule)baseDAO.updateObject(modelname,eduCourseUserModule);
	}

	/**
	 *获取课程用户模块集合
 	 *@return List
	 */
	public List getEduCourseUserModules(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseUserModule",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程用户模块集合
 	 *@return List
	 */
	public List getEduCourseUserModules(String userid, String courseid){
		String sql = "select a from EduCourseUserModule as a where a.userid=" + userid;
		if(courseid != null && !"".equals(courseid)){
			sql = sql + " and a.courseid=" + courseid;
		}
		return baseDAO.getObjects(sql);
	}
	
	public List getEduCourseUserModules2(String userid, String courseid,String courseclassid){
        String sql = "select a from EduCourseUserModule as a where a.userid=" + userid;
        if(courseid != null && !"".equals(courseid)){
            sql = sql + " and a.courseid=" + courseid;
        }
        if(courseid != null && !"".equals(courseid)){
            sql = sql + " and a.courseid=" + courseid;
        }
        if(courseclassid != null && !"".equals(courseclassid)){
            sql = sql + " and a.courseclassid=" + courseclassid;
        }
        return baseDAO.getObjects(sql);
    }

	/**
	 *获取一页课程用户模块集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseUserModules (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseUserModule","usermoduleid",condition, orderby, start,pagesize);
	}

}