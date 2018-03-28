package com.wkmk.vwh.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.util.bo.BaseObject;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer filmid;
	private String title;//标题
	private String keywords;//关键字，简介
	private String descript;//描述
	private String actor;//作者，主讲教师
	private String sketch;//存放缩略图图片路径，视频截图，或缩略图,默认可以上传缩略图，如果没有上传，则自动从上传的影片截图获取
	private String sketchimg;//存放缩略图图片名称，和上面的sketch合并为完整路径【sketch改为完整路径，此字段值暂时和sketch值保持一直，手机图片备用】
	private Integer orderindex;//排序
	private Integer hits;//点击量
	private String status;//状态 0待审核，1已审核，2禁用，9删除
	private String createdate;//创建时间
	private String updatetime;//修改时间
	private Integer computerid;//所在服务器
	private EduGradeInfo eduGradeInfo;
	private SysUserInfo sysUserInfo;//视频上传作者
	private Integer unitid;
	private String twocodepath;//二维码图片存放路径

	public VwhFilmInfo(){
	}
	
 	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords=keywords;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getActor() {
		return this.actor;
	}

	public void setActor(String actor) {
		this.actor=actor;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits=hits;
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

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime=updatetime;
	}

	public Integer getComputerid() {
		return this.computerid;
	}

	public void setComputerid(Integer computerid) {
		this.computerid=computerid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("filmid",getFilmid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof VwhFilmInfo))
		return false;
		VwhFilmInfo castOther = (VwhFilmInfo)other;
		return new EqualsBuilder().append(filmid, castOther.filmid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(filmid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getSketchimg() {
		return sketchimg;
	}

	public void setSketchimg(String sketchimg) {
		this.sketchimg = sketchimg;
	}

	public EduGradeInfo getEduGradeInfo() {
		return eduGradeInfo;
	}

	public void setEduGradeInfo(EduGradeInfo eduGradeInfo) {
		this.eduGradeInfo = eduGradeInfo;
	}

	public String getTwocodepath() {
		return twocodepath;
	}

	public void setTwocodepath(String twocodepath) {
		this.twocodepath = twocodepath;
	}

}