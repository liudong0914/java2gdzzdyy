package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级学生拍照上传</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUpload extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer uploadid;
	private Integer bookcontentid;//具体作业内容
	private String imgpath;//图片路径
	private String createdate;//上传时间
	private Integer userid;//学员
	private Integer classid;//班级

	public TkClassUpload(){
	}

 	public Integer getUploadid() {
		return this.uploadid;
	}

	public void setUploadid(Integer uploadid) {
		this.uploadid=uploadid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public String getImgpath() {
		return this.imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath=imgpath;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("uploadid",getUploadid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassUpload))
		return false;
		TkClassUpload castOther = (TkClassUpload)other;
		return new EqualsBuilder().append(uploadid, castOther.uploadid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(uploadid).toHashCode();
	}

}