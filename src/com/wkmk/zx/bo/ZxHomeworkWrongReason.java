package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线作业常见错误原因</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkWrongReason extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer wrid;
	private String title;//常见错误原因名称
	private Integer subjectid;//所属学科
	private Integer orderindex;//排序
	private String createdate;//创建时间
	private Integer userid;//创建用户

	public ZxHomeworkWrongReason(){
	}

 	public Integer getWrid() {
		return this.wrid;
	}

	public void setWrid(Integer wrid) {
		this.wrid=wrid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
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

	public String toString() {
		return new ToStringBuilder(this)
		.append("wrid",getWrid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHomeworkWrongReason))
		return false;
		ZxHomeworkWrongReason castOther = (ZxHomeworkWrongReason)other;
		return new EqualsBuilder().append(wrid, castOther.wrid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(wrid).toHashCode();
	}

}