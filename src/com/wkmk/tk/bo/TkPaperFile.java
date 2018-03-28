package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试卷附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperFile extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fileid;
	private String filename;//附件名称
	private String filepath;//附件路径
	private Integer filesize;//附件大小
	private String fileext;//文件扩展名
	private Integer subjectid;//关联学科
	private String theyear;//试卷所属年份，如：2015,2016,
	private String area;//试卷所属地区，直接填入即可
	private TkPaperType tkPaperType;//所属分类
	private String createdate;//上传时间
	private Integer downloads;//下载次数
	private Integer userid;//上传用户
	private String status;//默认1正常显示，2禁用，0待审核

	public TkPaperFile(){
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

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public String getTheyear() {
		return this.theyear;
	}

	public void setTheyear(String theyear) {
		this.theyear=theyear;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area=area;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads=downloads;
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
		if (!(other instanceof TkPaperFile))
		return false;
		TkPaperFile castOther = (TkPaperFile)other;
		return new EqualsBuilder().append(fileid, castOther.fileid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fileid).toHashCode();
	}

	public TkPaperType getTkPaperType() {
		return tkPaperType;
	}

	public void setTkPaperType(TkPaperType tkPaperType) {
		this.tkPaperType = tkPaperType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}