package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业本附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookFile extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fileid;
	private String filename;//附件名称
	private String filepath;//文件上传路径
	private String pdfpath;//转码后pdf存放路径
	private Integer pagenum;//文件总页数
	private String imagepath;//pdf转图片存放路径，文件夹路径
	private Integer filesize;//文件大小
	private String fileext;//文件扩展名
	private String createdate;//上传时间
	private String convertstatus;//转码状态，0待转码，1转码成功，2转码失败
	private String type;//1作业本原稿pdf，2作业本电子教案，3作业本内容碎片化图片试题文件，关联bookcontentid
	private Integer downloads;//附件被下载次数
	private Integer bookcontentid;//关联作业本内容
	private Integer bookid;//关联作业本
	private Integer userid;//上传用户

	public TkBookFile(){
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

	public String getImagepath() {
		return this.imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath=imagepath;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads=downloads;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
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
		if (!(other instanceof TkBookFile))
		return false;
		TkBookFile castOther = (TkBookFile)other;
		return new EqualsBuilder().append(fileid, castOther.fileid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fileid).toHashCode();
	}

	public Integer getBookcontentid() {
		return bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid = bookcontentid;
	}

}