package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 * <p>
 * Description: 试卷信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkPaperInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer paperid;
	private String title;// 试卷标题
	private String papertype;// 1=普通卷，2=手机卷（只能由客观题组成）【默认都为1，手机卷通过控制只显示里面的客观题和填空题，不用此字段区分】
	private String createdate;
	private String descript;// 试卷描述
	private String status;// 默认1为正常，2为禁用或删除
	private Integer userid;
	private Integer gradeid;// 试卷所属年级
	private Integer subjectid;// 所属所属学科
	private Integer unitid;
	private String filepath;// 试卷dsc格式文件存放路径
	private String htmlfilepath;// dsc文件转换html文件存放路径
	private String gradeName;

	public TkPaperInfo() {
	}

	public Integer getPaperid() {
		return this.paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid = paperid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPapertype() {
		return this.papertype;
	}

	public void setPapertype(String papertype) {
		this.papertype = papertype;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String toString() {
		return new ToStringBuilder(this).append("paperid", getPaperid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperInfo))
			return false;
		TkPaperInfo castOther = (TkPaperInfo) other;
		return new EqualsBuilder().append(paperid, castOther.paperid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(paperid).toHashCode();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getHtmlfilepath() {
		return htmlfilepath;
	}

	public void setHtmlfilepath(String htmlfilepath) {
		this.htmlfilepath = htmlfilepath;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}