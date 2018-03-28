package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentFilmWatch;
import com.wkmk.tk.service.TkBookContentFilmWatchManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmWatchManagerImpl implements TkBookContentFilmWatchManager{

	private BaseDAO baseDAO;
	private String modelname = "解题微课播放记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch getTkBookContentFilmWatch(String watchid){
		Integer iid = Integer.valueOf(watchid);
		return  (TkBookContentFilmWatch)baseDAO.getObject(modelname,TkBookContentFilmWatch.class,iid);
	}

	/**
	 *根据id获取解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch getTkBookContentFilmWatch(Integer watchid){
		return  (TkBookContentFilmWatch)baseDAO.getObject(modelname,TkBookContentFilmWatch.class,watchid);
	}

	/**
	 *增加解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch addTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch){
		return (TkBookContentFilmWatch)baseDAO.addObject(modelname,tkBookContentFilmWatch);
	}

	/**
	 *删除解题微课播放记录
	 *@param watchid Integer
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch delTkBookContentFilmWatch(String watchid){
		TkBookContentFilmWatch model = getTkBookContentFilmWatch(watchid);
		return (TkBookContentFilmWatch)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch delTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch){
		return (TkBookContentFilmWatch)baseDAO.delObject(modelname,tkBookContentFilmWatch);
	}

	/**
	 *修改解题微课播放记录
	 *@param tkBookContentFilmWatch TkBookContentFilmWatch
	 *@return TkBookContentFilmWatch
	 */
	public TkBookContentFilmWatch updateTkBookContentFilmWatch(TkBookContentFilmWatch tkBookContentFilmWatch){
		return (TkBookContentFilmWatch)baseDAO.updateObject(modelname,tkBookContentFilmWatch);
	}

	/**
	 *获取解题微课播放记录集合
 	 *@return List
	 */
	public List getTkBookContentFilmWatchs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentFilmWatch",condition,orderby,pagesize);
	}

	/**
	 *获取一页解题微课播放记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentFilmWatchs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentFilmWatch","watchid",condition, orderby, start,pagesize);
	}

	/**
	 * 
	  * 方法描述：每天解题微课播放记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午5:51:24
	 */
	public List getTkBookContentFilmWatchsOfAll(){
		String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(*) from TkBookContentFilmWatch as a   group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	/* 
	* 方法描述：每天解题微课播放记录最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getTkBookContentFilmWatchsOfMaxNum(){
	String sql="select count(*)  from TkBookContentFilmWatch as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
	}

	/**
	 * 
	  * 方法描述：试听解题微课播放记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-29 下午5:55:10
	 */
	public List getTkBookContentFilmAuditionWatchOfAll(){
		String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(*) from TkBookContentFilmAuditionWatch as a   group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	/* 
	* 方法描述：每天试听解题微课播放记录最大峰值
	* @param: 
	* @return: 
	* @version: 1.0
	* @author: 刘冬
	* @version: 2016-11-25 上午9:38:45
	*/
	public List getTkBookContentFilmAuditionWatchOfMaxNum(){
	String sql="select count(*)  from TkBookContentFilmAuditionWatch as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
	}
}