package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 习题举一反三</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsSimilar extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private Integer questionid;//原始题
	private Integer similarquestionid;//关联的举一反三题

	public TkQuestionsSimilar(){
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

	public Integer getSimilarquestionid() {
		return this.similarquestionid;
	}

	public void setSimilarquestionid(Integer similarquestionid) {
		this.similarquestionid=similarquestionid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tid",getTid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsSimilar))
		return false;
		TkQuestionsSimilar castOther = (TkQuestionsSimilar)other;
		return new EqualsBuilder().append(tid, castOther.tid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tid).toHashCode();
	}

}