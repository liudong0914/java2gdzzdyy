package com.wkmk.tk.service.impl;

import java.util.List;
import java.util.Map;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.service.TkClassInfoManager;

/**
 *<p>Description: 班级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
@SuppressWarnings("rawtypes")
public class TkClassInfoManagerImpl implements TkClassInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "班级信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo getTkClassInfo(String classid){
		Integer iid = Integer.valueOf(classid);
		return  (TkClassInfo)baseDAO.getObject(modelname,TkClassInfo.class,iid);
	}

	/**
	 *根据id获取班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo getTkClassInfo(Integer classid){
		return  (TkClassInfo)baseDAO.getObject(modelname,TkClassInfo.class,classid);
	}

	/**
	 *增加班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo addTkClassInfo(TkClassInfo tkClassInfo){
		return (TkClassInfo)baseDAO.addObject(modelname,tkClassInfo);
	}

	/**
	 *删除班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo delTkClassInfo(String classid){
		TkClassInfo model = getTkClassInfo(classid);
		return (TkClassInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo delTkClassInfo(TkClassInfo tkClassInfo){
		return (TkClassInfo)baseDAO.delObject(modelname,tkClassInfo);
	}

	/**
	 *修改班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo updateTkClassInfo(TkClassInfo tkClassInfo){
		return (TkClassInfo)baseDAO.updateObject(modelname,tkClassInfo);
	}

	/**
	 *获取班级信息集合
 	 *@return List
	 */
	
	public List getTkClassInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkClassInfo",condition,orderby,pagesize);
	}
	
	/**
	 *获取班级信息集合
 	 *@return List
	 */
	public List getTkClassInfoAndBooks(String userid){
		String sql = "select a.classid, a.classname, b.bookid, b.title, b.version from TkClassInfo as a, TkBookInfo as b where a.bookid=b.bookid and b.status='1' and a.userid=" + userid + " order by a.createdate desc";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取班级编号集合
 	 *@return List
	 */
	public List getAllClassNos(){
		String sql = "select a.classno from TkClassInfo as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取班级信息
 	 *@return List
	 */
	public TkClassInfo getTkClassInfoByClassno(String classno){
		String sql = "select a from TkClassInfo as a where a.classno='" + classno + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (TkClassInfo) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页班级信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkClassInfo","classid",condition, orderby, start,pagesize);
	}
	
	/**
	 * 查询班级内的作业列表 
	 * @param classid
	 * @return
	 */
	public PageList getBookContentByClass(Map param,String sorderindex,int stat,int pagesize) {
		String sql="SELECT bc FROM TkBookContent as bc,TkClassUpload as cu WHERE cu.bookcontentid=bc.bookcontentid and cu.classid="+param.get("classid");
		String sqlCount="select count(distinct cu.bookcontentid) FROM TkBookContent as bc,TkClassUpload as cu WHERE cu.bookcontentid=bc.bookcontentid and cu.classid="+param.get("classid");
		if(param.get("title")!=null&&!"".equals(param.get("title"))){
			sql+=" and bc.title like '%"+param.get("title")+"%'";
			sqlCount+=" and bc.title like '%"+param.get("title")+"%'";
		}
		if(param.get("contentno")!=null&&!"".equals(param.get("contentno"))){
			sql+=" and bc.contentno like '%"+param.get("contentno")+"%'";
			sqlCount+=" and bc.contentno like '%"+param.get("contentno")+"%'";
		}
		sql += " GROUP BY cu.bookcontentid";
		if (sorderindex != null &&!"".equals(sorderindex)) {
			sql+=" order by " + sorderindex;
		}
		return baseDAO.getPageObjects(sqlCount, sql, stat, pagesize);
	}
	
	/**
	 * 根据班级id查询该班级密码
	 * @param classid
	 * @return
	 */
	public List getPasswordByClass(String classid){
		String sql="select a from TkClassPassword as a where a.classid="+classid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 根据班级id和bookcontentid查询学员列表
	 * @param param
	 * @param sorderindex
	 * @param start
	 * @param pagesize
	 * @return
	 */
	public PageList getStudentByBookContent(Map param,String sorderindex,int start,int pagesize){
		String sql="SELECT u FROM SysUserInfo u,TkClassUpload c where c.userid=u.userid and c.classid="+param.get("classid")+" AND c.bookcontentid="+param.get("bookcontentid");
		String sqlCount="select count(distinct c.userid) FROM SysUserInfo u,TkClassUpload c where c.userid=u.userid and classid="+param.get("classid")+" AND bookcontentid="+param.get("bookcontentid");
		if(!"".equals(param.get("loginname"))&&param.get("loginname")!=null){
			sql+=" and u.loginname like '%"+param.get("loginname")+"%'";
			sqlCount+=" and u.loginname like '%"+param.get("loginname")+"%'";
		}
		if(!"".equals(param.get("username"))&&param.get("username")!=null){
			sql+=" and u.username like '%"+param.get("username")+"%'";
			sqlCount+=" and u.username like '%"+param.get("username")+"%'";
		}
		if(!"".equals(param.get("usertype"))&&param.get("usertype")!=null){
			sql+=" and u.usertype="+param.get("usertype");
			sqlCount+=" and u.usertype="+param.get("usertype");
		}
		if(!"".equals(param.get("studentno"))&&param.get("studentno")!=null){
			sql+=" and u.studentno like '%"+param.get("studentno")+"%'";
			sqlCount+=" and u.studentno like '%"+param.get("studentno")+"%'";
		}
		if(!"".equals(param.get("sex"))&&param.get("sex")!=null){
			sql+=" and u.sex="+param.get("sex");
			sqlCount+=" and u.sex="+param.get("sex");
		}
		sql+=" group by c.userid";
		if(!"".equals(sorderindex)&&sorderindex!=null){
			sql+=" order by "+sorderindex;
		}
		return baseDAO.getPageObjects(sqlCount,sql,start,pagesize);
	}
	
	/**
	 * 查询作业完成情况
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @return
	 */
	public List getCompletion(String classid,String userid,String bookcontentid){
		String sql="SELECT c.classname,u.username,b.title,cu.imgpath FROM TkClassUpload as cu,SysUserInfo as u,TkClassInfo as c,TkBookContent as b WHERE cu.bookcontentid=b.bookcontentid AND cu.classid=c.classid AND cu.userid=u.userid AND cu.bookcontentid="+bookcontentid+" AND cu.userid="+userid+" AND cu.classid="+classid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 查询班级内是否存在学员
	 * @param classid
	 * @return
	 */
	public List getClassUser(String classid){
	    String sql="select u from TkClassUser cu,SysUserInfo u where cu.userid=u.userid AND cu.classid="+classid;
	    return baseDAO.getObjects(sql);
	}
	/**
	 * 根据班级类型进行分组，并获取每一类的总数
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-9-27 下午6:21:13 
	* @lastModified  2016-9-27 下午6:21:13 
	* @version  1.0 
	* @param hql
	* @return
	 */
	public List getClassInfoCountByType(String strings){
		String sql="";
		String strings1 = strings.substring(0,strings.length()-1);
		if(strings != null && !"".equals(strings)){
			sql = "select  c.gradename,c.subjectid,COUNT(*)  from EduGradeInfo as c , TkBookInfo AS b , TkClassInfo AS a WHERE c.gradeid = b.gradeid AND b.bookid = a.bookid  GROUP BY "+strings1;
		}
			List list = baseDAO.getObjects(sql);
			if (list != null && list.size() > 0) {
				return  list;
			} else {
				return null;
			}
	}
	
	/**
	 * 
	  * 方法描述：每天注册班级总数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午4:28:52
	 */
	public List getClassInfosOfAdd(){
		String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(*) from TkClassInfo as a   group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}

	/**
	 * 
	  * 方法描述：每天注册班级最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getClassInfosOfAddMaxNum(){
		String sql="select count(*)  from TkClassInfo as a   group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
}