package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程视频</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFilm extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer coursefilmid;
	private String qrcodeno;//课程视频二维码编号，系统唯一性
	private String title;//展示微课名称，或二维码对应微课名称
	private Integer filmid;//关联微课
	private Integer coursecolumnid;//课程目录
	private Integer courseid;//所属课程
	private Integer orderindex;//排序，默认为0，预留目录关联多个课程使用
	private String status;//状态，默认1正常，2禁用
	private String createdate;//创建时间
	private Integer hits;//查看次数
	private Integer sellcount;//售卖数量，销量
	private Float price;//标价
	private Float discount;//折扣
	private Float sellprice;//售价，小于等于0表示免费
	
	public EduCourseFilm(){
	}

 	public Integer getCoursefilmid() {
		return this.coursefilmid;
	}

	public void setCoursefilmid(Integer coursefilmid) {
		this.coursefilmid=coursefilmid;
	}

	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public Integer getCoursecolumnid() {
		return this.coursecolumnid;
	}

	public void setCoursecolumnid(Integer coursecolumnid) {
		this.coursecolumnid=coursecolumnid;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
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

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits=hits;
	}

	public Integer getSellcount() {
		return this.sellcount;
	}

	public void setSellcount(Integer sellcount) {
		this.sellcount=sellcount;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount=discount;
	}

	public Float getSellprice() {
		return this.sellprice;
	}

	public void setSellprice(Float sellprice) {
		this.sellprice=sellprice;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("coursefilmid",getCoursefilmid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseFilm))
		return false;
		EduCourseFilm castOther = (EduCourseFilm)other;
		return new EqualsBuilder().append(coursefilmid, castOther.coursefilmid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(coursefilmid).toHashCode();
	}

	public String getQrcodeno() {
		return qrcodeno;
	}

	public void setQrcodeno(String qrcodeno) {
		this.qrcodeno = qrcodeno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}