package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程用户模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserModule extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer usermoduleid;
	private Integer userid;//用户id
	private Integer courseclassid;//所属课程班，即批次
	private Integer courseid;//所属课程
	private String moduleid;//模块编号，固定值。1课程信息，2课程目录，3微课管理，4课程资源，6学员管理，7课程答疑
	private String moduletype;//模块权限类型，1只读（只可查看信息），2管理（可增、删、改）

	public EduCourseUserModule(){
	}

 	public Integer getUsermoduleid() {
		return this.usermoduleid;
	}

	public void setUsermoduleid(Integer usermoduleid) {
		this.usermoduleid=usermoduleid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
	}

	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid=moduleid;
	}

	public String getModuletype() {
		return this.moduletype;
	}

	public void setModuletype(String moduletype) {
		this.moduletype=moduletype;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("usermoduleid",getUsermoduleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseUserModule))
		return false;
		EduCourseUserModule castOther = (EduCourseUserModule)other;
		return new EqualsBuilder().append(usermoduleid, castOther.usermoduleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(usermoduleid).toHashCode();
	}

	public Integer getCourseclassid() {
		return courseclassid;
	}

	public void setCourseclassid(Integer courseclassid) {
		this.courseclassid = courseclassid;
	}

}