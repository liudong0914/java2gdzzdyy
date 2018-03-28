package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业本内容校验授权</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentPower extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer powerid;
	private TkBookContent tkBookContent;
	private Integer subjectid;//作业本内容关联学科id，目录树展示用
	private Integer userid;//授权用户id
	private String type;//1内容校验授权，2试题替换授权，3解题微课授权

	public TkBookContentPower(){
	}

 	public Integer getPowerid() {
		return this.powerid;
	}

	public void setPowerid(Integer powerid) {
		this.powerid=powerid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("powerid",getPowerid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentPower))
		return false;
		TkBookContentPower castOther = (TkBookContentPower)other;
		return new EqualsBuilder().append(powerid, castOther.powerid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(powerid).toHashCode();
	}

	public TkBookContent getTkBookContent() {
		return tkBookContent;
	}

	public void setTkBookContent(TkBookContent tkBookContent) {
		this.tkBookContent = tkBookContent;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

}