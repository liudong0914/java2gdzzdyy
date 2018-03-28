package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 课程信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer courseid;
	private String title;//课程名称
	private String courseno;//课程编号，前台展示排序用
	private String coursetypeid;//课程所属大分类，如：1院校企业、2退役军人、3医护行业
	private String sketch;//缩略图
	private String status;//状态，0私有，1审核通过，2发布待审核，3审核不通过，4禁用
	private Float price;//总价，展示用
	private String classhour;//当前课程的总课时数，展示用
	private Integer hits;//查看次数
	private Integer collects;//收藏次数
	private Integer studys;//学习次数，课程下所有课程视频被点击学习总次数
	private Float score;//每次评价打分，求平均分
	private Integer scoreusers;//评分用户数
	private String objectives;//课程目标，
	private String htmlcontent;//课程详细描述
	private String createdate;//创建时间
	private String startdate;//课程开始时间
	private String enddate;//课程结束时间
	private SysUserInfo sysUserInfo;//创建课程教师，可查看教师头像、昵称、职称、简介等
	private Integer unitid;//单位id
	private String note;//价格注明文字

	public EduCourseInfo(){
	}

 	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getCourseno() {
		return this.courseno;
	}

	public void setCourseno(String courseno) {
		this.courseno=courseno;
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

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public String getClasshour() {
		return this.classhour;
	}

	public void setClasshour(String classhour) {
		this.classhour=classhour;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits=hits;
	}

	public Integer getCollects() {
		return this.collects;
	}

	public void setCollects(Integer collects) {
		this.collects=collects;
	}

	public Integer getStudys() {
		return this.studys;
	}

	public void setStudys(Integer studys) {
		this.studys=studys;
	}

	public String getHtmlcontent() {
		return this.htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent=htmlcontent;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate=startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("courseid",getCourseid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseInfo))
		return false;
		EduCourseInfo castOther = (EduCourseInfo)other;
		return new EqualsBuilder().append(courseid, castOther.courseid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(courseid).toHashCode();
	}

	public String getCoursetypeid() {
		return coursetypeid;
	}

	public void setCoursetypeid(String coursetypeid) {
		this.coursetypeid = coursetypeid;
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getScoreusers() {
		return scoreusers;
	}

	public void setScoreusers(Integer scoreusers) {
		this.scoreusers = scoreusers;
	}

}