package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程目录信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseColumn extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer columnid;
	private String title;//课程目录名称
	private String columnno;//课程编号
	private String parentno;//课程父编号
	private String status;//状态，1正常，2禁用
	private Integer courseid;//所属课程
	private Integer unitid;//所属单位
	private String linkurl;//外部链接地址，组合模块目录跳转到单模块课程展示页面
	private String isopen;//是否开放给学生，0不开放，1开放
	private String secondTitle;//开放给学生的别名

	public EduCourseColumn(){
	}

 	public Integer getColumnid() {
		return this.columnid;
	}

	public void setColumnid(Integer columnid) {
		this.columnid=columnid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getColumnno() {
		return this.columnno;
	}

	public void setColumnno(String columnno) {
		this.columnno=columnno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}
	
	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("columnid",getColumnid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseColumn))
		return false;
		EduCourseColumn castOther = (EduCourseColumn)other;
		return new EqualsBuilder().append(columnid, castOther.columnid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(columnid).toHashCode();
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

}