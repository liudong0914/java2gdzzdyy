package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 题库组卷试题蓝</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperCart extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer cartid;
	private Integer userid;//用户id
	private Integer subjectid;//学科id
	private Integer xueduanid;//学段id【2小学，3初中，4高中】
	private Integer paperid;//空是新增，有值为修改
	private Integer orderindex;//试题在当前试卷中的排序
	private Integer questionsid;//试题id
	private Float score;//默认为题库的参考分值，也可以手动修改，当前值为当前试题所占试卷的分值大小

	public TkPaperCart(){
	}

 	public Integer getCartid() {
		return this.cartid;
	}

	public void setCartid(Integer cartid) {
		this.cartid=cartid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getPaperid() {
		return this.paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid=paperid;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Integer getQuestionsid() {
		return this.questionsid;
	}

	public void setQuestionsid(Integer questionsid) {
		this.questionsid=questionsid;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score=score;
	}

	public Integer getXueduanid() {
		return xueduanid;
	}

	public void setXueduanid(Integer xueduanid) {
		this.xueduanid = xueduanid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("cartid",getCartid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperCart))
		return false;
		TkPaperCart castOther = (TkPaperCart)other;
		return new EqualsBuilder().append(cartid, castOther.cartid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(cartid).toHashCode();
	}

}