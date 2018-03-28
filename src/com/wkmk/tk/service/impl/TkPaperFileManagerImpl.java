package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.service.TkPaperFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 试卷附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperFileManagerImpl implements TkPaperFileManager{

	private BaseDAO baseDAO;
	private String modelname = "试卷附件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile getTkPaperFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (TkPaperFile)baseDAO.getObject(modelname,TkPaperFile.class,iid);
	}

	/**
	 *根据id获取试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile getTkPaperFile(Integer fileid){
		return  (TkPaperFile)baseDAO.getObject(modelname,TkPaperFile.class,fileid);
	}

	/**
	 *增加试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile addTkPaperFile(TkPaperFile tkPaperFile){
		return (TkPaperFile)baseDAO.addObject(modelname,tkPaperFile);
	}

	/**
	 *删除试卷附件
	 *@param fileid Integer
	 *@return TkPaperFile
	 */
	public TkPaperFile delTkPaperFile(String fileid){
		TkPaperFile model = getTkPaperFile(fileid);
		return (TkPaperFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile delTkPaperFile(TkPaperFile tkPaperFile){
		return (TkPaperFile)baseDAO.delObject(modelname,tkPaperFile);
	}

	/**
	 *修改试卷附件
	 *@param tkPaperFile TkPaperFile
	 *@return TkPaperFile
	 */
	public TkPaperFile updateTkPaperFile(TkPaperFile tkPaperFile){
		return (TkPaperFile)baseDAO.updateObject(modelname,tkPaperFile);
	}

	/**
	 *获取试卷附件集合
 	 *@return List
	 */
	public List getTkPaperFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("TkPaperFile",condition,orderby,pagesize);
	}

	/**
	 *获取一页试卷附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("TkPaperFile","fileid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取一页试卷附件集合，和所属学科名称
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageTkPaperFilesAndSubjectName (List<SearchModel> condition,String orderby,int start,int pagesize){
		
			String sql = "select a,b.subjectname from TkPaperFile as a,EduSubjectInfo as b where a.subjectid=b.subjectid";
			String sqlcount = "select count(*) from TkPaperFile as a,EduSubjectInfo as b where a.subjectid=b.subjectid";
			if (condition != null && condition.size() > 0) {
				SearchModel model = null;
				for (int i = 0, size = condition.size(); i < size; i++) {
					model = (SearchModel) condition.get(i);
					if ("0".equals(model.getType())) {
						if ("in".equals(model.getRelation().toLowerCase())) {
							sql = sql + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
							sqlcount = sqlcount + " and a." + model.getItem() + " " + model.getRelation() + " (" + model.getSvalue() + ")";
						} else {
							sql = sql + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
							sqlcount = sqlcount + " and a." + model.getItem() + " " + model.getRelation() + " '" + model.getSvalue() + "'";
						}
					}else if("1".equals(model.getType())){
						sql = sql + " and a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue() + "";
						sqlcount = sqlcount + " and a." + model.getItem() + " " + model.getRelation() + " " + model.getIvalue() + "";
					}
				}
			}
			if(!"".equals(orderby)){
				sql+=" order by "+orderby;
			}else{
				sql+=" order by a.createdate desc";
			}		
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}
	
	/**
	 * 查询所有被使用的试卷分类id
	 */
	public List getPaperTypeids(){
		String sql="select a.tkPaperType from TkPaperFile as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 查询试卷附件中的所有年份
	 */
	public List getPaperFileYears(){
		String sql="select distinct a.theyear from TkPaperFile as a order by a.theyear";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 按照学科分类查询试卷被下载总次数
	 */
	public List getSubjectCount(){
		String sql="SELECT a.subjectname,COUNT(*) FROM EduSubjectInfo as a , TkPaperFile as b , TkPaperFileDownload as c where a.subjectid=b.subjectid and b.fileid=c.fileid GROUP BY a.subjectid ORDER BY a.orderindex ";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 查询试卷每天下载的总次数
	 */
	public List getDownloadCount(){
		String sql="select date_format(b.createdate,'%Y/%m/%d'),count(*) from TkPaperFile as a,TkPaperFileDownload as b where a.fileid=b.fileid group by date_format(b.createdate,'%Y/%m/%d') order by date_format(b.createdate,'%Y/%m/%d') ";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 查询有试卷的学科分类
	 */
	public List getSubjectList(){
		String sql="SELECT a.subjectid,a.subjectname FROM EduSubjectInfo as a , TkPaperFile as b where a.subjectid=b.subjectid GROUP BY a.subjectid ORDER BY a.orderindex ";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 * 根据学科id查询试卷每天被下载 
	 */
	public List getDownloadCountByDate(){
		String sql="SELECT DATE_FORMAT(c.createdate,'%Y/%m/%d'),a.subjectid,COUNT(*) FROM EduSubjectInfo AS a , TkPaperFile AS b , TkPaperFileDownload AS c WHERE a.subjectid=b.subjectid AND b.fileid=c.fileid GROUP BY DATE_FORMAT(c.createdate,'%Y/%m/%d'),a.subjectid ORDER BY DATE_FORMAT(c.createdate,'%Y/%m/%d')";
		return baseDAO.getObjects(sql);
	}
}