package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 个人错题集</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserErrorQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer uerrorid;
	private Integer contentid;//试卷内容
	private String type;//默认1为作业本对应试卷的题，2为课前预习题【直接关联TkQuestionInfo主键questionid】，3为举一反三的题【同理2】
	private Integer bookcontentid;//具体作业内容
	private Integer userid;//用户
	private Integer classid;//所属班级，没有关联班级则classid=0
	private String createdate;
	private Integer taskid;//记录备存用

	public TkUserErrorQuestion(){
	}

 	public Integer getUerrorid() {
		return this.uerrorid;
	}

	public void setUerrorid(Integer uerrorid) {
		this.uerrorid=uerrorid;
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
		.append("uerrorid",getUerrorid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkUserErrorQuestion))
		return false;
		TkUserErrorQuestion castOther = (TkUserErrorQuestion)other;
		return new EqualsBuilder().append(uerrorid, castOther.uerrorid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(uerrorid).toHashCode();
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

}