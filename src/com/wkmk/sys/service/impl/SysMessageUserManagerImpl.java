package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysMessageUser;
import com.wkmk.sys.service.SysMessageUserManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 消息用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageUserManagerImpl implements SysMessageUserManager{

	private BaseDAO baseDAO;
	private String modelname = "消息用户";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser getSysMessageUser(String messageuserid){
		Integer iid = Integer.valueOf(messageuserid);
		return  (SysMessageUser)baseDAO.getObject(modelname,SysMessageUser.class,iid);
	}

	/**
	 *根据id获取消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser getSysMessageUser(Integer messageuserid){
		return  (SysMessageUser)baseDAO.getObject(modelname,SysMessageUser.class,messageuserid);
	}

	/**
	 *增加消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser addSysMessageUser(SysMessageUser sysMessageUser){
		return (SysMessageUser)baseDAO.addObject(modelname,sysMessageUser);
	}

	/**
	 *删除消息用户
	 *@param messageuserid Integer
	 *@return SysMessageUser
	 */
	public SysMessageUser delSysMessageUser(String messageuserid){
		SysMessageUser model = getSysMessageUser(messageuserid);
		return (SysMessageUser)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser delSysMessageUser(SysMessageUser sysMessageUser){
		return (SysMessageUser)baseDAO.delObject(modelname,sysMessageUser);
	}

	/**
	 *修改消息用户
	 *@param sysMessageUser SysMessageUser
	 *@return SysMessageUser
	 */
	public SysMessageUser updateSysMessageUser(SysMessageUser sysMessageUser){
		return (SysMessageUser)baseDAO.updateObject(modelname,sysMessageUser);
	}

	/**
	 *获取消息用户集合
 	 *@return List
	 */
	public List getSysMessageUsers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysMessageUser",condition,orderby,pagesize);
	}
	
	/**
	 * 统计用户未读消息数量
	 * @param userid
	 * @return
	 */
	public String getUnreadMessageCount(String userid){
		String sql="select count(*) from SysMessageUser as a where a.isread='0' and a.userid="+userid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			String unreadnum = list.get(0).toString();
			if("0".equals(unreadnum)){
				unreadnum="";
			}
			return unreadnum;
		}else {
			return "";
		}
	}

	/**
	 *获取一页消息用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageUsers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysMessageUser","messageuserid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取一页消息用户集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageUsers (String messageid,String username,String usertype,String xueduan,String isread,String orderby, int start, int pagesize){
		String sql="select a,b from SysMessageUser as a,SysUserInfo as b where a.userid=b.userid ";
		String sqlcount="select count(*) from SysMessageUser as a,SysUserInfo as b where a.userid=b.userid ";
		if(!"".equals(messageid)){
			sql+=" and a.sysMessageInfo.messageid="+messageid;
			sqlcount+=" and a.sysMessageInfo.messageid="+messageid;
		}
		if(!"".equals(isread)){
			sql+=" and a.isread='"+isread+"'";
			sqlcount+=" and a.isread='"+isread+"'";
		}
		if(!"".equals(username)){
			sql+=" and b.username like '%"+username+"%'";
			sqlcount+=" and b.username like '%"+username+"%'";
		}
		if(!"".equals(usertype)){
			sql+=" and b.usertype = '"+usertype+"'";
			sqlcount+=" and b.usertype = '"+usertype+"'";
		}
		if(!"".equals(xueduan)){
			sql+=" and b.xueduan = '"+xueduan+"'";
			sqlcount+=" and b.xueduan = '"+xueduan+"'";
		}
		if(!"".equals(orderby)){
			sql+=" order by "+orderby;
		}else{
			sql+=" order by a.isread ";
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}
}