package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级密码</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassPassword extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer passwordid;
	private String password;//随机生成不重复密码，RandomCodeUtil.getClassPasswords获取随机密码
	private String used;//默认0未使用，1已被学生绑定
	private Integer userid;//绑定学生id
	private Integer classid;//所属班级
	private Integer unitid;

	public TkClassPassword(){
	}

 	public Integer getPasswordid() {
		return this.passwordid;
	}

	public void setPasswordid(Integer passwordid) {
		this.passwordid=passwordid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String getUsed() {
		return this.used;
	}

	public void setUsed(String used) {
		this.used=used;
	}

	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("passwordid",getPasswordid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassPassword))
		return false;
		TkClassPassword castOther = (TkClassPassword)other;
		return new EqualsBuilder().append(passwordid, castOther.passwordid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(passwordid).toHashCode();
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}