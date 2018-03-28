package com.wkmk.tk.service;

import java.util.List;
import java.util.Map;

import com.wkmk.tk.bo.TkClassInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 班级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface TkClassInfoManager {
	/**
	 *根据id获取班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo getTkClassInfo(String classid);

	/**
	 *根据id获取班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo getTkClassInfo(Integer classid);

	/**
	 *增加班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo addTkClassInfo(TkClassInfo tkClassInfo);

	/**
	 *删除班级信息
	 *@param classid Integer
	 *@return TkClassInfo
	 */
	public TkClassInfo delTkClassInfo(String classid);

	/**
	 *删除班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo delTkClassInfo(TkClassInfo tkClassInfo);

	/**
	 *修改班级信息
	 *@param tkClassInfo TkClassInfo
	 *@return TkClassInfo
	 */
	public TkClassInfo updateTkClassInfo(TkClassInfo tkClassInfo);

	/**
	 *获取班级信息集合
	 *@return List
	 */
	public List getTkClassInfos (List<SearchModel> condition, String sorderindex, int pagesize);

	/**
	 *获取班级信息集合
 	 *@return List
	 */
	public List getTkClassInfoAndBooks(String userid);
	
	/**
	 *获取班级编号集合
 	 *@return List
	 */
	public List getAllClassNos();
	
	/**
	 *获取班级信息
 	 *@return List
	 */
	public TkClassInfo getTkClassInfoByClassno(String classno);
	
	/**
	 *获取一页班级信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkClassInfos (List<SearchModel> condition, String orderby, int start, int pagesize);
	
	/**
	 * 查询班级内的学员列表
	 * @param classid
	 * @return
	 */
	public PageList getBookContentByClass(Map param,String orderby,int start,int pagesize);
	
	
	/**
	 * 根据班级id查询该班级密码
	 * @param classid
	 * @return
	 */
	public List getPasswordByClass(String classid);
	
	/**
	 * 根据班级id和bookcontentid查询学员列表
	 * @param param
	 * @param sorderindex
	 * @param start
	 * @param pagesize
	 * @return
	 */
	public PageList getStudentByBookContent(Map param,String sorderindex,int start,int pagesize);
	
	/**
	 * 查询作业完成情况
	 * @param classid
	 * @param userid
	 * @param bookcontentid
	 * @return
	 */
	public List getCompletion(String classid,String userid,String bookcontentid);
	
	/**
	 * 查询班级内是否存在学员
	 * @param classid
	 * @return
	 */
	public List getClassUser(String classid);
	
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
	public List getClassInfoCountByType(String strings);
	
	/**
	 * 
	  * 方法描述：每天注册班级总数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午4:28:52
	 */
	public List getClassInfosOfAdd();
	
	/**
	 * 
	  * 方法描述：每天注册班级最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getClassInfosOfAddMaxNum();
}