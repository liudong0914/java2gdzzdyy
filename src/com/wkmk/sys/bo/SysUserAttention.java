package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 微信用户关注</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserAttention extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer attentionid;
	private String nickname;
	private String openid;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private String createdate;
	private String deletedate;
	private String registertime;
	private String unregistertime;
	private Integer userid;
	private Integer unitid;

	public SysUserAttention(){
	}

 	public Integer getAttentionid() {
		return this.attentionid;
	}

	public void setAttentionid(Integer attentionid) {
		this.attentionid=attentionid;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname=nickname;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid=openid;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex=sex;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language=language;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city=city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province=province;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country=country;
	}

	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl=headimgurl;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getDeletedate() {
		return this.deletedate;
	}

	public void setDeletedate(String deletedate) {
		this.deletedate=deletedate;
	}

	public String getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime=registertime;
	}

	public String getUnregistertime() {
		return this.unregistertime;
	}

	public void setUnregistertime(String unregistertime) {
		this.unregistertime=unregistertime;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("attentionid",getAttentionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserAttention))
		return false;
		SysUserAttention castOther = (SysUserAttention)other;
		return new EqualsBuilder().append(attentionid, castOther.attentionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(attentionid).toHashCode();
	}

}