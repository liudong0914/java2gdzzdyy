package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 题库知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsKnopoint extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private Integer questionid;
	private String type;//O=客观题，S=主观题
	private Integer knopointid;

	public TkQuestionsKnopoint(){
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

	public Integer getKnopointid() {
		return this.knopointid;
	}

	public void setKnopointid(Integer knopointid) {
		this.knopointid=knopointid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tid",getTid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsKnopoint))
		return false;
		TkQuestionsKnopoint castOther = (TkQuestionsKnopoint)other;
		return new EqualsBuilder().append(tid, castOther.tid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tid).toHashCode();
	}

}