package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.service.SysUserMoneyManager;

/**
 *<p>Description: 用户交易记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserMoneyManagerImpl implements SysUserMoneyManager{

	private BaseDAO baseDAO;
	private String modelname = "用户交易记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney getSysUserMoney(String moneyid){
		Integer iid = Integer.valueOf(moneyid);
		return  (SysUserMoney)baseDAO.getObject(modelname,SysUserMoney.class,iid);
	}

	/**
	 *根据id获取用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney getSysUserMoney(Integer moneyid){
		return  (SysUserMoney)baseDAO.getObject(modelname,SysUserMoney.class,moneyid);
	}

	/**
	 *增加用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney addSysUserMoney(SysUserMoney sysUserMoney){
		return (SysUserMoney)baseDAO.addObject(modelname,sysUserMoney);
	}

	/**
	 *删除用户交易记录
	 *@param moneyid Integer
	 *@return SysUserMoney
	 */
	public SysUserMoney delSysUserMoney(String moneyid){
		SysUserMoney model = getSysUserMoney(moneyid);
		return (SysUserMoney)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney delSysUserMoney(SysUserMoney sysUserMoney){
		return (SysUserMoney)baseDAO.delObject(modelname,sysUserMoney);
	}

	/**
	 *修改用户交易记录
	 *@param sysUserMoney SysUserMoney
	 *@return SysUserMoney
	 */
	public SysUserMoney updateSysUserMoney(SysUserMoney sysUserMoney){
		return (SysUserMoney)baseDAO.updateObject(modelname,sysUserMoney);
	}

	/**
	 *获取用户交易记录集合
 	 *@return List
	 */
	public List getSysUserMoneys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserMoney",condition,orderby,pagesize);
	}

	/**
	 *获取一页用户交易记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserMoneys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserMoney","moneyid",condition, orderby, start,pagesize);
	}

	public PageList getSysUserMoneysOfPage(String title, String username, String createdate, String descript,String changetype,String usertype,String xueduan, String sorderindex,int start, int size) {
		String sql = "select a from SysUserMoney as a,SysUserInfo as b where a.userid=b.userid  and b.status= '1'";
		String sqlcount = "select count(*) from SysUserMoney as a,SysUserInfo as b where a.userid=b.userid  and b.status='1' ";
		if (title != null && title.trim().length() > 0) {
			sql += " and a.title like '%" + title + "%'";
			sqlcount += " and a.title like '%" + title + "%'";
		}
		if (username != null && username.trim().length() > 0) {
			sql += " and b.username like '%" + username + "%'";
			sqlcount += " and b.username like '%" + username + "%'";
		}
		if (createdate != null && createdate.trim().length() > 0) {
			sql += " and a.createdate like '%" + createdate + "%'";
			sqlcount += " and a.createdate like '%" + createdate + "%'";
		}
		if (descript != null && descript.trim().length() > 0) {
			sql += " and a.descript like '%" + descript + "%'";
			sqlcount += " and a.descript like '%" + descript + "%'";
		}
		if (changetype != null && changetype.trim().length() > 0) {
			sql += " and a.changetype ='" + changetype + "'";
			sqlcount += " and a.changetype ='" + changetype + "'";
		}
		if (usertype != null && usertype.trim().length() > 0) {
			sql += " and b.usertype ='" + usertype + "'";
			sqlcount += " and b.usertype ='" + usertype + "'";
		}
		if (xueduan != null && xueduan.trim().length() > 0) {
			sql += " and b.xueduan ='" + xueduan + "'";
			sqlcount += " and b.xueduan ='" + xueduan + "'";
		}
		if (sorderindex != null && sorderindex.trim().length() > 0) {
			sql += " order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, size);
	}
	
	public PageList getSysUserMoneysOfPage2(Integer userid, String title, String username, String createdate, String descript,String changetype,String usertype,String xueduan, String sorderindex,int start, int size) {
        String sql = "select a from SysUserMoney as a,SysUserInfo as b where a.userid=b.userid  and b.status= '1' and a.userid="+userid;
        String sqlcount = "select count(*) from SysUserMoney as a,SysUserInfo as b where a.userid=b.userid  and b.status='1'  and a.userid="+userid;
        if (title != null && title.trim().length() > 0) {
            sql += " and a.title like '%" + title + "%'";
            sqlcount += " and a.title like '%" + title + "%'";
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and b.username like '%" + username + "%'";
            sqlcount += " and b.username like '%" + username + "%'";
        }
        if (createdate != null && createdate.trim().length() > 0) {
            sql += " and a.createdate like '%" + createdate + "%'";
            sqlcount += " and a.createdate like '%" + createdate + "%'";
        }
        if (descript != null && descript.trim().length() > 0) {
            sql += " and a.descript like '%" + descript + "%'";
            sqlcount += " and a.descript like '%" + descript + "%'";
        }
        if (changetype != null && changetype.trim().length() > 0) {
            sql += " and a.changetype ='" + changetype + "'";
            sqlcount += " and a.changetype ='" + changetype + "'";
        }
        if (usertype != null && usertype.trim().length() > 0) {
            sql += " and b.usertype ='" + usertype + "'";
            sqlcount += " and b.usertype ='" + usertype + "'";
        }
        if (xueduan != null && xueduan.trim().length() > 0) {
            sql += " and b.xueduan ='" + xueduan + "'";
            sqlcount += " and b.xueduan ='" + xueduan + "'";
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
}