package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkClassUser;
import com.wkmk.tk.service.TkClassUserManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUserManagerImpl implements TkClassUserManager{

	private BaseDAO baseDAO;
	private String modelname = "班级用户信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser getTkClassUser(String classuserid){
		Integer iid = Integer.valueOf(classuserid);
		return  (TkClassUser)baseDAO.getObject(modelname,TkClassUser.class,iid);
	}

	/**
	 *根据id获取班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser getTkClassUser(Integer classuserid){
		return  (TkClassUser)baseDAO.getObject(modelname,TkClassUser.class,classuserid);
	}

	/**
	 *增加班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser addTkClassUser(TkClassUser tkClassUser){
		return (TkClassUser)baseDAO.addObject(modelname,tkClassUser);
	}

	/**
	 *删除班级用户信息
	 *@param classuserid Integer
	 *@return TkClassUser
	 */
	public TkClassUser delTkClassUser(String classuserid){
		TkClassUser model = getTkClassUser(classuserid);
		return (TkClassUser)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser delTkClassUser(TkClassUser tkClassUser){
		return (TkClassUser)baseDAO.delObject(modelname,tkClassUser);
	}

	/**
	 *修改班级用户信息
	 *@param tkClassUser TkClassUser
	 *@return TkClassUser
	 */
	public TkClassUser updateTkClassUser(TkClassUser tkClassUser){
		return (TkClassUser)baseDAO.updateObject(modelname,tkClassUser);
	}

	/**
	 *获取班级用户信息集合
 	 *@return List
	 */
	public List getTkClassUsers(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassUser",condition,orderby,pagesize);
	}
	
	/**
	 *获取班级用户信息集合
 	 *@return List
	 */
	public List getBookAndClassByUserid(String userid){
		String sql = "select b.bookid, b.title, b.version, a.classid from TkClassUser as a, TkBookInfo as b where a.bookid=b.bookid and b.status='1' and a.status='1' and a.userid=" + userid + " order by a.classuserid desc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取班级用户关联作业本信息集合
 	 *@return List
	 */
	public List getAllBookidByUserid(String userid){
		String sql = "select distinct a.bookid from TkClassUser as a where a.userid=" + userid;
		return baseDAO.getObjects(sql);
	}
	
	public int getStudentsByClassid(String classid){
		String sql = "select count(*) from TkClassUser as a where a.classid=" + classid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			 return 0;
		}
	}
	
	public List getSysUserInfosByClassid(String classid){
		String sql = "select b from TkClassUser as a, SysUserInfo as b where a.userid=b.userid and a.status='1' and a.classid=" + classid + " order by b.username asc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 未提交作业学生
	 */
	public List getUncommitStudentsByClassid(String classid, List answerUseridList){
		String sql = "select b from TkClassUser as a, SysUserInfo as b where a.userid=b.userid and a.status='1' and a.classid=" + classid;
		if(answerUseridList != null && answerUseridList.size() > 0){
			StringBuffer str = new StringBuffer();
			for(int i=0, size=answerUseridList.size(); i<size; i++){
				str.append(" and b.userid<>").append(answerUseridList.get(i));
			}
			sql = sql + str.toString();
		}
		sql = sql + " order by b.username asc";
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页班级用户信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassUsers (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassUser","classuserid",condition, orderby, start,pagesize);
	}

}