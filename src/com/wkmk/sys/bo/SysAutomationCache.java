package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统自动化高速缓存</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAutomationCache extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/*
	 * 由于会涉及到数据表的频繁操作，所以过期数据直接删除
	 */
	private Integer cacheid;
	private String keyid;//缓存的键
	private Integer keyvalue;//缓存的键对应的值
	private long livetime;//缓存有效存活时间（单位毫秒），从createdate开始计时
	private String createdate;//开始计时缓存有效时间起始时间

	public SysAutomationCache(){
	}

 	public Integer getCacheid() {
		return this.cacheid;
	}

	public void setCacheid(Integer cacheid) {
		this.cacheid=cacheid;
	}

	public String getKeyid() {
		return this.keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid=keyid;
	}

	public Integer getKeyvalue() {
		return this.keyvalue;
	}

	public void setKeyvalue(Integer keyvalue) {
		this.keyvalue=keyvalue;
	}

	public long getLivetime() {
		return this.livetime;
	}

	public void setLivetime(long livetime) {
		this.livetime=livetime;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("cacheid",getCacheid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysAutomationCache))
		return false;
		SysAutomationCache castOther = (SysAutomationCache)other;
		return new EqualsBuilder().append(cacheid, castOther.cacheid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(cacheid).toHashCode();
	}

}