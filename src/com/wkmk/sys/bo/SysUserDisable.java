package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户禁用记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserDisable extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer disableid;
	private SysUserInfo sysUserInfo;//禁用用户
	private String starttime;//禁用开始时间，默认当前时间
	private String endtime;//禁用结束时间
	private String descript;//禁用原因描述
	private Integer outlinkid;//禁用原因，关联处理禁用的外部链接id
	private String outlinktype;//1表示处理在线答疑投诉，2恶意抢单答疑未回答(关联答疑订单)

	public SysUserDisable(){
	}

 	public Integer getDisableid() {
		return this.disableid;
	}

	public void setDisableid(Integer disableid) {
		this.disableid=disableid;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime=starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime=endtime;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("disableid",getDisableid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserDisable))
		return false;
		SysUserDisable castOther = (SysUserDisable)other;
		return new EqualsBuilder().append(disableid, castOther.disableid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(disableid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public Integer getOutlinkid() {
		return outlinkid;
	}

	public void setOutlinkid(Integer outlinkid) {
		this.outlinkid = outlinkid;
	}

	public String getOutlinktype() {
		return outlinktype;
	}

	public void setOutlinktype(String outlinktype) {
		this.outlinktype = outlinktype;
	}

}