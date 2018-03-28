package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUnitDept;
import com.wkmk.sys.service.SysUnitDeptManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位机构</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptManagerImpl implements SysUnitDeptManager{

	private BaseDAO baseDAO;
	private String modelname = "系统单位机构";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept getSysUnitDept(String deptid){
		Integer iid = Integer.valueOf(deptid);
		return  (SysUnitDept)baseDAO.getObject(modelname,SysUnitDept.class,iid);
	}

	/**
	 *根据id获取系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept getSysUnitDept(Integer deptid){
		return  (SysUnitDept)baseDAO.getObject(modelname,SysUnitDept.class,deptid);
	}

	/**
	 *增加系统单位机构
	 *@param sysUnitDept SysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept addSysUnitDept(SysUnitDept sysUnitDept){
		return (SysUnitDept)baseDAO.addObject(modelname,sysUnitDept);
	}

	/**
	 *删除系统单位机构
	 *@param deptid Integer
	 *@return SysUnitDept
	 */
	public SysUnitDept delSysUnitDept(String deptid){
		SysUnitDept model = getSysUnitDept(deptid);
		return (SysUnitDept)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统单位机构
	 *@param SysUnitDept sysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept delSysUnitDept(SysUnitDept sysUnitDept){
		return (SysUnitDept)baseDAO.delObject(modelname,sysUnitDept);
	}

	/**
	 *修改系统单位机构
	 *@param sysUnitDept SysUnitDept
	 *@return SysUnitDept
	 */
	public SysUnitDept updateSysUnitDept(SysUnitDept sysUnitDept){
		return (SysUnitDept)baseDAO.updateObject(modelname,sysUnitDept);
	}

	/**
	 *获取系统单位机构集合
 	 *@return List
	 */
	public List getSysUnitDepts(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUnitDept",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统单位机构集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDepts (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUnitDept","deptid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取系统用户组父编号集合
	 *@return List
	 */
	public List getAllParentno(Integer unitid){
		String sql = "SELECT DISTINCT a.parentno FROM SysUnitDept AS a WHERE a.unitid=" + unitid;
		return baseDAO.getObjects(sql);
	}

}