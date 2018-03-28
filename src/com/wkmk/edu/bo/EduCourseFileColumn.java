package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程资源目录信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileColumn extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer filecolumnid;
	private String title;
	private String columnno;
	private String status;//状态，1正常，2禁用
	private Integer unitid;
	private String isopen;//是否开放给学生，0不开放，1开放
	private String secondtitle;//开放给学生的别名

	public EduCourseFileColumn(){
	}

 	public Integer getFilecolumnid() {
		return this.filecolumnid;
	}

	public void setFilecolumnid(Integer filecolumnid) {
		this.filecolumnid=filecolumnid;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String getIsopen() {
		return this.isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen=isopen;
	}

	public String getSecondtitle() {
		return this.secondtitle;
	}

	public void setSecondtitle(String secondtitle) {
		this.secondtitle=secondtitle;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("filecolumnid",getFilecolumnid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseFileColumn))
		return false;
		EduCourseFileColumn castOther = (EduCourseFileColumn)other;
		return new EqualsBuilder().append(filecolumnid, castOther.filecolumnid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(filecolumnid).toHashCode();
	}

}