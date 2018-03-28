package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUnitDeptMember;
import com.wkmk.sys.service.SysUnitDeptMemberManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统单位机构成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptMemberManagerImpl implements SysUnitDeptMemberManager{

	private BaseDAO baseDAO;
	private String modelname = "系统单位机构成员";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember getSysUnitDeptMember(String memberid){
		Integer iid = Integer.valueOf(memberid);
		return  (SysUnitDeptMember)baseDAO.getObject(modelname,SysUnitDeptMember.class,iid);
	}

	/**
	 *根据id获取系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember getSysUnitDeptMember(Integer memberid){
		return  (SysUnitDeptMember)baseDAO.getObject(modelname,SysUnitDeptMember.class,memberid);
	}

	/**
	 *增加系统单位机构成员
	 *@param sysUnitDeptMember SysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember addSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember){
		return (SysUnitDeptMember)baseDAO.addObject(modelname,sysUnitDeptMember);
	}

	/**
	 *删除系统单位机构成员
	 *@param memberid Integer
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember delSysUnitDeptMember(String memberid){
		SysUnitDeptMember model = getSysUnitDeptMember(memberid);
		return (SysUnitDeptMember)baseDAO.delObject(modelname,model);
	}
	
	/**
	 *删除系统单位机构成员
	 *@param SysUnitDeptMember sysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember delSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember){
		return (SysUnitDeptMember)baseDAO.delObject(modelname,sysUnitDeptMember);
	}

	/**
	 *修改系统单位机构成员
	 *@param sysUnitDeptMember SysUnitDeptMember
	 *@return SysUnitDeptMember
	 */
	public SysUnitDeptMember updateSysUnitDeptMember(SysUnitDeptMember sysUnitDeptMember){
		return (SysUnitDeptMember)baseDAO.updateObject(modelname,sysUnitDeptMember);
	}

	/**
	 *获取系统单位机构成员集合
 	 *@return List
	 */
	public List getSysUnitDeptMembers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUnitDeptMember",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统单位机构成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDeptMembers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUnitDeptMember","memberid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取一页系统用户组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUnitDeptMembers (String condition,String username,Integer unitid,String deptno,String orderby,int start,int pagesize){
		String sql = " from SysUnitDeptMember as a, SysUnitDept as b where a.sysUserInfo.unitid=" + unitid + " and a.sysUserInfo.status='1' and a.deptid=b.deptid and b.unitid=" + unitid;
		if(deptno != null && !"".equals(deptno)){
			sql = sql +  " and b.deptno like '" + deptno + "%'";
		}
		if(!"".equals(username)){
			sql = sql + " and a.sysUserInfo.username like '%" + username + "%'";
		}
		if(condition != null && condition.length() > 0){
			sql = sql + " and (" + condition + ")";
		}
		String totalsql = "select count(distinct a.sysUserInfo.userid)" + sql;
		
		sql = sql + " group by a.sysUserInfo.userid";
		if(orderby != null && !"".equals(orderby)){
			sql = sql + " order by " + orderby;
		}
		String selectsql = "select a.sysUserInfo" + sql;
		
		return baseDAO.getPageObjects(totalsql,selectsql, start,pagesize);
	}
	
	/**
	 *获取系统用户组成员集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public List getSysUnitDeptMembers (String condition,String username,Integer unitid,String deptno,String orderby,int pagesize){
		String sql = " from SysUnitDeptMember as a, SysUnitDept as b where a.sysUserInfo.unitid=" + unitid + " and a.sysUserInfo.status='1' and a.deptid=b.deptid and b.unitid=" + unitid;
		if(deptno != null && !"".equals(deptno)){
			sql = sql +  " and b.deptno like '" + deptno + "%'";
		}
		if(!"".equals(username)){
			sql = sql + " and a.sysUserInfo.username like '%" + username + "%'";
		}
		if(condition != null && condition.length() > 0){
			sql = sql + " and (" + condition + ")";
		}
		sql = sql + " group by a.sysUserInfo.userid";
		if(orderby != null && !"".equals(orderby)){
			sql = sql + " order by " + orderby;
		}
		String selectsql = "select a.sysUserInfo" + sql;
		
		return baseDAO.getObjects(selectsql);
	}
	
	/**
	 *获取所以组id集合
	 *@return List
	 */
	public List getAllDeptidsByUnitid(Integer unitid){
		String sql = "SELECT DISTINCT a.deptid FROM SysUnitDeptMember AS a, SysUnitDept AS b WHERE a.deptid=b.deptid AND b.unitid=" + unitid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取所以组id集合
	 *@return List
	 */
	public List getAllDeptidsByUserid(Integer userid){
		String sql = "SELECT DISTINCT a.deptid FROM SysUnitDeptMember AS a WHERE a.sysUserInfo.userid=" + userid;
		return baseDAO.getObjects(sql);
	}

}