package com.wkmk.edu.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduCourseBuy;
import com.wkmk.edu.service.EduCourseBuyManager;

import com.util.dao.BaseDAO;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 课程购买信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseBuyManagerImpl implements EduCourseBuyManager{

	private BaseDAO baseDAO;
	private String modelname = "课程购买信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy getEduCourseBuy(String buyid){
		Integer iid = Integer.valueOf(buyid);
		return  (EduCourseBuy)baseDAO.getObject(modelname,EduCourseBuy.class,iid);
	}

	/**
	 *根据id获取课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy getEduCourseBuy(Integer buyid){
		return  (EduCourseBuy)baseDAO.getObject(modelname,EduCourseBuy.class,buyid);
	}

	/**
	 *增加课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy addEduCourseBuy(EduCourseBuy eduCourseBuy){
		return (EduCourseBuy)baseDAO.addObject(modelname,eduCourseBuy);
	}

	/**
	 *删除课程购买信息
	 *@param buyid Integer
	 *@return EduCourseBuy
	 */
	public EduCourseBuy delEduCourseBuy(String buyid){
		EduCourseBuy model = getEduCourseBuy(buyid);
		return (EduCourseBuy)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy delEduCourseBuy(EduCourseBuy eduCourseBuy){
		return (EduCourseBuy)baseDAO.delObject(modelname,eduCourseBuy);
	}

	/**
	 *修改课程购买信息
	 *@param eduCourseBuy EduCourseBuy
	 *@return EduCourseBuy
	 */
	public EduCourseBuy updateEduCourseBuy(EduCourseBuy eduCourseBuy){
		return (EduCourseBuy)baseDAO.updateObject(modelname,eduCourseBuy);
	}

	/**
	 *获取课程购买信息集合
 	 *@return List
	 */
	public List getEduCourseBuys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseBuy",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程购买信息集合
 	 *@return List
	 */
	public List getAllCoursefilmidByUserid(String userid, String courseid){
		String sql = "select a.coursefilmid from EduCourseBuy as a where a.userid=" + userid + " and a.courseid=" + courseid + " and a.enddate>'" + DateTime.getDate() + "'";
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页课程购买信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseBuys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseBuy","buyid",condition, orderby, start,pagesize);
	}

}