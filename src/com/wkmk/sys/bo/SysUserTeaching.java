package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统用户教学设置</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserTeaching extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer teachingid;
	private Integer subjectid;
	private Integer gradeid;
	private Integer userid;

	public SysUserTeaching(){
	}

 	public Integer getTeachingid() {
		return this.teachingid;
	}

	public void setTeachingid(Integer teachingid) {
		this.teachingid=teachingid;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid=gradeid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("teachingid",getTeachingid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserTeaching))
		return false;
		SysUserTeaching castOther = (SysUserTeaching)other;
		return new EqualsBuilder().append(teachingid, castOther.teachingid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(teachingid).toHashCode();
	}

}