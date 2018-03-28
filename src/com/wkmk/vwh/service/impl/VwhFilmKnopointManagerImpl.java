package com.wkmk.vwh.service.impl;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmKnopoint;
import com.wkmk.vwh.service.VwhFilmKnopointManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微课知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmKnopointManagerImpl implements VwhFilmKnopointManager{

	private BaseDAO baseDAO;
	private String modelname = "微课知识点";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint getVwhFilmKnopoint(String fid){
		Integer iid = Integer.valueOf(fid);
		return  (VwhFilmKnopoint)baseDAO.getObject(modelname,VwhFilmKnopoint.class,iid);
	}

	/**
	 *根据id获取微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint getVwhFilmKnopoint(Integer fid){
		return  (VwhFilmKnopoint)baseDAO.getObject(modelname,VwhFilmKnopoint.class,fid);
	}

	/**
	 *增加微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint addVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint){
		return (VwhFilmKnopoint)baseDAO.addObject(modelname,vwhFilmKnopoint);
	}

	/**
	 *删除微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint delVwhFilmKnopoint(String fid){
		VwhFilmKnopoint model = getVwhFilmKnopoint(fid);
		return (VwhFilmKnopoint)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint delVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint){
		return (VwhFilmKnopoint)baseDAO.delObject(modelname,vwhFilmKnopoint);
	}
	
	/**
	 *删除微课知识点
	 */
	public void delVwhFilmKnopoint(Integer filmid, Integer knopointid){
		String sql = "select a from VwhFilmKnopoint as a where 1=1";
		if(filmid != null){
			sql = sql + " and a.filmid=" + filmid;
		}
		if(knopointid != null){
			sql = sql + " and a.knopointid=" + knopointid;
		}
		baseDAO.delObjects(sql);
	}

	/**
	 *修改微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint updateVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint){
		return (VwhFilmKnopoint)baseDAO.updateObject(modelname,vwhFilmKnopoint);
	}

	/**
	 *获取微课知识点集合
 	 *@return List
	 */
	public List getVwhFilmKnopoints(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("VwhFilmKnopoint",condition,orderby,pagesize);
	}
	
	/**
	 *获取所有知识点id
 	 *@return List
	 */
	public List getAllKnopointids(){
		String sql = "select distinct a.knopointid from VwhFilmKnopoint as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取微课关联的所有知识点
 	 *@return List
	 */
	public List getEduKnopointInfoByFilmid(Integer filmid){
		String sql = "select a from EduKnopointInfo as a, VwhFilmKnopoint as b where a.knopointid=b.knopointid and b.filmid=" + filmid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页微课知识点集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmKnopoints (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("VwhFilmKnopoint","fid",condition, orderby, start,pagesize);
	}

}