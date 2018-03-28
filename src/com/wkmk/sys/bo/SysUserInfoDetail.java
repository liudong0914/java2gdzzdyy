package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统用户扩展信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoDetail extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer userid;
	private String qq;
	private String msn;
	private String birthday;
	private String telephone;
	private String cardtype;
	private String cardno;
	private String nation;
	private String province;
	private String city;
	private String county;
	private String postcode;
	private String address;
	private String mood;
	private String descript;
	private String flag;
	private String pwdquestion;
	private String pwdanswer;
	private String createdate;
	private String lastlogin;
	private Integer logintimes;
	private String updatetime;
	private String education;
	private String jobtitle;
	private String canaddcourse;//如果是教师身份，是否允许添加课程，默认0不允许
	private String thetitle;//职称，头衔，手动填写
	private String major;//专业名词，手动填写

	public SysUserInfoDetail(){
	}

 	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq=qq;
	}

	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn=msn;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday=birthday;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype=cardtype;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno=cardno;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation=nation;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province=province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city=city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county=county;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address=address;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag=flag;
	}

	public String getPwdquestion() {
		return this.pwdquestion;
	}

	public void setPwdquestion(String pwdquestion) {
		this.pwdquestion=pwdquestion;
	}

	public String getPwdanswer() {
		return this.pwdanswer;
	}

	public void setPwdanswer(String pwdanswer) {
		this.pwdanswer=pwdanswer;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin=lastlogin;
	}

	public Integer getLogintimes() {
		return this.logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes=logintimes;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime=updatetime;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education=education;
	}

	public String getJobtitle() {
		return this.jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle=jobtitle;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("userid",getUserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserInfoDetail))
		return false;
		SysUserInfoDetail castOther = (SysUserInfoDetail)other;
		return new EqualsBuilder().append(userid, castOther.userid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(userid).toHashCode();
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getCanaddcourse() {
		return canaddcourse;
	}

	public void setCanaddcourse(String canaddcourse) {
		this.canaddcourse = canaddcourse;
	}

	public String getThetitle() {
		return thetitle;
	}

	public void setThetitle(String thetitle) {
		this.thetitle = thetitle;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

}