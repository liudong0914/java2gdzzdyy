package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserPayTrade;
import com.wkmk.sys.service.SysUserPayTradeManager;

/**
 *<p>Description: 在线交易订单明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPayTradeManagerImpl implements SysUserPayTradeManager{

	private BaseDAO baseDAO;
	private String modelname = "在线交易订单明细";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade getSysUserPayTrade(String tradeid){
		Integer iid = Integer.valueOf(tradeid);
		return  (SysUserPayTrade)baseDAO.getObject(modelname,SysUserPayTrade.class,iid);
	}

	/**
	 *根据id获取在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade getSysUserPayTrade(Integer tradeid){
		return  (SysUserPayTrade)baseDAO.getObject(modelname,SysUserPayTrade.class,tradeid);
	}

	/**
	 *增加在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade addSysUserPayTrade(SysUserPayTrade sysUserPayTrade){
		return (SysUserPayTrade)baseDAO.addObject(modelname,sysUserPayTrade);
	}

	/**
	 *删除在线交易订单明细
	 *@param tradeid Integer
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade delSysUserPayTrade(String tradeid){
		SysUserPayTrade model = getSysUserPayTrade(tradeid);
		return (SysUserPayTrade)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade delSysUserPayTrade(SysUserPayTrade sysUserPayTrade){
		return (SysUserPayTrade)baseDAO.delObject(modelname,sysUserPayTrade);
	}

	/**
	 *修改在线交易订单明细
	 *@param sysUserPayTrade SysUserPayTrade
	 *@return SysUserPayTrade
	 */
	public SysUserPayTrade updateSysUserPayTrade(SysUserPayTrade sysUserPayTrade){
		return (SysUserPayTrade)baseDAO.updateObject(modelname,sysUserPayTrade);
	}

	/**
	 *获取在线交易订单明细集合
 	 *@return List
	 */
	public List getSysUserPayTrades(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserPayTrade",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线交易订单明细
 	 *@return List
	 */
	public SysUserPayTrade getSysUserPayTradeByOuttradeno(String outtradeno){
		String sql = "select a from SysUserPayTrade as a where a.outtradeno='" + outtradeno + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserPayTrade) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页在线交易订单明细集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserPayTrades (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserPayTrade","tradeid",condition, orderby, start,pagesize);
	}

	public PageList getSysUserPayTradesOfPage(String subject, String username, String createdate, String body,String paytype,String state,String usertype,String xueduan, String sorderindex,int start, int size) {
		String sql = "select a from SysUserPayTrade as a,SysUserInfo as b where a.userid=b.userid  and b.status= '1' ";
		String sqlcount = "select count(*) from SysUserPayTrade as a,SysUserInfo as b where a.userid=b.userid  and b.status='1' ";
		if (subject != null && subject.trim().length() > 0) {
			sql += " and a.subject like '%" + subject + "%'";
			sqlcount += " and a.subject like '%" + subject + "%'";
		}
		if (username != null && username.trim().length() > 0) {
			sql += " and b.username like '%" + username + "%'";
			sqlcount += " and b.username like '%" + username + "%'";
		}
		if (createdate != null && createdate.trim().length() > 0) {
			sql += " and a.createdate like '%" + createdate + "%'";
			sqlcount += " and a.createdate like '%" + createdate + "%'";
		}
		if (body != null && body.trim().length() > 0) {
			sql += " and a.body like '%" + body + "%'";
			sqlcount += " and a.body like '%" + body + "%'";
		}
		if (paytype != null && paytype.trim().length() > 0) {
			sql += " and a.paytype ='" + paytype + "'";
			sqlcount += " and a.paytype ='" + paytype + "'";
		}
		if (state != null && state.trim().length() > 0) {
			sql += " and a.state ='" + state + "'";
			sqlcount += " and a.state ='" + state + "'";
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
	
	public PageList getSysUserPayTradesOfPage2(Integer userid,String subject, String username, String createdate, String body,String paytype,String state,String usertype,String xueduan, String sorderindex,int start, int size) {
        String sql = "select a from SysUserPayTrade as a,SysUserInfo as b where a.userid=b.userid  and b.status= '1' and a.userid="+userid;
        String sqlcount = "select count(*) from SysUserPayTrade as a,SysUserInfo as b where a.userid=b.userid  and b.status='1' and a.userid="+userid;
        if (subject != null && subject.trim().length() > 0) {
            sql += " and a.subject like '%" + subject + "%'";
            sqlcount += " and a.subject like '%" + subject + "%'";
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and b.username like '%" + username + "%'";
            sqlcount += " and b.username like '%" + username + "%'";
        }
        if (createdate != null && createdate.trim().length() > 0) {
            sql += " and a.createdate like '%" + createdate + "%'";
            sqlcount += " and a.createdate like '%" + createdate + "%'";
        }
        if (body != null && body.trim().length() > 0) {
            sql += " and a.body like '%" + body + "%'";
            sqlcount += " and a.body like '%" + body + "%'";
        }
        if (paytype != null && paytype.trim().length() > 0) {
            sql += " and a.paytype ='" + paytype + "'";
            sqlcount += " and a.paytype ='" + paytype + "'";
        }
        if (state != null && state.trim().length() > 0) {
            sql += " and a.state ='" + state + "'";
            sqlcount += " and a.state ='" + state + "'";
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