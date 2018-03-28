package com.wkmk.vwh.service.impl;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.service.VwhFilmInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmInfoManagerImpl implements VwhFilmInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "视频库视频信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo getVwhFilmInfo(String filmid){
		Integer iid = Integer.valueOf(filmid);
		return  (VwhFilmInfo)baseDAO.getObject(modelname,VwhFilmInfo.class,iid);
	}

	/**
	 *根据id获取视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo getVwhFilmInfo(Integer filmid){
		return  (VwhFilmInfo)baseDAO.getObject(modelname,VwhFilmInfo.class,filmid);
	}

	/**
	 *增加视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo addVwhFilmInfo(VwhFilmInfo vwhFilmInfo){
		return (VwhFilmInfo)baseDAO.addObject(modelname,vwhFilmInfo);
	}

	/**
	 *删除视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo delVwhFilmInfo(String filmid){
		VwhFilmInfo model = getVwhFilmInfo(filmid);
		return (VwhFilmInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo delVwhFilmInfo(VwhFilmInfo vwhFilmInfo){
		return (VwhFilmInfo)baseDAO.delObject(modelname,vwhFilmInfo);
	}

	/**
	 *修改视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo updateVwhFilmInfo(VwhFilmInfo vwhFilmInfo){
		return (VwhFilmInfo)baseDAO.updateObject(modelname,vwhFilmInfo);
	}

	/**
	 *获取视频库视频信息集合
 	 *@return List
	 */
	public List getVwhFilmInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("VwhFilmInfo",condition,orderby,pagesize);
	}
	
	/**
	 *获取视频库视频信息集合
 	 *@return List
	 */
	public List getVwhFilmInfos(List<SearchModel> condition, String typeno, String orderby, int pagesize){
		String sql = null;
		if(typeno != null && !"".equals(typeno)){
			sql = "SELECT a FROM VwhFilmInfo as a, VwhFilmType as b WHERE 1=1";
		}else {
			sql = "SELECT a FROM VwhFilmInfo as a WHERE 1=1";
		}
		
		SearchModel model = null;
		for (int i = 0, size = condition.size(); i < size; i++) {
			model = condition.get(i);
			if("0".equals(model.getType()) && model.getSvalue() != null && !"".equals(model.getSvalue())){
				if ("in".equals(model.getRelation().toLowerCase())) {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
				} else {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
				}
			}
			if("1".equals(model.getType()) && model.getIvalue() != null && model.getIvalue() != -01 && !"-01".equals(model.getIvalue().toString())){
				if ("in".equals(model.getRelation().toLowerCase())) {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
				} else {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue();
				}
			}
		}
		if(typeno != null && !"".equals(typeno)){
			sql += " AND a.typeid=b.typeid AND b.typeno like '" + typeno + "%'";
		}
		if (!"".equals(orderby)) {
			sql += " ORDER BY " + orderby;
		}
		
		return baseDAO.getObjects(sql, pagesize);
	}
	
	/**
	 *获取视频库服务器id集合
 	 *@return List
	 */
	public List getAllComputerids(Integer unitid){
		String sql = "select distinct a.computerid from VwhFilmInfo as a";
		if(unitid != null && !"".equals(unitid.toString())){
			sql = sql + " where a.unitid=" + unitid;
		}
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取视频库分类id集合
 	 *@return List
	 */
	public List getAllTypeids(Integer unitid){
		String sql = "select distinct a.typeid from VwhFilmInfo as a where a.unitid=" + unitid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页视频库视频信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("VwhFilmInfo","filmid",condition, orderby, start,pagesize);
	}

	/**
	 *获取一页视频库视频信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmInfos (List<SearchModel> condition, String typeno, String orderby, int start, int pagesize){
		String sql = null;
		if(typeno != null && !"".equals(typeno)){
			sql = " FROM VwhFilmInfo as a, VwhFilmType as b WHERE 1=1";
		}else {
			sql = " FROM VwhFilmInfo as a WHERE 1=1";
		}
		
		SearchModel model = null;
		for (int i = 0, size = condition.size(); i < size; i++) {
			model = condition.get(i);
			if("0".equals(model.getType()) && model.getSvalue() != null && !"".equals(model.getSvalue())){
				if ("in".equals(model.getRelation().toLowerCase())) {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
				} else {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
				}
			}
			if("1".equals(model.getType()) && model.getIvalue() != null && model.getIvalue() != -01 && !"-01".equals(model.getIvalue().toString())){
				if ("in".equals(model.getRelation().toLowerCase())) {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " (" + model.getIvalue() + ")";
				} else {
					sql += " AND a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue();
				}
			}
		}
		if(typeno != null && !"".equals(typeno)){
			sql += " AND a.typeid=b.typeid AND b.typeno like '" + typeno + "%'";
		}
		String totalsql = "SELECT COUNT(a.filmid)" + sql;
		String listsql = "SELECT a" + sql;
		if (!"".equals(orderby)) {
			listsql += " ORDER BY " + orderby;
		}
		
		return baseDAO.getPageObjects(totalsql, listsql, start, pagesize);
	}
}