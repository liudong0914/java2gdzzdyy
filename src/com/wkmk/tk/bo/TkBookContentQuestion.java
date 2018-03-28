package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业本内容关联试题</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private Integer bookcontentid;//作业本具体作业id
	private Integer questionid;//关联试题
	private String type;//1=课前预习题，2=教学案题

	public TkBookContentQuestion(){
	}

 	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid=tid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tid",getTid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentQuestion))
		return false;
		TkBookContentQuestion castOther = (TkBookContentQuestion)other;
		return new EqualsBuilder().append(tid, castOther.tid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tid).toHashCode();
	}

	public Integer getBookcontentid() {
		return bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid = bookcontentid;
	}

}