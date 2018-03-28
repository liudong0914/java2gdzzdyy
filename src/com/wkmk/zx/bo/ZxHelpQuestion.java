package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 在线答疑提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer questionid;
	private String title;//提问标题
	private String descript;//提问内容
	private String createdate;//提问时间
	private String enddate;//有效结束时间（在当前时间没有回复，当前提问失效，无法被抢单），如果到了结束时间还没有被回答，列表显示“未答”，灰色字体，用户可在个人中心重新修改时间
	//【说明】无人回答的在线答疑，自动给改为免费答疑，方便供大家在线评价回复
	private Float money;//答疑提问定价（龙币）【改：在线支付状态默认为0，只有后台接口交互成功状态才为1】【无在线支付，只能从余额扣款，因为从余额扣款可以直接再返回余额，二是需要先支付再提交问题，无对应主键，同时页面微信支付接口返回支付成功（但微信不保障一定成功，需要根据后台交互接口确定），此时答疑已发布，导致支付不安全。除非默认发布草稿，支付成功交互后再发布】
	private String userip;//用户ip地址
	private SysUserInfo sysUserInfo;//提问人
	private Integer subjectid;//答疑所属学科
	private String subjectname;//学科名称，方便列表显示【学科和年级名称不会经常或轻易变动】
	private Integer gradeid;//答疑所属年级
	private String gradename;//年级名称，方便列表显示
	private Integer cxueduanid;//所属子学段，方便根据子学段查询
	private String status;//状态【前台只显示状态等于1的】，0待发布（保存草稿），1已发布，2回复被投诉（敷衍性回复，只为了挣龙币）,3回复被受理（老师受到处罚）【投诉被撤回状态恢复1】，9关闭答疑（对回复不满意或其他原因，关闭不显示）
	private String replystatus;//教师回复状态，默认0未回复，1已被抢单，2已回复，3回复被确认付款（当前答疑完成所有交易流程，免费答疑无需确认）
	private Integer hits;//被查看次数，可用于热门或排序
	private Integer sellcount;//当前答疑被卖次数，即销量

	public ZxHelpQuestion(){
	}

 	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money=money;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid=gradeid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("questionid",getQuestionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHelpQuestion))
		return false;
		ZxHelpQuestion castOther = (ZxHelpQuestion)other;
		return new EqualsBuilder().append(questionid, castOther.questionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(questionid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public Integer getCxueduanid() {
		return cxueduanid;
	}

	public void setCxueduanid(Integer cxueduanid) {
		this.cxueduanid = cxueduanid;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getReplystatus() {
		return replystatus;
	}

	public void setReplystatus(String replystatus) {
		this.replystatus = replystatus;
	}

	public Integer getSellcount() {
		return sellcount;
	}

	public void setSellcount(Integer sellcount) {
		this.sellcount = sellcount;
	}

}