package com.wkmk.vwh.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 视频库服务器信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhComputerInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer computerid;
	private String computername;//视频存放服务器名称
	private String ip;
	private String port;
	private Integer unitid;

	public VwhComputerInfo(){
	}

 	public Integer getComputerid() {
		return this.computerid;
	}

	public void setComputerid(Integer computerid) {
		this.computerid=computerid;
	}

	public String getComputername() {
		return this.computername;
	}

	public void setComputername(String computername) {
		this.computername=computername;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip=ip;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port=port;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("computerid",getComputerid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof VwhComputerInfo))
		return false;
		VwhComputerInfo castOther = (VwhComputerInfo)other;
		return new EqualsBuilder().append(computerid, castOther.computerid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(computerid).toHashCode();
	}

}