package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程班</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseClass extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer courseclassid;
	private String classname;//班级名称，批次名称
	private Integer courseid;//所属课程
	private String status;//0待发布，1审核通过，2发布待审核，3审核不通过，4禁用
	private String createdate;//创建时间
	private String startdate;//开始时间
	private String enddate;//结束时间
	private Integer usercount;//班级用户数，只统计学员人数

	public EduCourseClass(){
	}

 	public Integer getCourseclassid() {
		return this.courseclassid;
	}

	public void setCourseclassid(Integer courseclassid) {
		this.courseclassid=courseclassid;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname=classname;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
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

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate=startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public Integer getUsercount() {
		return this.usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount=usercount;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("courseclassid",getCourseclassid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseClass))
		return false;
		EduCourseClass castOther = (EduCourseClass)other;
		return new EqualsBuilder().append(courseclassid, castOther.courseclassid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(courseclassid).toHashCode();
	}

}