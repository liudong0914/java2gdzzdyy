package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduSubjectInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer subjectid;
	private String subjectname;//学科名称
	private String fullname;//学科全称【主要用于后台查看】
	private String banner;//学科的banner图片
	private Integer orderindex;
	private String status;//0禁用，1正常
	private String updatetime;//修改时间

	public EduSubjectInfo(){
	}

 	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public String getSubjectname() {
		return this.subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname=subjectname;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("subjectid",getSubjectid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduSubjectInfo))
		return false;
		EduSubjectInfo castOther = (EduSubjectInfo)other;
		return new EqualsBuilder().append(subjectid, castOther.subjectid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(subjectid).toHashCode();
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

}