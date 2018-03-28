package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 知识点信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduKnopointInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer knopointid;
	private String title;//知识点名称
	private String knopointno;//知识点编号
	private String parentno;//父编号
	private String type;//0表示知识点大纲，1表示具体知识点
	private String kno;//知识点记忆编号，由用户通过页面录入，方便用户快速查找，如：1.1, 1.2, 1.1.1等
	private String status;//0禁用，1正常
	private String descript;//知识点描述
	private String gradetype;//2表示小学知识点，3表示初中知识点，4表示高中知识点【与学段表主键对应】
	private Integer subjectid;//学科id，【公共知识点只与学科关联，此时教材版本和教材年级id都为0】
	private String updatetime;//更新时间

	public EduKnopointInfo(){
	}

 	public Integer getKnopointid() {
		return this.knopointid;
	}

	public void setKnopointid(Integer knopointid) {
		this.knopointid=knopointid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getKnopointno() {
		return this.knopointno;
	}

	public void setKnopointno(String knopointno) {
		this.knopointno=knopointno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getKno() {
		return this.kno;
	}

	public void setKno(String kno) {
		this.kno=kno;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime=updatetime;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("knopointid",getKnopointid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduKnopointInfo))
		return false;
		EduKnopointInfo castOther = (EduKnopointInfo)other;
		return new EqualsBuilder().append(knopointid, castOther.knopointid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(knopointid).toHashCode();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGradetype() {
		return gradetype;
	}

	public void setGradetype(String gradetype) {
		this.gradetype = gradetype;
	}

}