package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试卷内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperContent extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer contentid;
	private Integer paperid;//试卷id
	private Integer questionid;//题库id
	private Integer orderindex;//试题在当前试卷中的排序
	private Float score;//默认为题库的参考分值，也可以手动修改，当前值为当前试题所占试卷的分值大小

	public TkPaperContent(){
	}

 	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid=contentid;
	}

	public Integer getPaperid() {
		return this.paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid=paperid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score=score;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("contentid",getContentid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperContent))
		return false;
		TkPaperContent castOther = (TkPaperContent)other;
		return new EqualsBuilder().append(contentid, castOther.contentid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(contentid).toHashCode();
	}

}