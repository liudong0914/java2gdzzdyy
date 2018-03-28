package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 题库试题类型</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsType extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer typeid;
	private String typeno;//题型分类编号，排序
	private String typename;//题型名称，如：单选题、填空题等
	private String type;//固定码表值：客观题【A=单选题，B=多选题，C=判断题，D=连线题】，【S=主观题】，详情参考Constants.questionstype
	private Integer subjectid;//学科id
	private Integer unitid;//单位id
	private String countnum;//这类学科存在的题目数量
	private String percent;//这类学科题目数量的百分比
	private String score;//这学科这类题目的分数

	public TkQuestionsType(){
	}

 	public Integer getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid=typeid;
	}

	public String getTypeno() {
		return this.typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno=typeno;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename=typename;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("typeid",getTypeid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsType))
		return false;
		TkQuestionsType castOther = (TkQuestionsType)other;
		return new EqualsBuilder().append(typeid, castOther.typeid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(typeid).toHashCode();
	}

	public String getCountnum() {
		return countnum;
	}

	public void setCountnum(String countnum) {
		this.countnum = countnum;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}