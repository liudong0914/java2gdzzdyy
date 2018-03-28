package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysMessageInfo;
import com.wkmk.sys.service.SysMessageInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 系统消息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageInfoManagerImpl implements SysMessageInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统消息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo getSysMessageInfo(String messageid){
		Integer iid = Integer.valueOf(messageid);
		return  (SysMessageInfo)baseDAO.getObject(modelname,SysMessageInfo.class,iid);
	}

	/**
	 *根据id获取系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo getSysMessageInfo(Integer messageid){
		return  (SysMessageInfo)baseDAO.getObject(modelname,SysMessageInfo.class,messageid);
	}

	/**
	 *增加系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo addSysMessageInfo(SysMessageInfo sysMessageInfo){
		return (SysMessageInfo)baseDAO.addObject(modelname,sysMessageInfo);
	}

	/**
	 *删除系统消息
	 *@param messageid Integer
	 *@return SysMessageInfo
	 */
	public SysMessageInfo delSysMessageInfo(String messageid){
		SysMessageInfo model = getSysMessageInfo(messageid);
		return (SysMessageInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo delSysMessageInfo(SysMessageInfo sysMessageInfo){
		return (SysMessageInfo)baseDAO.delObject(modelname,sysMessageInfo);
	}

	/**
	 *修改系统消息
	 *@param sysMessageInfo SysMessageInfo
	 *@return SysMessageInfo
	 */
	public SysMessageInfo updateSysMessageInfo(SysMessageInfo sysMessageInfo){
		return (SysMessageInfo)baseDAO.updateObject(modelname,sysMessageInfo);
	}

	/**
	 *获取系统消息集合
 	 *@return List
	 */
	public List getSysMessageInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysMessageInfo",condition,orderby,pagesize);
	}
	
	/**
	 *获取一页系统消息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysMessageInfo", "messageid", condition, orderby, start, pagesize);
	}

	/**
	 *获取一页系统消息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysMessageInfos (String title,String username,String orderby,int start,int pagesize){
		String sql="select a,b.username from SysMessageInfo as a,SysUserInfo as b where a.userid=b.userid";
		String sqlcount="select count(*) from SysMessageInfo as a,SysUserInfo as b where a.userid=b.userid";
		if(!"".equals(title)){
			sql+=" and a.title like '%"+title+"%'";	
			sqlcount+=" and a.title like '%"+title+"%'";	
		}
		if(!"".equals(username)){
			sql+=" and b.username like '%"+username+"%'";	
			sqlcount+=" and b.username like '%"+username+"%'";	
		}
		if(!"".equals(orderby)){
			sql+=" order by "+orderby;
		}else{
			sql+=" order by a.createdate desc";
		}
		
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}

	/**
	 * 统计用户未读消息数量
	 * @param userid
	 * @return
	 */
	public String getUnreadMessage(Integer userid){
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
}