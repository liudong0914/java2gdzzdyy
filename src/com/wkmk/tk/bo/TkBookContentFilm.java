package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 作业本微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilm extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fid;
	private String title;//微课名称
	private Integer orderindex;//微课在当前作业本内容中的排序
	private String type;//默认1解题微课，2举一反三微课
	private String status;//默认0待审核，1审核通过
	private Integer bookcontentid;//作业本内容
	private Integer bookid;
	private Integer filmid;//关联微课
	private String filmtwocode;//解题微课关联二维码图片路径
	private SysUserInfo sysUserInfo;
	private String createdate;
	private Integer hits;//购买用户播放记录，试听播放记录在其他地方单独记录

	public TkBookContentFilm(){
	}

 	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid=fid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("fid",getFid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentFilm))
		return false;
		TkBookContentFilm castOther = (TkBookContentFilm)other;
		return new EqualsBuilder().append(fid, castOther.fid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fid).toHashCode();
	}

	public String getFilmtwocode() {
		return filmtwocode;
	}

	public void setFilmtwocode(String filmtwocode) {
		this.filmtwocode = filmtwocode;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

}