package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程资源下载记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileDownload extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer downloadid;
	private Integer fileid;//下载文件
	private Integer userid;//下载用户
	private String createdate;//下载时间

	public EduCourseFileDownload(){
	}

 	public Integer getDownloadid() {
		return this.downloadid;
	}

	public void setDownloadid(Integer downloadid) {
		this.downloadid=downloadid;
	}

	public Integer getFileid() {
		return this.fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid=fileid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("downloadid",getDownloadid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseFileDownload))
		return false;
		EduCourseFileDownload castOther = (EduCourseFileDownload)other;
		return new EqualsBuilder().append(downloadid, castOther.downloadid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(downloadid).toHashCode();
	}

}