package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 举一反三观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilarWatch extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer watchid;
	private Integer userid;
	private Integer questionid;
	private String createdate;
	private String userip;

	public TkQuestionsSimilarWatch(){
	}

 	public Integer getWatchid() {
		return this.watchid;
	}

	public void setWatchid(Integer watchid) {
		this.watchid=watchid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("watchid",getWatchid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsSimilarWatch))
		return false;
		TkQuestionsSimilarWatch castOther = (TkQuestionsSimilarWatch)other;
		return new EqualsBuilder().append(watchid, castOther.watchid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(watchid).toHashCode();
	}

}