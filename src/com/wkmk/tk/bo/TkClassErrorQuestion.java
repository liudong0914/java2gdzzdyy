package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级错题集</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassErrorQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer cerrorid;
	private Integer contentid;//试卷内容
	private String type;//默认1为作业本对应试卷的题，2为课前预习题【直接关联TkQuestionInfo主键questionid】，3为举一反三的题【同理2】
	private Integer bookcontentid;//具体作业内容
	private Integer classid;//所属班级
	private String createdate;

	public TkClassErrorQuestion(){
	}

 	public Integer getCerrorid() {
		return this.cerrorid;
	}

	public void setCerrorid(Integer cerrorid) {
		this.cerrorid=cerrorid;
	}

	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid=contentid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("cerrorid",getCerrorid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassErrorQuestion))
		return false;
		TkClassErrorQuestion castOther = (TkClassErrorQuestion)other;
		return new EqualsBuilder().append(cerrorid, castOther.cerrorid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(cerrorid).toHashCode();
	}

}