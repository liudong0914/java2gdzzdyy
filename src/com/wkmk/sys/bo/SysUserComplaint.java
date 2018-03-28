package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户被投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserComplaint extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer usercomplaintid;
	private Integer complaintid;//关联投诉id
	private String type;//1在线答疑投诉，2在线批阅作业投诉
	private Integer userid;//关联投诉用户
	private String descript;//投诉内容简要说明，给被投诉用户看的
	private String createdate;//创建时间
	private String enddate;//投诉结束时间，用户被投诉后账号被禁用截止时间

	public SysUserComplaint(){
	}

 	public Integer getUsercomplaintid() {
		return this.usercomplaintid;
	}

	public void setUsercomplaintid(Integer usercomplaintid) {
		this.usercomplaintid=usercomplaintid;
	}

	public Integer getComplaintid() {
		return this.complaintid;
	}

	public void setComplaintid(Integer complaintid) {
		this.complaintid=complaintid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("usercomplaintid",getUsercomplaintid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserComplaint))
		return false;
		SysUserComplaint castOther = (SysUserComplaint)other;
		return new EqualsBuilder().append(usercomplaintid, castOther.usercomplaintid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(usercomplaintid).toHashCode();
	}

}