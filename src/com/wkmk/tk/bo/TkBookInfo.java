package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 * <p>
 * Description: 作业本信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer bookid;

	private String title;//课本名称
	private String bookno;//课本编号
	private Integer subjectid;//所属学科
	private Integer gradeid;//所属年级
	private String version;//教材版本，和Constants.version对应
	private String part;//0=全册，1=上册，2=下册
	private String editor;//主编
	private String deputyeditor;//副主编
	private String sketch;//缩略图
	private String status;//默认1正常，2禁用
	private String descript;//课本简介
	private String htmlcontent;//课本详细介绍
	private String createdate;//创建时间
	private String updatetime;
	private Integer userid;
	private Integer unitid;
	private String twocodepath;// 课本二维码图片存放路径
	private String gradename;
	
	private String theyear;//所属年份，如2016年，2017年
	private String vodstate;//当前作业本解题微课开放状态，默认0不开放，1开放解题微课，2开放举一反三微课，3两个一起开放

	public TkBookInfo() {
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBookno() {
		return this.bookno;
	}

	public void setBookno(String bookno) {
		this.bookno = bookno;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getDeputyeditor() {
		return this.deputyeditor;
	}

	public void setDeputyeditor(String deputyeditor) {
		this.deputyeditor = deputyeditor;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getHtmlcontent() {
		return this.htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent = htmlcontent;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getTwocodepath() {
		return this.twocodepath;
	}

	public void setTwocodepath(String twocodepath) {
		this.twocodepath = twocodepath;
	}

	public String toString() {
		return new ToStringBuilder(this).append("bookid", getBookid()).toString();
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookInfo))
			return false;
		TkBookInfo castOther = (TkBookInfo) other;
		return new EqualsBuilder().append(bookid, castOther.bookid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(bookid).toHashCode();
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTheyear() {
		return theyear;
	}

	public void setTheyear(String theyear) {
		this.theyear = theyear;
	}

	public String getVodstate() {
		return vodstate;
	}

	public void setVodstate(String vodstate) {
		this.vodstate = vodstate;
	}

}