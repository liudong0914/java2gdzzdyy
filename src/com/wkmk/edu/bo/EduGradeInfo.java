package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教育年级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduGradeInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer gradeid;
	private String gradename;//年级名称
	private Integer orderindex;
	private String status;//0禁用，1正常
	private Integer subjectid;//所属学科
	private Integer xueduanid;//所属学段
	private Integer cxueduanid;//所属子学段，如一年级
	private String updatetime;//修改时间

	public EduGradeInfo(){
	}

 	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid=gradeid;
	}

	public String getGradename() {
		return this.gradename;
	}

	public void setGradename(String gradename) {
		this.gradename=gradename;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getXueduanid() {
		return this.xueduanid;
	}

	public void setXueduanid(Integer xueduanid) {
		this.xueduanid=xueduanid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("gradeid",getGradeid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduGradeInfo))
		return false;
		EduGradeInfo castOther = (EduGradeInfo)other;
		return new EqualsBuilder().append(gradeid, castOther.gradeid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(gradeid).toHashCode();
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public Integer getCxueduanid() {
		return cxueduanid;
	}

	public void setCxueduanid(Integer cxueduanid) {
		this.cxueduanid = cxueduanid;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}