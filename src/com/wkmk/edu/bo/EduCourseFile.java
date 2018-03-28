package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程文件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFile extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	//默认只支持文档上传，暂不支持视频和音频上传，后面再根据具体需求扩展
	private Integer fileid;
	private String filename;//文件名称
	private String filepath;//文件路径
	private String pdfpath;//文件转码pdf路径
	private Integer pagenum;//文件页码数
	private String sketch;//缩略图
	private Long filesize;//文件大小
	private String fileext;//扩展名
	private String createdate;//上传时间
	private String convertstatus;//转码状态，0待转换，1正常，2失败
	private String notifystatus;
	private String filetype;//文件类型，1文档，2图片，3音频,4视频
	private String coursefiletype;//资源类型
	private Integer hits;//查看次数
	private Integer downloads;//下载次数
	private Integer coursecolumnid;//关联课程目录
	private Integer courseid;//所属课程
	private Integer userid;//上传用户，教师，助教

	public EduCourseFile(){
	}

 	public Integer getFileid() {
		return this.fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid=fileid;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename=filename;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath=filepath;
	}

	public String getPdfpath() {
		return this.pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath=pdfpath;
	}

	public Integer getPagenum() {
		return this.pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum=pagenum;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize=filesize;
	}

	public String getFileext() {
		return this.fileext;
	}

	public void setFileext(String fileext) {
		this.fileext=fileext;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getConvertstatus() {
		return this.convertstatus;
	}

	public void setConvertstatus(String convertstatus) {
		this.convertstatus=convertstatus;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits=hits;
	}

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads=downloads;
	}

	public Integer getCoursecolumnid() {
		return this.coursecolumnid;
	}

	public void setCoursecolumnid(Integer coursecolumnid) {
		this.coursecolumnid=coursecolumnid;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("fileid",getFileid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseFile))
		return false;
		EduCourseFile castOther = (EduCourseFile)other;
		return new EqualsBuilder().append(fileid, castOther.fileid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fileid).toHashCode();
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getCoursefiletype() {
		return coursefiletype;
	}

	public void setCoursefiletype(String coursefiletype) {
		this.coursefiletype = coursefiletype;
	}

	public String getNotifystatus() {
		return notifystatus;
	}

	public void setNotifystatus(String notifystatus) {
		this.notifystatus = notifystatus;
	}

}