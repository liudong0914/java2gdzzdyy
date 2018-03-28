package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer userid;
	private String loginname;
	private String username;
	private String nickname;
	private String usertype;//用户类型(如：0系统用户，1老师，2学生，3家长等)
	private String password;
	private String sex;//性别(0男，1女)
	private String email;
	private String mobile;
	private String photo;
	private String status;//状态(0申请，1正常，2禁用，3删除)
	private String studentno;
	private String stno;
	private String temppass;
	private String uuid;
	private Integer unitid;
	private String xueduan;//学段：1小学，2初中，3高中
	//作业宝2.0功能，添加用户虚拟货币，可充值与提现，初步兑换比例1:1
	private Float money;
	private String authentication;//1教师已认证，0未认证。认证过的教师可以抢单
	private String paypassword;//支付密码，所有涉及到支付的地方，都需要输入支付密码，加密方式：MD5（和密码一样）
	private String vipmark;//是否为导入vip用户；0或者null新增或者导入的普通用户，1导入的未激活的vip用户，2导入的已激活的vip用户

	public SysUserInfo(){
	}

 	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname=loginname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname=nickname;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype=usertype;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex=sex;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile=mobile;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo=photo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getStudentno() {
		return this.studentno;
	}

	public void setStudentno(String studentno) {
		this.studentno=studentno;
	}

	public String getStno() {
		return this.stno;
	}

	public void setStno(String stno) {
		this.stno=stno;
	}

	public String getTemppass() {
		return this.temppass;
	}

	public void setTemppass(String temppass) {
		this.temppass=temppass;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid=uuid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String getVipmark() {
		return vipmark;
	}

	public void setVipmark(String vipmark) {
		this.vipmark = vipmark;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("userid",getUserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserInfo))
		return false;
		SysUserInfo castOther = (SysUserInfo)other;
		return new EqualsBuilder().append(userid, castOther.userid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(userid).toHashCode();
	}

	public String getXueduan() {
		return xueduan;
	}

	public void setXueduan(String xueduan) {
		this.xueduan = xueduan;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

}