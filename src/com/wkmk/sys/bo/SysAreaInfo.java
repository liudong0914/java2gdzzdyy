package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 地区信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAreaInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer areaid;
	private String areaname;//地区名称，省、市、区
	private String areano;//地区编号
	private String parentno;//地区父编号
	private String citycode;//城市编码【唯一性】
	private String postcode;//邮政编码
	private String telecode;//电话区号
	private String pinyin;//地区拼音
	private String shortname;//简称

	public SysAreaInfo(){
	}

 	public Integer getAreaid() {
		return this.areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid=areaid;
	}

	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname=areaname;
	}

	public String getAreano() {
		return this.areano;
	}

	public void setAreano(String areano) {
		this.areano=areano;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode=citycode;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}

	public String getTelecode() {
		return this.telecode;
	}

	public void setTelecode(String telecode) {
		this.telecode=telecode;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin=pinyin;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("areaid",getAreaid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysAreaInfo))
		return false;
		SysAreaInfo castOther = (SysAreaInfo)other;
		return new EqualsBuilder().append(areaid, castOther.areaid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(areaid).toHashCode();
	}

}