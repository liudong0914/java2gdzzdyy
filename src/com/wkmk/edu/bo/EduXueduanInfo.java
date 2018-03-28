package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教育学段信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduXueduanInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer xueduanid;
	private String name;//学段名称
	private Integer parentid;//学段父id，默认一级学段父id为0
	private Integer orderindex;
	private String status;//0禁用，1正常
	private String updatetime;//修改时间

	public EduXueduanInfo(){
	}

 	public Integer getXueduanid() {
		return this.xueduanid;
	}

	public void setXueduanid(Integer xueduanid) {
		this.xueduanid=xueduanid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
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
		.append("xueduanid",getXueduanid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduXueduanInfo))
		return false;
		EduXueduanInfo castOther = (EduXueduanInfo)other;
		return new EqualsBuilder().append(xueduanid, castOther.xueduanid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(xueduanid).toHashCode();
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}