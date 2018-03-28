package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUser extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer courseuserid;
	private Integer courseclassid;//所属课程班，即批次
	private Integer courseid;//关联课程id
	private Integer userid;//课程用户
	private String status;//状态，默认1正常，2禁用
	private String createdate;//创建时间
	private String usertype;//用户类型，1专家教师，2助教，3学生
	private String vip;//1表示为vip用户，即院企用户（已付费），vip用户可直接查看课程所有资源
	private String startdate;//vip开始时间
	private String enddate;//vip结束时间
	private String validitytime;//vip有效时间

	public EduCourseUser(){
	}

 	public Integer getCourseuserid() {
		return this.courseuserid;
	}
 	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setCourseuserid(Integer courseuserid) {
		this.courseuserid=courseuserid;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype=usertype;
	}

	public String getVip() {
		return this.vip;
	}

	public void setVip(String vip) {
		this.vip=vip;
	}

	public String getValiditytime() {
		return validitytime;
	}

	public void setValiditytime(String validitytime) {
		this.validitytime = validitytime;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("courseuserid",getCourseuserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseUser))
		return false;
		EduCourseUser castOther = (EduCourseUser)other;
		return new EqualsBuilder().append(courseuserid, castOther.courseuserid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(courseuserid).toHashCode();
	}

	public Integer getCourseclassid() {
		return courseclassid;
	}

	public void setCourseclassid(Integer courseclassid) {
		this.courseclassid = courseclassid;
	}

}