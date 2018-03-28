package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassUser extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer classuserid;
	private Integer userid;//班级学生
	private Integer classid;//所属班级【学生手机端只显示作业本名称，班级名称不显示，因为学生默认都知道自己是哪个班级】
	private Integer bookid;//关联作业本，个人购买的学生可能没有班级加入，只能和作业本直接关联，此时classid=0
	private String status;//默认1正常
	private String createdate;

	public TkClassUser(){
	}

 	public Integer getClassuserid() {
		return this.classuserid;
	}

	public void setClassuserid(Integer classuserid) {
		this.classuserid=classuserid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
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
		.append("classuserid",getClassuserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassUser))
		return false;
		TkClassUser castOther = (TkClassUser)other;
		return new EqualsBuilder().append(classuserid, castOther.classuserid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(classuserid).toHashCode();
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}