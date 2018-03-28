package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 习题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsFilm extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private Integer questionid;
	private Integer filmid;

	public TkQuestionsFilm(){
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

	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tid",getTid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsFilm))
		return false;
		TkQuestionsFilm castOther = (TkQuestionsFilm)other;
		return new EqualsBuilder().append(tid, castOther.tid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tid).toHashCode();
	}

}