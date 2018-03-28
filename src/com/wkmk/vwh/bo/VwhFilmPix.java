package com.wkmk.vwh.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 视频库视频影片</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmPix extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer pixid;
	private String name;//影片名称
	private String srcpath;//影片原始路径
	private String flvpath;//转码后的视频路径
	private String imgpath;//视频截图路径
	private Integer imgwidth;//视频截图宽度，默认为0，截取原始视频宽度
	private Integer imgheight;//视频截图高度，默认为0
	private String second;//视频截取第几秒的画面，默认为0，转码工具默认截取第15秒画面
	private Long filesize;//影片大小
	private String fileext;//扩展名
	private String timelength;//视频播放总时长，格式：06:58，01:32:58
	private String resolution;//视频分辨率，格式：352x288
	private String convertstatus;//转码状态，0待转换，1转换成功，2转换失败
	private String notifystatus;//与转码服务器的交互情况，通知状态 0未通知 1通知成功，2通知失败【通知失败的文档需要通过定时器重新与转码服务器交互】
	private Integer orderindex;//排序
	private String createdate;//上传时间
	private String md5code;//文档唯一标示码
	private Integer filmid;//视频id
	private Integer unitid;
	//视频影片修改标示，当添加视频并关联影片时，此时为1，当管理员审核通过视频后修改为0，当用户再次修改并重新关联其他视频时，
	//新关联的视频影片标示为1，只有等管理员审核通过后才标示为0，这样的目的是为了方便记录用户修改的视频影片关联情况。
	private String updateflag;//0已审核，1修改影片内容(重新上传)，2修改影片属性值
	private String updatetime;

	public VwhFilmPix(){
	}

 	public Integer getPixid() {
		return this.pixid;
	}

	public void setPixid(Integer pixid) {
		this.pixid=pixid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getSrcpath() {
		return this.srcpath;
	}

	public void setSrcpath(String srcpath) {
		this.srcpath=srcpath;
	}

	public String getFlvpath() {
		return this.flvpath;
	}

	public void setFlvpath(String flvpath) {
		this.flvpath=flvpath;
	}

	public String getImgpath() {
		return this.imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath=imgpath;
	}

	public Integer getImgwidth() {
		return this.imgwidth;
	}

	public void setImgwidth(Integer imgwidth) {
		this.imgwidth=imgwidth;
	}

	public Integer getImgheight() {
		return this.imgheight;
	}

	public void setImgheight(Integer imgheight) {
		this.imgheight=imgheight;
	}

	public String getSecond() {
		return this.second;
	}

	public void setSecond(String second) {
		this.second=second;
	}

	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize=filesize;
	}

	public String getFileext() {
		return this.fileext;
	}

	public void setFileext(String fileext) {
		this.fileext=fileext;
	}

	public String getConvertstatus() {
		return this.convertstatus;
	}

	public void setConvertstatus(String convertstatus) {
		this.convertstatus=convertstatus;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("pixid",getPixid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof VwhFilmPix))
		return false;
		VwhFilmPix castOther = (VwhFilmPix)other;
		return new EqualsBuilder().append(pixid, castOther.pixid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(pixid).toHashCode();
	}

	public String getNotifystatus() {
		return notifystatus;
	}

	public void setNotifystatus(String notifystatus) {
		this.notifystatus = notifystatus;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(String updateflag) {
		this.updateflag = updateflag;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getTimelength() {
		return timelength;
	}

	public void setTimelength(String timelength) {
		this.timelength = timelength;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getMd5code() {
		return md5code;
	}

	public void setMd5code(String md5code) {
		this.md5code = md5code;
	}

}