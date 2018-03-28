package com.wkmk.tk.bo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.util.bo.BaseObject;

/**
 * <p>
 * Description: 题库试题信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer questionid;
	private Integer parentid;// 复合题型中，如英语学科完型填空题，有多个小题，父id就是完型填空的主观题id，默认都为0
	private String questionno;// 系统自动生成，题编号，按编号排序
	private String title;// 标题
	private String titlecontent;// 试题内容，即标题题干
	private String filepath;// 存放试题dsc源文件路径地址
	private Integer optionnum;// 客观题选项个数，程序自动计算填充；填空题默认为1表示只有一个填空，其他空格数量需要用户自己选择
	private String option1;// 最多选项不超过10个，默认只显示4个，可以手动触发新增选项个数，总不超过10个
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	private String option6;
	private String option7;
	private String option8;
	private String option9;
	private String option10;
	private String questiontype;// 题型，O=客观题，S=主观题，注意：统一用大写的英文字母表示
	private String doctype;// 题干和答案呈现类型，默认0为普通文本，1为通过doc批量导入生成的图片
	// 有标准答案填空题也是自动判卷，但英语可能有唯一、唯二或唯三的答案，此时正确答案填写形式如：yes【或】ok【或】right等用中括号字符串“【或】”隔开，程序自动匹配满足其中一个即为正常。
	private String rightans;// 正确答案，单选题：A、B；多选题：AB、ACD；判断题：1表示判断题是对的，0表示判断题是错的；主观题为参考答案。填空题多个空格时，参考答案用“【】”隔开
	private String isrightans;// 1是否是固定标准答案，如选择题、判断题、只有唯一答案的填空题等，0表示不是标准答案
	private String difficult;// 难易程度，1=容易，2=较易，3=一般，4=较难，5=很难，改：用英文字母表示【Constants.difficult】
	// private Integer score;//参考分值，组卷时可以直接用此参考分值，也可以自行修改
	private String score;// 参考分值，因为填空题有多个空格时，每个空格有自己独立的分值，用“【】”隔开
	private String cretatdate;// 创建时间
	private String updatetime;
	private String descript;// 答案解析
	private String descriptpath;// dsc格式试题解析文件存放路径
	private String status;// 状态，1正常 2.禁用
	private String area;// 地区，当前题库参考的是哪个地区的考题，比如：北京
	private String theyear;// 年份，当前题库参考的是哪年的考题，比如：2014
	private String authorname;// 创建者名称
	private Integer authorid;// 创建者id
	private TkQuestionsType tkQuestionsType;// 题型分类
	private Integer subjectid;
	private Integer gradeid;// 学科和年级分别用id记录
	private Integer unitid;
	private String tag;// 试题标签，方便根据关键字快速搜索题组卷
	private String filmtwocodepath;// 试题关联微课的二维码
	private String similartwocodepath;// 举一反三的二维码

	private String typeName;
	private String papercartSocre;
	private List childrenquestions;
	private int errorNum;
	private int rightNum;
	private int papecontentid;

	public TkQuestionsInfo() {
	}

	public void setItem(String itemname, String value) {
		if ("题号".equals(itemname)) {
			setQuestionno(value);
		} else if ("题干".equals(itemname)) {
			setTitle(value);
		} else if ("选A项".equals(itemname)) {
			setOption1(value);
		} else if ("选B项".equals(itemname)) {
			setOption2(value);
		} else if ("选C项".equals(itemname)) {
			setOption3(value);
		} else if ("选D项".equals(itemname)) {
			setOption4(value);
		} else if ("选E项".equals(itemname)) {
			setOption5(value);
		} else if ("选F项".equals(itemname)) {
			setOption6(value);
		} else if ("选G项".equals(itemname)) {
			setOption7(value);
		} else if ("选H项".equals(itemname)) {
			setOption8(value);
		} else if ("选I项".equals(itemname)) {
			setOption9(value);
		} else if ("选J项".equals(itemname)) {
			setOption10(value);
		} else if ("答案".equals(itemname)) {
			if ("对".equals(value)) {
				setRightans("1");
			} else if ("错".equals(value)) {
				setRightans("0");
			} else {
				setRightans(value);
			}
		} else if ("解析".equals(itemname)) {
			setDescript(value);
		} else if ("地区".equals(itemname)) {
			setArea(value);
		} else if ("年份".equals(itemname)) {
			setTheyear(value);
		} else if ("分值".equals(itemname)) {
			setScore(value);
		} else if ("难度".equals(itemname)) {
			setDifficult(value);
		}
	}

	public void setOptionContent(int num, String value) {
		if (num == 1) {
            setOption1(value);
		}else if(num==2){
			setOption2(value);
		}else if(num==3){
			setOption3(value);
		}else if(num==4){
			setOption4(value);
		}else if(num==5){
			setOption5(value);
		}else if(num==6){
			setOption6(value);
		}else if(num==7){
			setOption7(value);
		}else if(num==8){
			setOption8(value);
		}else if(num==9){
			setOption9(value);
		}else if(num==10){
			setOption10(value);
		}
	}

	public String getOptionNo(int num) {
		String optionno = "A";
		if (num == 2) {
			optionno = "B";
		} else if (num == 3) {
			optionno = "C";
		} else if (num == 4) {
			optionno = "D";
		} else if (num == 5) {
			optionno = "E";
		} else if (num == 6) {
			optionno = "F";
		} else if (num == 7) {
			optionno = "G";
		} else if (num == 8) {
			optionno = "H";
		} else if (num == 9) {
			optionno = "I";
		} else if (num == 10) {
			optionno = "J";
		}
		return optionno;
	}

	public String getOptionValue(int num) {
		String optionvalue = getOption1();
		if (num == 2) {
			optionvalue = getOption2();
		} else if (num == 3) {
			optionvalue = getOption3();
		} else if (num == 4) {
			optionvalue = getOption4();
		} else if (num == 5) {
			optionvalue = getOption5();
		} else if (num == 6) {
			optionvalue = getOption6();
		} else if (num == 7) {
			optionvalue = getOption7();
		} else if (num == 8) {
			optionvalue = getOption8();
		} else if (num == 9) {
			optionvalue = getOption9();
		} else if (num == 10) {
			optionvalue = getOption10();
		}
		return optionvalue;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid = questionid;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getQuestionno() {
		return this.questionno;
	}

	public void setQuestionno(String questionno) {
		this.questionno = questionno;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOptionnum() {
		return this.optionnum;
	}

	public void setOptionnum(Integer optionnum) {
		this.optionnum = optionnum;
	}

	public String getOption1() {
		return this.option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return this.option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return this.option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return this.option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return this.option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	public String getOption6() {
		return this.option6;
	}

	public void setOption6(String option6) {
		this.option6 = option6;
	}

	public String getOption7() {
		return this.option7;
	}

	public void setOption7(String option7) {
		this.option7 = option7;
	}

	public String getOption8() {
		return this.option8;
	}

	public void setOption8(String option8) {
		this.option8 = option8;
	}

	public String getOption9() {
		return this.option9;
	}

	public void setOption9(String option9) {
		this.option9 = option9;
	}

	public String getOption10() {
		return this.option10;
	}

	public void setOption10(String option10) {
		this.option10 = option10;
	}

	public String getQuestiontype() {
		return this.questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getRightans() {
		return this.rightans;
	}

	public void setRightans(String rightans) {
		this.rightans = rightans;
	}

	public String getDifficult() {
		return this.difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCretatdate() {
		return this.cretatdate;
	}

	public void setCretatdate(String cretatdate) {
		this.cretatdate = cretatdate;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTheyear() {
		return this.theyear;
	}

	public void setTheyear(String theyear) {
		this.theyear = theyear;
	}

	public String getAuthorname() {
		return this.authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public Integer getAuthorid() {
		return this.authorid;
	}

	public void setAuthorid(Integer authorid) {
		this.authorid = authorid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String toString() {
		return new ToStringBuilder(this).append("questionid", getQuestionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsInfo))
			return false;
		TkQuestionsInfo castOther = (TkQuestionsInfo) other;
		return new EqualsBuilder().append(questionid, castOther.questionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(questionid).toHashCode();
	}

	public TkQuestionsType getTkQuestionsType() {
		return tkQuestionsType;
	}

	public void setTkQuestionsType(TkQuestionsType tkQuestionsType) {
		this.tkQuestionsType = tkQuestionsType;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public Integer getGradeid() {
		return gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	public String getTitlecontent() {
		return titlecontent;
	}

	public void setTitlecontent(String titlecontent) {
		this.titlecontent = titlecontent;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescriptpath() {
		return descriptpath;
	}

	public void setDescriptpath(String descriptpath) {
		this.descriptpath = descriptpath;
	}

	public String getIsrightans() {
		return isrightans;
	}

	public void setIsrightans(String isrightans) {
		this.isrightans = isrightans;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getPapercartSocre() {
		return papercartSocre;
	}

	public void setPapercartSocre(String papercartSocre) {
		this.papercartSocre = papercartSocre;
	}

	public List getChildrenquestions() {
		return childrenquestions;
	}

	public void setChildrenquestions(List childrenquestions) {
		this.childrenquestions = childrenquestions;
	}

	public int getPapecontentid() {
		return papecontentid;
	}

	public void setPapecontentid(int papecontentid) {
		this.papecontentid = papecontentid;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public String getRightansName() {
		if ("C".equals(this.tkQuestionsType.getType())) {
			if ("1".equals(this.rightans)) {
				return "对";
			} else {
				return "错";
			}
		} else if ("E".equals(this.tkQuestionsType.getType())) {// 填空题
			StringBuffer result = new StringBuffer();
			String[] answers = this.rightans.split("【】");
			for (int i = 0, size = answers.length; i < size; i++) {
				result.append("第").append(i + 1).append("空答案：").append(answers[i]).append("<br/>");
			}
			return result.toString();
		} else {
			return this.rightans;
		}
	}

	/**
	 * 判断作答是否正确
	 * 
	 * @param rightanswer
	 *            正确答案
	 * @param answer
	 *            作答答案
	 * @return true表示作答正确，false表示作答错误
	 */
	public boolean getIsRight(String rightanswer, String answer) {
		if (rightanswer != null && !"".equals(rightanswer) && answer != null && !"".equals(answer)) {
			String[] rights = rightanswer.split("【或】");
			boolean result = false;
			for (int i = 0, size = rights.length; i < size; i++) {
				if (rights[i].equals(answer)) {
					result = true;
					break;
				}
			}
			return result;
		} else {
			return false;
		}
	}

	public Float getTotalScore() {
		Float totalScore = 0.0F;
		if (this.score.indexOf("【】") != -1) {
			String[] scores = this.score.split("【】");
			for (int i = 0, size = scores.length; i < size; i++) {
				totalScore = totalScore + Float.valueOf(scores[i]);
			}
		} else {
			totalScore = Float.valueOf(this.score);
		}

		return totalScore;
	}

	public String getFilmtwocodepath() {
		return filmtwocodepath;
	}

	public void setFilmtwocodepath(String filmtwocodepath) {
		this.filmtwocodepath = filmtwocodepath;
	}

	public String getSimilartwocodepath() {
		return similartwocodepath;
	}

	public void setSimilartwocodepath(String similartwocodepath) {
		this.similartwocodepath = similartwocodepath;
	}
}