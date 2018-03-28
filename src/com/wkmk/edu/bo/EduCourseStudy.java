package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程学习点击播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseStudy extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer studyid;
	private Integer courseid;//课程id
	private Integer coursefilmid;//课程视频
	private Integer userid;//学习用户
	private String userip;//用户ip
	private String createdate;//创建时间

	public EduCourseStudy(){
	}

 	public Integer getStudyid() {
		return this.studyid;
	}

	public void setStudyid(Integer studyid) {
		this.studyid=studyid;
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

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("studyid",getStudyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseStudy))
		return false;
		EduCourseStudy castOther = (EduCourseStudy)other;
		return new EqualsBuilder().append(studyid, castOther.studyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(studyid).toHashCode();
	}

	public Integer getCoursefilmid() {
		return coursefilmid;
	}

	public void setCoursefilmid(Integer coursefilmid) {
		this.coursefilmid = coursefilmid;
	}

}