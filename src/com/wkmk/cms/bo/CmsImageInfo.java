package com.wkmk.cms.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 图片广告信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsImageInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer imageid;
	private String title;//标题
	private String url;//跳转地址
	private String createdate;//创建时间
	private String sketch;//缩略图，建议尺寸大小：640*312
	private String status;//状态，0待审核发布，1已发布
	private Integer orderindex;//排序，越大越靠前
	private Integer unitid;//单位id

	public CmsImageInfo(){
	}

 	public Integer getImageid() {
		return this.imageid;
	}

	public void setImageid(Integer imageid) {
		this.imageid=imageid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url=url;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("imageid",getImageid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof CmsImageInfo))
		return false;
		CmsImageInfo castOther = (CmsImageInfo)other;
		return new EqualsBuilder().append(imageid, castOther.imageid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(imageid).toHashCode();
	}

}