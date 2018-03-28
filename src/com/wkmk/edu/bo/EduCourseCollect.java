package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程收藏</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCollect extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer collectid;
	private EduCourseInfo eduCourseInfo;//被收藏课程
	private Integer userid;//收藏课程用户
	private String createdate;//收藏时间

	public EduCourseCollect(){
	}

 	public Integer getCollectid() {
		return this.collectid;
	}

	public void setCollectid(Integer collectid) {
		this.collectid=collectid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("collectid",getCollectid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseCollect))
		return false;
		EduCourseCollect castOther = (EduCourseCollect)other;
		return new EqualsBuilder().append(collectid, castOther.collectid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(collectid).toHashCode();
	}

	public EduCourseInfo getEduCourseInfo() {
		return eduCourseInfo;
	}

	public void setEduCourseInfo(EduCourseInfo eduCourseInfo) {
		this.eduCourseInfo = eduCourseInfo;
	}

}