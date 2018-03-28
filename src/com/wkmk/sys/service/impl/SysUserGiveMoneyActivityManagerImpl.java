package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;
import com.wkmk.sys.service.SysUserGiveMoneyActivityManager;

/**
 *<p>Description: 用户赠送学币活动</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyActivityManagerImpl implements SysUserGiveMoneyActivityManager{

	private BaseDAO baseDAO;
	private String modelname = "用户赠送学币活动";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity getSysUserGiveMoneyActivity(String activityid){
		Integer iid = Integer.valueOf(activityid);
		return  (SysUserGiveMoneyActivity)baseDAO.getObject(modelname,SysUserGiveMoneyActivity.class,iid);
	}

	/**
	 *根据id获取用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity getSysUserGiveMoneyActivity(Integer activityid){
		return  (SysUserGiveMoneyActivity)baseDAO.getObject(modelname,SysUserGiveMoneyActivity.class,activityid);
	}

	/**
	 *增加用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity addSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity){
		return (SysUserGiveMoneyActivity)baseDAO.addObject(modelname,sysUserGiveMoneyActivity);
	}

	/**
	 *删除用户赠送龙币活动
	 *@param activityid Integer
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity delSysUserGiveMoneyActivity(String activityid){
		SysUserGiveMoneyActivity model = getSysUserGiveMoneyActivity(activityid);
		return (SysUserGiveMoneyActivity)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity delSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity){
		return (SysUserGiveMoneyActivity)baseDAO.delObject(modelname,sysUserGiveMoneyActivity);
	}

	/**
	 *修改用户赠送龙币活动
	 *@param sysUserGiveMoneyActivity SysUserGiveMoneyActivity
	 *@return SysUserGiveMoneyActivity
	 */
	public SysUserGiveMoneyActivity updateSysUserGiveMoneyActivity(SysUserGiveMoneyActivity sysUserGiveMoneyActivity){
		return (SysUserGiveMoneyActivity)baseDAO.updateObject(modelname,sysUserGiveMoneyActivity);
	}

	/**
	 *获取用户赠送龙币活动集合
 	 *@return List
	 */
	public List getSysUserGiveMoneyActivitys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserGiveMoneyActivity",condition,orderby,pagesize);
	}

	/**
	 *获取一页用户赠送龙币活动集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserGiveMoneyActivitys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserGiveMoneyActivity","activityid",condition, orderby, start,pagesize);
	}

}