package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 客户端用户作业本内容授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserBookContent extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private Integer userid;//用户id
	private Integer bookcontentid;//作业本内容id
	private Integer bookid;//作业本id
	private Integer gradeid;//年级id
	private Integer subjectid;//学科id

	public TkUserBookContent(){
	}

 	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid=tid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid=gradeid;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tid",getTid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkUserBookContent))
		return false;
		TkUserBookContent castOther = (TkUserBookContent)other;
		return new EqualsBuilder().append(tid, castOther.tid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tid).toHashCode();
	}

}