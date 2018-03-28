package com.wkmk.tk.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.tk.bo.TkBookContentWatch;
import com.wkmk.tk.service.TkBookContentWatchManager;

/**
 *<p>Description: 音频文件观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentWatchManagerImpl implements TkBookContentWatchManager{

	private BaseDAO baseDAO;
	private String modelname = "音频文件观看记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch getTkBookContentWatch(String watchid){
		Integer iid = Integer.valueOf(watchid);
		return  (TkBookContentWatch)baseDAO.getObject(modelname,TkBookContentWatch.class,iid);
	}

	/**
	 *根据id获取音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch getTkBookContentWatch(Integer watchid){
		return  (TkBookContentWatch)baseDAO.getObject(modelname,TkBookContentWatch.class,watchid);
	}

	/**
	 *增加音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch addTkBookContentWatch(TkBookContentWatch tkBookContentWatch){
		return (TkBookContentWatch)baseDAO.addObject(modelname,tkBookContentWatch);
	}

	/**
	 *删除音频文件观看记录
	 *@param watchid Integer
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch delTkBookContentWatch(String watchid){
		TkBookContentWatch model = getTkBookContentWatch(watchid);
		return (TkBookContentWatch)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch delTkBookContentWatch(TkBookContentWatch tkBookContentWatch){
		return (TkBookContentWatch)baseDAO.delObject(modelname,tkBookContentWatch);
	}

	/**
	 *修改音频文件观看记录
	 *@param tkBookContentWatch TkBookContentWatch
	 *@return TkBookContentWatch
	 */
	public TkBookContentWatch updateTkBookContentWatch(TkBookContentWatch tkBookContentWatch){
		return (TkBookContentWatch)baseDAO.updateObject(modelname,tkBookContentWatch);
	}

	/**
	 *获取音频文件观看记录集合
 	 *@return List
	 */
	public List getTkBookContentWatchs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkBookContentWatch",condition,orderby,pagesize);
	}

	/**
	 *获取一页音频文件观看记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkBookContentWatchs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkBookContentWatch","watchid",condition, orderby, start,pagesize);
	}
	/**
	 * 
	  * 方法描述：每天听力播放次数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getTkBookContentWatchsOfDay(){
		String sql="select a.userid,date_format(a.createdate,'%Y/%m/%d') as day,count(*) from TkBookContentWatch as a    group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}

	/**
	 * 
	  * 方法描述：每天听力播放次数最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getTkBookContentWatchsOfDayMaxNum(){
		String sql="select count(*)  from TkBookContentWatch as a    group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
}