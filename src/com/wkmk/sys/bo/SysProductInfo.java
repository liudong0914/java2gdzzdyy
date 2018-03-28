package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统产品信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysProductInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer productid;
	private String productno;//产品编号
	private String productname;//产品名称
	private String shortname;//产品简称
	private String status;//状态(0禁用，1开通)
	private String type;//产品类型(1自主产品，2合作产品，3第三方产品等)
	private String sketch;//缩略图
	private String linkurl;//外部链接url地址
	private Integer orderindex;//排序
	private String descript;//产品简单描述

	public SysProductInfo(){
	}

 	public Integer getProductid() {
		return this.productid;
	}

	public void setProductid(Integer productid) {
		this.productid=productid;
	}

	public String getProductno() {
		return this.productno;
	}

	public void setProductno(String productno) {
		this.productno=productno;
	}

	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname=productname;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname=shortname;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getLinkurl() {
		return this.linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl=linkurl;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("productid",getProductid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysProductInfo))
		return false;
		SysProductInfo castOther = (SysProductInfo)other;
		return new EqualsBuilder().append(productid, castOther.productid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(productid).toHashCode();
	}

}