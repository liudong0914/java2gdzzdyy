package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户历史搜索关键词</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserSearchKeywords extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer searchid;
	private String keywords;//搜索关键字
	private String searchtype;//搜索类型，默认为1（搜索解题微课）
	private String createdate;//创建时间
	private Integer userid;//搜索用户
	private String userip;//用户所在ip地址

	public SysUserSearchKeywords(){
	}

 	public Integer getSearchid() {
		return this.searchid;
	}

	public void setSearchid(Integer searchid) {
		this.searchid=searchid;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords=keywords;
	}

	public String getSearchtype() {
		return this.searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype=searchtype;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("searchid",getSearchid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserSearchKeywords))
		return false;
		SysUserSearchKeywords castOther = (SysUserSearchKeywords)other;
		return new EqualsBuilder().append(searchid, castOther.searchid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(searchid).toHashCode();
	}

}