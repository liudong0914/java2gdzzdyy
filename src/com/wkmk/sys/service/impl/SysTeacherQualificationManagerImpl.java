package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysTeacherQualification;
import com.wkmk.sys.service.SysTeacherQualificationManager;

/**
 *<p>Description: 教师资格认证</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysTeacherQualificationManagerImpl implements SysTeacherQualificationManager{

	private BaseDAO baseDAO;
	private String modelname = "教师资格认证";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification getSysTeacherQualification(String teacherid){
		Integer iid = Integer.valueOf(teacherid);
		return  (SysTeacherQualification)baseDAO.getObject(modelname,SysTeacherQualification.class,iid);
	}

	/**
	 *根据id获取教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification getSysTeacherQualification(Integer teacherid){
		return  (SysTeacherQualification)baseDAO.getObject(modelname,SysTeacherQualification.class,teacherid);
	}

	/**
	 *增加教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification addSysTeacherQualification(SysTeacherQualification sysTeacherQualification){
		return (SysTeacherQualification)baseDAO.addObject(modelname,sysTeacherQualification);
	}

	/**
	 *删除教师资格认证
	 *@param teacherid Integer
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification delSysTeacherQualification(String teacherid){
		SysTeacherQualification model = getSysTeacherQualification(teacherid);
		return (SysTeacherQualification)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification delSysTeacherQualification(SysTeacherQualification sysTeacherQualification){
		return (SysTeacherQualification)baseDAO.delObject(modelname,sysTeacherQualification);
	}

	/**
	 *修改教师资格认证
	 *@param sysTeacherQualification SysTeacherQualification
	 *@return SysTeacherQualification
	 */
	public SysTeacherQualification updateSysTeacherQualification(SysTeacherQualification sysTeacherQualification){
		return (SysTeacherQualification)baseDAO.updateObject(modelname,sysTeacherQualification);
	}

	/**
	 *获取教师资格认证集合
 	 *@return List
	 */
	public List getSysTeacherQualifications(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysTeacherQualification",condition,orderby,pagesize);
	}

	/**
	 *获取一页教师资格认证集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysTeacherQualifications (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysTeacherQualification","teacherid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 根据userid查询教师申请信息
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2016-12-19 下午2:30:34 
	* @lastModified ; 2016-12-19 下午2:30:34 
	* @version ; 1.0 
	* @param userid
	* @return
	 */
	public List geTeacherQualificationByUserid(Integer userid){
        String sql ="SELECT a FROM SysTeacherQualification AS a WHERE a.userid ="+userid;
        List list = baseDAO.getObjects(sql);
        if (list != null && list.size() > 0) {
            return  list;
        } else {
            return null;
        }
    }
}