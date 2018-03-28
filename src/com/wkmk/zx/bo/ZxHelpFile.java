package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线答疑附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpFile extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fileid;
	private String filepath;//附件上传路径
	private String mp3path;//音频文件转码后的mp3存放路径
	private String mp4path;//视频文件转码后的mp4存放路径
	private String thumbpath;//拍照上传的缩略图路径
	private Long filesize;//文件大小
	private String filetype;//文件类型，1图片，2音频，3上传的视频
	private String convertstatus;//转码状态，0待转换，1转换成功，2转换失败【此处的音频和视频文件因为实时性要求比较高，所以调用转码进行实时转码，但无需等转码服务器给出转码结果，默认转码完成，后面转码服务器会自动更改状态】
	private String notifystatus;//与转码服务器的交互情况，通知状态 0未通知 1通知成功，2通知失败【通知失败的文档需要通过定时器重新与转码服务器交互】
	private Integer timelength;//录音时长（秒）
	private String createdate;//上传时间
	private String type;//1学生在线提问，2老师在线回答
	private Integer questionid;//type=1，关联提问
	private Integer answerid;//type=2，关联回复

	public ZxHelpFile(){
	}

 	public Integer getFileid() {
		return this.fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid=fileid;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath=filepath;
	}

	public String getMp3path() {
		return this.mp3path;
	}

	public void setMp3path(String mp3path) {
		this.mp3path=mp3path;
	}

	public String getThumbpath() {
		return this.thumbpath;
	}

	public void setThumbpath(String thumbpath) {
		this.thumbpath=thumbpath;
	}

	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize=filesize;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype=filetype;
	}

	public String getConvertstatus() {
		return this.convertstatus;
	}

	public void setConvertstatus(String convertstatus) {
		this.convertstatus=convertstatus;
	}

	public Integer getTimelength() {
		return this.timelength;
	}

	public void setTimelength(Integer timelength) {
		this.timelength=timelength;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public Integer getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(Integer answerid) {
		this.answerid=answerid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("fileid",getFileid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHelpFile))
		return false;
		ZxHelpFile castOther = (ZxHelpFile)other;
		return new EqualsBuilder().append(fileid, castOther.fileid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fileid).toHashCode();
	}

	public String getNotifystatus() {
		return notifystatus;
	}

	public void setNotifystatus(String notifystatus) {
		this.notifystatus = notifystatus;
	}

	public String getMp4path() {
		return mp4path;
	}

	public void setMp4path(String mp4path) {
		this.mp4path = mp4path;
	}

}