package com.wkmk.cms.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 资讯信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer newsid;
	private String title;//资讯标题
	private String linkurl;//资讯连接地址，如果有，则跳转到此连接地址，没有则显示htmlcontent内容
	private String keywords;//关键字
	private String sketch;//缩略图
	private String author;//资讯发布作者，默认是当前登录用户
	private String htmlcontent;//资讯内容
	private String status;//资讯状态，0待审核，1已审核
	private String createdate;//资讯创建时间
	private String happendate;//资讯发布时间，页面显示的时间
	private Integer hits;//点击量
	private String recommand;//是否推荐，资讯列表排序：推荐倒序、发布时间倒序
	private CmsNewsColumn cmsNewsColumn;//关联资讯栏目
	private Integer userid;//发布资讯用户
	private Integer unitid;//关联单位

	public CmsNewsInfo(){
	}

 	public Integer getNewsid() {
		return this.newsid;
	}

	public void setNewsid(Integer newsid) {
		this.newsid=newsid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getLinkurl() {
		return this.linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl=linkurl;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords=keywords;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author=author;
	}

	public String getHtmlcontent() {
		return this.htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent=htmlcontent;
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

	public String getHappendate() {
		return this.happendate;
	}

	public void setHappendate(String happendate) {
		this.happendate=happendate;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits=hits;
	}

	public String getRecommand() {
		return this.recommand;
	}

	public void setRecommand(String recommand) {
		this.recommand=recommand;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("newsid",getNewsid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof CmsNewsInfo))
		return false;
		CmsNewsInfo castOther = (CmsNewsInfo)other;
		return new EqualsBuilder().append(newsid, castOther.newsid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(newsid).toHashCode();
	}

	public CmsNewsColumn getCmsNewsColumn() {
		return cmsNewsColumn;
	}

	public void setCmsNewsColumn(CmsNewsColumn cmsNewsColumn) {
		this.cmsNewsColumn = cmsNewsColumn;
	}

}