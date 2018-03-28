package com.wkmk.edu.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.service.EduCourseColumnManager;

/**
 *<p>Description: 课程目录信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseColumnManagerImpl implements EduCourseColumnManager{

	private BaseDAO baseDAO;
	private String modelname = "课程目录信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn getEduCourseColumn(String columnid){
		Integer iid = Integer.valueOf(columnid);
		return  (EduCourseColumn)baseDAO.getObject(modelname,EduCourseColumn.class,iid);
	}

	/**
	 *根据id获取课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn getEduCourseColumn(Integer columnid){
		return  (EduCourseColumn)baseDAO.getObject(modelname,EduCourseColumn.class,columnid);
	}

	/**
	 *增加课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn addEduCourseColumn(EduCourseColumn eduCourseColumn){
		return (EduCourseColumn)baseDAO.addObject(modelname,eduCourseColumn);
	}

	/**
	 *删除课程目录信息
	 *@param columnid Integer
	 *@return EduCourseColumn
	 */
	public EduCourseColumn delEduCourseColumn(String columnid){
		EduCourseColumn model = getEduCourseColumn(columnid);
		return (EduCourseColumn)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn delEduCourseColumn(EduCourseColumn eduCourseColumn){
		return (EduCourseColumn)baseDAO.delObject(modelname,eduCourseColumn);
	}

	/**
	 *修改课程目录信息
	 *@param eduCourseColumn EduCourseColumn
	 *@return EduCourseColumn
	 */
	public EduCourseColumn updateEduCourseColumn(EduCourseColumn eduCourseColumn){
		return (EduCourseColumn)baseDAO.updateObject(modelname,eduCourseColumn);
	}

	/**
	 *获取课程目录信息集合
 	 *@return List
	 */
	public List getEduCourseColumns(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("EduCourseColumn",condition,orderby,pagesize);
	}
	
	/**
	 *获取课程目录信息集合
 	 *@return List
	 */
	public EduCourseColumn getEduCourseColumnByParentno(String courseid, String parentno){
		String sql = "select a from EduCourseColumn as a where a.courseid=" + courseid + " and a.columnno='" + parentno + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (EduCourseColumn) list.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 *获取课程目录信息父编号集合
 	 *@return List
	 */
	public List getAllParentnos(String courseid){
		String sql = "select distinct a.parentno from EduCourseColumn as a";
		if(courseid != null && !"".equals(courseid)){
			sql = sql + " where a.courseid=" + courseid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取一页课程目录信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageEduCourseColumns (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("EduCourseColumn","columnid",condition, orderby, start,pagesize);
	}
	
	/**
     * 根据课程id查询课程目录信息
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-14 下午4:39:54 
    * @lastModified ; 2017-2-14 下午4:39:54 
    * @version ; 1.0 
    * @param courseid
    * @return
     */
    public List getEduCourseColumnByCourseid(Integer courseid,Integer unitid){
        String sql = "select a from EduCourseColumn as a where a.courseid=" + courseid+"and a.unitid="+unitid;
        return baseDAO.getObjects(sql);
    }

}